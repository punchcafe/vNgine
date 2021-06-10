package dev.punchcafe.vngine.config.yaml;

public enum VariableTypes {
    STR, BOOL, INT;
    public static VariableTypes convertFromPom(final dev.punchcafe.vngine.pom.model.VariableTypes variableTypes){
        switch (variableTypes){
            case INT:
                return INT;
            case STR:
                return STR;
            case BOOL:
                return BOOL;
            default:
                return null;
        }
    }
}
