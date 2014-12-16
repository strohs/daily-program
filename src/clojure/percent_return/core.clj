(ns percent-return.core
  (:use [clojure.tools.trace])
  (:gen-class))

(def buy-price 209.00)

(defn percent-return [ buy strike bid ]
  (* (/ (+ (- strike buy) bid) buy ) 100))

(defn toString [buy strike bid]
  (format "BUY:%3.2f  STRIKE:%3.2f  BID:%3.2f      PR:%3.2f" buy strike bid (percent-return buy strike bid) ) )
(toString buy-price 210.0 10.40)
(toString buy-price 212.50 8.55)
(toString buy-price 215.0 9.5)
(toString buy-price 217.50 7.85)
(toString buy-price 220.0 6.4)
(toString buy-price 222.50 5.25)
(toString buy-price 225.0 3.85)
(toString buy-price 227.50 2.93)
(toString buy-price 230.0 2.25)
(toString buy-price 232.50 1.66)


