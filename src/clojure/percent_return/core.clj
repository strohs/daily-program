(ns percent-return.core
  (:use [clojure.tools.trace])
  (:gen-class))

(def buy-price 118.75)

(defn percent-return [ buy strike bid ]
  (* (/ (+ (- strike buy) bid) buy ) 100))

(defn toString [buy strike bid]
  (format "BUY:%3.2f  STRIKE:%3.2f  BID:%3.2f      PR:%3.2f" buy strike bid (percent-return buy strike bid) ) )
(toString buy-price 125.0 0.60)
(toString buy-price 120.0 4.30)
(toString buy-price 115.0 9.20)
;(toString buy-price 80.0 3.90)
;(toString buy-price 77.5 5.90)
;(toString buy-price 75.0 8.10)


