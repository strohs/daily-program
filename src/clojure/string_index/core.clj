(ns string-index.core)

(def test-string "...You...!!!@!3124131212 Hello have this is a --- string Solved !!...? to test @\n\n\n#!#@#@%$**#$@ Congratz this!!!!!!!!!!!!!!!!one ---Problem\n\n")

(def test-idx [12 -1 1 -100 4 1000 9 -1000 16 13 17 15])

(defn get-si [s idx]
  (let [v (vec (re-seq #"\w+" s))]
    (get v (dec idx) "")))

(defn solve []
  (->> (map #(get-si test-string %) test-idx)
       (filter not-empty)
       (interpose " ")
       (apply str)))
