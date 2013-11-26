(ns change-ringing.core)
(js/console.log (+ 3 2))

(def api-key "b1de6c92bba14d96a9fd957696c8cc4e")

(set! (.-apiKey js/freesound) api-key)
(. js/freesound
   (get_sound 148432
              (fn [sound] (-> sound
                          (aget "preview-hq-mp3")
                          (js/Audio.)
                          (.play)))))

