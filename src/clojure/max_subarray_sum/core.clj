(ns max-subarray-sum.core)

(def arr1 [-2 -3 4 -1 -2 1 5 -3])

(defn sub-vecs
  "generate all subvectors of a vector"
  [v]
  (for [ei (range (dec (count v)) 0 -1) ;end index
        si (range 0 ei)]                ;start index
    (subvec v si ei)))

(defn max-subcol-sum
  "find the max sub-vector sum within the passed in vector 'v'. This is the brute-force approach
   that generate all sub-vectors of the input vector and then adds the integers together and then
   finds the max"
  [v]
  (let [subvs (sub-vecs v)
        vsums (map #(vector % (apply + %)) subvs)
        res (sort-by second vsums)]
    (last res)))

