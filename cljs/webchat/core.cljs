(ns webchat.core
  (:require
    [re-com.core :refer [v-box label h-box input-text button box]]
    [cljs.reader :refer [read-string]]
    [reagent.core :refer [atom render]]
    [reagent.ratom :refer-macros [reaction]]))

(enable-console-print!)


; state
(defonce app-state (atom {}))


(defn chat []
  [:div "Hello"])

; render
(defn ^:export run []
  (render
    [chat]
    (.getElementById js/document "app")))
