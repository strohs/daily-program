(ns chain-reaction.core)

;;; NOTE: this challenge is not working

;x is row  y is column
;test a Java version of this
(def data {
                :A {:x 0 :y 0 :r 5 :d "udlr" :reacted? false}
                :B {:x 0 :y 4 :r 5 :d "ud" :reacted? false}
                :C {:x 2 :y 4 :r 2 :d "lr" :reacted? false}
                :D {:x 3 :y 2 :r 3 :d "udlr" :reacted? false}})


(def dir->fn { :u up? :d down? :l left? :r right?})

(defn empty-matrix [n]
  (vec (repeatedly n #(vec (repeat n " ")))))

(defn data->2d [data]
  (reduce
    (fn [col k]
      (let [{{x :x y :y r? :reacted?} k} data]
        (if r?
          (assoc-in col [x y] "X")
          (assoc-in col [x y] (name k)))))
    (empty-matrix (count data))
    (keys data)))

(defn dir->keys [s]
  (into [] (map keyword (map str (seq s)))))

(defn to-string [data]
  (doseq [r (data->2d data)] (println (apply str r))))

(defn reacted? [k data] (get-in data [k :reacted?]))

(defn not-reacted
  "get elements that haven't reacted"
  [data]
  (reduce
    (fn [result [key value]]
      (if (not (:reacted? value))
        (assoc result key value)
        result))
    {}
    data))

(defn up?
  "is target element, t, within the up radius of source element, s"
  [s t]
  (let [{sx :x sy :y sr :r} s
        {tx :x ty :y tr :r} t
        distance (- sx tx)]
    (if (and (= sy ty) (>= distance 0) (>= sr distance))
      true
      false)))

(defn down?
  "is target element, t, within the down radius of source element, s"
  [s t]
  (let [{sx :x sy :y sr :r} s
        {tx :x ty :y tr :r} t
        distance (- tx sx)]
    (if (and (= sy ty) (>= distance 0) (>= sr distance))
      true
      false)))

(defn left?
  "is target element, t, within the left radius of source element, s"
  [s t]
  (let [{sx :x sy :y sr :r} s
        {tx :x ty :y tr :r} t
        distance (- sy ty)]
    (if (and (= sx tx) (>= distance 0) (>= sr distance))
      true
      false)))

(defn right?
  "is target element, t, within the right radius of source element, s"
  [s t]
  (let [{sx :x sy :y sr :r} s
        {tx :x ty :y tr :r} t
        distance (- ty sy)]
    (if (and (= sx tx) (>= distance 0) (>= sr distance))
      true
      false)))

;concat c1 c2 &c
;(group-by #(up? e1 %) @data)
;(.contains str1 str2)
;(propagate-dir :B {:D {:r 3, :y 2, :x 3, :d "udlr", :reacted? false}, :C {:r 2, :y 4, :x 2, :d "lr", :reacted? false}} (:u dir->fn))

(defn propagate-dir
  "returns a map of data elements that were affected by the propagation"
  [sk data dirf]
  (reduce
    (fn [result [key value]]
      (if (and (not (reacted? key data) ) (dirf (sk data) (key data)))
        (assoc result key (assoc value :reacted? true))
        result))
    {}
    data))

(defn propagate [k data]
  (loop [dirs (dir->keys (get-in data [k :d]))
         reacted {}]
    (if (empty? dirs)
      reacted
      (let [dir (first dirs)]
        (recur (rest dirs) (propagate-dir k data (dir dir->fn)))))))

(defn react [init-key data]
  (loop [elements data
         ekey init-key
         reacted (hash-map ekey (ekey elements))]
    (to-string elements)
    (if (empty? reacted)
      elements
      (let [reacted-elements (propagate ekey elements)
            new-elements (dissoc reacted-elements ekey)]
        (recur (merge elements reacted-elements) (ffirst new-elements) new-elements)))))

(defn p75? []
  (<= (rand-int 100) 74))