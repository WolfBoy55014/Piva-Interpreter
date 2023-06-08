package net.wolfboy.piva;

import net.wolfboy.piva.exception.PivaParsingErrorException;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws PivaParsingErrorException {

        while (true) {
            Scanner scanner = new Scanner(System.in);
            String code = scanner.nextLine();
            Lexer lexer = new Lexer(code);
            Parser parser = new Parser(lexer);

            System.out.println(parser.parce());
        }

    }
}