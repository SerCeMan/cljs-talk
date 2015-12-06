(ns main
  (:gen-class)
  (:require [clojure.edn :refer [read-string]])
  (:use org.httpkit.server
        (compojure [core :only [defroutes GET POST]]
                   [route :only [files not-found]]
                   [handler :only [site]]
                   [route :only [not-found]])))


(def clients (atom {}))

(defn mesg-received [channel msg]
  (prn "mesg received" msg)
  (let [data (read-string msg)]
    (case (:type data)
      :msg (doseq [client (keys @clients)]
             (send! client msg))
      :user (do
              (swap! clients assoc channel (:data data))
              (doseq [client (keys @clients)]
                (send! client msg))))))

(defn init [client]
  (send! client (str {:data (into {} (vals @clients))
                      :type :user})))

(defn chat-handler [req]
  (with-channel req channel
                (prn channel "connected")
                (swap! clients assoc channel {})
                (init channel)
                (on-receive channel (partial mesg-received channel))
                (on-close channel (fn [status]
                                    (swap! clients dissoc channel)
                                    (prn channel "closed, status" status)))))

(defroutes chartrootm
           (GET "/ws" [] chat-handler)
           (not-found "<p>Page not found.</p>"))


(comment

  (def stop
    (run-server #'chartrootm {:port 9899}))

  (stop)

  )