(ns polygon-perimeter.core)

;;; Challenge 146 Polygon Perimeter
;;;  https://www.reddit.com/r/dailyprogrammer/comments/1tixzk/122313_challenge_146_easy_polygon_perimeter/

;;; compute the perimeter length of a regular polygon, given the circumradius and number of sides

(defn circumradius
  "s - sidelength, n - number of sides"
  [n s]
  (/ s (* 2 (Math/sin (/ Math/PI n)))))

(defn side-length
  "n - number of sides, r - circumradius"
  [n r]
  (* r 2 (Math/sin (/ Math/PI n))))

(defn perimeter [n r]
  (* n (side-length n r)))

(defn to-string [p] (format "perimeter: %.2f" p))

(defn main [radius sides] (perimeter radius sides))
;;(main 5 3.7)