(ns goldilocks.core
  (:require [clojure.java.io :as io]))

;;; Challenge 291: Goldilocks Bear Necessities
;;; ------------------------------------------
;;; Given descriptions of Goldilocks' needs and of the available porridge/chairs at the dinner table, tell Goldilocks
;;; which chair to sit in so the chair does not break, and the porridge is at an edible temperature.
;;;
;;; Input
;;; -----
;;; The input begins with a line specifying Goldilocks' weight (as an integer in arbitrary weight-units) and the
;;; maximum temperature of porridge she will tolerate (again as an arbitrary-unit integer). This line is then
;;; followed by some number of lines, specifying a chair's weight capacity, and the temperature of the porridge
;;; in front of it.
;;;
;;; Output
;;; ------
;;; The output must contain the numbers of the seats that Goldilocks can sit down at and eat up. This number
;;; counts up from 1 as the first seat.

(def input-path "src/clojure/goldilocks/goldilocks-input.txt")

(defn str->int
  "convert a numeric string into an integer"
  [s] (Integer/parseInt s))

(defn strs->ints
  "convert a sequence of numeric strings into a sequence of integers"
  [xs] (map #(str->int %) xs))

(defn read-lines [input-path]
  (let [lines (line-seq (io/reader input-path))]
    (map (fn [line]
           (->> line
                (re-seq #"\d+")
                (strs->ints))) lines)))

(defn -main [input-file]
  (let [[weight temp] (first (read-lines input-file))
        data (rest (read-lines input-file))]
    (keep-indexed (fn [idx [w t]]
                   (if (and (<= weight w) (<= t temp))
                     (list (inc idx) w t))) data)))


