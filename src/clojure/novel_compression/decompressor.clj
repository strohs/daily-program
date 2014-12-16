(ns novel-compression.decompressor
  (:require [clojure.string :as str]))

;;;; http://www.reddit.com/r/dailyprogrammer/comments/25clki/5122014_challenge_162_easy_novel_compression_pt_1/

(def ^:dynamic *word-path* "src/clojure/novel_compression/words.txt")

(defn read-words
  "read the words contained in the file at path into a vector of words"
  [path]
  (with-open [rdr (clojure.java.io/reader path)]
    (reduce conj [] (line-seq rdr))))

(def dictionary (read-words *word-path*))

(defn chunk-number? [s] (some? (re-matches #"\d+" s)))
(defn chunk-capitalize? [s] (some? (re-matches #"\d+\^" s)))
(defn chunk-upper-case? [s] (some? (re-matches #"\d+!" s)))
(defn chunk-hyphen? [s] (= s "-"))
(defn chunk-newline? [s] (or (= s "R") (= s "r")))
(defn chunk-eol? [s] (or (= s "E") (= s "e")))
(defn chunk-symbol? [s] (some? (re-matches #"[,.?!:;]" s)))

(defn parse-int
  "parse the first integer string found in s into an Integer"
  [s] (Integer/parseInt (re-find #"\d+" s)))

(defn append-str [coll s] (into (vec coll) s))

(defn chunk->chars
  "looks up chunk in the dictionary, gets the word, and adds it to the coll. returns coll as a seq of chars"
  [coll chunk]
  (cond
    (chunk-number? chunk) (append-str coll (str (get dictionary (parse-int chunk)) " "))
    (chunk-capitalize? chunk) (append-str coll (str (str/capitalize (get dictionary (parse-int chunk))) " "))
    (chunk-upper-case? chunk) (append-str coll (str (str/upper-case (get dictionary (parse-int chunk))) " "))
    (chunk-hyphen? chunk) (append-str (butlast coll) "-")
    (chunk-newline? chunk) (append-str coll "\n")
    (chunk-symbol? chunk) (append-str (butlast coll) (str chunk " "))
    (chunk-eol? chunk) (append-str coll "")
    :else (println "unknown chunk found " chunk)))

(defn decompress
  [chunk-str]
  (let [chunks (str/split chunk-str #" ")]
    (reduce chunk->chars [] chunks)))

(def ts "0^ 1 6 7 8 5 10 2 . R")
(def ts5 "0^ 1 6 7 8 , 18^ - 0^ - 19 . R E")

