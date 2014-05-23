(ns easy-probability.core)

;;;; http://www.reddit.com/r/dailyprogrammer/comments/25y2d0/5192014_challenge_163_easy_probability/

(defn roll
  "roll a six sided die n times, returns a lazy seq of the rolls"
  [n]
  (repeatedly n #(rand-nth (range 1 7))))

(defn do-rolls
  "coll is a collection of ints indicating the number of times to roll the die.
  returns a map of the die rolling results, with keys = the number of times the die was rolled and values = to a
  collection containing each die roll"
  [coll]
  (reduce
    (fn [m n] (assoc m n (roll n)))
    (sorted-map) coll))

(defn pct-of
  "how often does the number n occur within the coll. returns a bigdec percentage"
  [n coll]
  (let [cnt (count (filter #(= n %) coll))]
    (with-precision 4 (* 100 (/ (bigdec cnt) (count coll))))))

(defn simulate []
  (let [roll-map (do-rolls [10 100 1000 10000 100000 1000000])
        header "Rolls    1s      2s      3s      4s      5s      6s\n========================================================="
       ]
    (println header)
    (doseq [n (keys roll-map)
          :let [pcts (mapv #(pct-of % (roll-map n)) (range 1 7))]]
      (println (format "%-7d  %5.2f%%  %5.2f%%  %5.2f%%  %5.2f%%  %5.2f%%  %5.2f%%" n (pcts 0) (pcts 1) (pcts 2) (pcts 3) (pcts 4) (pcts 5))))))