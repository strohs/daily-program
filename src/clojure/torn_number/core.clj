(ns torn-number.core)

;;;;  I had the other day in my possession a label bearing the number 3 0 2 5 in large figures. This got accidentally
;;;; torn in half, so that 3 0 was on one piece and 2 5 on the other. On looking at these pieces I began to make a
;;;; calculation, when I discovered this little peculiarity. If we add the 3 0 and the 2 5 together and square the sum
;;;; we get as the result, the complete original number on the label! Thus, 30 added to 25 is 55, and 55 multiplied by
;;;; 55 is 3025. Curious, is it not?
;;;; Now, the challenge is to find another number, composed of four figures, all different, which may be divided in
;;;; the middle and produce the same result.

(defn ->digits
  "return a list of the individual digits of an integer"
  [n]
  (loop [digit n
         digits '()]
    (if (< digit 10)
      (conj digits digit)
      (recur (quot digit 10) (conj digits (rem digit 10))))))

(defn unique-digits?
  "test if n (an integer) is comprised of unique digits"
  [n]
  (let [digits (->digits n)
        udigits (into #{} digits)]
    (= (count digits) (count udigits))))

(defn equal-sum-square?
  [n]
  {:pre [(even? (count (->digits n)))]}
  (let [digits (->digits n)
        half-size (quot (count digits) 2)
        first-half (Integer/parseInt (apply str (take half-size digits)))
        last-half (Integer/parseInt (apply str (take-last half-size digits)))
        sum (+ first-half last-half)
        square (* sum sum)]
    (= square n)))

(defn torn-digit? [n]
  (when (even? (count (->digits n)))
    (let [digits (->digits n)
          half-size (quot (count digits) 2)
          first-half (Integer/parseInt (apply str (take half-size digits)))
          last-half (Integer/parseInt (apply str (take-last half-size digits)))
          sum (+ first-half last-half)
          square (* sum sum)]
      (= square n))))

;; brute force: get all unique 4 digit numbers and check each to see if they have equal sum squares
(filter equal-sum-square? (filter unique-digits? (range 1000 10000)))

(->> (range 1000 10000)
     (filter unique-digits?)
     (filter equal-sum-square?))