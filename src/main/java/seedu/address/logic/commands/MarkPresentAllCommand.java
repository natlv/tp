package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL_GROUP;

import java.time.LocalDate;
import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.student.Student;
import seedu.address.model.student.TutorialGroup;

/**
 * Marks the attendance of all students in a specified tutorial group as present for a specified date.
 */
public class MarkPresentAllCommand extends Command {

    public static final String COMMAND_WORD = "markpresentall";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Marks all students in the specified tutorial group "
            + "as present for the specified date.\n"
            + "Parameters: " + PREFIX_TUTORIAL_GROUP + "TUTORIAL_GROUP " + PREFIX_DATE + "DATE\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_TUTORIAL_GROUP + "A01 " + PREFIX_DATE + "2019-10-09";

    public static final String MESSAGE_SUCCESS = "Marked all students from tutorial group %1$s as present on %2$s.";
    public static final String MESSAGE_EMPTY_TG = "The specified tutorial group is empty.";

    private final TutorialGroup tutorialGroup;
    private final LocalDate date;

    /**
     * Creates a MarkPresentAllCommand to mark the attendance of all students in the specified {@code TutorialGroup} as
     * present on the specified date.
     * @param tutorialGroup Tutorial group for which attendance is being marked.
     * @param date Date that students are marked present for.
     */
    public MarkPresentAllCommand(TutorialGroup tutorialGroup, LocalDate date) {
        requireNonNull(tutorialGroup);
        requireNonNull(date);
        this.tutorialGroup = tutorialGroup;
        this.date = date;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Student> studentsFromSpecifiedTutorialGroup = model.getStudentsByTutorialGroup(tutorialGroup);

        if (studentsFromSpecifiedTutorialGroup.isEmpty()) {
            throw new CommandException(MESSAGE_EMPTY_TG);
        }

        for (Student student : studentsFromSpecifiedTutorialGroup) {
            student.markAttendance(date, "p");
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, tutorialGroup, date));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MarkPresentAllCommand)) {
            return false;
        }

        MarkPresentAllCommand otherMarkPresentAllCommand = (MarkPresentAllCommand) other;
        return tutorialGroup.equals(otherMarkPresentAllCommand.tutorialGroup)
                && date.equals(otherMarkPresentAllCommand.date);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("tutorialGroup", tutorialGroup)
                .add("date", date)
                .toString();
    }
}