(ns sum-digits.core)

(defn sum-digits
  "sum all the individual digits of an integer"
  [digits]
  (if (< digits 10)
    digits
    (+ (mod digits 10) (sum-digits (quot digits 10)))))

(defn digital-root
  [digits]
  (let [sum (sum-digits digits)]
    (if (>= sum 10)
      (sum-digits sum)
      sum)))

;;reverse string using recursion
(defn rev [s]
  (loop [sseq (seq s)
         rseq '()]
    (if (empty? sseq)
      rseq
      (recur (next sseq) (conj rseq (first sseq))))))

