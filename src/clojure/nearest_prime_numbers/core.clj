(ns nearest-prime-numbers.core
  (:require [clojure.math.numeric-tower :as math]))

;;; Challenge 326 nearest primes
;;; WORK IN PROGRESS
;;;  https://www.reddit.com/r/dailyprogrammer/comments/6s70oh/2017087_challenge_326_easy_nearest_prime_numbers/

(defn- rand-num [n]
  " random number between 2 and n-2 "
  (bigint (math/floor (+' 2 (*' (- n 4) (rand))))))

(defn- m* [p q m]
  " Computes (p*q) mod m "
  (mod (*' p q) m))

(defn- power
  "modular exponentiation (i.e. b^e mod m"
  [b e m]
  (loop [b b, e e, x 1]
    (if (zero? e)
      x
      (if (even? e) (recur (m* b b m) (quot e 2) x)
                    (recur (m* b b m) (quot e 2) (m* b x m))))))

(defn millerTest?
  "This function is called for all k trials. It returns false if n is composite and returns true
   if n is probably prime. d is an odd number such that d*2r = n-1 for some r >= 1"
  [n,d]
  (let [a (rand-num n)
        x (power a d n)]
    (if (or (= x 1) (= x (- n 1)))
      true
      (if (not= d (- n 1))
        (loop [x (m* x x n)
               d (* d 2)]
          (cond
            (= x 1) false
            (= x (- n 1)) true
            :else (if (not= d (- n 1))
                    (recur (m* x x n) (* d 2))
                    false)))
        false))))


(defn- findr
  "Find r such that n = 2^d * r + 1 for some r >= 1"
  [n]
  (let [d (- n 1)]
    (loop [d d]
      (if (odd? d)
        d
        (recur (quot d 2))))))

(defn isPrime? [n k]
  (cond
    (or (<= n 1) (= n 4)) false
    (<= n 3) true
    :else (let [d (findr n)]
            (every? true? (for [_ (range k)] (millerTest? n d))))))