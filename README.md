# ing sw 2022: Paradina Peri Palmiotti
Progetto di Ingegneria del Software (3 CFU), 2022


* Marco "Il Puma" Paradina

* Samuele Peri

* Davide Palmiotti

Sono state implementate le seguenti funzionalità:
* Complete rules
* CLI
* GUI
* Socket 
* Character cards (Bonus functionality)
* 4 Player game (Bonus functionality)

# Coverage

|Package|Class %|Method %|Line %|
|---|---|---|---|
|Controller|100% (27/27)|89% (125/139)|80%|
|Model|100% (17/17)|86% (148/172)|86%|

# Execution of jar files

Using Java 11

### Server

To execute the server, download ServerApp.jar and run the following command

```
java -jar ServerApp.jar
```

### Client

To execute the client, download ClientApp.jar and run the following command
```
java -jar ClientApp.jar
```
you will then be asked to choose between CLI (0) and GUI (1)
*IMPORTANT*: for the correct usage of the CLI Enviroment, use the terminal in the biggest size possible (we reccomend fullscreen).
The GUI is set to the default resolution of 1500x850, we haven't found any problems with different screen sizes.

