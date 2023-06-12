package net.wolfboy.piva.ast;

import net.wolfboy.piva.token.Token;
import org.jetbrains.annotations.NotNull;

public class IntegerNode extends ASTNode {
    public final Token token;
    public final String value;

    public IntegerNode(@NotNull Token token) {
        this.token = token;
        this.value = token.value;
    }
}
