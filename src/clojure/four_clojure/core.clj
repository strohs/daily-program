(ns four-clojure.core
  (:use [clojure.pprint])
  (:use [clojure.tools.trace]))
;problems from 4clojure.com



;#44
(fn rotate [n xs]
  (if (= n 0)
    xs
    (if (pos? n)
      (rotate (dec n) (concat (drop 1 xs) (take 1 xs)))
      (rotate (inc n) (concat (take-last 1 xs) (drop-last 1 xs))))))

;#153
;some every? not-any? not-every? contains?
#(apply distinct? (mapcat seq %))

;#146
(defn kvm [mp]
  (into {}
    (for [[k v] mp
          [vk vv] v]
      (vec [[k vk] vv]))))

;pascals trapezoid
(defn pt [v]
  (iterate #(vec (map + (cons 0 %) (conj % 0))) v))


;#95 To Tree or Not to Tree
(defn istree? [root]
  (or (nil? root)
      (and (sequential? root)
           (= 3 (count root))
           (every? istree? (rest root)))))

;;#96 Beauty is Symmetry
(defn symmetric-tree? [[root left right]]
  (letfn [(terminal? [node] (not (sequential? node)))
          (mirror? [l r]
                   (if (and (terminal? l) (terminal? r))
                     (= l r)
                     (if (and (sequential? l) (sequential? r))
                       (and (= (nth l 0) (nth r 0)) (mirror? (nth l 1) (nth r 2)) (mirror? (nth l 2) (nth r 1)))
                       false)))]
    (mirror? left right)))

;another solution
#(= % ((fn flip [[v l r :as n]]
         (when n
           [v (flip r) (flip l)])) %))


;#100 Least Common Multiple
(defn lcm [a b & more]
  ; gcd (Greatest Common Divisor) function assumes a and b are positive numbers
  (letfn [(gcd [a b]
               (if (= a b)
                   a
                   (if (> a b)
                     (gcd (- a b) b)
                     (gcd (- b a) a))))]
    (reduce #(/ (* %1 %2) (gcd %1 %2)) (conj more b a))))



;#118
(fn mmap [f coll]
  (lazy-seq
    (if-let [s (seq coll)]
      (cons (f (first coll)) (mmap f (rest coll))))))

;#31 replicate a sequence
(fn rep-seq [coll n]
  (for [x coll
        i (range n)]
    x))

;#31 Pack a Sequence
(defn pack-seq [coll]
  (partition-by identity coll))

;#40 interpose a sequence
(defn interpose-seq [x coll]
  (rest (interleave (repeat (count coll) x) coll)))

;#41
(defn drop-nth [coll n]
  (keep-indexed #(if (not (zero? (rem (inc %1) n))) %2) coll))
;(drop-nth [1 2 3 4 5 6 7 8] 2)

;#88 Symmetric Difference
(fn symmetric-difference [set1 set2]
  (clojure.set/union
    (clojure.set/difference set1 set2)
    (clojure.set/difference set2 set1)))

;#120 sum of squared digits
(fn sum-of-squared-digits [coll]
  (letfn [(sum-of-squares [n]
                          (loop [quotient n
                                 accum 0]
                            (if (< quotient 10)
                              (+ accum (* quotient quotient))
                              (let [remainder (rem quotient 10)]
                                (recur (quot quotient 10) (+ accum (* remainder remainder)))))))]
    (count (filter #(< % (sum-of-squares %)) coll))))

;#157 Indexing Sequences
(fn idx-seq [coll]
  (map-indexed (fn [idx itm] (vector itm idx)) coll))

;#156 map defaults
(fn map-def [default coll] (into {} (map #(vector % default) coll)))(reverse [2 5 4 1 3 6])


