package net.wolfboy.piva.token;

import net.wolfboy.piva.exception.PivaParsingErrorException;

public class Token {
    public final TokenTypes type;
    public final String value;

    // Token Constructor
    public Token(TokenTypes type, String value) {
        this.value = value;
        this.type = type;
    }

    public int getValueAsInt() throws PivaParsingErrorException {
        try {
            return Integer.parseInt(this.value);
        } catch (NumberFormatException e) {
            throw new PivaParsingErrorException("Value is not an Integer");
        }
    }
}

