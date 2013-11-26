(ns change-ringing.methods)

(defn minor-notes [base-note]
  (let [offsets [0 2 2 1 2 2]]
    (->> offsets
         (reductions +)
         (map #(- base-note %)))))

(def identity [1 2 3 4 5 6])
(def handstroke-hunt [2 1 4 3 6 5])
(def backstroke-hunt [1 3 2 5 4 6])
(def seconds [1 2 4 3 6 5])

(defn permute [notes perm]
  (->> perm
       (map #(- % 1))
       ( map #(nth notes %))))

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

