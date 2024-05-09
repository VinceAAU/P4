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
- [ ] Verificer fejlhåndtering ved adgang til indekser uden for grænser.
- [ ] Sørg for typekompatibilitet i array-tildelinger.
- [ ] Kontroller for fejl i array-deklaration med forkerte størrelsestyper.
- [ ] Valider typekonsistens under tildeling af arrayelementer.

## Håndtering af strukturer
- [ ] Valider fejlhåndtering ved adgang til uerklærede strukturfelter.
- [ ] Kontroller for typefejlmatch i tildelinger af strukturfelter.
- [ ] Sørg for at strukturer ikke tillader dobbelte feltnavne.
- [ ] Bekræft at strukturdeklarationer håndterer scope korrekt.

## Generel typekontrol
- [ ] Sørg for typekompatibilitet i binære operationer.
- [ ] Valider boolean-udtryk i kontrolstrukturer (if, while).
- [ ] Tjek for typefejl i relationelle og logiske operationer.

## Fejlhåndtering og beskeder
- [ ] Gennemgå at alle fejlbeskeder er klare og informative.
- [ ] Sørg for at fejltælleren inkrementerer korrekt.

## Diverse
- [ ] Test håndtering af re-deklaration af indbyggede funktioner.
- [ ] Verificer typekontrol for adgang til egenskaber og metodekald.

