(ns ascii-architect.core)

;;; Challenge 158  http://www.reddit.com/r/dailyprogrammer/comments/236va2/4162014_challenge_158_intermediate_part_1_the/

;; sample input string
(def ss "j3f3e3e3d3d3c3cee3c3c3d3d3e3e3f3fjij3f3f3e3e3d3d3c3cee3c3c3d3d3e3e3fj")

(defn key->line
  "maps letters (as clojure keys) to their ascii line. returns a vec of chars"
  [k]
  (let [ascii-line "...***--++"
        keymap (zipmap [:a :b :c :d :e :f :g :h :i :j] [9 8 7 6 5 4 3 2 1 0])]
    (vec (drop (k keymap) ascii-line))))

(defn ascii-line
  "returns an ascii line (seq of characters) corresponding to the key: k, and with n number of spaces appended"
  [k n]
  (reduce conj (key->line k) (repeat n \space)))

(defn build-column
  "build a single ascii coiumn with spaces added to the end of the column
  spaces - integer indicating number of spaces to add
  letter - string between a - j indicating the letter of the column to build
  returns a seq of characters representing the ascii column"
  [[_ spaces letter]]
  (let [s (if (empty? spaces) 0 (Integer/parseInt spaces))
        l (keyword letter)]
    (ascii-line (keyword l) s)))


(defn parse-lines
  "parse an input line into a VoV of ascii lines"
  [line]
  (let [matches (re-seq #"(\d?)([a-z])" line)]
    (mapv build-column matches)))

(defn -main [line]
  (let [ascii-lines (parse-lines line)
        reverse-lines (map vec (mapv reverse ascii-lines))
        max-rows (apply max (map count reverse-lines))]
    (doseq [row (range (dec max-rows) -1 -1)]
      (println (apply str (map #(get % row \space) reverse-lines))))))

