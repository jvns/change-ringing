(ns change-ringing.core
  (:require [cljs.core.async :as async
             :refer [timeout dropping-buffer chan]])
  (:require-macros [cljs.core.async.macros :refer [go]]))

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
                    (aset sound-cache name snd))))))

(get-sound "note" 148501)

(def sound-chan (chan (dropping-buffer 1000)))

(defn play-timer
  ([] (play-timer 250))
  ([interval]
     (go (while true
           (js/console.log sound-cache)
           (when-let [name (<! sound-chan)]
             (when-let [sound (aget sound-cache name)]
               (.play sound)
               (.log js/console interval (<! (timeout interval)))
               (aset sound "currentTime" 0)))
           (.log js/console interval (<! (timeout interval)))
           ))))

(defn send-sounds []
  (go (dotimes [_ 800]
        (>! sound-chan "note"))))

(play-timer)
(send-sounds)


