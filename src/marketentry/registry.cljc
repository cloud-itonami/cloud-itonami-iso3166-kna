(ns marketentry.registry
  "Pure-function market-entry filing-draft + filing-submit record
  construction -- an append-only market-entry book-of-record draft.

  Like every sibling actor's registry, there is no single international
  reference-number standard for a public-procurement market-entry
  filing -- every jurisdiction assigns its own format. This namespace
  does NOT invent one; it builds a jurisdiction-scoped sequence number
  and validates the record's required fields, the same honest,
  non-fabricating discipline `marketentry.facts` uses.

  `engagement-fee-matches-claim?` is an HONEST reapplication of the
  SAME ground-truth-recompute DISCIPLINE sibling actors use (verify a
  claimed monetary total against the entity's own recorded quantity x
  unit fields), reapplied to a market-entry engagement fee line.

  `notice-period-days` / `notice-period-insufficient?` are THIS
  vertical's own new ground-truth recompute, grounding KNA's flagship
  governor check (`marketentry.governor/notice-period-insufficient-
  violations`): the Procurement and Contract (Administration) Act,
  Cap. 23.36 s.13 (own primary text, see `marketentry.facts`) requires
  that notice of a tender solicitation be given 'in at least two
  newspapers of general circulation in the Federation no less than six
  weeks before the day and time for the close of bids'.

  This is a DIFFERENT check SHAPE from every prior sibling this repo's
  family has implemented: not a turnover-scaled formula (Bulgaria), not
  a flat statutory threshold (Albania), not a boolean registry-
  membership read (Azerbaijan/Armenia), not a 3-tier vendor-class
  contract-value classification (Antigua and Barbuda), not a dual,
  independently-escalating authority ladder (Dominica), not a
  conviction-disqualification-expiry-date recompute (Grenada) -- it is
  a COMPOUND duration-and-multiplicity recompute over the solicitation's
  OWN declared process timeline: (a) the whole-day gap between the
  engagement's own declared `:notice-published-date` and
  `:bid-close-date` must meet a minimum ADVANCE-NOTICE DURATION (42
  days / six weeks), AND (b) the engagement's own declared
  `:notice-newspaper-count` must meet a minimum PUBLICATION COUNT (two
  newspapers) -- two independent minimums over two independent fields,
  neither of which is a monetary threshold or an authority/eligibility
  classification at all.

  Dates are plain ISO-8601 \"YYYY-MM-DD\" strings -- deliberately no
  external date/calendar library and no host date API (`java.time` /
  `js/Date`), the SAME technique this family's Barbados/Grenada
  siblings established for their own (different) date-shaped checks:
  `julian-day-number` is the Fliegel & Van Flandern (1968) proleptic-
  Gregorian Julian Day Number formula, pure integer arithmetic, so a
  whole-day gap between two calendar dates can be computed portably on
  JVM, ClojureScript, or a WASM guest with no host clock/calendar
  dependency.

  This namespace is pure data + pure functions -- no I/O, no network
  call to any real Procurement Board, Ministry of Finance, or newspaper
  publisher. It builds the RECORD an operator would keep, not the act of
  submitting a portal registration itself (that is
  `marketentry.operation`'s `:filing/submit`, always human-gated -- see
  README Actuation)."
  (:require [clojure.string :as str]))

(defn- unsigned-certificate
  "Every certificate this actor produces is UNSIGNED -- signature is
  the market-entry operator's act, not this actor's."
  [kind subject record-id]
  {"@context" ["https://www.w3.org/ns/credentials/v2"]
   "type" ["VerifiableCredential" kind]
   "credentialSubject" {"id" subject "record" record-id}
   "proof" nil
   "issued_by_registry" false
   "status" "draft-unsigned"})

(defn- zero-pad [n w]
  (let [s (str n)]
    (str (apply str (repeat (max 0 (- w (count s))) "0")) s)))

(defn compute-engagement-fee
  "The ground-truth engagement fee for `engagement`'s own `:base-fee`
  and `:monitoring-months` x `:monthly-rate` -- a single flat
  base + months x rate calculation, not a full pricing engine."
  [{:keys [base-fee monthly-rate monitoring-months]}]
  (+ (double base-fee)
     (* (double monthly-rate) (double monitoring-months))))

(defn engagement-fee-matches-claim?
  "Does `engagement`'s own `:claimed-fee` equal the independently
  recomputed `compute-engagement-fee`?"
  [{:keys [claimed-fee] :as engagement}]
  (== (double claimed-fee) (compute-engagement-fee engagement)))

;; ------------------------- notice-period recompute -------------------------

(def minimum-notice-days
  "Procurement and Contract (Administration) Act, Cap. 23.36, s.13: a
  tender solicitation notice must be given 'no less than six weeks
  before the day and time for the close of bids' -- six weeks = 42
  days."
  42)

(def minimum-notice-newspapers
  "s.13: notice must be given 'in at least two newspapers of general
  circulation in the Federation'."
  2)

(defn- parse-int [s]
  #?(:clj (Integer/parseInt s)
     :cljs (js/parseInt s 10)))

(defn- parse-iso-date
  "Parses a plain ISO-8601 \"YYYY-MM-DD\" string into [year month day]."
  [s]
  (let [[y m d] (str/split s #"-")]
    [(parse-int y) (parse-int m) (parse-int d)]))

(defn- julian-day-number
  "Fliegel & Van Flandern (1968) proleptic-Gregorian Julian Day Number
  formula -- pure integer arithmetic, no external date/calendar library
  and no host date API (java.time / js/Date), the same discipline this
  family's Barbados/Grenada siblings established for their own
  (different) date-shaped checks."
  [year month day]
  (let [a (quot (- 14 month) 12)
        y (- (+ year 4800) a)
        m (+ month (* 12 a) -3)]
    (+ day
       (quot (+ (* 153 m) 2) 5)
       (* 365 y)
       (quot y 4)
       (- (quot y 100))
       (quot y 400)
       -32045)))

(defn- days-between
  "Whole days from ISO-8601 date string `from` to ISO-8601 date string
  `to` (positive when `to` is later)."
  [from to]
  (let [[fy fm fd] (parse-iso-date from)
        [ty tm td] (parse-iso-date to)]
    (- (julian-day-number ty tm td) (julian-day-number fy fm fd))))

(defn notice-period-days
  "Whole days between `engagement`'s own declared `:notice-published-date`
  and `:bid-close-date`. nil if either date is missing."
  [{:keys [notice-published-date bid-close-date]}]
  (when (and notice-published-date bid-close-date)
    (days-between notice-published-date bid-close-date)))

(defn notice-period-insufficient?
  "Does `engagement`'s own declared notice window/newspaper-count fall
  SHORT of what s.13 requires (>=42 days AND >=2 newspapers)? Missing
  dates or a missing/zero newspaper count are always insufficient --
  never assume compliance from absent ground truth. The flagship check
  this vertical adds -- a compound duration-and-multiplicity recompute,
  not a monetary threshold or an authority/eligibility classification."
  [{:keys [notice-newspaper-count] :as engagement}]
  (let [days (notice-period-days engagement)]
    (boolean
     (or (nil? days)
         (< days minimum-notice-days)
         (< (long (or notice-newspaper-count 0)) minimum-notice-newspapers)))))

(defn register-draft
  "Validate + construct the FILING-DRAFT registration DRAFT -- the
  market-entry operator's own act of preparing a portal registration
  package. Pure function -- does not touch any real procurement
  system."
  [engagement-id jurisdiction sequence]
  (when-not (and engagement-id (not= engagement-id ""))
    (throw (ex-info "draft: engagement_id required" {})))
  (when-not (and jurisdiction (not= jurisdiction ""))
    (throw (ex-info "draft: jurisdiction required" {})))
  (when (< sequence 0)
    (throw (ex-info "draft: sequence must be >= 0" {})))
  (let [draft-number (str (str/upper-case jurisdiction) "-DFT-" (zero-pad sequence 6))
        record {"record_id" draft-number
                "kind" "filing-draft"
                "engagement_id" engagement-id
                "jurisdiction" jurisdiction
                "immutable" true}]
    {"record" record "draft_number" draft-number
     "certificate" (unsigned-certificate "FilingDraft" draft-number draft-number)}))

(defn register-submit
  "Validate + construct the FILING-SUBMIT registration DRAFT -- the
  market-entry operator's own act of actually submitting a portal
  registration (always human-gated upstream)."
  [engagement-id jurisdiction sequence]
  (when-not (and engagement-id (not= engagement-id ""))
    (throw (ex-info "submit: engagement_id required" {})))
  (when-not (and jurisdiction (not= jurisdiction ""))
    (throw (ex-info "submit: jurisdiction required" {})))
  (when (< sequence 0)
    (throw (ex-info "submit: sequence must be >= 0" {})))
  (let [submit-number (str (str/upper-case jurisdiction) "-SUB-" (zero-pad sequence 6))
        record {"record_id" submit-number
                "kind" "filing-submit"
                "engagement_id" engagement-id
                "jurisdiction" jurisdiction
                "immutable" true}]
    {"record" record "submit_number" submit-number
     "certificate" (unsigned-certificate "FilingSubmit" submit-number submit-number)}))

(defn append [history result]
  (conj (vec history) (get result "record")))
