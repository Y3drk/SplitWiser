# Project realisation

## Roadmap

* 2 XII Wprowadzenie M1 (0pkt)
* 9 XII Prezentacja modelu (6pkt)
* 16 XII Review M1 (12pkt)
* 22 XII Wprowadzenie M2 (0pkt)
* 13 I Konsultacje M2 (0pkt)
* 20 I Review M2 (12pkt)
* 27 I Prezentacja końcowa (0pkt)

## Review:

* nie musimy przychodzić na zajęcia
* pull request based
* dostajemy komentarze kodu

## Tips

* potrzebujemy repo
    * BitBucket
* komunikacja
    * Teams (jeden duży kanał na całą grupę)
* dokumentacja
    * raczej changelog jako README.MD
    * piszemy co zostało zrobione przez ostatni tydzień
* na prezentacji dodatkowo rysunki, schematy, diagramy itd.

## Temat

* klasyczna client-server
* grupa znajomych, każdy wydaje cash, trzeba się rozliczać :(
* trackujemy kto komu ile zapłacił :)

```
  $10
  X  -->  Y (masło)
  $5
  Y  -->  X (bilet)
  $20
  X  -->  Z (obiad)
  .
  .
  .
  Podsumowanie:
  X -> wiszę Y: [kwota], wiszę Z: [kwota]
  Y -> wiszę X: [kwota]
  Z -> wiszę X: [kwota]
  Uwaga możliwa sytuacja:
  $100
  X  --> Y,Z,A,B,C (lub prościej jedna osoba wszystkim)
```

## Dodatkowe info:

* wszystko dzieje się na serwerze, który udostępnia endpoint
* do klienta polecany jest Retrofit
* baza danych obojętna
* bez uwierzytelniania jakiejkolwiek
* po prostu wybieramy którą osobą jesteśmy, do której grupy należymy itd.
* wizualizacja w/e
* bez przechodniości w rozliczeniach (!)
* kako uczestnik grupy widzimy wszystkich
* bez funkcji "rozlicz" (resetowanie balansu do 0)