(ns barbershop.async
  (:refer-clojure :exclude [map reduce into partition partition-by take merge])
  (:require [clojure.core.async :refer :all :as async]
            [clojure.pprint :refer [pprint]]))

;; number of "waiting" seats for customers
(def empty-seats-chan (chan (dropping-buffer 3)))


(defn create-log-chan []
  (let [c (chan)]
    (thread
      (loop []
        (when-let [v (<!! c)]
          (println v)
          (recur)))
      (println "Log Closed"))
    c))

;;channel for logging status messages
(def log-chan (create-log-chan))

(defn log-cust
  "log customer status message to the log channel"
  [msg n]
  (>!! log-chan (str msg (apply str (repeat (- 45 (count msg)) \space)) n)))

(defn cut-hair
  "simulates cutting hair by waiting up to wait-ms"
  [wait-ms]
  (Thread/sleep (+ 100 (rand-int wait-ms))))

(defn start-barber
  "listens to channel, ch, for incoming customers, simulates a hair cut when a customer is put on the channel.
  returns the number of hair cuts given"
  [id ch]
  (thread
    (let [tally (atom 0)]
      (loop []
        (when-let [v (<!! ch)]
          (log-cust (str "(b" id ") cutting hair of customer") v)
          (cut-hair 800)
          (swap! tally inc)
          (log-cust (str "(b" id ") done cutting hair of customer") v)
          (recur)))
      @tally)))

(defn enter-the-shop [n]
  (log-cust "(c) entering the shop" n)
  (>!! empty-seats-chan n))


(defn -main [& args]
  ;(dosync (ref-set empty-seats 3))

  (let [b1 (start-barber 1 empty-seats-chan)
        b2 (start-barber 2 empty-seats-chan)]               ;start the barbers
    (doseq [customer (range 1 50)]
      (Thread/sleep (+ 100 (rand-int 200)))
      (enter-the-shop customer))
    (log-cust "(!) all customers have entered the shop" 0)
    (Thread/sleep 2000)
    (close! empty-seats-chan)
    (println "(b1) " (<!! b1) "customers got haircuts today")
    (println "(b2) " (<!! b2) "customers got haircuts today")))