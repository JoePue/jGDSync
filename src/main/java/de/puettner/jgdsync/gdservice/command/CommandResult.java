package de.puettner.jgdsync.gdservice.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class CommandResult {
    /** Die Verarbeitung des Eingabebefehls war erfolgreich. */
    public static final CommandResult SUCCESS = new CommandResult(true, true);
    /** Beim Verarbeiten des Eingabebefehls ist ein Fehler aufgetreten (Befehl erkannt, Ausführung nicht erfolgreich) */
    public static final CommandResult PROCESS_ERROR = new CommandResult(true, false);

    private boolean processed;
    private boolean successful;

}
