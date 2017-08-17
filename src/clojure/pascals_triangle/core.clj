(ns pascals-triangle.core)

;;; Challenge 153 Pascals Pyramid
;;; https://www.reddit.com/r/dailyprogrammer/comments/20l2it/17042014_challenge_153_easy_pascals_pyramid/
;;; NOT COMPLETED

(defn factorial [n]
    (loop [cnt n acc 1]
      (if (zero? cnt)
        acc
        (recur (dec' cnt) (*' acc cnt)))))

(defn cell-value
  "n = row number, r = element index in the row"
  [n r]
  (/ (factorial n) (* (factorial r) (factorial (- n r)))))