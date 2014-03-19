(ns disemvowler.emvowler
  (:require [clojure.math.combinatorics :as combo]
            [clojure.string :as str]))

;; combine vowels and consonants into one list
;; generate permutations
;; filter matching first-lasts
;; for each resulting permutation:
;;   generate partitions
;;   for each partition:
;;     flatten it and filter out partitions that are in the same order as its parent permutation
;;     for each filtered partition, take the ones where ALL sub-partitions match a word in the word list


(def word-path "src/clojure/disemvowler/words.txt")
(defn read-words
  "read the words contained in the file at path into a set of words"
  [path]
  (with-open [rdr (clojure.java.io/reader path)]
    (reduce conj #{} (line-seq rdr))))
(def dictionary (read-words word-path))

(defn chars->str
  "convert a collection of chars into a string"
  [cs]
  (apply str cs))

(defn character-permutations
  "cs is a string of consonants, vs is a string of vowels. cs and vs are combined and a collection of their
  permutations are returned. The returned collection is pruned such that every item must begin with the first
  character from cs or vs, AND end with the last character from cs or vs."
  [cs vs]
  (let [ all-chars (interpose \space (concat (seq cs) (seq vs)))
         first-chars (hash-set (first (seq cs)) (first (seq vs)))
         last-chars (hash-set (last (seq cs)) (last (seq vs)))]
    (filter #(and (first-chars (first %))
                  (last-chars (last %)))
            (combo/permutations all-chars))))


(defn all-matching-words?
  "do all of the strings in col match a string contained in the set of words"
  [col]
  (every? dictionary col))

(defn matching-permutation?
  [perm]
  (let [sentence (chars->str perm)
        words (str/split sentence #"\s+")]
    (every? dictionary words)))

(defn pemvowel [consonants vowels]
  (let [perms (character-permutations consonants vowels)
        mperms (pmap #(if (matching-permutation? %) (str/replace (chars->str %) #"\s+" " ") nil) perms)]
    (into #{} mperms)))

(defn emvowel [consonants vowels]
  (let [perms (character-permutations consonants vowels)]
    (for [p perms
          :let [ sentence (chars->str p)
                 words (str/split sentence #"\s+")]
          :when (all-matching-words? words)
          ]
      words)))

