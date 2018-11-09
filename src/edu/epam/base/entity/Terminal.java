package edu.epam.base.entity;

import edu.epam.base.util.IdGenerator;


public class Terminal {
    private long terminalId;

    public Terminal() {
        this.terminalId = IdGenerator.getTerminalCounter();
    }

    public long getTerminalId() {
        return terminalId;
    }
}
