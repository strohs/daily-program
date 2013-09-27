(ns cannon-timing
  (:gen-class))

(def cannon (ref {:firing? false :fired-count 0}))
(def propellant (ref {:loaded? false}))
(def shell (ref {:loaded? false}))

(defn debug [msg]
  (println msg (apply str (repeat (- 35 (count msg)) \space)))
  (flush))


(defn load-propellant
  [time-ms]
  (dosync
    (debug "loading propellant")
    (Thread/sleep time-ms)
    (debug "propellant loaded")
    (alter propellant assoc :loaded? true)))

(defn load-shell
  [time-ms]
  (if (not (:loaded? @shell))
    (do
      (Thread/sleep time-ms)
      (println "shell loaded")
      (swap! shell assoc :loaded? true))
    (:loaded? @shell)))




;(defn simulate
;  [shell-time propel-time fire-time]
;  (loop [pf (future (load-propellant (* propel-time 1000)))
;         sf (future (load-shell (* shell-time 1000)))]
;    (if (and @pf @sf)
;      (fire fire-time))
;    (recur (future (load-propellant (* propel-time 1000)))
;      (future (load-shell (* shell-time 1000))))))

;(defn fire [shellt propt firet]
;  (let [shell (future (load-shell (* shell-time 1000)))
;        prop (future (load-propellant (* shell-time 1000)))]
;    ))

;(defn -main [simt shellt propt firet]
;  (def f (future (simulate shellt propt firet)))
;  (deref f (* simt 1000) :simulation-done)
;  (println "Count:" (:fired-count @cannon))
;  (future-cancel f))