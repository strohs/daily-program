(ns percent-return.core
  (:use [clojure.tools.trace])
  (:gen-class))

(defn percent-return [ buy strike bid ]
  (* (/ (+ (- strike buy) bid) buy ) 100))

(defn toString [buy strike bid]
  (format "BUY:%3.2f  STRIKE:%3.2f  BID:%3.2f      PR:%3.2f" buy strike bid (percent-return buy strike bid) ) )
(toString 78.98 87.50 0.45)
(toString 78.98 85.0 1.05)
(toString 78.98 82.5 2.25)
(toString 78.98 80.0 3.90)
(toString 78.98 77.5 5.90)
(toString 78.98 75.0 8.10)

(.indexOf "fooby" "b")



