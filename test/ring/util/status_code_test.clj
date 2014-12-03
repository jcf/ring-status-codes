(ns ring.util.status-code-test
  (:require [clojure.test.check.clojure-test :refer [defspec]]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            [clojure.test :refer :all]
            [ring.util.status-code :refer :all]))

(defn- code->desc [code]
  (->> store
       (filter #(= (:code %) x))
       first
       :desc))

(deftest test-codes
  (is (= (count codes) (count store)))
  (is (every? codes (map :code store))))

(deftest test-descs
  (is (= (count descs) (count store)))
  (is (every? descs (map :desc store))))

(deftest test-keys
  (is (= (count keys) (count store)))
  (is (every? keys (map :key store))))

(defspec test-lookup
  500
  (prop/for-all [x (gen/elements store)]
    (and (= x (-> x :code lookup))
         (= x (-> x :key  lookup))
         (= x (-> x :desc lookup)))))

(defspec test-status-codes
  500
  (prop/for-all [x (gen/elements codes)]
    (if-let [desc (code->desc x)]
      (= (status-codes x) desc))))
