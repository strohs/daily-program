(ns greatest-subseq-sum.core)

;;; Given a sequence of integers, find a continuous subsequence which maximizes the sum of its elements, that is,
;;; the elements of no other single subsequence add up to a value larger than this one.

; N = size of the original sequence
(def test-seq [8 2 6 1 5 3 4 7 9])
(def test2 [-1, -2, 3, 5, 6, -2, -1, 4, -4, 2, -1])

(defn gen-partitions
  "generate all partitions of a collection (of size N).
  Partitions will range in size from N-2 to 1"
  [xs]
  (reduce (fn [acc psize]
            (into acc (partition psize 1 xs)))
          []
          (range (dec (count xs)) 0 -1)))

(defn gss-bf
  "brute force function to find greatest subsequence sum:
  1. partition sequence into subesequences of size N-1 to 1
  2. sum each subsequence and sort by the largest sum, returning a map of sum -> subseqs
   OR simply keep track of the largest sum and return the largest sum with subsequence"
  [xs]
  (->> (gen-partitions xs)
       (reduce (fn [map partition] (assoc-in map [partition] (apply + partition))) {})
       (sort-by val)
       (last)))

(defn main [& args]
  (let [col [-1, -2, 3, 5, 6, -2, -1, 4, -4, 2, -1]
        gss (gss-bf col)]
    (println col)
    (println "greatest subsequence sum: " gss)))


;;; solution from rosetta-code.com
(defn max-subseq-sum [coll]
  (->> (take-while seq (iterate rest coll)) ; tails
       (mapcat #(reductions conj [] %)) ; inits
       (apply max-key #(reduce + %)))) ; max sum
