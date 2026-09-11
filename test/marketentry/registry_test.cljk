(ns marketentry.registry-test
  (:require [clojure.test :refer [deftest is testing]]
            [marketentry.registry :as registry]))

(deftest engagement-fee-recompute
  (let [e {:base-fee 500000 :monthly-rate 30000 :monitoring-months 12 :claimed-fee 860000.0}]
    (is (== 860000.0 (registry/compute-engagement-fee e)))
    (is (true? (registry/engagement-fee-matches-claim? e))))
  (let [bad {:base-fee 500000 :monthly-rate 30000 :monitoring-months 12 :claimed-fee 999000.0}]
    (is (false? (registry/engagement-fee-matches-claim? bad)))))

(deftest register-draft-and-submit
  (let [d (registry/register-draft "eng-1" "KNA" 0)
        s (registry/register-submit "eng-1" "KNA" 0)]
    (is (= "KNA-DFT-000000" (get d "draft_number")))
    (is (= "KNA-SUB-000000" (get s "submit_number")))
    (is (nil? (get-in d ["certificate" "proof"])))
    (is (= "draft-unsigned" (get-in s ["certificate" "status"])))))

(deftest register-requires-ids
  (is (thrown? Exception (registry/register-draft "" "KNA" 0)))
  (is (thrown? Exception (registry/register-submit "eng-1" "" 0))))

(deftest notice-period-days-computes-whole-day-gap
  (testing "exactly six weeks (42 days) -- s.13's own stated minimum"
    (is (= 42 (registry/notice-period-days {:notice-published-date "2026-01-01"
                                            :bid-close-date "2026-02-12"}))))
  (testing "leap-year February is handled correctly (pure Julian Day Number arithmetic)"
    (is (= 2 (registry/notice-period-days {:notice-published-date "2024-02-28"
                                           :bid-close-date "2024-03-01"}))))
  (testing "missing either date -> nil, never a fabricated gap"
    (is (nil? (registry/notice-period-days {:notice-published-date "2026-01-01"})))
    (is (nil? (registry/notice-period-days {})))))

(deftest notice-period-insufficient
  (testing "exactly 42 days and 2 newspapers meets s.13 -- not insufficient"
    (is (false? (registry/notice-period-insufficient?
                 {:notice-published-date "2026-01-01" :bid-close-date "2026-02-12"
                  :notice-newspaper-count 2}))))
  (testing "more than 42 days and more than 2 newspapers -- still not insufficient (cumulative minimums)"
    (is (false? (registry/notice-period-insufficient?
                 {:notice-published-date "2026-01-01" :bid-close-date "2026-03-01"
                  :notice-newspaper-count 3}))))
  (testing "fewer than 42 days -- insufficient even with enough newspapers"
    (is (true? (registry/notice-period-insufficient?
                {:notice-published-date "2026-02-01" :bid-close-date "2026-02-12"
                 :notice-newspaper-count 2}))))
  (testing "fewer than 2 newspapers -- insufficient even with enough days"
    (is (true? (registry/notice-period-insufficient?
                {:notice-published-date "2026-01-01" :bid-close-date "2026-02-12"
                 :notice-newspaper-count 1}))))
  (testing "missing dates or missing newspaper count -- always insufficient, never assumed compliant"
    (is (true? (registry/notice-period-insufficient? {:notice-newspaper-count 2})))
    (is (true? (registry/notice-period-insufficient?
                {:notice-published-date "2026-01-01" :bid-close-date "2026-02-12"})))))

;; ---------------------------------------------------------------------------
;; Money is compared at money precision, not at double precision
;; ---------------------------------------------------------------------------

(deftest whole-unit-fees-were-already-correct-and-stay-correct
  (testing "the seeded shape: base + rate x months in whole currency units"
    (is (registry/engagement-fee-matches-claim?
         {:base-fee 500000 :monthly-rate 30000 :monitoring-months 12
           :claimed-fee 860000.0}))))

(deftest cent-denominated-fees-are-no-longer-rejected-while-correct
  (testing "`(== (double claimed) (+ (double base) (* (double rate) (double months))))`
            rejected CORRECT totals once an amount carried cents -- 40,989 of
            327,060 combinations (12.5%), against 0 of 327,060 in whole units"
    (let [bad (for [m (range 1 37)
                    bc (range 10000 90000 2100)
                    rc (range 500 6000 210)
                    :let [truth (/ (+ bc (* rc m)) 100.0)]
                    :when (not (registry/engagement-fee-matches-claim?
                                {:base-fee (/ bc 100.0) :monthly-rate (/ rc 100.0)
                                  :monitoring-months m :claimed-fee truth}))]
                [m (/ bc 100.0) (/ rc 100.0) truth])]
      (is (empty? bad) (str "false rejections: " (count bad) " e.g. " (first bad))))))

(deftest a-genuinely-wrong-fee-is-still-caught
  (testing "rounding to money precision must not blunt the check"
    (is (not (registry/engagement-fee-matches-claim?
              {:base-fee 500000 :monthly-rate 30000 :monitoring-months 12
                :claimed-fee 860000.01})))
    (is (not (registry/engagement-fee-matches-claim?
              {:base-fee 500000 :monthly-rate 30000 :monitoring-months 12
                :claimed-fee 859999.99})))))

(deftest an-unverifiable-fee-never-matches
  (testing "un-verifiable is not the same as correct, and not a crash"
    (is (not (registry/engagement-fee-matches-claim?
              {:base-fee 500000 :monthly-rate 30000 :monitoring-months 12})))
    (is (not (registry/engagement-fee-matches-claim?
              {:base-fee "500000" :monthly-rate 30000 :monitoring-months 12
                :claimed-fee 860000.0})))
    (is (nil? (registry/compute-engagement-fee {:base-fee 500000 :monthly-rate 30000})))))
