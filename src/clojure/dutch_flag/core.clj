(ns dutch-flag.core)

;;; https://en.wikipedia.org/wiki/Dutch_national_flag_problem
;;; run with  (-main n) where n is the total number of 'balls', each ball representing a color

(def colors [:red :white :blue])

;collection with number of each color equal
;(def col (flatten (repeatedly 10 #(shuffle colors))))

(def col (repeatedly 30 #(rand-nth colors)))

(sort-by #(.indexOf colors %) < col)

(defn -main [n]
  ;; build a collection of random colors from colors
  (def col (repeatedly n #(rand-nth colors)))
  ;;sort the colors based on their index in colors
  (sort-by #(.indexOf colors %) < col))