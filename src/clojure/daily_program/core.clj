(ns daily-program.core
  (:require [clj-time.core :as ct])
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "The current time is " (ct/now)))
