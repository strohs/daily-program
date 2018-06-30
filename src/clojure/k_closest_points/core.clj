(ns k-closest-points.core)

;;; given an array of x,y integer pairs representing 2D coordinates, find the k closest pairs to the origin (0,0)

;{ {-2,-4},{0,-2}, {-1,0}, {3,-5}, {-2,-3}, {3,2} }
(def points1 [[-2 -4] [0 -2] [-1 0] [-3 5] [-2 -3] [3 2]])

(defn distance-to-origin
  "euclidian distance between a point and the origin"
  [[x y]]
  (let [xd (* x x)
        yd (* y y)]
    (int (Math/sqrt (+ xd yd)))))

(defn k-closest-points
  "find the k closest points to the origin. points should be a vector of x,y integer tuples"
  [points k]
  (take k (sort-by distance-to-origin points)))




