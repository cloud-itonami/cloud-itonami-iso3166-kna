# Business Model: Independent Public-Sector Market-Entry & Procurement Compliance Service — Saint Kitts and Nevis

## Classification

- Repository: `cloud-itonami-iso3166-kna`
- ISO 3166: `KNA` (Saint Kitts and Nevis)
- Activity: public-procurement market-entry and ongoing regulatory-
  compliance navigation for an already-incorporated operator

## Customer

- an already-incorporated `cloud-itonami-cofog-{code}` /
  `cloud-itonami-isco-{code}` / `cloud-itonami-unspsc-{segment}` /
  `cloud-itonami-{ISIC}` operator wanting to bid on a Saint Kitts and
  Nevis public contract
- a foreign SME or civic-tech vendor entering the public sector in
  Saint Kitts and Nevis for the first time
- a `cloud-itonami-M6910` client that has just completed incorporation
  and now needs public-sector market access

## Offer

- tender-notice compliance screening: independent verification that a
  solicitation's own declared notice window and newspaper-publication
  count actually meet the Procurement and Contract (Administration)
  Act, Cap. 23.36 s.13 minimum (42 days, two newspapers of general
  circulation in the Federation), before any filing submission
- business/tax registration checklist: mandatory Business Licence
  (Ministry of Finance, Licences on Businesses and Occupations Act,
  Cap. 18.20), Certificate of Incorporation where relevant (Registrar
  of Companies / Financial Services Regulatory Commission), and
  business/tax registration at the Inland Revenue Department
- federal-structure guidance: clarifying which regime applies where
  Saint Kitts and Nevis's company-law and tax-law carve-outs for
  Nevis-formed entities (Nevis Business Corporation Ordinance / Nevis
  Limited Liability Companies Ordinance) are relevant to the operator's
  own structure
- ongoing regulatory-change monitoring subscription
- compliance-audit export package for the client's own records

## Revenue

- per-engagement market-entry fee (one-time registration + checklist
  completion)
- recurring regulatory-change monitoring subscription
- compliance-audit export package

## Trust Controls

- any actual portal registration or filing submission requires
  Market-Entry Compliance Governor clearance and always escalates to
  human sign-off (`:filing/submit` is never automated at any phase)
- a false or fabricated regulatory-requirement claim is a HARD hold that
  cannot be overridden by human approval alone -- it must be corrected
  against a cited official source first
- a tender-solicitation notice window/newspaper count that falls short
  of the Procurement Act's own s.13 minimum is a HARD hold on
  `:filing/submit`, independently recomputed rather than trusted from a
  self-reported notice history
- this service does **not** provide legal or tax advice; characterization
  and filing on the client's behalf beyond checklist/draft assistance
  routes to Saint-Kitts-and-Nevis-licensed counsel or a registered agent
- this service is **not** a Citizenship by Investment (CBI) intermediary
  -- a genuinely different, individual-citizenship regulatory domain,
  deliberately investigated and excluded from scope

## Boundary with adjacent actors (read before forking)

- **`cloud-itonami-M6910`**: helps a client BECOME a legal entity
  (incorporation, ISIC 6910) -- a prior, different regulatory phase
  (company law). This blueprint assumes incorporation is already done
  (or, for a sole trader, is not required at all) and handles
  public-procurement market entry (a different regulatory domain).
- **`cloud-itonami-cofog-{code}`**: a jurisdiction-agnostic operator
  template for ONE public function. This blueprint is the orthogonal
  jurisdiction-specific axis -- the two compose (fork a COFOG-function
  blueprint AND this one to operate in Saint Kitts and Nevis).
