(ns real-world-merge.core)

;;; Challenge 126 - Real World Merge Sort
;;; https://www.reddit.com/r/dailyprogrammer/comments/1epasu/052013_challenge_126_easy_realworld_merge_sort/

;;real world merge sort. The idea is to sort 'alist' into 'blist' using only the "0" spaces in blist
;;to hold an item from alist

(def alist [692 1 32 8])
(def blist [0 0 0 0 14 15 123 2431])

(defn swap
  "swap the values at indices i1 and i2 in the vector v"
  [v i1 i2]
  (assoc (assoc v i1 (v i2)) i2 (v i1)))

(defn bubble [col idx]
  (if-let [nidx (if (< (+ idx 1) (count col)) (+ idx 1) nil)]
    (if (<= (get col idx) (get col nidx))
      col
      (bubble (swap col idx nidx) nidx))
    col))

(defn merge-sort [as bs]
  (if (not-any? #(= % 0) bs)
    bs
    (let [idx0 (.lastIndexOf bs 0)
          mitem (first as)
          sorted-col (bubble (assoc bs idx0 mitem) idx0)]
      (merge-sort (rest as) sorted-col))))

(defn main [as bs] (merge-sort as bs))
;;(main alist blist)

