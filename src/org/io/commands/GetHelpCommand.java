package org.io.commands;

import org.annotations.Alias;
import org.exceptions.InvalidInputException;
import org.io.OutputWriter;

@Alias("help")
public class GetHelpCommand extends Command {

    public GetHelpCommand(String input, String[] data) {
        super(input, data);
    }

    @Override
    public void execute() throws Exception {
        if (this.getData().length != 1) {
            throw new InvalidInputException(this.getInput());
        }

        displayHelp();
    }

    private void displayHelp() {
        StringBuilder builder = new StringBuilder();
        builder.append("make directory - mkdir nameOfFolder")
                .append(System.lineSeparator());
        builder.append("traverse directory - ls depth")
                .append(System.lineSeparator());
        builder.append("comparing files - cmp absolutePath1 absolutePath2")
                .append(System.lineSeparator());
        builder.append("change directory - cdRel relativePath or \"..\" for level up")
                .append(System.lineSeparator());
        builder.append("change directory - cdAbs absolutePath")
                .append(System.lineSeparator());
        builder.append("read students data base - readDb fileName")
                .append(System.lineSeparator());
        builder.append("filter students - filter {courseName} excellent/average/poor take 2/5/all")
                .append(System.lineSeparator());
        builder.append("order students - order {courseName} ascending/descending take 20/10/all")
                .append(System.lineSeparator());
        builder.append("download file - download URL (saved in current directory)")
                .append(System.lineSeparator());
        builder.append("download file on new thread - downloadAsynch URL (saved in the current directory)")
                .append(System.lineSeparator());
        builder.append("display data entities - display students/courses ascending/descending")
                .append(System.lineSeparator());
        builder.append("get help – help")
                .append(System.lineSeparator());
        OutputWriter.writeMessage(builder.toString());
        OutputWriter.writeEmptyLine();
    }
}
