(ns percent-return.core)

(defn pr [ buy strike bid ]
  (* (/ (+ (- strike buy) bid) buy ) 100))

(println (format "%3.2f" (pr 182.19 180 12.20) ) )
(println (format "%3.2f" (pr 182.19 185 7.60) ) )
(println (format "%3.2f" (pr 182.19 190 3.75) ) )