package net.wolfboy.piva;

import net.wolfboy.piva.ast.ASTNode;
import net.wolfboy.piva.ast.BinaryOperationNode;
import net.wolfboy.piva.ast.IntegerNode;
import net.wolfboy.piva.ast.UnaryOperationNode;
import net.wolfboy.piva.exception.PivaParsingErrorException;
import net.wolfboy.piva.token.Token;
import net.wolfboy.piva.token.TokenTypes;
import org.jetbrains.annotations.NotNull;

public class Parser  {
    final Lexer lexer;
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

    ASTNode parce() throws PivaParsingErrorException {
        return expr();
    }

    ASTNode expr() throws PivaParsingErrorException {
        /*
        expr   : muldiv ((ADD | SUBTRACT) muldiv)*
        muldiv : expon ((MULTIPLY | DIVIDE) expon)*
        expon  : paren (EXPONENT paren)*
        paren  : (LPAREN expr RPAREN) | factor
        factor : ((ADD | SUBTRACT) factor) | INTEGER
         */


        ASTNode node = muldiv();
        while (currentToken.type == TokenTypes.ADD | currentToken.type == TokenTypes.SUBTRACT) {
            Token token = currentToken;
            if (token.type == TokenTypes.ADD) {
                eat(TokenTypes.ADD);
            } else {
                eat(TokenTypes.SUBTRACT);
            }
            node = new BinaryOperationNode(node, token.value, muldiv());

        }
        return node;
    }

    ASTNode muldiv() throws PivaParsingErrorException {
        ASTNode node = expon();
        while (currentToken.type == TokenTypes.MULTIPLY | currentToken.type == TokenTypes.DIVIDE) {
            Token token = currentToken;
            if (token.type == TokenTypes.MULTIPLY) {
                eat(TokenTypes.MULTIPLY);
            } else {
                eat(TokenTypes.DIVIDE);
            }
            node = new BinaryOperationNode(node, token.value, expon());
        }
        return node;
    }

    ASTNode expon() throws PivaParsingErrorException {
        ASTNode node = paren();
        while (currentToken.type == TokenTypes.EXPONENT) {
            eat(TokenTypes.EXPONENT);
            node = new BinaryOperationNode(node, "^", paren());
        }
        return node;
    }

    ASTNode paren() throws PivaParsingErrorException {
        ASTNode node;
        if (currentToken.type == TokenTypes.LPAREN) {
            eat(TokenTypes.LPAREN);
            node = expr(); // Loop to hold entire expression in parentheses
            eat(TokenTypes.RPAREN);
        } else {
            node = factor();
        }
        return node;
    }

    ASTNode factor() throws PivaParsingErrorException {
        Token token = currentToken;
        if (token.type == TokenTypes.ADD) {

            eat(TokenTypes.ADD);
            return new UnaryOperationNode("+", factor()); // Unary operation +8

        } else if (token.type == TokenTypes.SUBTRACT) {

            eat(TokenTypes.SUBTRACT);
            return new UnaryOperationNode("-", factor()); // Unary operation -7

        } else if (token.type == TokenTypes.INTEGER) {

            eat(TokenTypes.INTEGER);
            return new IntegerNode(token); // Normal, old integer

        } else {
            throw new PivaParsingErrorException("Incomplete or Incorrect Expression");
        }
    }
}
