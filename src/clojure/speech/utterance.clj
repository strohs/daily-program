(ns speech.utterance
  (:require [clojure.string :as cstr]))

(defrecord SpeechCell [text start dur])

(defprotocol Utterance
  "protocol for working with a collection of SpeechCell(s)."
  (words [this start end] "return all words (non token text) of this utterance seperated by spaces")
  (text-index [this text] "index of the first cell that contains the input text")
  (token? [this index] "is the text at index a special token"))

(defn- join-cells
  "join two speech cells together, merging their text fields and adding their durations togther"
  [c1 c2]
  {:pre [(and c1 c2)]}
  (SpeechCell.
    (cstr/join " " (.text c1) (.text c2))
    (.start c1)
    (+ (.dur c1) (.dur c2))))

(defn join
  [utt start end]
  (reduce-kv (fn [acc index val]
               (if (< start index end)
                 (assoc acc index (join-cells (last acc) val))
                 (conj acc val))) [] utt))

(defn- equal? [col s]
  (= s (apply str (interpose " " (map #(get % 1) col)))))

(extend-protocol Utterance
  clojure.lang.IPersistentVector

  (token? [utt index]
    (keyword? (:text (utt index))))

  (words [utt start end]
    (cstr/join " " (map :text (remove token? (subvec utt start end)))))

  (text-index [utt text]
    (let [indexed-text (keep-indexed (fn [idx itm] [idx (get itm :text)]) utt)
          beam (count (clojure.string/split text #" "))
          part-text (partition-all beam 1 indexed-text)
          match (first (filter #(equal? % text) part-text))]
      (if (seq match)
        (get (first match) 0)
        nil))))






