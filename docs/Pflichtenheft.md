Pflichtenheft FitApp


1. Zielbestimmung

1.1 Ausgangssituation
Im Rahmen des Moduls Pattern & Frameworks soll eine Anwendung entwickelt werden.

1.2 Zielsetzung
Es soll eine Desktop Anwendung entwickelt werden, die den Benutzern ermöglicht, ihre Fitnessdaten zu speichern und ihre tägliche Kalorienaufnahme aufzuzeichnen.

1.3 Abgrenzung
Was ist nicht Bestandteil?
Keine Web-Version, keine mobile App

2. Produkteinsatz
2.1 Zielgruppe
Die Hauptzielgruppe sind gesundheitsbewusste Menschen, die ihre Fitness und ihr Wohlbefinden verbessern möchten. Dazu gehören sowohl Hobbysportler als auch Personen, die einfach einen Überblick über ihre täglichen Gewohnheiten behalten wollen.
2.2 Einsatzumgebung
•	Betriebssystem(e): alle gängigen Desktopbetriebssysteme, die mit Java arbeiten können
•	Hardwareanforderungen: keine, die über der auf den Rechnern eingesetzten Betriebssysteme liegt (z.B. Windows 11: CPU mit min. 2 Kernen und min. 1GHz Taktfrequenz, 4 GB Arbeitsspeicher)
2.3 Betriebsbedingungen
Das Programm soll lokal auf dem PC ausgeführt werden




3. Produktübersicht
Kurze Liste der Hauptfunktionen:
•	Kalorienspeicherung
•	Trainingsverwaltung 
•	Erstellung von Trainingsplänen

4. Funktionale Anforderungen
F-001: Benutzer kann sich registrieren und anmelden
F-002: Benutzer kann aufgenommene Kalorienmenge speichern
F-003: Benutzer kann Training speichern
F-004: Benutzer kann Trainingspläne anlegen
F-005: Benutzer kann bereits aufgenommene Kalorienmenge sehen

5. Nicht-funktionale Anforderungen
5.1 Performance
•	Startzeit < 5 Sekunden 
•	Reaktionszeit < 1 Sekunde 
5.2 Sicherheit
•	Passwortverschlüsselung 
•	Zugriffskontrolle 
5.3 Benutzerfreundlichkeit (Usability)
•	Intuitive GUI 
5.4 Wartbarkeit
•	Modularer Code 
5.5 Portabilität
•	Plattformunabhängigkeit durch den Einsatz von Java

6. Systemarchitektur
6.1 Architekturmodell
MVC-Architektur
6.2 Technologien
•	Programmiersprache: Java 
•	GUI-Framework: JavaFX
•	Datenbank: SQL-Datenbank

7. Datenmodell
•	Benutzer: ID, Username, Alter, Gewicht, Größe, Geschlecht, Mailadresse, Passwort  
•	Trainingsplan: ID, Name, Startdatum, Enddatum, Übungsliste
•	Übung: ID, Name, Beschreibung, Datum, Schwierigkeit, Dauer, Kalorien

8. Benutzeroberfläche
•	Startseite für Eingabe von Benutzername und Passwort mit Loginbutton
•	Hauptseite mit der Übersicht der Anwendungsfunktionen und Buttons, die Unterseiten zu erreichen
•	Unterseiten für die einzelnen Funktionen mit spezifischem Funktionsumfang, z.B. Eintragung der aufgenommenen Kalorienmenge und Anzeige der noch erlaubten Kalorienmenge

9. Qualitätsanforderungen
•	Anwendung reagiert schnell auf Nutzereingaben
•	Nutzereingaben werden zuverlässig gespeichert 
•	Skalierbarkeit







10. Tests & Abnahme
10.1 Teststrategie
•	Unit-Tests 
•	Integrationstests 
10.2 Abnahmekriterien
•	Alle Muss-Anforderungen erfüllt 
•	Keine kritischen Bugs



11. Projektorganisation
11.1 Zeitplan
•	Woche 1: Teamorganisation, Repository Aufbau, Softwareinstallation
•	Woche 2-3: Architektur und anlegen der Datenbank 
•	Woche 4: Tests
11.2 Ressourcen
•	Build-Tool: Apache Maven
•	Code-Repository: Github
•	Kollaborationssoftware: Discord


