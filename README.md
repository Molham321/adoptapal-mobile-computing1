# AdoptAPal

An Android App for Mobile Computing 1.

## Installationsanleitung

Um die vollständige Funktionalität der App sicherzustellen müssen die nächsten Schritte durchgeführt
werden

In `localhost.properties` muss der GoogleMaps Api Key gesetzt werden, damit die Karte funktioniert,
diesen kann man (
hier)[https://developers.google.com/maps/documentation/android-sdk/start?hl=de] ersellen
Für die Umwandlung von Adressdaten in LatitudeLongitude kann der Google Maps Api verwendet werden.
Alternativ kann ein kostenloser Api-Key auf (Positionstack)[# https://positionstack.com/] erstellt
werden und in `localhost.properties` hinterlegt werde.

```
# https://positionstack.com/ API Key to Convert Address to LatLong Value
LAT_LONG_API=YOUR_POSITIONSTACK_API_KEY
# API KEY to use google maps in an app
MAPS_API_KEY=YOUR_GOOGLE_MAPS_API_KEY
```

## Additional information

App needs to be build (green hammer) before it can be installed and run on phone or emulator

## App features

- Address to LatLong conversion with positionstack-Api
- MapsScreen with clickable markers for each user to navigate to user screen
- login simulation with