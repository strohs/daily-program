(ns daily-program.has77)

;;;; Given an array of ints, return true if the array contains two 7's next to each other, or there are two 7's
;;;; separated by one element, such as with {7, 1, 7}.


(defn has77? [[p1 p2 p3 :as v]]
  (cond
    (nil? p1) false
    (or (= p1 p2 7) (= p1 p3 7) (= p2 p3 7)) true
    :else (recur (rest v))))