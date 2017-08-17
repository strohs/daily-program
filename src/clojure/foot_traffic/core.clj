(ns foot-traffic.core
  (:require [clojure.string :as cs]))

;;; Challenge 133 Foot Traffic Analysis
;;;  https://www.reddit.com/r/dailyprogrammer/comments/1iambu/071513_challenge_133_easy_foottraffic_analysis/
;;;  run (-main)

(def file-path "src/clojure/foot_traffic/sample2.txt")

(defn file-lines [f]
  (with-open [rdr (clojure.java.io/reader f)]
    (reduce conj [] (line-seq rdr))))

(defn map-line [line]
  (let [[id room type time] (clojure.string/split line #" ")]
    {:visitor (Integer. id), :room (Integer. room), :type type, :time (Integer. time)}))

(def data (map map-line (file-lines file-path)))

(defn num-visitors [stats]
  (count (into #{} (map :visitor stats))))

(defn visitor-los [visitor-stats]
  (let [time-in (:time  (first (filter #(= "I" (:type %1)) visitor-stats)))
        time-out (:time (first (filter #(= "O" (:type %1)) visitor-stats)))]
    (- time-out time-in)))

(defn length-of-stay [stats]
  (let [visitor-stats (group-by :visitor stats)
        los (for [[id vstats] visitor-stats] (visitor-los vstats))]
    (/ (reduce + los) (count (keys visitor-stats)))))

(defn run []
  (let [room-stats (into (sorted-map) (group-by :room data))]
    (doseq [[id stats] room-stats]
      (println (format "Room %s, %s minute average visit, %s visitors total" id (length-of-stay stats) (num-visitors stats))))))

(defn -main [] (run))