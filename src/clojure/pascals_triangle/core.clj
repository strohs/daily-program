(ns pascals-triangle.core)

(defn factorial [n]
    (loop [cnt n acc 1]
      (if (zero? cnt)
        acc
        (recur (dec' cnt) (*' acc cnt)))))

(defn cell-value
  "n = row number, r = element index in the row"
  [n r]
  (/ (factorial n) (* (factorial r) (factorial (- n r)))))