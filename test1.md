# test 1

Pouzit Spring na vytvorenie jednoduchej aplikacie(jar suboru) spustitelnej z terminalu, ktora pracuje so vstupnym JSON suborom a na zaklade argumentu vykona jednu z dvoch moznosti.

1. Zadanie argumantu 'print'
- vypise do terminalu obsah json suboru v nasledovnom formate:
```
fruits
.. apple
..... green
..... red
.. pear
..... green
..... light greeen
..... dark green
.. lemon
..... yellow
..... green
vegetables
.. carrot
..... orange
..... red
.. cabbage
..... green
..... dark green
.. leek
..... green
..... light green
```

2. Zadanie argumentu 'findMax'
- najde a vypise zo vstupneho JSON suboru maximalnu hodnotu ovocia/zeleniny v tvare
ovocie/zelenina -> druh -> farba: max hodnota. Spravny vysledok je 
```vegetables -> carrot -> red: 21```
  
