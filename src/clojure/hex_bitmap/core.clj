(ns hex-bitmap.core
  (:require [clojure.string :as str]))

;;;; Today we will be making some simple 8x8 bitmap pictures. You will be given 8 hex values that can be 0-255 in
;;;; decimal value (so 1 byte). Each value represents a row. So 8 rows of 8 bits so a 8x8 bitmap picture.

(def in1 ["FF" "81" "BD" "A5" "A5" "BD" "81" "FF"])
(def in2 ["AA" "55" "AA" "55" "AA" "55" "AA" "55"])

(defn hex->binary
  "convert a hex string to a binary string that will be at least 8 bits long (zero padded)"
  [hex]
  (let [bstr (Integer/toBinaryString (Integer/parseInt hex 16))
        pad-str (format "%8s" bstr)]
    (str/replace pad-str #" " "0")))

(defn str->char [s] (if (= s "1") "X" " "))

(defn bin->chars
  "convert a binary string into its character string"
  [s]
  (->> (str/split s #"")
       (mapv str->char)
       (apply str)))

(defn -main [in]
  (let [bin-strs (mapv hex->binary in)
        ch-strs (mapv bin->chars bin-strs)]
    (doseq [s ch-strs]
      (println s))))
