(ns barbershop.core)

(def empty-seats (ref 3))
(def barber (agent 0))

(defn debug [msg n]
  (println msg (apply str (repeat (- 35 (count msg)) \space)) n)
  (flush))

(defn cut-hair [tally n]
  (dosync (commute empty-seats inc))
  (debug "(b) cutting hair of customer" n)
  (Thread/sleep (+ 100 (rand-int 600)))
  (debug "(b) done cutting hair of customer" n)
  (inc tally))

(defn enter-the-shop [n]
  (debug "(c) entering the shop" n)
  (when-not (dosync
              (when (pos? @empty-seats)
                (alter empty-seats dec)
                (send-off barber cut-hair n)))
    (debug "(s) turning away customer" n)))


(defn -main [& args]
  ;(dosync (ref-set empty-seats 3))
  ;(restart-agent barber 0)
  (doseq [customer (range 1 20)]
    (Thread/sleep (+ 100 (rand-int 200)))
    (future (enter-the-shop customer)))

  (Thread/sleep 2000)
  (println "(!) " @barber "customers got haircuts today"))