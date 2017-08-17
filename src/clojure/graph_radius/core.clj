9(ns graph-radius.core
  (:require [clojure.java.io :as io]))

;;In graph theory, a graph's radius is the minimum eccentricity of any vertex for a given graph.
;; More simply: it is the minimum distance between all possible pairs of vertices in a graph.
;; As an example, the Petersen graph has a radius of 2 because any vertex is connected to any other vertex
;; within 2 edges. On the other hand, the Butterfly graph has a radius of 1 since its middle vertex can connect to any
;; other vertex within 1 edge, which is the smallest eccentricity of all vertices in this set. Any other vertex has
;; an eccentricity of 2.
;; OUTPUT: Print the radius of the graph as an integer

;;; NOT COMPLETED

(def file "src/clojure/graph_radius/matrix1.txt")


(defn get-lines
  "read input from file"
  [file-path]
  (with-open [rdr (io/reader file-path)]
    (reduce conj [] (line-seq rdr))))

(defn parse-line [l]
  (let [tokens (.split l " ")]
    (vec (map #(Integer/parseInt %) tokens))))

(defn read-adjacency-matrix
  "read an adjacency matrix from a file, returns a vector of vectors"
  [file]
  (vec (map #(parse-line %) (get-lines file))))


;(defn indexed [coll] (map-indexed vector coll))
;
;(defn index-filter [pred coll]
;  (when pred
;    (for [[idx elt] (indexed coll)
;          :when (pred elt)]
;      idx)))