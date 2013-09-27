(ns speech.stuff
  (:require [clojure.java.io :as cio])
  (:import (java.nio.charset Charset)))


(def file-path "/Users/cliff/yapapi.html")

(with-open [rdr (cio/reader file-path)]
  (for [c (.read rdr)
        :when (> 0 c)]
    (print c)))


;will need to import java.nio.charset.Charset
(defn all-letters
  [cset-name]
  (def ce (-> (Charset/forName cset-name) .newEncoder))
  (filter #(and (.canEncode ce (char %)) (Character/isLetter (char %))) (range 0 (int (Character/MAX_VALUE)))))

(println (all-letters "US-ASCII"))


(defn read-file [path]
  (with-open [rdr (cio/reader path)]
    (loop [c (.read rdr)
           col []]
      (if (neg? c)
        col
        (recur (.read rdr) (conj col (char c)))))))
