(ns novel-compression.compressor
  (:require [clojure.string :as str]
            [clojure.core.match :as pm]))

;;; Challenge 162 Novel Compressor
;;; http://www.reddit.com/r/dailyprogrammer/comments/25hlo9/5142014_challenge_162_intermediate_novel/

(defn tokenize-words [s] (str/split s #"[- ,.?!;:\n]+"))

(defn into-dictionary
  "read an input string into the dictionary. returns a new dictionary (vector) with the words added.
  Duplicate words will be removed, but capitalization IS taken into account, i.e. the The are seperate words."
  [s]
  (vec (sort compare (into #{} (tokenize-words s)))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;; input validation functions
(defn valid-capitalization?
  "only the first character of a word can be capitalized"
  [s]
  (every? (fn [word]
            (not-any? #(Character/isUpperCase %) (rest word)))
          (tokenize-words s)))

(defn has-digit? [s] (if (some #(Character/isDigit %) s) true false))

(defn valid-chars?
  "does the input string contain valid characters"
  [s] (every? #(re-matches #"[a-zA-Z0-9 \t\n\x0B\f\r,.?!;:-]" (str %)) s))

(defn duplicate-symbols? [s] (some? (re-find #"[,.?!;:-]{2,}" s)))

(defn upper-case? "is every char in the string upper-case?"
  [s] (every? #(Character/isUpperCase %) s))

(defn check-input [s]
  (cond
    (not (valid-chars? s)) (throw (IllegalArgumentException. "invalid character in input"))
    (has-digit? s) (throw (IllegalArgumentException. "input has digit in it"))
    (not (valid-capitalization? s)) (throw (IllegalArgumentException. "invalid capitalization"))
    (duplicate-symbols? s) (throw (IllegalArgumentException. "duplicate symbols found"))
    :else true))
;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn index-of
  "return index of s in the dict(ionary). returns nil if item does not exist"
  [s dict]
  (first (keep-indexed (fn [idx itm] (if (= itm s) idx)) dict)))

(defn to-tokens
  "convert the words and punctiation chars in a string into a list of tokens"
  [s] (map str/trim (filter #(not= " " %) (str/split s #"\b"))))

(defn compress-word [s dict]
  (cond
    (= s "-") "-"
    (re-matches #"[,.?!;:]" s) s
    (re-matches #"[,.?!;:]\n" s) (let [matches (re-matches #"([,.?!;:])(\n)" s)] (str (matches 1) " " "R"))
    (upper-case? s) (str (index-of s dict) "!")
    (Character/isUpperCase (first s)) (str (index-of s dict) "^")
    :else (str (index-of s dict))))

(defn compress [s dict]
  (let [toks (to-tokens s)]
    (str/trim (reduce (fn [acc tok] (str acc (compress-word tok dict) " ")) "" toks))))

(def in1 "The quick brown fox jum-ped over the lazy dog.\nOr did the dog skip?")
(def in2 "The quick brown fox jumps over the lazy dog.\nOr, did it?")
(def in3 "I would not, could not, in the rain.
Not in the dark. Not on a train.
Not in a car. Not in a tree.
I do not like them, Sam, you see.
Not in a house. Not in a box.
Not with a mouse. Not with a fox.
I will not eat them here or there.
I do not like them anywhere!")

(defn -main [s]
  (when (check-input s)
    (let [dictionary (into-dictionary s)]
      (compress s dictionary))))
;;(-main in1)
