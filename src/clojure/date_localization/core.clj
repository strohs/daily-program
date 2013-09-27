(ns date-localization.core
  (:import java.util.Calendar)
  (:require [clojure.string :as cs])
  (:gen-class))

;;"%l": Milliseconds (000 to 999)
;       %1$tL
; "%s": Seconds (00 to 59)
;       %1$tS
; "%m": Minutes (00 to 59)
;       %1$tM
; "%h": Hours (in 1 to 12 format)
;       %1$tI
; "%H": Hours (in 0 to 23 format)
;       %1$tH
; "%c": AM / PM (regardless of hour-format)
;       %1$tp
; "%d": Day (1 up to 31)
;       %1$td
; "%M": Month (1 to 12)
;       %1$tm
; "%y": Year (four-digit format)
;       %1$ty

(def mapper {
            "%l" "%1$tL"
            "%s" "%1$tS"
            "%m" "%1$tM"
            "%h" "%1$tI"
            "%H" "%1$tH"
            "%c" "%1$tp"
            "%d" "%1$td"
            "%M" "%1$tm"
            "%y" "%1$ty"})

(defn replacer
  [in-str]
  (reduce (fn [s k] (.replace s k (mapper k))) in-str (keys mapper)))


(defn -main
  [& args]
  (println (format (replacer "%s.%l") (Calendar/getInstance)))
  (println (format (replacer "%s:%m:%h %M/%d/%y") (Calendar/getInstance)))
  (println (format (replacer "The minute is %m! The hour is %h.") (Calendar/getInstance))))



