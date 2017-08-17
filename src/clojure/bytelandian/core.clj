(ns bytelandian.core)

;;; Challenge 121 Bytelandian exchange - Intermediate
;;; https://www.reddit.com/r/dailyprogrammer/comments/1a7ca0/031313_challenge_121_intermediate_bytelandian/
;;; run with (-main n) where n is the value of the coin

(defmacro memoize-fn
  "Produces a memoized anonymous function that can recursively call itself."
  [fn-name & fn-args]
  `(with-local-vars
     [~fn-name (memoize
                (fn ~@fn-args))]
     (.bindRoot ~fn-name @~fn-name)
     @~fn-name))


(defn exchange [n]
  (if (= n 0)
    1
    (+ (exchange (quot n 2)) (exchange (quot n 3)) (exchange (quot n 4)))))

(defn profit* [n]
  (let [n2 (quot n 2)
        n3 (quot n 3)
        n4 (quot n 4)]
    (if (> n (+ n2 n3 n4))
      n
      (+ (profit* n2) (profit* n3) (profit* n4)))))

;uses the macro for memoization with recursion
(def mp (memoize-fn profit [n]
          (let [n2 (quot n 2)
                n3 (quot n 3)
                n4 (quot n 4)]
            (if (> n (+ n2 n3 n4))
              n
              (+ (profit n2) (profit n3) (profit n4))))))

(def profit (memoize (fn [n]
                         (let [n2 (quot n 2)
                               n3 (quot n 3)
                               n4 (quot n 4)]
                           (if (> n (+ n2 n3 n4))
                             n
                             (+ (profit n2) (profit n3) (profit n4)))))))

(defn -main [n] (profit n))