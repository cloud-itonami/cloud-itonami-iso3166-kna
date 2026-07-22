# ADR-0001: Architecture — Saint Kitts and Nevis market-entry compliance actor (`marketentry`)

**Status**: accepted
**Date**: 2026-07-23

## Context

`cloud-itonami-iso3166-kna` was published as a `:blueprint` (docs +
`blueprint.edn` only, then a country-level `culture.facts` catalog in a
separate Wave 1 batch) but carried ZERO `src/marketentry` or
`src/statute` content -- its `:public-sector/market-entry-compliance`
domain, declared in `blueprint.edn`, was unimplemented. This ADR closes
that gap, following the pattern established by `cloud-itonami-iso3166-jpn`
(origin) and `cloud-itonami-iso3166-atg` / `-dma` / `-grd` / `-est` (the
simpler, no-`goyoukiki` shape this blueprint also uses -- `blueprint.edn`'s
`:required-technologies` does not list `:ontology`, so this fork skips
the `marketentry.goyoukiki` real-tender-fact bridge JPN carries).

## Decision

Build the full governed-actor architecture for `marketentry`, mirroring
JPN/ATG/DMA/GRD/EST's harness verbatim (StateGraph node names, governor
hard/escalate contract, phase 0-3 rollout, `Store` protocol with
MemStore + DatomicStore parity) and researching Saint Kitts and Nevis's
own real market-entry rules from scratch for the country-specific
content.

- **Store**: `marketentry.store`, MemStore + DatomicStore, proven parity
  via contract test.
- **Registry**: `marketentry.registry`, pure DRAFT-certificate
  construction via `unsigned-certificate`, jurisdiction-scoped sequence
  numbering (`KNA-DFT-000000`, `KNA-SUB-000000`), plus the flagship
  notice-period recompute (see below).
- **Governor**: `:market-entry-compliance-governor` (family keyword from
  `blueprint.edn`).
- **Entity shape**: `engagement`, sequential draft -> submit on the same
  record. `high-stakes` = `#{:actuation/draft-filing
  :actuation/submit-filing}`.
- **Phase**: 0->3; `:filing/draft` and `:filing/submit` NEVER auto-
  commit at any phase.

### Which body administers procurement -- confirmed, not assumed

The task named a Ministry of Finance procurement unit as the likely
administering body, and that is confirmed correct. The Procurement and
Contract (Administration) Act, Cap. 23.36 (Act 28 of 2012, in force 4
October 2012, amended by Act 14 of 2013, downloaded directly from
`lawcommission.gov.kn` and read in full via `pdftotext`) establishes a
Procurement Board (s.23) chaired by the Financial Secretary (s.24(1)),
with a Manager of Procurement in the Ministry of Finance handling most
goods (s.7(3)). `agc.gov.kn`, the host the task suggested for the
Attorney General's Chambers, does NOT resolve at all (DNS failure,
curl-verified) -- the real official consolidated-law host is
`lawcommission.gov.kn` (the St. Kitts and Nevis Law Commission), reached
via a POST-based file-search widget on its own site.

### Flagship HARD check: `notice-period-insufficient` -- a genuinely new check SHAPE

s.13 of the Procurement Act, fetched and read directly, requires that
notice of a tender solicitation be given "in at least two newspapers of
general circulation in the Federation no less than six weeks before the
day and time for the close of bids". `marketentry.registry/notice-
period-days` independently recomputes the whole-day gap between an
engagement's own declared `:notice-published-date` and `:bid-close-date`
(via a portable, dependency-free Julian Day Number calculation -- no
`java.time`/`js/Date`, the same discipline this family's Barbados/
Grenada siblings established for their own date-shaped checks), and
`notice-period-insufficient?` HARD-holds `:filing/submit` if that gap
falls under 42 days OR the engagement's own declared
`:notice-newspaper-count` falls under 2.

This is a genuinely different check SHAPE than every prior iso3166
sibling this repo mirrors: not a turnover-scaled formula (Bulgaria), not
a flat statutory threshold (Albania), not a boolean registry-membership
read (Azerbaijan/Armenia), not a 3-tier vendor-class contract-value
classification (Antigua and Barbuda), not a dual independently-
escalating authority ladder (Dominica), not a conviction-
disqualification-expiry-date recompute (Grenada) -- it is a COMPOUND
duration-and-multiplicity recompute over the solicitation's OWN process
timeline, with no monetary threshold and no authority/eligibility
classification involved at all.

The Procurement Act's own s.11 delegates the tender-vs-quotation
financial threshold to "an amount prescribed by the Minister" -- this
iteration could NOT locate a gazetted figure for that threshold within
its time budget on `lawcommission.gov.kn`, so this catalog honestly
does not state one (unlike ATG's tiered vendor-registration classes,
which ARE a published, current, curl-verified figure). s.30(1)(d) also
gives the Board a general debarment power, but -- like ATG's s.44(1)(k)
-- the grounds for its use are not stated in the Act's own text and no
gazetted Regulations were located; `rep-spec-basis` is therefore
deliberately nil for KNA, the same honest-scope-narrowing discipline
ATG's catalog already established.

### The federal structure (Nevis) -- genuinely dual, investigated rather than assumed

The task asked every iteration to investigate, rather than assume,
whether a two-island/federal structure fragments market-entry
regulation. For Saint Kitts and Nevis the honest answer is genuinely
DUAL, not a single simple story:

- **Procurement is uniform.** The Procurement Act's own s.2 states
  plainly that the Act "shall be of equal application to the island of
  Nevis as provided in section 104 of the Constitution".
- **Company/tax law has a real carve-out.** The Companies Act, Cap.
  21.03 (2020 revised edition, downloaded directly and read) s.247
  states that its provisions "shall not extend or apply to companies
  formed under or subject to the Nevis Business Corporation Ordinance,
  the Nevis Limited Liability Companies Ordinance or any other
  Ordinance of the Nevis Island Assembly" UNLESS such a company does
  business in the Federation (s.247(2)) -- the same constitutional
  Ordinance-making power the Constitution's own s.103 grants the Nevis
  Island Legislature. Symmetrically, the Income Tax Act, Cap. 20.22
  (2017 revised edition, downloaded directly and read) ss.83-84 provide
  that corporate tax on business carried on IN Nevis is payable to the
  NEVIS ISLAND ADMINISTRATION (into its own Consolidated Fund), not the
  Federal Government, and vice versa for a Nevis-based company doing
  business in Saint Christopher. This was independently corroborated a
  further two times: the Nevis Island Administration's own website
  (`nia.gov.kn`, fetched directly) lists its own "Inland Revenue
  Services" as a distinct e-service, and the Financial Services
  Regulatory Commission Act, Cap. 21.10 (2020 revised edition,
  downloaded directly and read) s.5(2) requires the Commission itself
  to maintain "two operational departments, one located in Saint
  Christopher and the other in Nevis", with s.6(1) naming separate
  Commissioners for "the Financial Secretary of Saint Christopher" and
  "the Permanent Secretary in the Ministry responsible for Finance in
  Nevis" -- re-confirmed a third time by `fsrc.kn` itself, which
  identifies as the "Financial Services Regulatory Commission - St.
  Kitts Branch".

This catalog documents BOTH findings (`marketentry.facts`,
`:legal-basis`/`:corporate-number-*` keys) rather than picking whichever
story is simpler.

### Business registration -- a THREE-ACT model, genuinely different from ATG's two-act model

The Inland Revenue Department's own "Guiding Your Business" page
(`sknird.com/guiding-your-business/`, fetched directly and read in full)
describes: (1) OPTIONAL incorporation via the Financial Services
Regulatory Commission (offices in Basseterre, St. Kitts and Charlestown,
Nevis) -- skippable for a sole trader; (2) a MANDATORY Business Licence
from the Minister (Ministry of Finance) under the Licences on Businesses
and Occupations Act, Cap. 18.20 s.3 -- required of EVERY business,
incorporated or not, before starting operations; and (3) business/tax
REGISTRATION at the Inland Revenue Department itself (Unincorporated
Business Tax for sole traders, Corporate Income Tax for incorporated
companies), on completion of which IRD issues a Business and Occupation
Licence Certificate. Saint Kitts and Nevis's own sources never use the
term "Taxpayer Identification Number (TIN)" the way this catalog's ATG
sibling documents for its own jurisdiction -- this catalog's
`:corporate-number-*` keys honestly describe the registration-for-tax
act these sources actually document, rather than inventing a TIN
concept.

### Citizenship by Investment (CBI) -- investigated, found not relevant, excluded

Saint Kitts and Nevis runs the world's oldest Citizenship by Investment
programme (since 1984). This iteration specifically checked whether it
is relevant to business/procurement market entry, per the task's own
instruction. The Law Commission's own file listings surface only
individual-citizenship instruments under this heading (e.g. the "Saint
Christopher and Nevis Citizenship by Substantial Investment Regulations
2023", S.R.O. 26/2023) -- an entirely different regulatory domain
(natural-person citizenship, not business registration). Deliberately
excluded from `marketentry.facts`, the same honest-scope-narrowing
discipline this family's Dominica sibling already applied to its own
(materially identical in kind) CBI programme.

### `statute.facts` (second, orthogonal catalog)

Three Saint Kitts and Nevis statutes, all confirmed by downloading the
PDF directly from `lawcommission.gov.kn` (real text layers, no OCR
needed) and reading the extracted text: the Companies Act, Cap. 21.03
(2020 revised edition), the Protection of Employment Act, Cap. 18.27
(the labour-law citation the task specifically flagged as a candidate,
confirmed real and current), and the Licences on Businesses and
Occupations Act, Cap. 18.20. A Data Protection Act (the third statute
this catalog's ATG sibling carries) was specifically searched for via
the Law Commission's own file-search widget and NOT found -- honestly
excluded rather than invented.

## Consequences

- `src/` now genuinely exists with real, tested, curl/pdftotext-cited
  content for this blueprint's declared domain (`:public-sector/
  market-entry-compliance`) -- moves this repo's
  `manifest/itonami-fleet-audit.edn` `:prod-ready?` signal from `:stub`
  to `:active`.
- The existing `culture.facts` catalog (Wave 1, unrelated batch) is
  untouched.
- The Procurement Act's own s.11 threshold-delegated-to-the-Minister and
  s.30(1)(d) general debarment power are genuine, verified,
  NOT-implemented extension points for a future iteration -- only if
  that iteration can locate and read a gazetted figure/Regulations this
  iteration could not.
- Sibling country blueprints can continue forking JPN/ATG/DMA/GRD/EST/KNA
  and swapping in their own genuinely-researched `marketentry.facts` /
  `statute.facts` content and whichever flagship check their own law
  actually supports -- this ADR is itself further evidence that the
  flagship check should be chosen from real, currency-checked research,
  not copied by rote, and that a genuinely federal jurisdiction can cut
  BOTH ways (uniform for one domain, split for another) rather than
  fitting a single simple federal/unitary label.
