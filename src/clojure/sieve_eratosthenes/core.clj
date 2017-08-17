(ns sieve-eratosthenes.core)

;;very basic implementation of sieve of Eratosthenes



(defn primes
  "Computes all prime numbers up to a given number using sieve of Eratosthenes"
  [number]
  (loop [candidates (range 2 number)
         primes [2]]
    (let [toremove (range (last primes) number (last primes))]
      (let [newcandidates (remove (set toremove) candidates)]
        (if (> (last primes) (Math/sqrt number))
          (concat primes newcandidates)
          (recur newcandidates
                 (concat primes [(first newcandidates)])))))))

(defn prime? [n]
  (contains? (set (primes (inc n))) n))


;; brute force prime number test
(defn bf-prime? [n]
  (not-any? zero? (map #(rem n %) (range 2 n))))

(defn to-matrix [size]
  (for [cols (range 0 size)
        rows (range 0 size)
        :let [prod (if (prime? (* rows cols))
                     (* rows cols)
                     0)]]
    prod))

(defn main
  "compute all prime numbers from 0 to max-number"
  [max-number] (primes max-number))
;;(partition 10 (to-matrix 10))
;;(doseq [coll (partition 10 (to-matrix 10))]
;;  (println coll))