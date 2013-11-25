(ns change-ringing.core
  (require
   [overtone.live :refer :all]
   [overtone.inst.sampled-piano :refer :all]))


(sampled-piano (note :C4))

(defn minor-notes [base-note]
  (let [offsets [0 2 2 1 2 2]]
    (->> offsets
         (reductions +)
         (map #(- (note base-note) %)))))

(def metro (metronome 240))
(defn play-sequence
  ([metro notes] (play-sequence metro (metro) notes))
  ([metro beat notes]
  (let [intervals (->> beat
                       (iterate #(+ 1 %))
                       (map metro))]
    (doseq [[note time] (map vector notes intervals)]
      (at time (sampled-piano note))))))


  (defn permute [notes perm]
    (->> perm
         (map #(- % 1))
         ( map #(nth notes %))))

(def identity [1 2 3 4 5 6])
(def handstroke-hunt [2 1 4 3 6 5])
(def backstroke-hunt [1 3 2 5 4 6])
(def seconds [1 2 4 3 6 5])

(defmacro defmethod [name sequence]
  `(def ~name (->> ~sequence
                   (concat [identity identity])
                   (reductions permute))))

(defmethod plain-hunt
  (->> [handstroke-hunt backstroke-hunt]
       (cycle)
       (take 12)))

(defmethod plain-bob
  (->> (concat (take 11 (cycle [handstroke-hunt backstroke-hunt]))
               [seconds])
       (cycle)
       (take 60)))

(defn play-method
  ([metro method notes] (play-method metro (metro) method notes))
  ([metro beat method notes]
      (let
          [hs-change (first method)
           bs-change (second method)
           hs-notes (permute notes hs-change)
           bs-notes (permute notes bs-change)]
        ;; Play the notes
        (play-sequence metro beat (concat hs-notes bs-notes))
        ;; Repeat in 13 beats (with a handstroke gap)
        (apply-at
         (metro (+ beat 13))
         play-method metro (+ beat 13) (drop 2 method) notes []))))

(play-method metro (take 14 plain-hunt) (minor-notes :C4))
(play-method metro plain-bob (minor-notes :C4))
(play-method metro plain-hunt (minor-notes :C4))
(stop)
