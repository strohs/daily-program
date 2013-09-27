(ns change-calculator.core
  (:gen-class))

(defn coins
  "computes the amount of U.S. coins needed to equal the inputed 'money' amount"
  [money]
  (loop [[coin & coins] [25 10 5 1]
         amount (int (* money 100))
         result []]
    (if (zero? amount)
      result
      (recur coins (rem amount coin) (conj result (quot amount coin))))))

(defn coins-to-str [coll]
  (let [[q d n p] coll]
    (println "Quarters:" q)
    (println "Dimes:" d)
    (println "Nickles:" n)
    (println "Pennies:" p)))


(defn -main [& args]
  (coins-to-str (coins (nth args 0))))
