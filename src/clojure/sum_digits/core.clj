(ns sum-digits.core)

;;; Challenge 122 Sum Them Digits
;;; https://www.reddit.com/r/dailyprogrammer/comments/1berjh/040113_challenge_122_easy_sum_them_digits/

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

(defn main [digits] (digital-root digits))
;;(main 31337)

