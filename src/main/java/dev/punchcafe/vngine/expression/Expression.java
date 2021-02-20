package dev.punchcafe.vngine.expression;

public interface Expression {

    String getLhs();
    String getRhs();

    default ArgumentType getLhsType(){
        return this.getLhs().startsWith("$") ? ArgumentType.VARIABLE : ArgumentType.VALUE;
    }

    default ArgumentType getRhsType(){
        return this.getRhs().startsWith("$") ? ArgumentType.VARIABLE : ArgumentType.VALUE;
    };
}
