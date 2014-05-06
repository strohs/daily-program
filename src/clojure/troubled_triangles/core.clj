(ns troubled-triangles.core)

(def ins {:a nil :b nil :c 7 :A 43 :B nil :C 70})
(def sm {:a 6.85 :b 5.08 :c 7 :A nil :B nil :C nil})

(defn rads [n] (Math/toRadians n))
(defn degs [n] (Math/toDegrees n))

(defn side-count [{:keys [a b c]}]
  (count (filter some? (vector a b c))))

(defn angle-count [{:keys [A B C]}]
  (count (filter some? (vector A B C))))

(defn los-side
  "use law of sines to compute a side when a side and two (or more) angles are known"
  [side angle1 angle2]
  (with-precision 4 (* (Math/sin (rads angle1)) (/ side (Math/sin (rads angle2))))))

(defn los-angle
  "use law of sines to compute value of an angle when an angle and at least two sides are known"
  [angle side1 side2]
  (with-precision 4 (degs (Math/asin (* side1 (/ (Math/sin (rads angle)) side2))))))

;(defn right-triangle-angle
;  "compute a missing angle of a right triangle using arcsin and 2 sides"
;  [s1 s2]
;  (with-precision 4 (degs (Math/asin (/ s1 s2)))))

;;right triangle formulas using pythagorean theorem
;(defn pt-hyp [s1 s2] (with-precision 4 (Math/sqrt (+ (* s1 s1) (* s2 s2)))))
;(defn pt-side [c side] (with-precision 4 (Math/sqrt (- (* c c) (* side side)))))

(defn loc-side
  "use law of cosines to compute length of a missing side if given two sides and the angle opposite those sides"
  [s1 s2 angle] (with-precision 4 (Math/sqrt (-
                                               (+ (* s1 s1) (* s2 s2))
                                               (* 2 s1 s2 (Math/cos (rads angle)))))))

(defn loc-angle
  "use law of cosines to solve a missing angle, assumes all three sides are known"
  [a b c]
  (with-precision 4 (degs (Math/acos (/ (-
                                          (+ (* b b) (* c c))
                                          (* a a))
                                        (* 2 b c))))))

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


(defn solve-two-angles [{:keys [A B C] :as m}]
  ; when two angles are known: 180 - A1 - A2 = unknown angle
  (cond
    (nil? A) (assoc m :A (- 180 B C))
    (nil? B) (assoc m :B (- 180 A C))
    (nil? C) (assoc m :C (- 180 A B))
    :else m))

;(defn solve-two-sides [{:keys [a b c] :as m}]
;  ; use Pyth Theorem to solve third side
;  (cond
;    (nil? a) (assoc m :a (pt-side c b))
;    (nil? b) (assoc m :b (pt-side c a))
;    (nil? c) (assoc m :c (pt-hyp a b))
;    :else m))


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
  "compute a missing angle if all three sides are known"
  [{:keys [a b c A B C] :as m}]
  (cond
    (nil? A) (assoc m :A (loc-angle a b c))
    (nil? B) (assoc m :B (loc-angle b a c))
    (nil? C) (assoc m :C (loc-angle c b a))
    :else m))

(defn solve-triangle [{:keys [a b c A B C] :as m}]
  (let [sides (side-count m)
        angles (angle-count m)]
    (cond
      (not-any? nil? (vals m)) m                             ;base case, all sides and angles given, return the map
      (= 2 angles) (recur (solve-two-angles m))
                                                             ;(= 2 sides) (recur (loc-side m))
      (<= sides 2) (recur (solve-one-side m))
      (<= angles 1) (recur (solve-one-angle m))
      :else "unknown triangle given")))

