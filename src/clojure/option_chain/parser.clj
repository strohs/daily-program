(ns option-chain.parser
  (require [clojure-csv.core :as csv]))

;;;; parse option quote sheet data from e-signal Q-Charts. The Data should be in the 'Single' 'Detailed' 'American' format

(def ^:dynamic *file-path* "C:\\Users\\Cliff\\Documents\\option montage.csv")


;; parsed csv file comes back as a list of vectors
;; [Title Open High Low Last Volume 'Tick Vol' 'Open Int' 'Bid' 'Ask' 'Bid/Ask Spread' 'Net' 'Dys Left' 'Time Val' 'Intrin.Val.' 'Type']

(defn as-float [s] (if (empty? s) 0.0 (Float/parseFloat s)))
(defn as-int [s] (if (empty? s) 0 (Integer/parseInt s)))

(defn parse-symbol
  "parse an option title/symbol string into its parts"
  [symbol]
  (let [[sym ticker exp-date strike type] (re-matches #"(\w+) (\w+) (\d+?.\d*) (\w+)" symbol)]
    {:ticker ticker :exp-date exp-date :strike strike}))

(defn clean-row
  "clean up a row of CSV data, parse numeric strings into ints and floats as needed
  [Title Open High Low Last Volume 'Tick Vol' 'Open Int' 'Bid' 'Ask' 'Bid/Ask Spread' 'Net' 'Dys Left' 'Time Val' 'Intrin.Val.' 'Type']
  [AAPL Aug14 100 Call 2.06 2.41 2.04 2.33 5243 103 28609 2.33 2.37 0.04 +0.53  0.24 2.08 Call]"
  [[symbol open high low last vol tick-vol open-int bid ask bid-ask-spread net days-left time-value intr-value type]]
  (let [symbol-map (parse-symbol symbol)
        ticker (symbol-map :ticker)
        strike (symbol-map :strike)
        exp-date (symbol-map :exp-date)]
    {:symbol symbol :ticker ticker :exp-date exp-date :strike (as-float strike)
     :bid (as-float bid) :ask (as-float ask) :bid-ask-spread (as-float bid-ask-spread) :days-left (as-int days-left)}))


(defn percent-return [buy strike bid]
  (* (/ (+ (- strike buy) bid) buy) 100))

(defn print-col-headers []
  (format "%8s %7s %7s %6s %5s %6s %7s" "TICKER" "EXP-MNTH" "STRIKE" "BID" "ASK" "DAYS" "PCT-RET"))

(defn format-row [row]
  (let [{:keys [ticker exp-date strike bid ask days-left percent-return]} row]
    (format "%7s %7s    %06.2f   %05.2f %05.2f  %3d   %5.2f%%" ticker exp-date strike bid ask days-left percent-return)))

(defn with-percent-return [data buy]
  (for [row data
        :let [{:keys [strike bid]} row]]
    (assoc-in row [:percent-return] (percent-return buy strike bid))))

(defn parse [path buy ticker]
  (let [ticker (.toUpperCase ticker)
        csv (csv/parse-csv (slurp path))
        rows (filter #(= ticker (get % :ticker)) (with-percent-return (map #(clean-row %) (rest csv)) buy))]
    (println (print-col-headers))
    (doseq [row (sort-by :percent-return > rows)]
      (println (format-row row)))))

