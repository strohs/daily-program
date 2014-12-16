(ns damage-control.core)

;;;;  http://www.reddit.com/r/dailyprogrammer/comments/24da3f/4302014_challenge_160_intermediate_part_2_damage/

;;; :B = regular building
;;; :* = destroyed building

(def b [:B :B :B :B :B :B :B :B])
(def d [13 5 2])

(defn build [n]
  (vec (repeat n :B)))

(defn destroy [index v]
  (assoc v index :*))

(defn destroyed-indexes [v]
  (keep-indexed #(if (= %2 :*) %1) v))

(defn count-right [index v]
  (count (take-while #(not= :* %) (rest (second (split-at index v))))))

(defn count-left [index v]
  (count (take-while #(not= :* %) (reverse (first (split-at index v))))))

(defn compute
  [buildings dest-list]
  (loop [bs buildings
         ds dest-list
         cnt 0]
    (if (empty? ds)
      cnt
      (let [dindex (first ds)
            destroyed (destroy bs dindex)]
        (recur destroyed (rest ds) (+ cnt (count-left destroyed dindex) (count-right destroyed dindex)))))))
