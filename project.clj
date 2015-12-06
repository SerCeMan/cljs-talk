(defproject cljs-webchat "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.170"]
                 [org.clojure/core.async "0.2.374"]
                 [re-frame "0.5.0"]
                 [figwheel "0.5.0-2"]
                 [com.cemerick/piggieback "0.2.1"]
                 [re-com "0.7.0"]
                 [reagent "0.5.1"]


                 [hiccup "1.0.5"]
                 [ring/ring-core "1.4.0"]
                 [ring-server "0.4.0"]
                 [compojure "1.4.0"]
                 [http-kit "2.1.19"]]

  :plugins [[lein-cljsbuild "1.1.1"]
            [lein-figwheel "0.5.0-2"]]

  :hooks [leiningen.cljsbuild]

  :source-paths ["cljs" "clj" "devsrc"]

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]

  :profiles {:dev {:cljsbuild
                   {:builds {:client {:source-paths ["devsrc"]}}}}}


  :cljsbuild {:builds
              {:client {:source-paths ["cljs"]

                        :compiler     {:main                 webchat.dev
                                       :asset-path           "js/compiled/out"
                                       :output-to            "resources/public/js/compiled/cljs_webchat.js"
                                       :output-dir           "resources/public/js/compiled/out"
                                       :optimizations        :none
                                       :source-map-timestamp true}}}}


  :figwheel {:nrepl-port 7888
             :css-dirs   ["resources/public/css"]})
