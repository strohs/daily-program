(ns disemvowler.trie)


(defn add [trie x]
  (assoc-in trie x (merge (get-in trie x) {:val x :terminal true})))

(defn in? [trie x]
  "Returns true if the value x exists in the specified trie."
  (:terminal (get-in trie x) false))

(defn prefix-matches [trie prefix]
  "Returns a list of matches with the prefix specified in the trie specified."
  (filter identity (map :val (tree-seq map? vals (get-in trie prefix)))))

(defn build [coll]
  "Builds a trie over the values in the specified seq coll."
  (reduce add {} coll))


(def numTree
  '((1 2)
    (1 2 3)
    (1 2 4 5 9)
    (1 2 4 10 15)
    (1 2 4 20 25)))

(def cTree
  '((\a \b)
    (\a \b \c)
    (\a \b \d \e \i)
    (\a \b \d \j \o)
    (\a \b \d \t \y)))

(def words ["a" "ad" "add" "adder" "addition" "abaco" "abbess" "abby" "al" "all" "algo" "also"])