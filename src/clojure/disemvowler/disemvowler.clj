(ns disemvowler.disemvowler)

;; Disemvoweling means removing the vowels from text. (For this challenge, the letters a, e, i, o, and u are considered
;; vowels, and the letter y is not.) The idea is to make text difficult but not impossible to read, for when somebody
;; posts something so idiotic you want people who are reading it to get extra frustrated.
;; To make things even harder to read, we'll remove spaces too. For example, this string:
;; two drums and a cymbal fall off a cliff
;; can be disemvoweled to get:
;; twdrmsndcymblfllffclff
;; We also want to keep the vowels we removed around (in their original order), which in this case is:
;; ouaaaaoai


(def vowels #{\a \e \i \o \u})
(def vowels-spaces (conj vowels \space))

(defn remove-vowels
  "filter the charactes in the char-set from the string s"
  [s]
  (remove #(contains? vowels-spaces %) s))

(defn disemvowler
  [strings]
  (doseq [s strings]
    (println (apply str (remove-vowels s vowels-spaces)))
    (println (apply str (filter vowels s)))
    (println "------------------------------")))

