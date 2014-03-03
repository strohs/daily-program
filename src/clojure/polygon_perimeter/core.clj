(ns polygon-perimeter.core)

;compute the perimeter length of a regular polygon, given the circumradius and number of sides

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