(ns speech.merge
  (:require [clojure.string :as cstr]))


;these are for testing
(def test-str "we are laughing out loud and going to the club at nine o'clock please start laughing out loud and bring
  twenty five cents")
(def rep-map {"nine" "9"
              "o'clock" "00"
              "five" "5"
              "twenty five" "25"
              "laughing out loud" "LOL"})

(def cseq (vec (.split test-str " ")))

(defn create-cell [text start-time dur]
  (assoc {} :text text :start start-time :dur dur))

(defn mock-cells
  [str-seq]
  (loop [str-seq str-seq, cur-dur 0.0, cells []]
    (if (seq str-seq)
      (let [rdur (rand 3)]
        (recur (rest str-seq) (+ rdur cur-dur) (conj cells (create-cell (first str-seq) cur-dur rdur))))
      cells)))

(defn- join-cells
  ([sidx beam cells]
    (assoc (get cells sidx)
         :dur (reduce + (map #(get % :dur) (subvec cells sidx (+ sidx beam))))
         :text (cstr/join " " (map #(get % :text) (subvec cells sidx (+ sidx beam))))))
  ([rep-str sidx beam cells]
    (assoc (get cells sidx)
         :dur (reduce + (map #(get % :dur) (subvec cells sidx (+ sidx beam))))
         :text rep-str)))

(defn cmerge
  "combine beam number of consecutive cells into a single cell at sidx" 
  ([rep-str sidx beam cells]
    (apply conj (subvec cells 0 sidx) (join-cells rep-str sidx beam cells) (subvec cells (+ sidx beam))))
  ([sidx beam cells]
    (apply conj (subvec cells 0 sidx) (join-cells sidx beam cells) (subvec cells (+ sidx beam)))))


(defn equal? [str cells]
  "is the passed in 'str' equal to the :text of the 'cells', seperated by spaces"
  (= str (cstr/join " " (map #(get % :text) cells))))


(defn first-index
  "return the first index of the matching str found in cells. will return ([idx ({:text \"A\"} {:text \"B\"}...)] [idx2"
  [str cells]
  (let [beam (count (.split str " "))]
    (first (keep-indexed #(if (equal? str %2) %1) (partition-all beam 1 cells)))))


(defn find-replace
  "find the keys of 'rep-map' in 'cells' and replace them with the rep-map values"
  [rep-map cells]
  (if (seq rep-map)
    (let [[str rep] (first rep-map)
        idx (first-index str cells)
        beam (count (.split str " "))]
    (if idx
      (find-replace rep-map (cmerge rep idx beam cells))
      (if str
        (find-replace (rest rep-map) cells)
        cells)))
    cells))

(defn total-dur
  [cells]
  (let [{:keys [start dur]} (last cells)]
    (+ start dur)))



