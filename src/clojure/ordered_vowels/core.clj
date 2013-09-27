(ns ordered_vowels.core
  (:require [clojure.java.io :as io]))

(def words (line-seq (io/reader "/Users/cliff/IdeaProjects/daily-program/src/clojure/ordered_vowels/enable1.txt")))

(def vowels (into #{} "aeiouy"))
(def vowel-seq (seq vowels))

(defn ordered-vowels? [word]
  (let [fv (filter vowels word)]
    (= fv vowel-seq)))

(filter ordered-vowels? words)