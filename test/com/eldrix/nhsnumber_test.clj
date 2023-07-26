(ns com.eldrix.nhsnumber-test
  (:require [clojure.string :as str]
            [clojure.test :refer :all]
            [com.eldrix.nhsnumber :as nnn]))

(def valid-examples
  ["1111111111"
   "6328797966"
   "6148595893"
   "4865447040"
   "4823917286"])

(def invalid-examples
  [""
   " "
   "4865447041"
   "a4785"
   "1234567890"
   "111 111 1111"
   "          "])

(deftest test-valid
  (doseq [nnn valid-examples]
    (is (nnn/valid? nnn))))

(deftest test-invalid
  (doseq [nnn invalid-examples]
    (is (not (nnn/valid? nnn)))))

(deftest test-normalise
  (is (= (nnn/normalise "123 123 4567") "1231234567"))
  (is (nnn/valid? (nnn/normalise "111 111 1111"))))

(deftest test-format
  (is (= (nnn/format-nnn "1231234567") "123 123 4567")))

(deftest test-random-seq
  (let [xs (take 10000 (nnn/random-sequence 999))]
    (is (every? nnn/valid? xs))
    (is  (every? true? (map #(str/starts-with? % "999") xs)))))

(deftest test-consecutive-seq
  (let [xs (take 10000 (nnn/ordered-sequence 999))]
    (is (every? nnn/valid? xs))
    (is  (every? true? (map #(str/starts-with? % "999") xs)))))

(comment
  (run-tests))
