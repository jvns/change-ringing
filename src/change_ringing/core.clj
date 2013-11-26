(ns change-ringing.core
  (require
   [overtone.live :refer :all]
  ;; [overtone.inst.sampled-piano :refer :all]
   [change-ringing.methods :refer :all]))


(sampled-piano (note :C4))

(def metro (metronome 240))
(defn play-sequence
  ([metro notes] (play-sequence metro (metro) notes))
  ([metro beat notes]
  (let [intervals (->> beat
                       (iterate #(+ 1 %))
                       (map metro))]
    (doseq [[note time] (map vector notes intervals)]
      (at time (sampled-piano note))))))



(defn play-method
  ([metro method notes] (play-method metro (metro) method notes))
  ([metro beat method notes]
     (when (> (count method) 0)
      (let
          [hs-notes (permute notes (first method))
           bs-notes (permute notes (second method))]
        ;; Play the notes
        (play-sequence metro beat (concat hs-notes bs-notes))
        ;; Repeat in 13 beats (with a handstroke gap)
        (apply-at
         (metro (+ beat 13))
         play-method metro (+ beat 13) (drop 2 method) notes [])))))

(play-method metro (take 14 plain-hunt) (minor-notes :C4))
(play-method metro plain-bob (minor-notes :C4))
(play-method metro plain-hunt (minor-notes :C4))
(stop)
