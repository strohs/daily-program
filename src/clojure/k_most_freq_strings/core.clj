(ns k-most-freq-strings.core)

;;given an array of strings, find the 'k' strings that occur most often in the array

(def s1 ["a","b","c","d","e","f","a","b","a","g","h","c","a","c"])

(defn k-freq-strings
  "strings is a collection of strings, k is the number of most frequent strings to return"
  [strings k]
  (->> strings
       (frequencies)   ;;O(n)
       (sort-by val >) ;;O(nlogn) uses java Arrays.sort
       (take k)))

