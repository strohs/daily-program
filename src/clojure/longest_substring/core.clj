(ns longest-substring.core)

(defn gen-subs
  "generate all substrings of 'string' with at least unique-cnt characters in the string"
  [in-str unique-cnt]
  (loop [cnt unique-cnt
         substrings '()]
    (if (> cnt (count in-string))
      substrings
      (recur (inc cnt) (into substrings (partition cnt 1 in-string))))))

(defn longest-substr
  [in-str unique-chars]
  (def filtered-strs (filter #(<= (count (set %1)) unique-chars) (gen-subs in-str unique-chars)))
  ;sort-by may not be needed since gen-subs generates the partitions in ascending order
  (last (sort-by count filtered-strs)))