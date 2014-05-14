(ns blackjack.core)

(def suits #{:C :D :H :S})
(def ranks #{:2 :3 :4 :5 :6 :7 :8 :9 :10 :J :Q :K :A})
(def rank->val {:2 2 :3 3 :4 4 :5 5 :6 6 :7 7 :8 8 :9 9 :10 10 :J 10 :Q 10 :K 10 :A 11})

(defn build-deck []
  (for [suit suits
        rank ranks]
    {:suit suit :rank rank}))

(defn print-card [{suit :suit rank :rank}]
  (str (name suit) (name rank)))

(defn print-hand [hand]
  (map #(str (print-card %)) hand))

(defn ace-count [cards]
  (count (filter #(= :A (:rank %)) cards)))

(defn sum [cards]
  (let [sum (reduce (fn [sum {rank :rank}]
                      (+ sum (rank rank->val)))
                    0 cards)]
    (if (> (ace-count cards) 1)                       ;if the cards have more than one Ace, one of them will count as 1
      (- sum 10)
      sum)))

(defn blackjack? [cards]
  (= 21 (sum cards)))

(defn stay? [cards]
  (> (sum cards) 11))

(defn into-hands [cards]
  (let [hands (transient [])]
    (reduce (fn [acc card]
              (let [hand (conj acc card)]
                (if (or (blackjack? hand) (stay? hand))
                  (do
                    (conj! hands hand)
                    (empty acc))
                  hand))) [] cards)
    (persistent! hands)))

(let [ deck (shuffle (flatten (repeatedly 2 build-deck)))
       hands (into-hands deck)
       blackjacks (count (filter blackjack? hands))
       bj-percent (with-precision 4 (* 100 (/ (bigdec blackjacks) (count hands))))]
  (doseq [hand hands]
    (println (print-hand hand) (if (blackjack? hand) "blackjack!" "")))
  (println (format "%d hands with %d blackjacks at %.2f percent" (count hands) blackjacks bj-percent)))