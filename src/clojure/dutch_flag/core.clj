(ns dutch-flag.core)

(def colors [:red :white :blue])

;collection with number of each color equal
;(def col (flatten (repeatedly 10 #(shuffle colors))))

(def col (repeatedly 30 #(rand-nth colors)))

(sort-by #(.indexOf colors %) < col)

(defn -main [n]
  (def col (repeatedly n #(rand-nth colors)))
  (sort-by #(.indexOf colors %) < col))