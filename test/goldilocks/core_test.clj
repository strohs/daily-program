(ns goldilocks.core-test
  (:require [clojure.test :refer :all]
            [goldilocks.core :refer :all]))

(deftest str-to-int
           (is (instance? Integer (str->int "123"))))

(deftest strings-to-integers
           (is (every? #(instance? Integer %) (strs->ints ["241" "5632" "-4523"]))))