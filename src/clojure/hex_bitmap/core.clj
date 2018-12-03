(ns hex-bitmap.core
  (:require [clojure.string :as str]))

;;;; Challenge 171 hex to 8x8 bitmap
;;;; https://www.reddit.com/r/dailyprogrammer/comments/2ao99p/7142014_challenge_171_easy_hex_to_8x8_bitmap/

;;;; Today we will be making some simple 8x8 bitmap pictures. You will be given 8 hex values that can be 0-255 in
;;;; decimal value (so 1 byte). Each value represents a row. So 8 rows of 8 bits so a 8x8 bitmap picture.
;;;;
;;;; run with (-main in1) or in2

;; sample inputs
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

(defn rgb->hexstr
  "convert R,G,B integer values to a HTML hex string"
  [r g b]
  (->> (map #(Integer/toHexString %) [r g b])
       (apply str "#")
       (.toUpperCase)))

(defn -main [in]
  (let [bin-strs (mapv hex->binary in)
        ch-strs (mapv bin->chars bin-strs)]
    (doseq [s ch-strs]
      (println s))))
