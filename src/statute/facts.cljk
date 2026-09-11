(ns statute.facts
  "General-law compliance catalog for Saint Kitts and Nevis (KNA) --
  extends this repo's existing `marketentry.facts` (public-procurement
  market-entry only, narrow scope) with a second, orthogonal catalog of
  statutes a company operating in this jurisdiction must generally track
  for compliance. Mirrors cloud-itonami-iso3166-jpn/-deu/-bgr/-aze/-alb/
  -arm/-atg/-dma's `statute.facts` (ADR-2607141700, cloud-itonami-
  compliance-fact-federation).

  Every entry cites an OFFICIAL Saint Kitts and Nevis government-hosted
  URL -- never fabricated. `agc.gov.kn` (the name the task suggested for
  the Attorney General's Chambers) does NOT resolve at all (DNS failure,
  curl-verified). The real official consolidated-law host is
  `lawcommission.gov.kn` (the St. Kitts and Nevis Law Commission -- 'a
  revised edition of the law, prepared by the Law Commission under the
  authority of the Law Commission Act, Cap. 1.03', per every downloaded
  PDF's own cover page). No TLS or JS-rendering blocker was hit for any
  `lawcommission.gov.kn` PDF; all three entries below were fetched
  directly and their text read via `pdftotext -layout` -- real text
  layers throughout, no OCR needed this iteration:

  - Companies Act, Cap. 21.03 (2020 revised edition -- Act No. shown on
    the consolidation page as originally enacted, most recently amended
    by Act 14 of 2018 per s.246) -- confirmed via its own text: s.9
    'Registration' (Registrar issues a certificate of incorporation
    stating the company's name, registration number and date of
    incorporation), s.215 ('there shall be appointed a person known as
    the Registrar of companies'). s.247 ('Application of this Act')
    additionally confirms Saint Kitts and Nevis's genuinely federal
    company-law structure: 'the provisions of this Act shall not extend
    or apply to companies formed under or subject to the Nevis Business
    Corporation Ordinance, the Nevis Limited Liability Companies
    Ordinance or any other Ordinance of the Nevis Island Assembly' unless
    such a company does business in the Federation (see
    `marketentry.facts` for the full federal-structure discussion).
  - Protection of Employment Act, Cap. 18.27 (Act 6 of 1986, in force 1
    September 1986, amended by Act 3 of 1987, Act 24 of 2001, Act 12 of
    2013 and Act 11 of 2014) -- confirmed via its own text: Part II
    'Termination of Employment' (s.5 Termination of employment, s.7
    Notice of termination, s.9 Labour Commissioner to be notified, s.11
    Prohibition against termination of employment), Part III 'Severance
    Payment, Long Service Gratuity and Service Charge' (s.12 Severance
    Payments Fund, administered together with the Social Security Board
    per s.15). This is the Act the task specifically flagged as a
    candidate, and it is confirmed real and current.
  - Licences on Businesses and Occupations Act, Cap. 18.20 (Act 6 of
    1972, in force 15 April 1972, amended by Act 9 of 1986, Act 5 of
    1999, Act 23 of 2009 and Act 3 of 2016) -- confirmed via its own
    text: s.3(1) ('every person wishing to carry on a business,
    occupation or trade ... shall apply in writing to and obtain from
    the Minister a licence'), independently corroborated verbatim by the
    Inland Revenue Department's own 'Guiding Your Business' page
    (`sknird.com/guiding-your-business/`, fetched directly), which cites
    this exact Cap. 18.20 s.3(1) text and describes the mandatory
    Business Licence as the SECOND of a three-act business-entry
    sequence (see `marketentry.facts`). s.3(6) additionally lets a
    business obtain an optional 'Business Licence Card from the Inland
    Revenue Department'.
  - This iteration also specifically searched (via the Law Commission's
    own file-search widget) for a Saint Kitts and Nevis Data Protection
    Act, the third statute this catalog's ATG sibling carries for its
    own jurisdiction -- 'Data Protection' returned ZERO real matches
    (the search widget fell back to its unfiltered recent-uploads
    default list). Rather than infer or invent one, this catalog
    HONESTLY carries three KNA statutes on the topics actually
    confirmed above (corporate governance, employment termination,
    business licensing), not a fourth, data-protection-shaped entry --
    the same honest-scope-narrowing discipline this family's DMA
    sibling already applied to its own (different) missing statute.

  A law not in this table has NO spec-basis, full stop; extend
  `catalog`, do not invent an id/url.")

(def catalog
  "iso3 -> vector of statute entries. `:statute/url` + `:statute/law-number`
  are the citation the governor requires before any compliance-fact
  proposal referencing this law can commit."
  {"KNA"
   [{:statute/id "kna.companies-act"
     :statute/title "Companies Act"
     :statute/jurisdiction "KNA"
     :statute/kind :law
     :statute/law-number "Cap. 21.03 (Revised Edition, showing the law as at 31 December 2020)"
     :statute/url "https://lawcommission.gov.kn/wp-content/documents/Revised-Acts-of-St-Kitts-and-Nevis/Revised-Acts-of-St-Kitts-and-Nevis-2020/Ch-21_03-Companies-Act.pdf"
     :statute/url-provenance :official-lawcommission-gov-kn
     :statute/enacted-date "2020-12-31"
     :statute/retrieved-at "2026-07-23"
     :statute/topic #{:corporate-governance :incorporation}}
    {:statute/id "kna.protection-of-employment-act"
     :statute/title "Protection of Employment Act"
     :statute/jurisdiction "KNA"
     :statute/kind :law
     :statute/law-number "Cap. 18.27 (Act 6 of 1986, amended by Act 3 of 1987, Act 24 of 2001, Act 12 of 2013 and Act 11 of 2014)"
     :statute/url "https://lawcommission.gov.kn/wp-content/documents/Revised-Acts-of-St-Kitts-and-Nevis/Revised-Acts-of-St-Kitts-and-Nevis-2017/Ch-18_27-Protection-of-Employment-Act.pdf"
     :statute/url-provenance :official-lawcommission-gov-kn
     :statute/enacted-date "1986-09-01"
     :statute/retrieved-at "2026-07-23"
     :statute/topic #{:labor :employment :termination}}
    {:statute/id "kna.licences-on-businesses-and-occupations-act"
     :statute/title "Licences on Businesses and Occupations Act"
     :statute/jurisdiction "KNA"
     :statute/kind :law
     :statute/law-number "Cap. 18.20 (Act 6 of 1972, amended by Act 9 of 1986, Act 5 of 1999, Act 23 of 2009 and Act 3 of 2016)"
     :statute/url "https://lawcommission.gov.kn/wp-content/documents/Revised-Acts-of-St-Kitts-and-Nevis/Revised-Acts-of-St-Kitts-and-Nevis-2017/Ch-18_20-Licences-on-Business-and-Occupations-Act.pdf"
     :statute/url-provenance :official-lawcommission-gov-kn
     :statute/enacted-date "1972-04-15"
     :statute/retrieved-at "2026-07-23"
     :statute/topic #{:business-licensing :market-entry}}]})

(defn spec-basis
  "The jurisdiction's statute vector, or nil -- nil means NO spec-basis
  for that jurisdiction yet."
  [iso3]
  (get catalog iso3))

(defn coverage
  "Honest coverage report, same shape/discipline as `marketentry.facts/coverage`:
  never report a missing jurisdiction as covered."
  ([] (coverage (keys catalog)))
  ([iso3s]
   (let [have (filter catalog iso3s)
         missing (remove catalog iso3s)]
     {:requested (count iso3s)
      :covered (count have)
      :covered-jurisdictions (vec (sort have))
      :missing-jurisdictions (vec (sort missing))
      :note (str "cloud-itonami-iso3166-kna statute.facts Wave 0 (ADR-2607141700): "
                 (count (get catalog "KNA")) " KNA statutes seeded with an "
                 "official government-hosted citation. Extend "
                 "`statute.facts/catalog`, never fabricate a law-id or URL.")})))

(defn by-topic
  "Statutes for `iso3` tagged with `topic` (e.g. :labor, :business-licensing)."
  [iso3 topic]
  (filterv #(contains? (:statute/topic %) topic) (spec-basis iso3)))
