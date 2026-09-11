(ns culture.facts
  "Country-level regional-culture catalog for Saint Kitts and Nevis (KNA) --
  national dishes, protected products, beverages, crafts, festivals and
  heritage sites, per ADR-2607171400 addendum 2 (cloud-itonami-municipality-
  culture-catalog Wave 1, in com-junkawasaki/root). Sibling namespace to
  `marketentry.facts` / `statute.facts` (ADR-2607141700); city-level
  counterparts live in the cloud-itonami-municipality-* repos.

  Catalog is keyed by UPPERCASE ISO3 (mirrors `statute.facts`); entries
  carry no :culture/municipality (that attribute is city-level only).

  Every entry cites a source URL that was actually fetched and read on
  :culture/retrieved-at -- never fabricated. Summaries state only what the
  cited source confirms. An item not in this table has NO spec-basis, full
  stop; extend `catalog`, do not invent an id/url.")

(def catalog
  "iso3 -> vector of culture entries."
  {"KNA"
   [{:culture/id "kna.dish.goat-water"
     :culture/name "Goat water"
     :culture/country "KNA"
     :culture/kind :dish
     :culture/summary "Stew of cubed goat meat in a tomato base, served with rice and boiled dasheen leaves; known and eaten in several Caribbean countries, most prominently in Saint Kitts and Nevis."
     :culture/url "https://en.wikipedia.org/wiki/Goat_water"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "kna.dish.cook-up"
     :culture/name "Cook-up (pelau)"
     :culture/country "KNA"
     :culture/kind :dish
     :culture/summary "Favorite dish of Saint Kitts and Nevis combining chicken, pig tail, saltfish and vegetables with rice and pigeon peas."
     :culture/url "https://en.wikipedia.org/wiki/Culture_of_Saint_Kitts_and_Nevis"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "kna.dish.conkies"
     :culture/name "Conkies"
     :culture/country "KNA"
     :culture/kind :dish
     :culture/summary "Tamale-like preparation of Saint Kitts and Nevis: cornmeal mixed with grated sweet potato, pumpkin and coconut, wrapped in banana leaves and boiled."
     :culture/url "https://en.wikipedia.org/wiki/Culture_of_Saint_Kitts_and_Nevis"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "kna.beverage.csr"
     :culture/name "Cane Spirit Rothschild (CSR)"
     :culture/country "KNA"
     :culture/kind :beverage
     :culture/summary "The national drink of Saint Kitts and Nevis, distilled from fresh sugar cane."
     :culture/url "https://en.wikipedia.org/wiki/Culture_of_Saint_Kitts_and_Nevis"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "kna.product.sugar-cane"
     :culture/name "Sugar cane"
     :culture/country "KNA"
     :culture/kind :product
     :culture/summary "Sugar cane, grown on Saint Kitts since 1640, dominated the local economy for about 365 years until the government closed the sugar industry in 2005."
     :culture/url "https://en.wikipedia.org/wiki/Saint_Kitts"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "kna.craft.caribelle-batik"
     :culture/name "Caribelle Batik (Romney Manor)"
     :culture/country "KNA"
     :culture/kind :craft
     :culture/summary "Batik enterprise at the historic Romney Manor in the Wingfield Estate on Saint Kitts, now a tourist destination with a botanical garden."
     :culture/url "https://en.wikipedia.org/wiki/Romney_Manor"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "kna.festival.st-kitts-music-festival"
     :culture/name "St. Kitts Music Festival"
     :culture/country "KNA"
     :culture/kind :festival
     :culture/summary "Festival of popular music held annually in June on St. Kitts, first established in 1996 as the Shak Shak Festival."
     :culture/url "https://en.wikipedia.org/wiki/St_Kitts_Music_Festival"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "kna.festival.culturama"
     :culture/name "Culturama"
     :culture/country "KNA"
     :culture/kind :festival
     :culture/summary "Nevis' carnival-style cultural festival, established in 1974 and celebrated annually around Emancipation Day in late July/early August with music, food festivals, arts and crafts."
     :culture/url "https://en.wikipedia.org/wiki/Culturama"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}
    {:culture/id "kna.heritage.brimstone-hill"
     :culture/name "Brimstone Hill Fortress National Park"
     :culture/country "KNA"
     :culture/kind :heritage
     :culture/summary "Well-preserved fortress in Saint Thomas Middle Island Parish on St. Kitts, designated a UNESCO World Heritage Site in 1999."
     :culture/url "https://en.wikipedia.org/wiki/Brimstone_Hill_Fortress_National_Park"
     :culture/url-provenance :wikipedia-en
     :culture/retrieved-at "2026-07-17"}]})

(defn spec-basis [iso3] (get catalog iso3))

(defn coverage
  ([] (coverage (keys catalog)))
  ([iso3s]
   (let [have (filter catalog iso3s)
         missing (remove catalog iso3s)]
     {:requested (count iso3s)
      :covered (count have)
      :covered-jurisdictions (vec (sort have))
      :missing-jurisdictions (vec (sort missing))
      :note (str "cloud-itonami-iso3166-kna culture catalog "
                 "(ADR-2607171400 addendum 2, Wave 1): " (count (get catalog "KNA"))
                 " KNA entries, each with a fetched-and-read citation. "
                 "Extend `culture.facts/catalog`, never fabricate an id/url.")})))

(defn by-kind [iso3 kind]
  (filterv #(= (:culture/kind %) kind) (spec-basis iso3)))
