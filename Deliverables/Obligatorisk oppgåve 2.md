###Deloppgave 1: Prosjekt og prosjektstruktur

Rollene i teamet fungerer greit så langt. Vi er enda litt tidlig til å kunne vurdere korleis det vil fungere framover. 
Vi beholder fortsatt samme roller som tidlegare, og avventar eventuelle endringar ettersom prosjektet utfoldar seg. Me
føler ikkje at det er behov for fleire roller enn dei me har bestemt oss for til no.

####Erfaringar i forhold til korleis teamet har fungert/Prosjekt metodikk: 
Det er vanskelig å delegere commits utover så tidlig i prosjektet når vi fortsatt jobbar med grunnmuren, 
og masse av kodinga/endringane er i dei samme klassane.

Gruppedynamikken fungerer bra og vi kjem godt overeins.
Kommunikasjon mellom gruppa går gjennom slack og git, dette har fungert greit så langt.
Me har hovudsaklig jobba kvar for oss til vanlig og så gått sammen og hatt parprogrammering/hjulpet 
kvarandre under møter/gruppertimene.

####Retrospekt
Så langt har me fått lagd vegger og har begynt å legge til dei forskjellige objekttypane eit brett skal ha. Me 
føler me ligger greit an og har fått komt igang med prosjektet. Me har klart å holde måla våre fram mot obligane.


I kodebasen er den en ujevn bidragsforhold i commits. Vi jobbar enda med å sette opp ein bra grunnmur til prosjektet og er vanskelig
dersom ein skal jobbe over i kvarandre under denne fasen. Vi har heller valgt å delegere rundt ansvar på forskjellige områder no i 
starten, men vill komme oss vidare til eit jamnare bidragsforhold etterkvart. Vi har heller ikkje komt langt nok til at alle rollene
er blitt utnytta til det dei er satte opp som. Vi har då prioritert å gi desse medlemmane andre oppgåver som feks. koding.


####Referat sida sist oblig:

4.Referat

Oppmøte:
-   Michal
-   Nikolai
-   Lasse
-   Vegard (Hadde eksamen, møtte opp i andre time)

Diskutert:

Korleis skal vi implementere veggar?

Diskutert feedback frå første oblig.

Plan vidare:

Få satt opp veggane på ein hensiktsmessig måte.

Jobba med playerklassen slik at det skal bli mulig å rotere player eventuelt.

Legge til muligheit for å falle utanfor brettet.

5.Referat

Oppmøte:
-   Michal
-   Nikolai
-   Lasse
-   Vegard
-   Henriette

Diskutert:

Diskutert diverse angåande obligatorisk oppgåve 2.

Lagt til en del kort til project sin TODO.

Jobba med:

Fiksa bugs.

Plan vidare:

Legge til testar til diverse, primært Board og Game.

Referat 6. Seminar

Oppmøte:
-   Michal
-   Nikolai
-   Lasse
-   Vegard
-   Henriette

Diskutert:

Kva som skal inn i Oblig 2.

Korleis vi skal få til diverse testar.

Jobba med:

Bugfixing.

Skriving oblig 2.

Code of Conduct.

Plan vidare:

Skriving av Oblig 2.

Forbedringspunkter
Sørge for at arbeidsfordelinga blir litt jamnare
Prøve å ha bedre test dekning til klassane våre
Ha ein litt meir strukturert agenda for gruppetimane/møtene

###Deloppgave 2: krav

Oversikt brukerhistorier, akseptansekriterier og arbeidsoppgåver.

####Brukerhistorier gjeldende for denne innleveringen:
1)  Som en spiller vil jeg at vegg objekter skal fungere ordentlig slik at de hindrer meg i å gå gjennom dem.
2)  Som en spiller vil jeg at bane-objekter skal få en riktig funksjonalitet slik at de flytter meg mot den retningen de peker på. (Trykk Q mens player står på banen for å se funksjonaliteten)
3)  Som en spiller forventer jeg en "respawn" funksjonalitet som flytter meg tilbake til start-posisjonen etter å ha gått utover brettet/falt i et hull.
4)  Som en bruker vil jeg kunne se menyen som kan starte spillet ved en knapp.
5)  Som en spiller vil eg at roboten min skal kunne ta skade fra andre roboter eller fra brettobjekt/lasere

####Akseptansekriterier:
1)  Det er umulig å gå gjennom vegg-objekter.
2)  Spilleren blir flyttet mot riktig retning etter å ha trykket "Q" på bane-objekter.
3)  Spilleren blir flyttet på start posisjonen (0,0) etter å ha gått utover bretter/falt i et hull.
4)  En funksjonell menu med start knapp som kjører spillet.
5)  Roboten må ha ein skade verdi som har kontroll på hvor mye skade roboter har fått.

Prioritert oppgave fremover:
-   Fjerne bugs på brettobjekta som utfører handlinger på spilleren
-   Legge til funskjoner på speleren som skadepoeng
-   Sørge for at alle akseptansekriteriene blir oppfylt

Kravet vi foreløpig har prioritert er å lage et funksjonell brett som inneholder vegger, lasere, baner og repair-kits.
Grunnen til det er at etter å ha blitt ferdig med brett, blir det enklere å implementere resten av spill funksjoner som baserer seg på brett-objekter.
Endringer siden siste innlevering:
-   Lagt til vegg og transportbånd objekter
-   Lagde en menu screen
-   Lagt til tester til Directions og PlayerTest

Deloppgave 3: Kode
Spillet kjører via main()-klassen

Prosjektet er mulig å bygge på, teste og kjøre på Windows og OSX, men vi har ikkje testa på Linux enda. Dette bør vere trivielt 
ettersom Linux og OSX er ganske like.

Manuelle tester:
For å bevege spilleren skal det gå an å bruke piltastane.
Det er lagt til en kommando når "Q" på tastaturet blir brukt. Denne sjekker ruten spilleren står på og gir 
beskjed til spilleren kva objekt han står på eller flytter/utfører handlinger på spilleren.
