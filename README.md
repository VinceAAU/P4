CARL: A Roguelike Language
==========================

CARL: A Roguelike Language is a language specialising in writing map 
generators for roguelike games.


Compilation
-----------
**Requirements:**
The program is written in Java 21, and is built with Apache Maven.
Therefore, make sure you have both Maven and JDK >=21 installed before 
compiling the program.

**Compilation:**
To compile, navigate to the root folder (the one containing the 
`pom.xml` file), and write:

    mvn package

Assuming nothing goes wrong during compilation, there should be a 
`.jar` file in the `target/` directory.


Usage
-------
After you've compiled the interpreter, and written your CARL script, 
you can run the interpreter as:

    java -jar carl-1.0.jar script-name.carl

(make sure to use the correct names for the jar file and script file)

The interpreter will then run the script, and all output will be sent 
to stdout. Output to a file or piping to a program is not natively 
supported, but can be done via the shell (e.g. bash or cmd).


Script language
---------------
A description of how to write in the language can be found in 
[language.md](language.md)

Contact
-------
This is a 4th semester project for the Software course at Aalborg 
University. Chances are that we will never look back at this project 
ever again after June 25, 2024 (our project exam date), except in 
shame. 

However, if by some chance you've been trapped by a sphinx and part of 
its riddle involves knowledge this software, we can be contacted via 
email as cs-24-sw-4-16@student.aau.dk.
