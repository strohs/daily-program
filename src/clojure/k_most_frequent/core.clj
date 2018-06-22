(ns k-most-frequent.core)

;;; given an array of numbers, find and return the 'k' most frequent numbers in the array
;;;   [1 2 3 1 4 1 5 3]  if k=2 then return [ 1 3 ] because 1 occurs 3 times and 3 occurs 2 times



(defn k-freqs
  "brute force approach using closure core functions"
  [col k]
  (let [vk-comp (fn [[k1 v1] [k2 v2]]
                  (if (= v1 v2)
                    (compare k2 k1)
                    (compare v2 v1)))]
    (->> col
         (frequencies)
         (sort-by identity vk-comp)
         (take 2))))


