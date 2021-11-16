# Work Time Manager

The application to manage your working time.

### Usage
You have at your disposal six command to manage:
```shell
Usage: work-time-manager [COMMAND]
Commands:
  save       Save today's working time
  print      Print monthly work time report
  history    Save historical work time
  export     Export work time report to file
  calculate  Working time calculator
  break      Save break times in the day
```

If you don't know how to use commands then write the `--help` option with the command e.g.
```shell
work-time-manager history --help
```
You should get that answer:
```shell
Save historical work time

work-time-manager history [-hV] -d=<date> [-e=<end>] [-s=<start>]

Description:
You can save the working time from another day

Options:
  -d, --date=<date>     Date in format dd.MM.yyyy
  -s, --start=<start>   Start working time in format HH:mm
  -e, --end=<end>       End working time in format HH:mm
  -h, --help            Show this help message and exit.
  -V, --version         Print version information and exit.
```

## Running the application

To run the application you will need [Java 11](https://www.oracle.com/java/technologies/downloads/#java11-linux) installed on your computer.

Clone this repo:
```shell
git clone https://github.com/mbalcer/WorkTimeManager.git && cd WorkTimeManager
```
Package the application using:
```shell script
./mvnw package -Dquarkus.package.type=uber-jar
```
The application is now runnable using:
```shell
java -jar target/work-time-manager-1.0.0-SNAPSHOT-runner.jar
```

### Create alias
You can create an alias and use it as a command. In your cmd execute this (change absolute_path to the right path): 
```shell
alias work-time-manager='java -jar {absolute_path}/target/work-time-manager-1.0.0-SNAPSHOT-runner.jar'
```
From now you can use `wtm` alias e.g.
```shell
work-time-manager save --start
```

## Technologies
- Java 11
- Quarkus
- Picocli
- Maven
- H2 Database

---

Created by <a href="https://github.com/mbalcer"> @mbalcer </a>