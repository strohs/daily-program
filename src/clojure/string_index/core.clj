(ns string-index.core)

;;; Challenge 168 String Index
;;; https://www.reddit.com/r/dailyprogrammer/comments/299hvt/6272014_challenge_168_easy_string_index/

(def test-string "...You...!!!@!3124131212 Hello have this is a --- string Solved !!...? to test @\n\n\n#!#@#@%$**#$@ Congratz this!!!!!!!!!!!!!!!!one ---Problem\n\n")

(def test-idx [12 -1 1 -100 4 1000 9 -1000 16 13 17 15])

(defn get-si [s idx]
  (let [v (vec (re-seq #"\w+" s))]
    (get v (dec idx) "")))

(defn solve [in-str]
  (->> (map #(get-si in-str %) test-idx)
       (filter not-empty)
       (interpose " ")
       (apply str)))

(defn main [in-str] (solve in-str))
;;(main test-str)
