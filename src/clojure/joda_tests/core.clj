(ns joda-tests.core
  (:require [clj-time.core :as ct])
  (:gen-class))

;;; testing clojures JODA time library

(defn -main [& args]
  (println "now is: " (ct/now))
  (println "epoch is: " (ct/epoch))
  (println "in eastern time " (ct/from-time-zone (ct/now) (ct/time-zone-for-offset -5))))
