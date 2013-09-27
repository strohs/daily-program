(ns alphabetizing-cipher.core
  (:require [clojure.java.io :as io]))

(def abc '(\a \b \c \d \e \f \g \h \i \j \k \l \m \n \o \p \q \r \s \t \u \v \w \x \y \z))

(def results (ref {}))
(def worker (agent 0))

;;six letter words
(def words (line-seq (io/reader "/Users/cliff/IdeaProjects/daily-program/src/clojure/alphabetizing_cipher/six-letter-words.txt")))


(defn build-map
  "build a map from keys to vals"
  [k v]
  (into {} (map vector k v)))

(defn encode
  "map the chars of s to corresponding vals contained in mapping"
  [mapping s]
  (apply str (map #(get mapping %) s)))

(defn ordered-string?
  "is the string: s, ordered according to the comparator: cmp"
  [cmp s]
  (apply cmp (map int s)))

(defn gen-cipher []
  (map char (shuffle (range 97 123))))

(defn count-alphas [n cipher]
  (let [mapping (build-map abc cipher)
        enc-words (map #(encode mapping %) words)
        alpha-cnt (count (filter #(ordered-string? < %) enc-words))]
    (if (> alpha-cnt n)
      (do
        (dosync (alter results assoc (apply str cipher) alpha-cnt))
        alpha-cnt)
      n)))

(defn -main [& args]
  (dotimes [i 1000000]
    (when-let [cipher (gen-cipher)]
      (if-not (dosync (get @results cipher))
        (send worker count-alphas cipher)
        (println "cipher already exists")))))


