# Grocery App

## Functionaliteit
App gebaseerd op api.mealsplanner.be (web service gemaakt in het kader van het vak web services, [code](https://github.com/HOGENT-Web/webservices-pieter-2122-miquel-vv)) en op [spoonacular api](https://spoonacular.com/food-api).

Voorlopig biedt de app twee functionaliteiten:
*Huishoudens beheren ("Manage households" in het hoofdmenu)
*Boodschappenlijst samenstellen ("Grocery list" in het hoofdmenu)

De andere opties in het hoofdmenu doen voorlopig niets.
Een gebruiker behoort toe tot een huishouden, dat ook andere gebruikers omvat. Via de web service (niet geimplementeerd in de app) kan een gebruiker maaltijden toevoegen aan een huishouden en recepten voor die maaltijden toevoegen. In de app kan de gebruiker dan een boodschappenlijst opstellen die ingredienten gaat verzamelen van al de recepten die gepland staan.

## De app testen
Vanuit android studio kan de app opgestart worden (Zelf heb ik Pixel 4, API 28 als emulator gebruikt). De app is verbonden met zowel de web service uit het eerste semester als met een gratis account op spoonacular. Om aan te melden kunnen de volgende gegevens gebruikt worden:
email: john.doe@admin.com, pwd:ok. Indien men het toevoegen van gebruikers in een huishouden wil testen zijn de volgende gebruikers geregistreerd (louis.lane@admin.com, mary.jane@admin.com, allemaal met pwd 'ok'.)
