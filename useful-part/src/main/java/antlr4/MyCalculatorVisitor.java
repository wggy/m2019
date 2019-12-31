package antlr4;

/***
 * created by wange on 2019/12/30 13:05
 */
public class MyCalculatorVisitor extends CalculatorBaseVisitor<Object> {

    @Override
    public Object visitParenExpr(CalculatorParser.ParenExprContext ctx) {
        return visit(ctx.expr());
    }

    @Override
    public Object visitMultOrDiv(CalculatorParser.MultOrDivContext ctx) {
        Object obj0 = ctx.expr(0).accept(this);
        Object obj1 = ctx.expr(1).accept(this);

        if ("*".equals(ctx.getChild(1).getText())) {
            return (Float) obj0 * (Float) obj1;
        } else if ("/".equals(ctx.getChild(1).getText())) {
            return (Float) obj0 / (Float) obj1;
        }
        return 0f;
    }

    @Override
    public Object visitAddOrSubstract(CalculatorParser.AddOrSubstractContext ctx) {
        Object obj0 = ctx.expr(0).accept(this);
        Object obj1 = ctx.expr(1).accept(this);

        if ("+".equals(ctx.getChild(1).getText())) {
            return (Float) obj0 + (Float) obj1;
        } else if ("-".equals(ctx.getChild(1).getText())) {
            return (Float) obj0 - (Float) obj1;
        }
        return 0f;
    }

    @Override
    public Object visitFloat(CalculatorParser.FloatContext ctx) {
        return Float.parseFloat(ctx.getText());
    }
}
