(ns repulsion-force.core)

;;; Challenge 183 Repulsion Force
;;; https://www.reddit.com/r/dailyprogrammer/comments/1ml669/091713_challenge_138_easy_repulsionforce/

;Force = (Particle 1's mass x Particle 2's mass) / Distance^2
;deltaX = (Particle 1's x-position - Particle 2's x-position)
;deltaY = (Particle 1's y-position - Particle 2's y-position)
;Distance = Square-root( deltaX * deltaX + deltaY * deltaY )

(defn deltax [x1 x2] (- x1 x2))
(defn deltay [y1 y2] (- y1 y2))

(defn distance [x1 y1 x2 y2]
  (Math/sqrt (+ (Math/pow (deltax x1 x2) 2.0) (Math/pow (deltay y1 y2) 2.0))))

(defn decimal-format [d] (format "%3.4f" d))

(defn repulsion-force [p1 p2]
  (let [{m1 :m x1 :x y1 :y} p1
        {m2 :m x2 :x y2 :y} p2]
    (/ (* m1 m2) (Math/pow (distance x1 y1 x2 y2) 2.0))))

(def particles1 [{:m 4 :x 0.04 :y  -0.02}]
                {:m 4 :x -0.02 :y -0.03})

(defn main [[pmap1 pmap2]]
  (decimal-format (repulsion-force pmap1 pmap2)))

;;(main particles1)

