(ns clojure-conj-talk.core2
  (:refer-clojure :exclude [map reduce into partition partition-by take merge])
  (:require [clojure.core.async :refer :all :as async]
            [clojure.pprint :refer [pprint]]
            [cheshire.core :as cheshire]))

(def xchan (chan))
(def ychan (chan))

(defn taskx []
  (while (<!! xchan)
    (println "X")
    (>!! ychan :YY))
  (println "taskX done"))

(defn tasky []
  (while (<!! ychan)
    (println "Y")
    (>!! xchan :XX))
  (println "taskY done"))

(let [fx (future (taskx))
      fy (future (tasky))]
  (>!! xchan "someval")
  (Thread/sleep 2000)
  (close! xchan)
  (close! ychan))