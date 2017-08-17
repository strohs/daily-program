(ns ordered_vowels.core
  (:require [clojure.java.io :as io]))

;;; Challenge 122 Words with ordered vowels
;;; https://www.reddit.com/r/dailyprogrammer/comments/1aih0v/031813_challenge_122_easy_words_with_ordered/
;;;
;;; Find words in a word list that contain all the vowels in alphabetical order, non-repeated, where vowels are
;;; defined as A E I O U Y.

(def words (line-seq (io/reader "src/clojure/ordered_vowels/enable1.txt")))

(def vowels (into #{} "aeiouy"))
(def vowel-seq (seq vowels))

(defn ordered-vowels? [word]
  (let [fv (filter vowels word)]
    (= fv vowel-seq)))

(defn -main []
  (filter ordered-vowels? words))