(ns webchat.core_full
  (:require
    [re-com.core :refer [v-box label h-box input-text button box]]
    [cljs.reader :refer [read-string]]
    [reagent.core :refer [atom render]]
    [reagent.ratom :refer-macros [reaction]]))

(enable-console-print!)


(defonce my-id (rand-int 10000))

; state
(defonce app-state (atom {:messages [{:text   "Hello, Bob"
                                      :author 1}
                                     {:text   "Hello, dude"
                                      :author 2}]
                          :users    {my-id {:name "YOU"}
                                     1     {:name "Marty"}
                                     2     {:name "Anon"}}
                          :input    ""}))


(defonce socket
         (let [sock (js/WebSocket. "ws://localhost:9899/ws")]
           (doto sock
             (aset "onopen" #(.send sock (str {:type :user
                                               :data (select-keys (:users @app-state) [my-id])})))
             (aset "onclose" #(prn "close socket"))
             (aset "onmessage"
                   (fn [evt]
                     (let [data (.-data evt)
                           edn (read-string data)
                           type (:type edn)
                           body (:data edn)]
                       (prn "TYPE " type " BODY" body)
                       (case type
                         :user (swap! app-state
                                      #(update % :users merge body))
                         :msg (swap! app-state
                                     #(update % :messages conj body))
                         :default (prn "Unknown " type))))))))


(defn message [i]
  (let [msg (reaction (get-in @app-state [:messages i]))
        user (reaction (get-in @app-state [:users (:author @msg)]))]
    (fn []
      (let [{:keys [text]} @msg]
        [label :label (str (:name @user) ": " text)]))))


(defn message-list []
  (let [count (reaction (count (:messages @app-state)))]
    [v-box
     :size "1"
     :children
     (for [i (range @count)]
       [message i])]))


(defn send-msg! []
  (swap! app-state
         (fn [state]
           (let [input (:input state)]
             (.send socket (str {:type :msg
                                 :data {:text   input
                                        :author my-id}}))
             (-> state
                 ;(update :messages conj {:text   input
                 ;                        :author my-id})
                 (assoc :input ""))
             ))))

(defn send-msg []
  (let [input (reaction (:input @app-state))]
    (fn []
      [h-box
       :gap "2px"
       :children
       [[box
         :size "1"
         :child [input-text
                 :width "100%"
                 :model input
                 :change-on-blur? false
                 :on-change #(swap! app-state assoc :input %)]]
        [button
         :label "Send"
         :on-click send-msg!]]])))

(defn your-message []
  (let [input (reaction (:input @app-state))]
    [label :label (str "You entered: " @input)]))


(defn user [i]
  (let [user (reaction (get-in @app-state [:users i]))]
    (fn []
      [label :label (str "U: " (:name @user))])))


(defn send-name! [name-path name]
  ;(swap! app-state assoc-in name-path name)
  (.send socket {:type :user
                 :data {my-id {:name name}}}))

(defn your-name []
  (let [name-path [:users my-id :name]
        name (reaction (get-in @app-state name-path))]
    (fn []
      [box
       :align-self :end
       :child [input-text
               :width "100%"
               :model name
               :change-on-blur? false
               :on-change #(send-name! name-path %)]])))

(defn user-list []
  (let [users (reaction (keys (:users @app-state)))]
    (fn []
      [v-box
       :size "1"
       :children
       [[v-box
         :size "1"
         :children (for [i @users]
                     [user i])]
        [your-name]]])))

(defn chat-window []
  [v-box
   :size "5"
   :children [[message-list]
              [your-message]
              [send-msg]]])

(defn chat []
  [h-box
   :height "100vh"
   :gap "10px"
   :size "1"
   :children
   [[chat-window]
    [user-list]]])

(defn ^:export run []
  (render
    [chat]
    (js/document.getElementById "app")))
