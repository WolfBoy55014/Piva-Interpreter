package net.wolfboy.piva;

import net.wolfboy.piva.exception.PivaParsingErrorException;
import net.wolfboy.piva.exception.PivaRuntimeException;

import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

public class Main {
    static long index = 0;
    public static void main(String[] args) throws PivaParsingErrorException, PivaRuntimeException {

        // Hi

        while (true) {

            Scanner scanner = new Scanner(System.in);
            String code = scanner.nextLine(); // get input

            long start = System.currentTimeMillis(); // record start time

            Lexer lexer = new Lexer(code); // new lexer
            Parser parser = new Parser(lexer); // new parser
            Interpreter interpreter = new Interpreter(); // new interpreter
            float result = interpreter.visit(parser.parce()); // parce code and execute
            System.out.println(result);

            long end = System.currentTimeMillis(); // record end time
            long elapsedTime = end - start; // get length of time
            System.out.println("Process took: " + elapsedTime + "ms");
        }
    }


}