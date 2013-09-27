(ns speech.core
  (:require [clojure.string :as cstr]))

(defrecord Cell [text start dur])

(defprotocol Mergeable
  "protocol for working with speech cells"
  (merge [cell1 cell2]))

(defn- cell-merge
  "merge two cells together, returning a new cell with values merged"
  [cell1 cell2]
  (let [text (str (get cell1 :text) " " (get cell2 :text))
        start (get cell1 :start)
        dur (+ (get cell1 :dur) (get cell2 :dur))]
    {:text text :start start :dur dur}))

(defn merge
  "merge cells together. rep-str can be used to specify a replacement text string"
  ([cells] (reduce cell-merge cells))
  ([cells rep-str] (assoc (reduce cell-merge cells) :text rep-str)))

(defn tok-count [s regx]
  (count (clojure.string/split s regx)))

(defn- equal? [col s]
  (= s (apply str (interpose " " (map #(get % 1) col)))))

(defn index-of
  "return index of the first cell that matches specified text"
  [cells text]
  (let [indexed-text (keep-indexed (fn [idx itm] [idx (get itm :text)]) cells)
        beam (count (clojure.string/split text #" "))
        part-text (partition-all beam 1 indexed-text)
        match (first (filter #(equal? % text) part-text))]
    (if (seq match)
      (get (first match) 0)
      nil)))

(defn find-replace
  "find the keys of 'rep-map' in 'cells' and replace them with the rep-map values"
  [cells fs rs]
  (let [beam (tok-count rs #" ")]
      (if-let [idx (index-of cells fs)]
        (apply conj (subvec cells 0 idx) (merge (subvec cells idx (+ idx (tok-count fs #" "))) rs) (subvec cells (+ idx (tok-count fs #" "))))
        cells)))


;dummy cells
(def cells [{:text "hi" :start 0.11 :dur 0.25}
            {:text "there" :start 0.36 :dur 0.35}
            {:text "dude" :start 0.71 :dur 0.24}
            {:text "see" :start 0.95 :dur 0.21}
            {:text "you" :start 1.01 :dur 0.13}])

(def rep-map {"nine" "9"
              "o'clock" "00"
              "five" "5"
              "twenty five" "25"
              "laughing out loud" "LOL"})
