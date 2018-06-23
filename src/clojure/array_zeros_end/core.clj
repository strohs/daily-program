(ns array-zeros-end.core)

;;; given an array (or collection) of integers, move all zeroes in the array to the end of the array (or collection)

(def arr1 [1 2 0 3 0 4 0 5 0 6 0 7 8 9])
(def arr2 [1 2 3 0 4 5 0 0])
(def arr4 [1 2 3])
(def arr3 [0])

(defn move-zeros
  "move any zeros in the collection to the end of the collection. don't use a sort, don't change
  the order of non-zero numbers. The algorithm below iterates through the collection one time but
  uses two temporary vectors to hold the non-zero numbers and the zero numbers, and then appends the
  zeros onto the end of the non-zero numbers once it is finished"
  [col]
  (loop [col col
         nums []
         zeros []]
    (if (empty? col)
      (into nums zeros)
      (if (zero? (first col))
        (recur (rest col) nums (conj zeros (first col)))
        (recur (rest col) (conj nums (first col)) zeros)))))






