package net.wolfboy.piva.ast;

public class UnaryOperationNode extends ASTNode {
    public final String operator;
    public final ASTNode right;

    public UnaryOperationNode(String operator, ASTNode right) {
        this.operator = operator;
        this.right = right;
    }
}
