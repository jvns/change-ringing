(defproject change-ringing "0.1.0-SNAPSHOT"
  :description "Change ringing game"
  :url "http://github.com/jvns/change-ringing"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [overtone "0.9.0-SNAPSHOT"]
                 [org.clojure/clojurescript "0.0-1934"
                  :exclusions [org.apache.ant/ant]]]
  :source-paths ["src"]
  :plugins [[lein-cljsbuild "0.3.4"]]
  :cljsbuild
  {:builds
   [{:id "dev"
     :source-paths ["src"]
     :compiler {:output-to "out/browser/main.js"
                :output-dir "out/browser"
                :optimizations :none}}]})
