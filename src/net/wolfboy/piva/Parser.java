package net.wolfboy.piva;

import net.wolfboy.piva.exception.PivaParsingErrorException;
import net.wolfboy.piva.token.Token;
import net.wolfboy.piva.token.TokenTypes;
import org.jetbrains.annotations.NotNull;

public class Parser  {
    Lexer lexer;
    Token currentToken;

    // Parser Constructor
    public Parser(@NotNull Lexer lexer) throws PivaParsingErrorException {
        this.lexer = lexer;
        this.currentToken = lexer.getNextToken();
    }

    void eat(TokenTypes tokenType) throws PivaParsingErrorException {
        if (tokenType == currentToken.type) {
            currentToken = lexer.getNextToken();
        } else {
            throw new PivaParsingErrorException("Incomplete or Incorrect Expression");
        }
    }

    float parce() throws PivaParsingErrorException {
        return expr();
    }

    float expr() throws PivaParsingErrorException {
        float result = muldiv();
        while (currentToken.type == TokenTypes.ADD | currentToken.type == TokenTypes.SUBTRACT) {
            if (currentToken.type == TokenTypes.ADD) {
                eat(TokenTypes.ADD);
                result = result + muldiv();
            } else {
                eat(TokenTypes.SUBTRACT);
                result = result - muldiv();
            }
        }
        return result;
    }

    float muldiv() throws PivaParsingErrorException {
        float result = expon();
        while (currentToken.type == TokenTypes.MULTIPLY | currentToken.type == TokenTypes.DIVIDE) {
            if (currentToken.type == TokenTypes.MULTIPLY) {
                eat(TokenTypes.MULTIPLY);
                result = result * expon();
            } else {
                eat(TokenTypes.DIVIDE);
                result = result / expon();
            }
        }
        return result;
    }

    float expon() throws PivaParsingErrorException {
        float result = paren();
        while (currentToken.type == TokenTypes.EXPONENT) {
            eat(TokenTypes.EXPONENT);
            result = (float) Math.pow(result, paren());
        }
        return result;
    }

    float paren() throws PivaParsingErrorException {
        float result = 0;
        if (currentToken.type == TokenTypes.INTEGER) {
            result = factor();
        } else {
            eat(TokenTypes.LPAREN);
            result = expr();
            eat(TokenTypes.RPAREN);
        }
        return result;
    }

    int factor() throws PivaParsingErrorException {
        Token token = currentToken;
        eat(TokenTypes.INTEGER);
        return Integer.parseInt(token.value);
    }

}
