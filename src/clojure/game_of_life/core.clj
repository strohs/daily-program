(ns game-of-life.core
  (:gen-class))

(def grid [[0 0 0]
           [1 1 1]
           [0 0 0]])

(def block [[0 0 0 0]
            [0 1 1 0]
            [0 1 1 0]
            [0 0 0 0]])

(def glider [[0 0 1 0 0 0 0 0]
             [1 0 1 0 0 0 0 0]
             [0 1 1 0 0 0 0 0]
             [0 0 0 0 0 0 0 0]
             [0 0 0 0 0 0 0 0]
             [0 0 0 0 0 0 0 0]])

(defn step-sugar [grid range]
  (let [count-neighbours ;; determine number of neighbours
        (fn[[i j]]
          (reduce + (map #(get-in grid % 0)
                      (for[a [-1 0 1] b [-1 0 1]]
                        [(+ i a) (+ j b)]))))
        new-value  ;; calculate new value for cell
        (fn [v [i j]]
          (let [c (- (count-neighbours [i j]) v)]
            (cond (= 3 c) 1
              (= 2 c) v
              :else 0)))
        evolve-cell ;; update cell in a grid
        (fn[g i] (update-in g i #(new-value % i)))]
    (reduce evolve-cell grid range)))

(defn evolution [g]
  (let [p (count g) q (count (get g 0))
        range (for [i (range p) j (range q)][i j])]
    (iterate #(step-sugar % range) g)))

(defn print-grid [g]
  (doseq [s (map #(apply str (replace {0 "." 1 "⚫"} %)) g)]
    (println s)))


(defn run [grid]
  (doseq [g (evolution grid)]
    (print-grid g)
    (Thread/sleep 1000)))

(defn -main [& args]
  (run glider))
