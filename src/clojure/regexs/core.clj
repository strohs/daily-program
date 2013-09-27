(ns regexs.core
  (:require [clojure.string :as string])
  (:require [clj-time.core :as ct])
  (:require [clj-time.format :as tf])
  (:require [clojure.java.io :as io])
  (:gen-class))

;;(def lines (line-seq (io/reader "src/clojure/regexs/oxpress-activity.txt")))
(def lines (with-open [rdr (io/reader "/Users/cliff/IdeaProjects/daily-program/src/clojure/regexs/oxpress-activity.txt")]
  (reduce conj [] (line-seq rdr))) )

;1/20/2012 3:55:31 PM
(def custom-formatter (tf/formatter "MM/dd/yyyy hh:mm:ss aaa"))

;Symbol, Description, Action, Quantity, Price, Commission, Reg Fees, Date, TransactionID, Order Number, Transaction Type ID, Total Cost
(def tstr "ROST^^120317C00055000,ROST Mar12 55 Call,Sell To Open,20,2.2,30,0.51,3/14/2012 9:51:29 AM,240668244,117646933,5,4369.49")
;(re-seq #"[0-9]+" "abs123def345ghi567")
;(re-find #"([-+]?[0-9]+)/([0-9]+)" "22/7")
;(let [[a b c] (re-matches #"([-+]?[0-9]+)/([0-9]+)" "22/7")]
;  [a b c])

(defn map-line [row]
  (zipmap [:symbol :desc :action :quantity :price :commission :reg-fee :date :trans-id :order-num :ttype-id :total-cost] row))

(def recs (map #(map-line (string/split % #",")) lines))

;sum all the total costs
(reduce + (map #(BigDecimal. (:total-cost %)) recs))
