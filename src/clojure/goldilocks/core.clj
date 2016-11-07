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

(defn str->int [s] (Integer/parseInt s))

(defn read-goldilocks-weight-temp
  [file]
  (let [first-line (first (line-seq (io/reader file)))
        weight-temp (re-seq #"\d+" first-line)]
    (map #(Integer/parseInt %) weight-temp)))

(defn read-weight-temp-data
  [file]
  (let [lines (rest (line-seq (io/reader file)))
        digit-lines (map #(re-seq #"\d+" %) lines)
        int-lines (map (fn [[w t]] (list (str->int w) (str->int t))) digit-lines)]
    int-lines))

(defn -main [input-file]
  (let [[weight temp] (read-goldilocks-weight-temp input-path)
        data (read-weight-temp-data input-file)]
    (keep-indexed (fn [idx [w t]]
                   (if (and (<= weight w) (<= t temp))
                     (list (inc idx) w t))) data)))

