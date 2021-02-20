package dev.punchcafe.vngine.expression;

public class IntegerPredicateExpression implements Expression {

    public enum Comparison {
        EQUALS, LESS_THAN, MORE_THAN,
    }

    private String leftHandSide;
    private String rightHandSide;
    private Comparison comparison;

    @Override
    public String getLhs() {
        return null;
    }

    @Override
    public String getRhs() {
        return null;
    }

    @Override
    public ArgumentType getLhsType() {
        return null;
    }

    @Override
    public ArgumentType getRhsType() {
        return null;
    }

}
