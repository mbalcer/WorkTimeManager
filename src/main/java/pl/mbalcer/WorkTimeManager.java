package pl.mbalcer;

import picocli.CommandLine;

public class WorkTimeManager {
    public static void main(String[] args) {
        new CommandLine(new GreetingCommand()).execute("mateusz");
    }
}
