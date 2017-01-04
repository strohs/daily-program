(ns too-many-parens.core
  (:require [clojure.string :as str])
  (:require [clojure.zip :as zip]))

;;; The rule for "too many parentheses" around part of an expression is that if removing matching parentheses
;;; around a section of text still leaves that section enclosed by parentheses, then those parentheses should be
;;; removed as extraneous.

(def in1 "((C))")
(def in2 "((a((bc)(de)))f)")
(def in3 "(((zbcd)(((e)fg))))")
(def in4 "ab((c))")
(def zzs "(a b (((c d e))) (f (g h) i) j)))")


(def zz (zip/seq-zip '(a b (((c d e))) (f (g h) i) j)))

(defn trim-ends [s]
  (->> (rest s)
       (butlast)
       (apply str)))

(defn print-tree [original]
  (loop [loc (zip/seq-zip (seq original))]
    (if (zip/end? loc)
      (zip/root loc)
      (recur (zip/next
               (do (println (zip/node loc))
                   loc))))))

(defn empty-paren-loc? [loc]
  (let [par-node (zip/node loc)
        chd-node (zip/node (zip/next loc))]
    (and (seq? par-node) (seq? chd-node)
         (= (trim-ends (pr-str par-node)) (pr-str chd-node)))))

(defn replace-dups [s]
  (loop [loc (zip/seq-zip (read-string s))]
    (if (zip/end? loc)
      (zip/root loc)
      (if (empty-paren-loc? loc)
        (recur (zip/replace loc (zip/node (zip/next loc))))
        (recur (zip/next loc))))))