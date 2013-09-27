(ns string-type-checker.core)


(defn type-checker
  [s]
  (cond
    (re-matches #"[+-]?\d+" s) "int"
    (re-matches #"[+-]?\d+\.\d+" s) "float"
    (re-matches #"\d+-\d+-\d+" s) "date"
    :else "string"))


