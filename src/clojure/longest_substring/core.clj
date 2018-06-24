(ns longest-substring.core)

;;; Longest 2 character substring
;;; https://www.reddit.com/r/dailyprogrammer/comments/1g0tw1/easy_longest_twocharacter_substring/

(defn gen-subs
  "generate all substrings of 'string' with at least unique-cnt characters in the string"
  [in-str unique-cnt]
  (loop [cnt unique-cnt
         substrings '()]
    (if (> cnt (count in-str))
      substrings
      (recur (inc cnt) (into substrings (partition cnt 1 in-str))))))

(defn longest-substr
  [in-str unique-chars]
  (def filtered-strs
    (filter #(<= (count (set %1)) unique-chars)
            (gen-subs in-str unique-chars)))
  ;sort-by may not be needed since gen-subs generates the partitions in ascending order
  (last (sort-by count filtered-strs)))


(defn -main [in-str unique-cnt] (longest-substr in-str unique-cnt))
;;(-main "abbcccc" 2)


(defn longest
  "longest contiguous character substring within the string s"
  [s]
  (loop [s s
         longest-str ""]
    (if (empty? s)
      (vector longest-str (count longest-str))
      (let [contig-chars (take-while #(= (first s) %) s)]
        (if (> (count contig-chars) (count longest-str))
          (recur (drop (count contig-chars) s) (apply str contig-chars))
          (recur (drop (count contig-chars) s) longest-str))))))
