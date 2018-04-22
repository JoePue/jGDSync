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

    private boolean processed;
    private boolean successful;

}
