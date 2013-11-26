(ns change-ringing.core)
(js/console.log (+ 3 2))

(def api-key "b1de6c92bba14d96a9fd957696c8cc4e")

(def sound-cache (js/Object.))

(set! (.-apiKey js/freesound) api-key)
(defn get-sound [name id]
  (. js/freesound
     (get_sound id
                (fn [sound]
                  (let [snd (-> sound
                                (aget "preview-hq-mp3")
                                (js/Audio.))]
                    (aset sound-cache name sound))))))

;; (get-sound "C4" 143214)


