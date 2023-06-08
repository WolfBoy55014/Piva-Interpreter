package net.wolfboy.piva;

import net.wolfboy.piva.exception.PivaParsingErrorException;
import net.wolfboy.piva.token.Token;
import net.wolfboy.piva.token.TokenTypes;

import java.util.Objects;

public class Lexer {
    String code;
    int pos;
    String currentCharacter;

    // Lexer Constructor
    public Lexer(String code) {
        this.code = code;
        this.pos = 0;
        this.currentCharacter = getCurrentCharacter();
    }

    Token getNextToken() throws PivaParsingErrorException {

        if (pos < code.length()) {

            space(); // Skip all spaces

            // Here we decide which token the character is
            if (Character.isDigit(currentCharacter)) {
                return new Token(TokenTypes.INTEGER, integer());

            } else if (Objects.equals(currentCharacter, "+")) {
                advance();
                return new Token(TokenTypes.ADD, "+");

            } else if (Objects.equals(currentCharacter, "-")) {
                advance();
                return new Token(TokenTypes.SUBTRACT, "-");

            } else if (Objects.equals(currentCharacter, "*")) {
                advance();
                return new Token(TokenTypes.MULTIPLY, "*");

            } else if (Objects.equals(currentCharacter, "/")) {
                advance();
                return new Token(TokenTypes.DIVIDE, "/");

            } else if (Objects.equals(currentCharacter, "^")) {
                advance();
                return new Token(TokenTypes.EXPONENT, "^");

            } else if (Objects.equals(currentCharacter, "(")) {
                advance();
                return new Token(TokenTypes.LPAREN, "(");

            } else if (Objects.equals(currentCharacter, ")")) {
                advance();
                return new Token(TokenTypes.RPAREN, ")");

            } else {
                throw new PivaParsingErrorException("Invalid Symbol"); // If we don't understand the character, give up
            }

        } else {
            // If we hit the end, send an End Of File
            return new Token(TokenTypes.EOF, null);
        }
    }

    String getCurrentCharacter() {
        return code.substring(pos, pos + 1);
    }
    void advance() {

        /*
        self.pos += 1
        if self.pos > len(self.text) - 1:
            self.current_char = None  # Indicates end of input
        else:
            self.current_char = self.text[self.pos]
         */

        pos++;
        if (pos >= code.length()) {
            currentCharacter = null;
        } else {
            currentCharacter = getCurrentCharacter();
        }
    }
    String integer() {
        String integer = "";
        while (pos < code.length() && Character.isDigit(currentCharacter)) {
            integer = integer.concat(currentCharacter);
            advance();
        }
        return integer;
    }
    void space() {
        /*
        while self.current_char is not None and self.current_char.isspace():
            self.advance()
         */

        while (Objects.equals(currentCharacter, " ")) {
            advance();
        }
    }

}
