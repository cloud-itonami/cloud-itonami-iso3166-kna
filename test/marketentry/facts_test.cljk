(ns marketentry.facts-test
  (:require [clojure.test :refer [deftest is testing]]
            [marketentry.facts :as facts]))

(deftest kna-has-spec-basis
  (let [sb (facts/spec-basis "KNA")]
    (is (some? sb))
    (is (string? (:provenance sb)))
    (is (seq (:required-evidence sb)))
    (is (some? (facts/corporate-number-spec-basis "KNA")))
    (is (some? (facts/notice-period-spec-basis "KNA")))))

(deftest kna-rep-spec-basis-is-honestly-absent
  (testing "no verified personal-exclusion-grounds/debarment-grounds text for KNA public-procurement participation -- deliberately not claimed"
    (is (nil? (facts/rep-spec-basis "KNA")))))

(deftest unknown-jurisdiction-has-no-spec-basis
  (is (nil? (facts/spec-basis "ATL")))
  (is (nil? (facts/spec-basis "ZZZ"))))

(deftest required-evidence-satisfied
  (let [sb (facts/spec-basis "KNA")
        all (:required-evidence sb)]
    (is (true? (facts/required-evidence-satisfied? "KNA" all)))
    (is (not (facts/required-evidence-satisfied? "KNA" (take 1 all))))
    (is (nil? (facts/required-evidence-satisfied? "ATL" all)))))

(deftest coverage-is-honest
  (let [c (facts/coverage ["KNA" "USA" "ATL"])]
    (is (= 3 (:requested c)))
    (is (= 2 (:covered c)))
    (is (= ["ATL"] (:missing-jurisdictions c)))))
