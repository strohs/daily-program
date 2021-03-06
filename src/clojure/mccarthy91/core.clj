(ns mccarthy91.core)

;;; Challenge 127  McCarthy91 function
;;; https://www.reddit.com/r/dailyprogrammer/comments/1f7qp5/052813_challenge_127_easy_mccarthy_91_function/

(defn m [n]
  (if (> n 100)
    (do
      (println "M(" (- n 10) ") since" (- n 10) "is greater than 100")
      (- n 10))
    (do
      (println "M(" (+ n 11) ") since" n "is equal to or less than 100")
      (m (m (+ n 11))))))

(defn -main [n]
  (println "M(" n ")")
  (m n))

;;(-main 99)

