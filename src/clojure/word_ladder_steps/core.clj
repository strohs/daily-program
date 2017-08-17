(ns word-ladder-steps.core
  (:require [clojure.java.io :as io]))

;;; Challenge 114 - Word Ladder Steps
;;; https://www.reddit.com/r/dailyprogrammer/comments/149kec/1242012_challenge_114_easy_word_ladder_steps/
;;; Given a word, list all the words that can appear next to it in a word ladder,

(def words (line-seq (io/reader "src/clojure/word_ladder_steps/four-letter-words.txt")))

(defn hamming-distance [s1 s2]
  "stolen from http://en.wikipedia.org/wiki/Hamming_distance"
  (assert (= (count s1) (count s2)))
  (reduce + (map #(cond (= (first %) (second %)) 0 :else 1) (map vector s1 s2))))

(defn neighbors [word]
  (for [w words
        :when (= (hamming-distance word w) 1)]
    w))

;; Bonus 1: One word in the list has 33 other words that can appear next to it. What is this word?
(defn bonus-one []
  (flatten (pmap
             #(cond (= (count (neighbors %)) 33) %
                :else '()) words)))

;; Bonus 2: How many different words can be reached, starting from "best", in 3 or fewer steps?
(defn bonus-two [word steps]
  (loop [words (cond (coll? word) word :else (list word))
         step steps]
    (cond (= step 0) (set words)
      :else (recur (flatten (map neighbors words)) (- step 1)))))

(defn main [word] (neighbors word))
;;(main "puma")