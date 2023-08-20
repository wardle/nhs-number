(ns com.eldrix.nhsnumber-test
  (:require [clojure.string :as str]
            [clojure.test :refer [deftest is run-tests]]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            [clojure.test.check.clojure-test :refer [defspec]]
            [com.eldrix.nhsnumber :as nnn]))

(def valid-examples
  ["1111111111"
   "6328797966"
   "6148595893"
   "4865447040"
   "4823917286"])

(def invalid-examples
  [nil
   ""
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

(deftest all-digits
  (is (#'nnn/all-digits? "123"))
  (is (not (#'nnn/all-digits? "a123"))))

(deftest test-normalise
  (is (nil? (nnn/normalise nil)))
  (is (= (nnn/normalise "123 123 4567") "1231234567"))
  (is (nnn/valid? (nnn/normalise "111 111 1111")))
  (is (nnn/valid? (nnn/normalise "111-111-1111" :strict)))
  (is (nnn/valid? (nnn/normalise "111 1111 111")))
  (is (not (nnn/valid? (nnn/normalise "111 1111 111" :strict))))
  (is (not (nnn/valid? (nnn/normalise "111 111  1111" :strict)))
      "Only single separator permitted in strict mode")
  (is (not (nnn/valid? (nnn/normalise "111a111a1111" :lenient)))
      "Only non-word characters can be used as separators")
  (is (thrown? #?(:cljs :default :clj Exception) (nnn/normalise "1111111111" nil))))

(deftest test-format
  (is (= (nnn/format-nnn "1231234567") "123 123 4567")))

(deftest test-random-seq
  (let [xs (take 10000 (nnn/random-sequence))]
    (is (every? nnn/valid? xs)))
  (let [xs (take 10000 (nnn/random-sequence 999))]
    (is (every? nnn/valid? xs))
    (is (every? true? (map #(str/starts-with? % "999") xs)))
    (is (every? true? (map #(str/starts-with? (nnn/format-nnn %) "999 ") xs)))))

(deftest test-random
  (is (nnn/valid? (nnn/random)))
  (is (thrown? #?(:cljs :default :clj Exception) (nnn/random "aaa"))))

(deftest test-invalid-prefix
  (is (thrown? #?(:cljs :default :clj Exception) (nnn/random-sequence "1111111111"))))

(deftest test-consecutive-seq
  (let [xs (take 10000 (nnn/ordered-sequence))]
    (is (every? nnn/valid? xs)))
  (let [xs (take 10000 (nnn/ordered-sequence 999))]
    (is (every? nnn/valid? xs))
    (is (every? true? (map #(str/starts-with? % "999") xs)))))

(defspec random-strings-valid?
  {:num-tests 5000, :reporter-fn (constantly nil)}
  (prop/for-all [s gen/string] ;; generate nonsense strings and exercise valid?
    (boolean? (nnn/valid? s))))

(defspec random-strings-normalise
  {:num-tests 5000 :reporter-fn (constantly nil)}
  (prop/for-all [s gen/string-alphanumeric]
    (let [result (nnn/normalise s)]
      (or (nil? result) (string? result)))))

(defspec random-strings-format
  {:num-tests 5000 :reporter-fn (constantly nil)}
  (prop/for-all [s gen/string-alphanumeric]
    (let [result (nnn/format-nnn s)]
      (or (nil? result) (string? result)))))

(defspec all-digits-correct
  {:num-tests 5000 :reporter-fn (constantly nil)}
  (prop/for-all [s (gen/fmap str (gen/large-integer* {:min 0}))]
    (#'nnn/all-digits? s)))

(defspec all-digits-incorrect
  {:num-tests 5000 :reporter-fn (constantly nil)}
  (prop/for-all [s (gen/fmap str/join (gen/vector gen/char-alpha))]
    (not (#'nnn/all-digits? s))))

(comment
  (run-tests))
