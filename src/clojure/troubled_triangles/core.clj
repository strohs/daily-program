(ns troubled-triangles.core)

(def ins {:a 3 :b 4 :c nil :A nil :B nil :C 90})

(defn rads [n] (Math/toRadians n))
(defn degs [n] (Math/toDegrees n))

(defn side-count [{:keys [a b c]}]
  (count (filter some? (vector a b c))))

(defn angle-count [{:keys [A B C]}]
  (count (filter some? (vector A B C))))

(defn los-side
  "use law of sines to compute a side when a side and two (or more angles are known)"
  [side angle1 angle2]
  (with-precision 4 (* (Math/sin (rads angle1)) (/ side (Math/sin (rads angle2))))))

(defn los-angle
  "use law of sines to compute value of an angle when an angle and at least two sides are known"
  [angle side1 side2]
  (with-precision 4 (degs (Math/asin (* side1 (/ (Math/sin (rads angle)) side2))))))

(defn right-triange-angle
  "compute a missing angle of a right triangle using arcsin and 2 sides"
  [s1 s2]
  (with-precision 4 (degs (Math/asin (/ s1 s2)))))

(defn pt-hyp [s1 s2] (with-precision 4 (Math/sqrt (+ (* s1 s1) (* s2 s2)))))
(defn pt-side [c side] (with-precision 4 (Math/sqrt (- (* c c) (* side side)))))

;(defn get-kvs
;  "return a vector of KV pairs"
;  [m ks]
;  (reduce (fn [acc k]
;            (if (k m)
;              (conj acc (vector k (k m)))
;              acc))
;          [] ks))
;
;(defn sides "return tuples of non nil sides" [m]
;  (get-kvs m [:a :b :c]))
;
;(defn angles "return tuples of non nil angles" [m]
;  (get-kvs m [:A :B :C]))


(defn solve-two-angles [{:keys [A B] :as m}]
  ; angle C will always be 90 degrees
  ; when two angles are known: 90 - known angle = unknown angle
  (cond
    (nil? A) (assoc m :A (- 90 B))
    (nil? B) (assoc m :B (- 90 A))
    :else m))

(defn solve-two-sides [{:keys [a b c] :as m}]
  ; use Pyth Theorem to solve third side
  (cond
    (nil? a) (assoc m :a (pt-side c b))
    (nil? b) (assoc m :b (pt-side c a))
    (nil? c) (assoc m :c (pt-hyp a b))
    :else m))


(defn solve-one-side
  "use Law of Sines to compute a missing side length.assumes at least two angles were given (and third angle has been computed"
  [{:keys [a b c A B C] :as m}]
  (cond
    (and (nil? a) (some? b)) (assoc m :a (los-side b A B))
    (and (nil? a) (some? c)) (assoc m :a (los-side c A C))
    (and (nil? b) (some? a)) (assoc m :b (los-side a B A))
    (and (nil? b) (some? c)) (assoc m :b (los-side c B C))
    (and (nil? c) (some? a)) (assoc m :c (los-side a C A))
    (and (nil? c) (some? b)) (assoc m :c (los-side b C B))
    :else m))

(defn solve-one-angle
  "compute a missing angle if at least two sides were given, uses trig functions to compute the angle"
  [{:keys [a b c A B C] :as m}]
  (cond
    (nil? A) (assoc m :A (right-triange-angle a c))
    (nil? B) (assoc m :B (right-triange-angle b c))
    (nil? C) (assoc m :C 90)                                ; right triangles only, C must = 90
    :else m))

(defn solve-triangle [{:keys [a b c A B C] :as m}]
  (let [sides (side-count m)
        angles (angle-count m)]
    (cond
      (not-any? nil? (vals m)) m                             ;base case, all sides and angles given, return the map
      (= 2 angles) (recur (solve-two-angles m))
      (= 2 sides) (recur (solve-two-sides m))
      (= 1 sides) (recur (solve-one-side m))
      (= 1 angles) (recur (solve-one-angle m))
      :else "unknown triangle given")))

