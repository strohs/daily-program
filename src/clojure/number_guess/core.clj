(ns number-guess.core
  (:gen-class))

;;; The number guess challenge fro Clojure Programming
;;;  run with (-main)

(defn get-input []
  (let [input (Integer/parseInt (read-line))]
    input))

(defn valid-input? [input]
  (if (re-matches #"\d{1,3}" input) true false))

(defn get-rand-num []
  (rand-nth (range 1 101)))

(defn check-guess [guess rand-num]
  (if (< guess rand-num) "higher" "lower"))

(defn acc-guess [guess col]
  (conj col guess))

(defn -main [& args]
  (let [rand-num (get-rand-num)]
    (println "Enter a guess between [1, 100]")
    (loop [guess (get-input) acc []]
      (cond
        (= guess rand-num) (str "You guessed the number! " rand-num " after the following guesses" acc)
        :else (do
                (println "guess" (check-guess guess rand-num))
                (recur (get-input) (conj acc guess)))))))