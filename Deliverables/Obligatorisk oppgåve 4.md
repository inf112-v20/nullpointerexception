# Obligatorisk oppgåve 4 
## Nullpointerexception
### Deloppgave 1: Team og prosjekt

#### Møtereferat
Møtereferat ligg i Wiki på git.

Oversikta på desse kan du finne her:

[Møtereferat](https://github.com/inf112-v20/nullpointerexception/wiki)

Under her er link til kvart av referata mellom denne obligen og forrige:

[Møtereferat 11](https://github.com/inf112-v20/nullpointerexception/wiki/Referat-11.-Seminar-01.04.2020)

[Møtereferat 12](https://github.com/inf112-v20/nullpointerexception/wiki/Referat-12.-Seminar-15.04.2020)

[Møtereferat 13](https://github.com/inf112-v20/nullpointerexception/wiki/Referat-13.-Seminar-22.04.2020)

[Møtereferat 14](https://github.com/inf112-v20/nullpointerexception/wiki/Referat-14.-Seminar-29.04.2020)

[Møtereferat 15](https://github.com/inf112-v20/nullpointerexception/wiki/Referat-15.-Seminar-06.05.2020)

[Møtereferat Ekstra](https://github.com/inf112-v20/nullpointerexception/wiki/Referat-f%C3%B8r-presentasjon-04.05.2020)

#### Erfaringer team-messig og prosjektmetodikk
Team-messig har prosjektet gått ganske greit. Alle har kome godt overens og alle har møtt opp til
avtalte møter eller vore tilgjengelig på Slack/Discord ved behov. Vi har hovudsakleg jobba individuelt og sjølvstending, 
men hadde òg økter der me jobba sammen noko som gjerne gjorde det lettare å finne feil i koden.
#### Retrospektiv 
På generell basis har prosjektet gått bra. Til tider har vi hatt behov for sprinter for å ikkje komme bakpå. 
I retrospekt burde vi nok ha fordelt det betre over vekene.
Når det gjelder implementering av koden brukte vi tid på å diskutere korleis vi ynskte at koden skulle fungere, men
istadenfor for å gå for det første som fungerte, kunne vi ha tenkt meir på korleis koden ville fungera framover.
Vi kunne også tatt i bruk Discord eller andre verktøy mykje tidligare som kunne gjort det enklare for oss å samlast
utanom dei fastsatte seminartidene. 
#### Gruppedynamikk og kommunikasjon i forhold til starten
Gruppedynamikken har vore god, og det har vore lett å kommunisere med alle. Hovudsaklig har kommunikasjonen gått over Slack,
men etter at universitet blei stengd ned byrja vi å bruke Discord for tale-samtaler og skjermdeling. Skjermdeling har vore
ein god måte å vise problem ein har støtt på og til å få hjelp til å løyse dei.
Etter nedstegninga blei prosjektet nedpriortert ein del sidan alle måtte omstille seg, ikkje berre i INF112
men i alle faga. I tillegg har fleire av oss fortsatt jobba under koronakrisen. Dette har gjort det vanskelig for oss å
finne tider der vi alle kan jobbe i lag utanfor dei fastsette seminargruppene. Vi har likevel klart å samle oss før 
viktige innleveringar og presentasjonar for å få diskutert en del rundt desse.

### Deloppgave 2: Krav
#### Kva krav har me prioritert:
Me har priortert å få til ei speleløkke som køyrer til spelet er ferdig, og å få til eit interaktivt
brukargrensesnitt der brukaren kan kunne lese informasjon og ta valg i spelet.

#### Krav	
    1.  Ein spelar må kunna vinna eller tapa
    2.  Ein brukar må kunna få velga kort
    4.  Ein brukar må kunne sjå informasjon frå spelet
    5.  Ein brukar må ha motspelarar
    6.  Ha ein gameloop
#### Krav 1 
##### Brukarhistorie:
*   Ein spelar må ha verdiar som endrer seg slik at spelet kan bli avsluttet
##### Akseptansekrav:		
*   Ein spelar må ha ein verdi som kan få spelaren til å vinna
*   Ein spelar må ha ein verdi som kan få spelaren til å tapa
##### Arbeidsoppgaver	
*   Laga verdiar til spelaren slik at ein kan vinna eller tapa
*   Få brettet til å endra verdiane til spelaren slik at han kan vinna eller tapa

#### Krav 2 
##### Brukarhistorie:
*   Ein brukar må kunna velga kort som kan endra spelaren slik at spelet sine mål kan bli nådd
##### Akseptansekrav:		
*   Kunne ta imot input ifrå ein brukar som velger kort
*   Bruka korta brukaren har valgt til å flytta spelaren
##### Arbeidsoppgaver	
*   Visa alle kort alternativa til brukaren slik at han kan velga
*   Visa alle korta brukaren valgte
*   Bruke korta som blir valgt til å flytta spelaren

#### Krav 3
##### Brukarhistorie:
*   Ein brukar må kunne sjå informasjon ifrå spelet for å kunne ta gode valg
##### Akseptansekrav:	
*   Visa dei viktigaste verdiane for spelaren	
##### Arbeidsoppgaver	
*   Visa kor mange skadepoeng spelaren har igjen
*   Visa kor mange helsepoeng spelaren har igjen
*   Visa kor mange flagg spelaren har aktivert

#### Krav 4
##### Brukarhistorie:
*   Ein spelar må kunne ha motspelarar som kan spela mot han
##### Akseptansekrav:	
*   Ha fleire spelarar på kartet som har alle funskjonane til spelaren, og kan vinna eller tapa
##### Arbeidsoppgaver	
*   Passe på at alle spelarane har samme funksjoner
*   Få alle spelarane til å bevega seg og reagera med kvarandre

#### Krav 5
##### Brukarhistorie:
*   Spelet må ha ei løkka som køyrer heilt fram til spelet er avslutta, tapt eller vunnet
##### Akseptansekrav:	
*   Ha ei løkka som oppdaterer seg igjennom spelet
*   Løkka skal ikkje slutte før spelet er over
##### Arbeidsoppgaver	
*   Sørge for løkka oppdaterer all informasjon for kvar runde
*   Løkka må kunne bruka informasjon ifrå brukaren til å endra å gjera val i spelet

#### Fordelig av commits
Det er noko ujamnt fordeling av commits, men ikkje alle har lagd commits lika ofta, og nokon har jobba med 
vanskeligare krav og kode enn andre noko som gjerne fører til mindre commits og kode sjølv om ein har brukt mykje tid på koden.
