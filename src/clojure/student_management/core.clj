(ns student-management.core
  (:require [clojure.java.io :as io]))

;;; Challenge 136 Student Management
;;;  https://www.reddit.com/r/dailyprogrammer/comments/1kphtf/081313_challenge_136_easy_student_management/

(def lines (line-seq (io/reader "src/clojure/student_management/grades.txt")))

;;(def num-students (Integer. (second (re-matches #"(\d+) (\d+)" (first lines)))))

(defn average [nums]
  (let [grades (map #(Integer. %) nums)]
    (double (/ (apply + grades) (count grades)))))

(defn student-name [line] (re-find #"\w+" line))

(defn main []
  (doseq [line lines]
    (println (format "%s %3.2f" (student-name line) (average (re-seq #"\d+" line))))))