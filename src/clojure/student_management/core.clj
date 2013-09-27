(ns student-management.core
  (:require [clojure.java.io :as io]))

(def lines (line-seq (io/reader "/Users/cliff/IdeaProjects/daily-program/src/clojure/student_management/grades.txt")))

;;(def num-students (Integer. (second (re-matches #"(\d+) (\d+)" (first lines)))))

(defn average [nums]
  (let [grades (map #(Integer. %) nums)]
    (double (/ (apply + grades) (count grades)))))

(defn student-name [line] (re-find #"\w+" line))

(doseq [line lines]
  (println (format "%s %3.2f" (student-name line) (average (re-seq #"\d+" line)))))