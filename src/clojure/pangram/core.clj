(ns pangram.core)

;;; Challenge 139 Pangrams
;;; https://www.reddit.com/r/dailyprogrammer/comments/1pwl73/11413_challenge_139_easy_pangrams/

(defn pangram? [s]
  (= 26 (count (sort (distinct (re-seq #"[A-Za-z]" (.toLowerCase s)))))))

(defn pangram2 [s]
  (let [all-letters (sort (re-seq #"[A-Za-z]" (.toLowerCase s)))
        distinct-letters (distinct all-letters)
        pangram? (= 26 distinct-letters)
        freqs (interpose "," (map (fn [[k v]] (list (keyword (str k)) v)) (frequencies all-letters)))]
    (println pangram? freqs)))

(defn main [str] (pangram? str))
 ;;(main "the quick brown fox jumps over the lazy dog")