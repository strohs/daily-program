(ns words-from-chars.core
  (require [clojure.set :as cset])
  (require [clojure.string :as s]))

;;;; Given a string of words and a string of letters. Find the largest string(s) that are in the 1st string of words
;;;; that can be formed from the letters in the 2nd string.
;;; Letters can be only used once. So if the string has "a b c" then words like "aaa" and "bbb" do not work because
;;; there is only 1 "a" or "b" to be used.
;;;
;;; If you have tie for the longest strings then output all the possible strings.
;;;
;;; If you find no words at all then output "No Words Found"

(def ^:dynamic *word-path* "src/clojure/disemvowler/words.txt")

(defn read-words
  "read the words contained in the file at path into a vector of words"
  [path]
  (with-open [rdr (clojure.java.io/reader path)]
    (reduce conj [] (line-seq rdr))))

(def dictionary (read-words *word-path*))


(defn get-words [word-string]
  (s/split word-string #"\s+"))

(defn max-length
  "get word(s) that has the most characters from the coll"
  [coll]
  {:pre [(not-empty coll)]}
  (let [max-count (apply max (map count coll))]
    (filter #(>= (count %) max-count) coll)))

(defn contains-all-chars?
  "returns true if all of the chars in 'word' are in 'char-string'"
  [word char-str]
  (let [word-freqs (frequencies word)
        char-freqs (frequencies char-str)]
    (every? (fn [[k v]]
              (= (char-freqs k) v)) word-freqs)))

(defn words-from-chars [words char-str]
  (let [wfc (filter #(contains-all-chars? % char-str) words)]
    (if (seq wfc)
      ;(max-length wfc)
      wfc
      "No Words Found")))

(def twords "abc cca aaaaaa bca")
(def tchars "abc")

(defn main [] (words-from-chars dictionary "abc"))

