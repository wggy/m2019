package antlr4;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

/***
 * created by wange on 2019/12/30 13:07
 */
public class Driver {

    public static void main(String[] args) {
        String query = "3.1 * (6.3 - 4.51) + 5 * 4";

        CalculatorLexer lexer = new CalculatorLexer(new ANTLRInputStream(query));
        CalculatorParser parser = new CalculatorParser(new CommonTokenStream(lexer));
        CalculatorVisitor visitor = new MyCalculatorVisitor();

        System.out.println(visitor.visit(parser.expr()));  // 25.549
    }
}
