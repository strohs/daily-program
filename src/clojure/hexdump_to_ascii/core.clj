(ns hexdump-to-ascii.core
  (:require [clojure.java.io :as cio])
  (:import (java.math BigInteger)
           (java.nio.charset Charset))
  (:gen-class))

(defn get-lines
  "read input from file"
  [file-path]
  (with-open [rdr (cio/reader file-path)]
    (reduce conj [] (line-seq rdr))))

(defn to-hex [s]
  (format "%060x" (new BigInteger (.getBytes s (Charset/defaultCharset)))))


;;someones actual solution
(defn hexdump
  ([file] (hexdump file 18))
  ([file width]
    (with-open [data (java.io.FileReader. file)]
      (loop [byte (.read data)
             counter 0]
        (when (>= byte 0)
          (if (zero? (mod counter width))
            (printf "\n%08X" (/ counter width)))
          (printf " %02X" byte)
          (recur (.read data) (inc counter)))))))


(defn -main [& args]
  (println "do something with hexdump here"))