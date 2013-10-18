(ns gcd.core)

(defn gcd [n1 n2]
  (if (= (rem n1 n2) 0)
    n2
    (recur n2 (rem n1 n2))))

(defn -main [& args]
  (println "Enter the numbers:")
  (let [in (read-line)
        [n1 n2] (clojure.string/split in #" ")]
    (gcd (Integer. n1) (Integer. n2))))
