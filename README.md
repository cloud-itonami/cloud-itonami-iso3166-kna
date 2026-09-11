# cloud-itonami-iso3166-kna

Open ISO 3166 Blueprint for **KNA**: Saint Kitts and Nevis --
**`:implemented`**.

This repository designs **and implements** a forkable OSS business for
an independent public-sector market-entry consultant: an already-
incorporated operator (e.g. a `cloud-itonami-cofog-{code}`,
`cloud-itonami-isco-{code}`, `cloud-itonami-unspsc-{segment}` or
`cloud-itonami-{ISIC}` blueprint fork) gets a Compliance Advisor +
independent **Market-Entry Compliance Governor** to navigate public-
procurement registration, local business/tax registration, and
regulatory-compliance rules in Saint Kitts and Nevis, so the operator
can win and service a government contract without hiring a full
in-house compliance department.

## Official surface (curl-verified 2026-07-23 -- every `lawcommission.gov.kn` / `sknird.com` / `fsrc.kn` / `gov.kn` host resolved cleanly and served real text; `agc.gov.kn`, the host the task suggested, does NOT resolve)

- Procurement: no dedicated e-procurement portal domain was found.
  Solicitations run through the Procurement Board and Ministry of
  Finance procurement officers under the Procurement and Contract
  (Administration) Act, Cap. 23.36 (Act 28 of 2012), with tender notice
  required in at least two newspapers of general circulation in the
  Federation no less than six weeks before the close of bids (s.13).
  s.2 confirms the Act applies with EQUAL force to Nevis.
- Business registration: a statutory Registrar of Companies (Companies
  Act, Cap. 21.03, s.215) issues the Certificate of Incorporation
  (s.9); in practice, incorporation enquiries are directed to the
  Financial Services Regulatory Commission (FSRC, `fsrc.kn`), which
  maintains offices in both Basseterre, St. Kitts and Charlestown,
  Nevis. Every business (incorporated or sole trader) additionally
  needs a Business Licence from the Ministry of Finance under the
  Licences on Businesses and Occupations Act, Cap. 18.20 (s.3), BEFORE
  registering for tax at the Inland Revenue Department.
- Tax: business/tax registration at the Inland Revenue Department
  (Income Tax Act, Cap. 20.22 + Tax Administration and Procedures Act,
  2003) issues a Business and Occupation Licence Certificate -- NOT a
  discrete numbered "TIN" the way this catalog's ATG sibling
  documents. For business carried on IN Nevis, corporate tax is
  instead payable to the Nevis Island Administration's own Inland
  Revenue Services, not the federal Department (Income Tax Act
  ss.83-84) -- a genuine federal fiscal split, independently
  corroborated by the Financial Services Regulatory Commission Act's
  own dual St. Kitts/Nevis departmental structure (s.5).

## Implementation (R0)

| Piece | Location |
|---|---|
| Actor namespaces | `src/marketentry/*` |
| Governor | `:market-entry-compliance-governor` |
| Ops | `:engagement/intake` · `:jurisdiction/assess` · `:filing/draft` · `:filing/submit` |
| Flagship HARD check | `notice-period-insufficient` (Procurement and Contract (Administration) Act, Cap. 23.36 s.13's minimum 42-day/2-newspaper tender-solicitation notice window, independently recomputed against the engagement's own declared notice/close dates and newspaper count -- see `docs/adr/0001-architecture.md`) |
| Compliance catalog | `src/statute/facts.cljk` -- Companies Act (Cap. 21.03), Protection of Employment Act (Cap. 18.27), Licences on Businesses and Occupations Act (Cap. 18.20) |
| Tests | `kbb -M:dev:test` |
| Demo | `kbb -M:dev:run` |
| Architecture ADR | [`docs/adr/0001-architecture.md`](docs/adr/0001-architecture.md) |

`:filing/submit` is never in any phase's `:auto` set -- human sign-off
is structural, not a rollout milestone.

## Federal structure (Nevis) -- genuinely investigated, not assumed

Saint Kitts and Nevis is a two-island federation: the Nevis Island
Assembly has its own constitutional Ordinance-making power (Constitution
of St. Christopher and Nevis s.103) and the Nevis Island Administration
runs its own Ministries (including its own Ministry of Finance and its
own Inland Revenue Services). This cuts BOTH ways for market entry, and
this repo documents both findings rather than picking one:

- **Procurement is uniform**: the Procurement and Contract
  (Administration) Act applies with equal force to Nevis (s.2).
- **Company/tax law has a genuine carve-out**: the Companies Act does
  NOT apply to companies formed under the Nevis Business Corporation
  Ordinance or the Nevis Limited Liability Companies Ordinance unless
  they do business in the Federation (s.247), and corporate tax on
  Nevis-based business is payable to the Nevis Island Administration,
  not the federal Inland Revenue Department (Income Tax Act ss.83-84).

See `src/marketentry/facts.cljk` for the full citation trail.

## No robotics premise -- digital/data service exemption

Market-entry and procurement-compliance navigation is a pure data/software
service with no physical-domain work (portal registration, document
checklists, regulatory-change monitoring) -- the same exemption class as
`cloud-itonami-6310` (HR SaaS replacement) and `cloud-itonami-gtin-*`.
`blueprint.edn` sets `:itonami.blueprint/robotics false` and
`:required-technologies` lists only real capabilities (`:identity`,
`:forms`, `:dmn`, `:bpmn`, `:audit-ledger`), no `:robotics`.

## Core Contract

```text
operator intake + prior filing history
        |
        v
Compliance Advisor -> Market-Entry Compliance Governor -> filing draft, or human sign-off
        |
        v
gated portal registration / filing submission + audit ledger
```

No automated proposal can submit a portal registration or filing the
governor refuses, suppress a compliance record, or claim a legal/tax
conclusion the governor has not cleared. `:filing/submit` is never in any
phase's `:auto` set -- it always requires human sign-off.

## What this is NOT

- **Not the government of Saint Kitts and Nevis.** This blueprint is an
  independent operator the government contracts with or that bids into
  its procurement -- never the government itself, and never an official
  channel.
- **Not legal or tax advice.** Every regulatory claim must cite the
  official source and route final filings to Saint-Kitts-and-Nevis-
  licensed counsel or a registered agent where the law requires licensed
  representation.
- **Not a Citizenship by Investment (CBI) service.** Saint Kitts and
  Nevis's CBI programme (individual citizenship for a real-estate
  purchase or Sustainable Growth Fund contribution) is a genuinely
  different regulatory domain from business market entry, deliberately
  investigated and excluded from this catalog -- see
  `src/marketentry/facts.cljk`.

## Capability layer

Required capabilities (`blueprint.edn`):

- :identity
- :forms
- :dmn
- :bpmn
- :audit-ledger

See [`docs/business-model.md`](docs/business-model.md) and
[`docs/operator-guide.md`](docs/operator-guide.md).

## License

AGPL-3.0-or-later.

## Culture catalog

Alongside the market-entry / statute catalogs, this repo carries a
**country-level regional-culture catalog** (ADR-2607171400 addendum 2,
`cloud-itonami-municipality-culture-catalog` Wave 1, in
`com-junkawasaki/root`) — national dishes, protected products, beverages,
crafts, festivals and heritage sites for Saint Kitts and Nevis:

- `src/culture/facts.cljk` — the catalog, source of truth (keyed by
  uppercase ISO3, mirroring `statute.facts`).
- `schema/culture.edn` — DataScript schema.
- `data/culture-tx.edn` — derived DataScript tx-data (regenerated from
  the catalog, never hand-edited).

City-level counterparts live in the `cloud-itonami-municipality-*` repos.
Same provenance discipline as the compliance catalogs: every entry cites a
source URL that was actually fetched and read on `:culture/retrieved-at`;
summaries state only what the cited source confirms. An item not in
`culture.facts/catalog` has no spec-basis — never fabricate one.
