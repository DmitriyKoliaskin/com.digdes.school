package com.digdes.school;

import com.digdes.school.Exception.InputException;

import java.util.Arrays;
import java.util.Map;
import java.util.regex.Pattern;

/*
    Данный класс содержит вспомогательные методы проверки входящих команд.
 */
public class JavaSchoolStarterCheck {
    public static boolean checkIndex(String index) throws InputException {
        String idRegex = "('id'=[0-9]+)";
        String lastnameRegex = "('lastname'(=)'[a-zA-Zа-яА-Я]+')";
        String ageRegex = "('age'=[0-9]+)";
        String costRegex = "('cost'=[0-9]+(\\.[0-9]+)*)";
        String activeRegex = "('active'=(true|false))";
        String regex = String.format(
                "(?i)%s|%s|%s|%s|%s",
                idRegex,
                lastnameRegex,
                ageRegex,
                costRegex,
                activeRegex
        );
        if (!Pattern.matches(regex, index)) {
            throw new InputException("Input error : " + index);
        } else {
            return true;
        }
    }

    public static boolean operator(String operator) throws InputException {
        String idRegex = "('id'(=|!=|>=|<=|>|<)[0-9]+)";
        String lastnameRegex = "('lastname'(=|!=|like|ilike)'%?[a-zA-Zа-яА-Я]+%?')";
        String ageRegex = "('age'(=|!=|>=|<=|>|<)[0-9]+)";
        String costRegex = "('cost'(=|!=|>=|<=|>|<)[0-9]+(\\.[0-9]+))";
        String activeRegex = "('active'(=|!=)(true|false))";
        String regex = String.format(
                        "(?i)%s|%s|%s|%s|%s",
                idRegex,
                lastnameRegex,
                ageRegex,
                costRegex,
                activeRegex
        );
        if (!Pattern.matches(regex, operator)) {
            throw new InputException("Input error :" + operator);
        } else {
            return true;
        }
    }
    public static boolean checkWhere(Map<String, Object> map, String string) {
        String[] strings = string.split("(?i)OR");
        Operator operator = new Operator();
        return Arrays.stream(strings)
            .map(cr -> cr.split("(?i)AND"))
            .anyMatch(conditions -> Arrays.stream(conditions)
                        .allMatch(condition -> {
                            try {
                                return operator.checkOperation(map, condition);
                            } catch (InputException e) {
                                throw new RuntimeException(e);
                            }
                        })
                );
    }
}
