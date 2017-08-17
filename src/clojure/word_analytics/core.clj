(ns word-analytics.core
  (:require [clojure.java.io :as io])
  (:require [clojure.string :as s]))

;;; Challenge 125 Word Analytics
;;; https://www.reddit.com/r/dailyprogrammer/comments/1e97ob/051313_challenge_125_easy_word_analytics/


(def lines (with-open [rdr (io/reader "src/clojure/word_analytics/lorem-ipsum.txt")]
             (reduce conj [] (filter not-empty (line-seq rdr)))))

;splits lines into words, keeps punctuation
(def words
  (filter not-empty
    (flatten (map #(s/split (.toLowerCase %) #"\s") lines))))

;seq of words without punctuation in them
(def words-no-punc (map #(s/replace % #"\W" "") words))

;all the letters from the words
(def letters (flatten (map #(re-seq #"[A-Za-z]" %) words)))

;count of all symbols (any non-letter and non-digit character, excluding white spaces
(def symbol-count (count
                    (filter not-empty
                      (flatten (map #(re-seq #"\W" %) words)))))

;take the n most common items in the collection using the frequencies function
(defn most-common [n col]
  (take n
    (sort-by val >
      (frequencies col))))

;top 3 most frequent words
(def most-common-words (most-common 3 words-no-punc))

;top 3 most frequent letters
(def most-common-letters (most-common 3 letters))

;first word of each paragraph
(def first-word-paragraph (map #(first (s/split (.toLowerCase %) #"\s")) lines))

;top 3 most common first words of a paragraph
(def most-common-first-words (most-common 3 first-word-paragraph))

;words used only once
(def words-used-once (map first
                       (filter #(= 1 (val %))
                         (frequencies words-no-punc))))