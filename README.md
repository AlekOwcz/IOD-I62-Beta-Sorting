# IOD-I62-Beta-Sorting
![Workflow](https://github.com/AlekOwcz/IOD-I62-Beta-Sorting/actions/workflows/ci.yml/badge.svg)
![Workflow](https://github.com/AlekOwcz/IOD-I62-Beta-Sorting/actions/workflows/tests.yml/badge.svg)
## Opis

Aplikacja służąca do sortowania zbiorów danych różnymi algorytmami. Ma pomóc użytkownikowi w ocenie, która metoda może się najlepiej sprawdzić w konkretnych przypadkach oraz, oczywiście, w uporządkowaniu danych. Dane liczbowe są sortowane standardowo, natomiast tekstowe - leksykograficznie. Należy zaimplementować co najmniej 6 różnych metod sortowania. Aplikacja będzie dostępna poprzez GUI, a także jako zdalne API, dzięki czemu można zintegrować z istniejącymi narzędziami.



## Tymczasowy Product Backlog i Sprint Backlog

https://docs.google.com/spreadsheets/d/1MLzHxiqQfQxyYSjGcKBivVYCn91ZMFkZ/edit?usp=sharing&ouid=118051814163568633415&rtpof=true&sd=true

## Instalacja
By zainstalować aplikacje uruchom polecenie ```mvn install``` w folderze z pom.xml

## Użycie

Z api łączymy się wysyłając polecenia POST na localhost:8080/api/

Dla sortowania jednowymiarowych zbiorów danych wysyłamy na localhost:8080/api/array/
Dla sortowania obiektów wysyłamy na localhost:8080/api/object/

Format wejściowy JSON'a:
```
{
  "algorithms":[<Lista algorytmów, dostępne: "quick","merge","heap","bubble","insertion","selection">],
  "data":[<Lista liczb, stringów lub obiektów>],
  (opcjonalne)"order":<"natural" lub "reversed">,
  (obowiązkowe dla obiektów)"attribute":<Atrybut obiektu względem którego ma odbyć się sortowanie>
}
```
W odpowiedzi otrzymuje się:
```
{
  "algorithms":[<Lista podanych na wejściu algorytmów>],
  "times":[<Lista czasów wykonania w milisekundach dla odpowiadających elementów listy "algorithms" (-1 jeśli algorytm był nieznany)>],
  "sortedData":[<>Lista posortowanych elementów wejściowych]
}
```
Przykład:

```
na localhost:8080/api/array/
{
  "algorithms": ["merge", "quick", "bubble"],
  "data": ["frog","cat","dog"]
}
```
Odpowiedź:
```
{
    "algorithms": [
        "merge",
        "quick",
        "bubble"
    ],
    "times": [
        0,
        0,
        0
    ],
    "sortedDataData": [
        "cat",
        "dog",
        "frog"
    ]
}
```
Przykład:
```
na localhost:8080/api/object/
{
  "algorithms": ["merge", "heap", "bubble"],
  "data": [
      {
          "a":5,
          "b":4
      },
      {
          "a":2.3,
          "c":"kot"
      },
      {
          "a":65.2,
          "b":4
      }
  ],
  "attribute":"a",
  "order":"reverse"
}
```
Odpowiedź:
```
{
    "algorithms": [
        "merge",
        "heap",
        "bubble"
    ],
    "times": [
        3,
        7,
        0
    ],
    "sortedDataData": [
        {
            "a": 65.2,
            "b": 4
        },
        {
            "a": 5,
            "b": 4
        },
        {
            "a": 2.3,
            "c": "kot"
        }
    ],
    "attribute": "a"
}
```

## UML:

![uml-diagram](https://user-images.githubusercontent.com/95354097/209042779-483d8249-baab-4063-9d7e-d1457964cf22.png)





