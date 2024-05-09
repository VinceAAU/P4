# TODO-liste til test af typechecker

## Håndtering af variabler
- [x] Kast en fejl, hvis en variabel ikke er erklæret.
- [x] Kast en fejl, hvis en forkert type er tildelt til en variabel.
- [x] Sørg for at variabel-redeklaration i samme scope kaster en fejl.
- [x] Tjek for brug af uerklærede variabler.

## Funktionshåndtering
- [x] Sikre at funktioner smider fejl vis forkert returtyper.
- [x] Sikre at funktioner ikke smider fejl vis korrect returtyper.
- [ ] Kontroller at alle nødvendige parametre er angivet i et funktionskald.
- [ ] Smid fejl vis fejler:Valider at argumenterne i functions call stemmer overens med de forventede typer i funktionsdeklaration.
- [ ] Smid ikke fejl hvis dette sker:Smid fejl vis fejler:Valider at argumenterne i functions call stemmer overens med de forventede typer i funktionsdeklaration.
- [x] Kast en fejl for retur-udtryk uden for funktioner.
- [ ] Bekræft at funktioner ikke tillader dobbelt deklarationer.

## Håndtering af arrays

- [x] test for fejl bliver smidt hvis at agumenterne i array [][] ikke er af typen int ved decleration
- [x] test for fejl bliver smidt hvis at agumenterne i array [2][4] ikke er lovlige. i array[][]=2
- [x] test at array:int   smider fejl hvis array[2][2] =string
- [x] Test at ingen fejl bliver smidt hvis array er deklereret kooretk.
- [x] Test for at ingen fejl bliver smidt vis array assignment er korrect


## Håndtering af strukturer
- [ ] Valider fejlhåndtering ved adgang til uerklærede strukturfelter.
- [ ] Kontroller for typefejlmatch i tildelinger af strukturfelter.
- [ ] Sørg for at strukturer ikke tillader dobbelte feltnavne.
- [ ] Bekræft at strukturdeklarationer håndterer scope korrekt.

## Generel typekontrol
- [x] Sørg for typekompatibilitet i binære operationer.
- [x] Valider boolean-udtryk i kontrolstrukturer (if, while).
- [x] Tjek for typefejl i relationelle og logiske operationer. RelationOperatorTypeCheck


## Inbuild functioner
- [x] Test håndtering at fejl bliver smidt for at  re-deklaration af indbyggede funktioner.


