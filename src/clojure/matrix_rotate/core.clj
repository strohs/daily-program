(ns matrix-rotate.core)

;;; Given a NxN size 2D array of numbers. Develop a way to rotate the data as if you rotated the data by 90 degrees
;;; clockwise.

(def m3 [[1 2 3]
         [4 5 6]
         [7 8 9]])

(def m10 [ [1 2 3 4 5 6 7 8 9 0]
           [0 9 8 7 6 5 4 3 2 1]
           [1 3 5 7 9 2 4 6 8 0]
           [0 8 6 4 2 9 7 5 3 1]
           [0 1 2 3 4 5 4 3 2 1]
           [9 8 7 6 5 6 7 8 9 0]
           [1 1 1 1 1 1 1 1 1 1]
           [2 2 2 2 2 2 2 2 2 2]
           [9 8 7 6 7 8 9 8 7 6]
           [0 0 0 0 0 0 0 0 0 0]])

(defn transpose
  "transpose a matrix (a vector of vectors)"
  [matrix]
  (apply mapv vector matrix))

(defn rotate-90 [matrix]
  (mapv reverse (transpose matrix)))

(->> m3
     (rotate-90)
     (rotate-90))