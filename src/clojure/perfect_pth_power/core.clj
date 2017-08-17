(ns perfect-pth-power.core)

;;; Challenge 126 Perfect Pth power
;;; https://www.reddit.com/r/dailyprogrammer/comments/1fcpnx/053013_challenge_126_intermediate_perfect_pth/

;;; Your goal is to find the highest value of P for a given X such that for some unknown integer Y, Y^P should equal X.
;;;  log y(17) = p


; change of base formula  log b (x) = log 10 (x) / log 10 (b)
(defn logbx [x b]
  (/ (Math/log x) (Math/log b)))

(defn perfect-p [x]
  (loop [y 2]
    (let [p (logbx x y)]
      (cond
        (= (float p) (Math/rint p)) (Math/round p)
        (< p 2) 1
        :else (recur (inc y))))))

(defn main [x] (perfect-p x))

;;(main 17)
;;(main 1073741824)
;;(main 25)