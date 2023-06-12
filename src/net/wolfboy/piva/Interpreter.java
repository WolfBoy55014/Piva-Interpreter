package net.wolfboy.piva;

import net.wolfboy.piva.ast.ASTNode;
import net.wolfboy.piva.ast.BinaryOperationNode;
import net.wolfboy.piva.ast.IntegerNode;
import net.wolfboy.piva.ast.UnaryOperationNode;
import net.wolfboy.piva.exception.PivaRuntimeException;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Interpreter {

    // Interpreting counterpart for piva.ast.UnaryOperationNode
    public float visitUnaryOperationNode(ASTNode node) throws PivaRuntimeException {

        /*
        def visit_UnaryOp(self, node):
            op = node.op.type
            if op == PLUS:
                return +self.visit(node.expr)
            elif op == MINUS:
                return -self.visit(node.expr)
         */

        UnaryOperationNode opNode = (UnaryOperationNode) node;
        return switch (opNode.operator) {
            case "+" -> +visit(opNode.right);
            case "-" -> -visit(opNode.right);
            default -> throw new PivaRuntimeException("Unknown Operator");
        };
    }

    // Interpreting counterpart for piva.ast.BinaryOperationNode
    public float visitBinaryOperationNode(ASTNode node) throws PivaRuntimeException {

        /*
        def visit_BinOp(self, node):
            if node.op.type == PLUS:
                return self.visit(node.left) + self.visit(node.right)
            elif node.op.type == MINUS:
                return self.visit(node.left) - self.visit(node.right)
            elif node.op.type == MUL:
                return self.visit(node.left) * self.visit(node.right)
            elif node.op.type == DIV:
                return self.visit(node.left) / self.visit(node.right)
         */

        BinaryOperationNode opNode = (BinaryOperationNode) node;
        return switch (opNode.operator) {
            case "+" -> visit(opNode.left) + visit(opNode.right);
            case "-" -> visit(opNode.left) - visit(opNode.right);
            case "*" -> visit(opNode.left) * visit(opNode.right);
            case "/" -> visit(opNode.left) / visit(opNode.right);
            case "^" -> (float) Math.pow(visit(opNode.left), visit(opNode.right));
            default -> throw new PivaRuntimeException("Unknown Operator");
        };
    }

    // Interpreting counterpart for piva.ast.IntegerNode
    public float visitIntegerNode(ASTNode node) {

        /*
        def visit_Num(self, node):
            return node.value
         */

        IntegerNode intNode = (IntegerNode) node;
        return Float.parseFloat(intNode.value);
    }


    public float visit(@NotNull ASTNode node) throws PivaRuntimeException {

        float result;
        String methodName = ""; // can be reached by catch from here |DO NOT MOVE|
        try {
            String typeName = node.getClass().getSimpleName(); // get the name of node type
            methodName = "visit" + typeName; // append "visit"

            Method func = this.getClass().getMethod(methodName, ASTNode.class); // find the method in the class, most prone to fail
            result = (float) func.invoke(this, node); // run the found method

        } catch (InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            // If method with corresponding inputs or name is not found, panic, this is our issue.
            throw new PivaRuntimeException("Cannot find appropriate " + methodName + " method");
        }

        return result;
    }

}
