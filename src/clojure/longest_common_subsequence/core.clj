(ns longest-common-subsequence.core)

;;; given two collections of some type T (ie. strings, or ints etc) return the longest sub-sequence common two
;;; both collections. If there are multiple common sub-sequences of the same length, then return one of them
;;; For example:
;;; Collection A: A B C B D A B
;;; Collection B: B D C A B A
;;; the longest subsequence is one of: BCBA, BDAB, BCAB

(def in1 ["A" "B" "C" "B" "D" "A" "B"])
(def in2 ["B" "D" "C" "A" "B" "A"])


(defn find-longest-sequence
  "iterates through the collections xs,ys once and returns a vector containing
  the longest sequence of matching elements between the two collections"
  ([xs ys]
   (find-longest-sequence xs ys []))

  ([xs ys longest-seq]
   (if (or (empty? xs) (empty? ys))
     longest-seq
     (if (= (first xs) (first ys))
       (recur (rest xs) (rest ys) (conj longest-seq (first xs)))
       (recur xs (rest ys) longest-seq)))))

(defn longest-common-subsequence
  ([xs ys]
   (if (< (count xs) (count ys))
     (longest-common-subsequence xs ys #{})
     (longest-common-subsequence ys xs #{})))
  ([xs ys res-set]
   (if (or (empty? xs) (empty? ys))
     res-set
     (let [longest-seq (apply str (find-longest-sequence xs ys))]
       (recur (rest xs) ys (conj res-set longest-seq))))))


;;;from Rosetta Code
(defn longest [xs ys] (if (> (count xs) (count ys)) xs ys))
(def lcs
  (memoize
    (fn [[x & xs] [y & ys]]
      (cond
        (or (= x nil) (= y nil)) nil
        (= x y) (cons x (lcs xs ys))
        :else (longest (lcs (cons x xs) ys)
                       (lcs xs (cons y ys)))))))


