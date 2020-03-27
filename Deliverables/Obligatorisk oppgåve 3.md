###Deloppgave 1: Team og Prosjekt

####Referat sida sist oblig: 

####Referat 7. Seminar

##Oppmøte:

Michal

Nikolai

Lasse

Vegard

Henriette

##Diskutert:

Korleis vi skal lage til grafisk design til kort.

Kortstokk.

Andre sine presentasjonar frå gårsdagen.

Delegering av arbeid.

##Jobba med:

Lage liste med kort.

Research.

##Plan vidare:

Grafisk design til kort.

Funksjonell kortstokk.


####Referat 8. Seminar

##Oppmøte:

Michal

Nikolai

Lasse

Vegard

Henriette

##Diskutert:

Tilbakemelding frå forrige oblig

Arbeid sida sist seminar.

##Jobba med:

Merge alle sine branches ihop

Referatskriving

##Plan vidare:

Sjå gjennom brukerhistorier og akseptansekrav

Jobbe med laser frå spelarar og i backend.

Jobbe med GUI


####9. Referat

##Oppmøte:

Michal

Nikolai

Lasse

Vegard

Henriette

##Diskutert:

Obligatorisk oppgåve 3.

Arbeid under Covid-19

##Jobba med:

Obligatorisk oppgåve 3.

Diverse nye bugs.

##Plan vidare:

Obligatorisk oppgåve 3.

Bugfiksing.

Brukerhistorier


####10. Referat

##Oppmøte:

Michal

Nikolai

Lasse

Vegard

Henriette

##Diskutert:

Obligatorisk oppgåve 3.

Fordeling av commits.

Nye players/skubbing av andre spelarar

##Jobba med:

Obligatorisk oppgåve 3.

Diverse bugs.

Spawn point.

Arbeid med hull

##Plan vidare:

Obligatorisk oppgåve 3.

Bugfiksing.

Nye players/skubbing av spelarar.


#### Retrospektiv for kva vi har klart til no samt forbedringspunkter videre:

Siden siste innlevering har vi klart å legge til players liv/helsepoeng samt funksjonaliteten å ta skade/miste liv. I tillegg 
har kortstokk blitt implementert og menyen har fått en "Exit" knapp som avslutter spillet. Viktig å nevne at spillet mangler fremdeles
en grafisk representasjon av liv og kortstokk og det er noe vi har bestemt å fikse senere.
Denne obligen obligen har vi fokusert meir på backendutvikling. 

#### Forbedringspunkt
    1. Flytte informasjon ifrå terminalen til ein UI
    2. Vi ønskjer å få flere spillere på brettet.
    3. Vi ønskjer at det skal bli mulig å bruke kort til å styre spiller.


#### Valg og liknande innanfor team
Me har fortsatt beholdt alle dei originale rollene og har ikkje følt me trenger å gjera 
nokon endringar angåande rollene me bestemte oss for.

#### Forbedringspunk frå retrospekt:

Vi synes måten vi har jobbet på hittil fungerer bra. Det er fremdeles en ujevn mengde av commits grunnet forskjellige 
arbeidsmetoder blant gruppemedlemmer. (noen pusher mange små endringer, og noen få store)

#### Oppgåve prioritering framover:

- Fokusere på det grafiske grensesnittet.
- Få flere spillerne på kartet
- Få til å bruke kort til å styre spillerne

#### Gruppedynamikk og kommunikasjon

Av åpenbare grunner har kommunikasjonen i gruppen blitt endret til remote hvor vi møtes på Discord en fast dag i uken, 
 og av og til andre dager for å hjelpa kvarandre eller diskutere 
endringene vi har gjort og planlegge fremtidige oppgaver. Resten av kommunikasjonen skjer på Slack. Funksjonen med delt skjerm som 
ligg inne på discord har fungert veldig bra for oss og har fungert som ei bra erstattning for når vi jobba i lag i seminar.

###Deloppgave 2: Krav

####Kva krav har vi prioritert:

#### Krav	
    1.	En spiller trenger liv 	
    2.	En spiller trenger å ta skade	
    3.	En robot må bli flyttet av samlebånd 	
    4.	En spiller trenger kort 
    5.  En mulighet å avslutte spillet fra menyen.

#### Krav 1 	
##### Brukerhistorie:
*   Som spiller trenger jeg liv for å kunne tape	

##### Akseptansekrav:		
*   En spiller har en verdi for liv. 	

##### Arbeidsoppgaver	
*   Lage liv verdi i player klassen 

#### Krav 2	
##### Brukerhistorie: 	
*   En spiller trenger å ta skade for å kunne miste liv 	

##### Akseptansekrav:	
*   En spiller har en verdi for hit points
*   Må kunne miste hit points ifrå brett objekt

##### Arbeidsoppgaver		
*   Lage en verdi for hit points i Player


#### Krav 3	
##### Brukerhistorie:
*   En robot må kunne bli flyttet på av samlebånd for å 	
oppfylle brettreglene	

##### Akseptansekrav:		
*   Samlebånd flytter spilleren 

##### Arbeidsoppgaver		
*   Lage samlebånd funskjoner som flytter spelaren når den har ein spelar på seg.

#### Krav 4	
##### Brukerhistorie:	
*   En spiller trenger kort for å kunne programmere roboten	

##### Akseptansekrav:	
*   Ha kort som kan flytte/endre på roboten
*   Kunne bli tildelt kort

##### Arbeidsoppgaver	
*   Lage korttyper som kan programmera roboten
*   Lage funskjoner som tildeler kort til ein spiller

#### Krav 5
##### Brukerhistorie:
*   Som en bruker vil jeg at menyen skal ha en "exit" knapp slik at man kan avslutte spillet.

##### Akseptansekrav:	
*   Menyen har en funksjonell exit knapp som avslutter spillet ved å trykke på den.	

##### Arbeidsoppgaver		
*   Lage 2 nye "exit" knapp Textures, en med default farget og en når man peker på den med muspekeren. Avslutt spillet når "aktiv" knappen blir trykket på.

#### Hovudkrav som vi anser som en del av MVP og som vi kommer til å fokusere på: 

Ha ein enkel og interaktiv grensesnitt.
*   Implementere player sin laser mekanikken.
*   Å kunne spela med andre spelarar(LAN).
*   Å implementere runder og faser.
*   Å kunne tape eller vinna.
*   Å kunne spela med andre spelarar.
*   Å kunne tape eller vinna
