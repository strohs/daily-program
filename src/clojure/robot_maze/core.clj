(ns robot-maze.core)

;; this maze has a loop
(def maze1
  [["X" "X" "X" "X" "X" "X" "X" "X"]
   ["X" "X" " " " " " " " " " " "X"]
   [" " " " " " "X" " " "X" " " "X"]
   ["X" " " "X" "X" " " "X" " " "X"]
   ["X" " " "X" "X" " " " " " " "X"]
   ["X" " " "X" "X" "X" " " "X" "X"]
   ["X" " " "X" "X" "X" "f" "X" "X"]
   ["X" "X" "X" "X" "X" "X" "X" "X"]])

(defn print-maze [maze]
  (doseq [row maze]
    (println (apply str row))))

(defn facing->str [facing]
  (case facing
    :U "^"
    :R ">"
    :D "v"
    :L "<"))

(defn print-robot [rob]
  (let [ {:keys [maze r c facing]} rob
         m (assoc-in maze [r c] (facing->str facing))]
    (print-maze m)))

;; maze functions
(defn wall? [maze r c] (= "X" (get-in maze [r c] "X")))
(defn blank? [maze r c] (= " " (get-in maze [r c] "X")))
(defn finish? [maze r c] (= "f" (get-in maze [r c] "X")))
(defn next-rc
  "return a vector of new maze row,col indices caused by moving in the current facing. r and c are the current row and column"
  [r c facing]
  (case facing
    :U [(dec r) c]
    :R [r (inc c)]
    :D [(inc r) c]
    :L [r (dec c)]))

(defprotocol PRobot
  "robot implementation"
  (can-go? [this] "can the robot move in the direction it is facing")
  (turn-right [this] "turn the robot clockwise direction to the right")
  (turn-left [this])
  (at-finish? [this] "is the robot on the finish square")
  (go [this] "move one square in the current direction the robot is facing"))

;; facing is a one of four keywords:   :U :R :D :L
(defrecord Robot [r c facing maze]
  PRobot
  (can-go? [robot]
    (let [[nr nc] (next-rc r c facing)]
      (not (wall? maze nr nc))))
  (turn-right [robot]
    (case facing
      :U (Robot. r c :R maze)
      :R (Robot. r c :D maze)
      :D (Robot. r c :L maze)
      :L (Robot. r c :U maze)))
  (turn-left [robot]
    (case facing
      :U (Robot. r c :L maze)
      :R (Robot. r c :U maze)
      :D (Robot. r c :R maze)
      :L (Robot. r c :D maze)))
  (at-finish? [robot]
    (finish? maze r c))
  (go [robot]
    (let [[new-row new-col] (next-rc r c facing)]
      (Robot. new-row new-col facing maze))))

(defn wall-on-right? [robot]
  (not (can-go? (turn-right robot))))
(defn wall-on-left? [robot]
  (not (can-go? (turn-left robot))))

;;This solve use right hand rule, doesn't work for mazes with loops
(defn solve [robot]
  (loop [rob robot]
    (print-robot rob)
    (println "----------------------------")
    (when-not (at-finish? rob)
      (cond
        (can-go? (turn-right rob)) (recur (go (turn-right rob)))
        (and (wall-on-right? rob) (can-go? rob)) (recur (go rob))
        ;(can-go? rob) (recur (go rob))
        :else (recur (turn-left rob))))))
