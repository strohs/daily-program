(ns disemvowler.emvoweler2
  (:require [clojure.math.combinatorics :as combo]
            [clojure.string :as str]
            [disemvowler.trie :as trie]))

;; generate trie from word list
;;
;;; NOT WORKING


(defn build-partitions [coll]
  (map #(take % coll) (range 1 (inc (count coll)))))

(def word-path "src/clojure/disemvowler/words.txt")

(defn build-trie
  "read the words contained in the file at path into a trie"
  [path]
  (with-open [rdr (clojure.java.io/reader path)]
    (trie/build (line-seq rdr))))
(def tri (build-trie word-path))
