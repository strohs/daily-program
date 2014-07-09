(ns blackjack-checker.core
  (:require [clojure.string :as str]))

(def rank->val {:two 2 :three 3 :four 4 :five 5 :six 6 :seven 7 :eight 8 :nine 9 :ten 10 :jack 10 :queen 10 :king 10 :ace 11})

(def inputs
  ["Alice: Ace of Diamonds, Ten of Clubs"
   "Bob: Three of Hearts, Six of Spades, Seven of Spades"])


(defn map-card [card]
  (let [[rank suit] (str/split card #" of ")]
    {:rank (keyword rank) :suit (keyword suit) :value (get rank->val (keyword rank))}))

(defn parse-input [s]
  (let [[player hand] (str/split s #":")
        cards (map (comp str/trim str/lower-case) (str/split hand #","))]
    {:player player :hand (mapv map-card cards)}))
