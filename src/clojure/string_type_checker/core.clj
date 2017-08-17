(ns string-type-checker.core)

;;; check the type of s String to see if it is an Integer,Float, or Date...else its considered a string

(defn type-checker
  "check the type of s String to see if it is an Integer,Float, or Date...else its considered a string"
  [s]
  (cond
    (re-matches #"[+-]?\d+" s) "int"
    (re-matches #"[+-]?\d+\.\d+" s) "float"
    (re-matches #"\d+-\d+-\d+" s) "date"
    :else "string"))


