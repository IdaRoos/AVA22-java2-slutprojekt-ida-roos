# AVA22-java2-slutprojekt-ida-roos

Slutprojekt beskrivning
Målet med detta projekt var att utveckla en produktionsregulator som dynamiskt övervakar och justerar produktionen och konsumtionen av items/enheter i ett system. Jag valde att lösa uppgiften genom att använda mig av MVC-pattern(Model-View-Controller) för att separera ansvarsområdet mellan presentation, applikationslogik och data.

Model:
Item-klassen representerar de enheter/items som produceras och konsumeras i systemet. 
Producer-klassen skapar producenter och genererar enheter/items i ett slumpmässigt intervall(1-10 sek) och placerar dem i buffer listan. Producenter kan startas, stoppas samt att klassen håller reda på antalet producerade items/enheter. 
Consumer-klassen skapar konsumenter och som konsumerar items/enheter från buffer listan i ett slumpmässigt intervall( 1-10 sek). Här håller vi även reda på antalet konsumerade items/enheter.
Buffer-klassen fungerar som en mellanhand där producerade items/enheter lagras innan de konsumeras. Buffer listan har en maxkapacitet på 100 för att undvika överbelastning och implementerar trådsäkerhet för att hantera samtidig tillgång
ProductionState-klassen hanterar systemets produktionstillstånd och har som ansvarsområde att spara aktuellt produktionstillstånd till en objektfil samt ladda/hämta det senast sparade produktionstillståndet från objektfilen.

Controller:
Jag har en Controller-klass som fungerar som en mellanhand mellan model-klasserna och view-klassen, och sköter bland annat eventhantering, skapande och kontroll av trådar(producers och consumers) samt uppdateringen av användargränssnittet baserat på realtidsförändringar i systemet. Här generarar vi bland annat ett randomiserat antal consumers mellan 3-15 till en lista. Controllern innehåller även inre klasser och metoder som lyssnar och hanterar användarinteraktioner genom att lägga till och ta bort producenter till/från en lista, hämta/ladda samt spara produktionstillstånd.Controller implementerar även PropertyChangeListener för att reagera på förändringar från Buffer-klassen, såsom när ett nytt item/enhet läggs till eller tas bort och uppdaterar GUI:t utifrån det. Controllern hanterar även loggning av systemhändelser och uppdateringar av loggen i realtid i GUI:t.

View:
ProductionView-klassen är ansvarig för att hantera GUI:t i applikationen. Klassen innehåller komponenter såsom knappar för att lägga till och ta bort producers, samt knappar för att spara och ladda systemets nuvarande tillstånd. Dessutom visar GUI:t en progressbar som visualiserar antalet tillgängliga items/enheter, i metoden som hanterar progressbaren använder jag även en if sats för att ändra färgen på progressbaren beroende på det procentuella värdet(röd under 50% och grön över 50%). Jag har även metoden “insertToLog” som lägger till ett nytt meddelande överst i loggtextarean - vilket tillåter realtiddsloggning i GUI:t.


Utils:
LoggerSingleton-klassen hanterar loggningen av händelser och meddelanden i applikationen med hjälp av det externa biblioteket log4j. Den är implementerad som en singleton för att säkerställa att en enda instans används genom hela applikationens livscykel. Klassen har metoderna “loginfo(String message) för att logga information och meddela alla registrerade lyssnare om nya loggmeddelanden, samt metoden addPropertyChangeListener för att lägga till lyssnare som ska meddela om nya loggningsmeddelanden.




