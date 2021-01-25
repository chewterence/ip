package duke;

import duke.command.Command;
import duke.command.HelpCommand;
import duke.command.ListCommand;
import duke.command.ByeCommand;
import duke.command.DoneCommand;
import duke.command.DeleteCommand;
import duke.command.AddToDo;
import duke.command.AddDeadline;
import duke.command.AddEvent;

import java.time.LocalDate;

public class Parser {

    public static Command parse(String input) throws DukeException {

        String[] processedInput = input.split(" ");
        String command = processedInput[0];
        String description = processDescription(processedInput);

        switch (command) {
        case "help":
            return new HelpCommand();
        case "list":
            return new ListCommand();
        case "bye":
            return new ByeCommand();
        case "done":
            if (processedInput.length == 1) {
                throw new DukeException("Please enter a duke.task number to mark done");
            }
            return new DoneCommand(Integer.parseInt(processedInput[1]));
        case "todo":
            return new AddToDo(command, description);
        case "event":
            try {
                return processEvent(input);
            } catch (Exception e) {
                e.printStackTrace();
                throw new DukeException("\nPlease enter a valid format '/at YYYY-MM-DD XXXX-YYYY'");
            }
        case "deadline":
            try {
                return processDeadline(input);
            } catch (Exception e) {
                e.printStackTrace();
                throw new DukeException("\nPlease enter a valid format '/by YYYY-MM-DD TIME'");
            }
        case "delete":
            if (processedInput.length == 1) {
                throw new DukeException("Please enter a duke.task number to delete");
            }
            return new DeleteCommand(Integer.parseInt(processedInput[1]));
        default:
            throw new DukeException("Invalid duke.command. Please enter a valid one");
        }
        
    }

    private static AddDeadline processDeadline(String input) throws Exception {
        String preProcessedData = input.split(" /by ")[1];
        String[] dateTime = preProcessedData.split(" ");
        String time = dateTime[1];
        LocalDate deadline = LocalDate.parse(dateTime[0]);
        String description = input.split(" /by ")[0].split("deadline ")[1];
        return new AddDeadline("deadline", description, deadline, time);
    }

    private static AddEvent processEvent(String input) throws Exception {
        String preProcessedData = input.split(" /at ")[1];
        String[] dateTime = preProcessedData.split(" ");
        LocalDate eventDate = LocalDate.parse(dateTime[0]);
        String startTime = dateTime[1].split("-")[0];
        String endTime = dateTime[1].split("-")[1];
        String description = input.split(" /at ")[0].split("event ")[1];
        return new AddEvent("event", description, eventDate, startTime, endTime);
    }

    public static String processDescription(String[] processedInput) {
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < processedInput.length; i++) {
            sb.append(processedInput[i] + " ");
        }
        return sb.toString();
    }

}