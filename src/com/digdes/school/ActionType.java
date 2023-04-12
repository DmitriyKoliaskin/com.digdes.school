package com.digdes.school;

/*
    Доступные команды для выполнения.
 */
public enum ActionType {
    INSERT("INSERT VALUES"),
    UPDATE("UPDATE VALUES"),
    DELETE("DELETE"),
    SELECT("SELECT");

    private final String value;

    ActionType(String value) {
        this.value = value;
    }

    public static ActionType actionType(String value) throws IllegalArgumentException {
            for (ActionType actionType : ActionType.values()) {
                if (value.equalsIgnoreCase(actionType.value)) {
                    return actionType;
                }
            }
        throw new IllegalArgumentException();
    }
}
