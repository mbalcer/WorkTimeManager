package pl.mbalcer;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;
import io.quarkus.runtime.annotations.QuarkusMain;
import picocli.CommandLine;
import pl.mbalcer.cmd.*;

import javax.inject.Inject;

@QuarkusMain
public class WorkTimeManager implements QuarkusApplication {
    @Inject SaveCommand saveCommand;
    @Inject PrintCommand printCommand;
    @Inject HistoryCommand historyCommand;
    @Inject ExportCommand exportCommand;

    public static void main(String[] args) {
        Quarkus.run(WorkTimeManager.class, args);
    }

    @Override
    public int run(String... args) throws Exception {
        return new CommandLine(new WorkTimeManagerCommand())
                .addSubcommand(saveCommand)
                .addSubcommand(printCommand)
                .addSubcommand(historyCommand)
                .addSubcommand(exportCommand)
                .execute(args);
    }
}
