(ns string-transposition.core)

(def ins '("cat" "horse" "zebra"))

(defn transpose [coll]
  (reduce #(conj (vec %1) (vec %2)) [] coll))

(defn max-col [mat] (count mat))
(defn max-row [mat]
  (apply max (map count mat)))

(defn print-ch [ch]
  (if ch (str ch) " "))

(defn print-matrix [mat]
  (let [cols (max-col mat)
        rows (max-row mat)]
    (doseq [r (range rows)
            c (range cols)]
      (print (print-ch (get-in mat [c r])))
      (if (= c (- cols 1)) (println)))))