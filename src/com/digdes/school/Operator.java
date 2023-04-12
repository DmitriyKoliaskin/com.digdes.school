package com.digdes.school;

import java.util.Map;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.digdes.school.Exception.InputException;

/*
    Класс реализующий операторы сравнения и операторы сравнения.
 */
public class Operator {
    public boolean checkOperation(Map<String, Object> map, String position) throws InputException {
        if (JavaSchoolStarterCheck.operator(position)) {
            position = position.replaceAll("'","");
            String numberKey = matcherGroup("(id|age)", position);
            String stringValue;
            String operator;
            if (!numberKey.isEmpty()) {
                if (map.get(numberKey) == null) {
                    return false;
                }
                stringValue = matcherGroup("[0-9]+", position);
                operator = matcherGroup("(=|!=|>=|<=|>|<)", position);
                int mapValue = (Integer) map.get(numberKey);
                int intValue = Integer.parseInt(stringValue);
                switch (operator) {
                    case "=" -> {
                        return mapValue == intValue;
                    }
                    case "!=" -> {
                        return mapValue != intValue;
                    }
                    case ">=" -> {
                        return mapValue >= intValue;
                    }
                    case "<=" -> {
                        return mapValue <= intValue;
                    }
                    case ">" -> {
                        return mapValue > intValue;
                    }
                    case "<" -> {
                        return mapValue < intValue;
                    }
                }
            } else if (position.toLowerCase().contains("lastname")) {
                if(map.get("lastname") == null) {
                    return false;
                }
                operator = matcherGroup("(=|!=|like|ilike)", position);
                position = position.substring(8);
                if(Objects.equals(operator, "=")) {
                    stringValue = matcherGroup("%?[a-zA-Zа-яА-Я]+%?", position.substring(1));
                    return(Objects.equals( map.get("lastname"), stringValue));
                } else if(Objects.equals(operator, "!=")) {
                    stringValue = matcherGroup("%?[a-zA-Zа-яА-Я]+%?", position.substring(2));
                    return(!Objects.equals( map.get("lastname"), stringValue));
                } else if(Objects.equals(operator, "ilike")) {
                    stringValue = matcherGroup("%?[a-zA-Zа-яА-Я]+%?", position.substring(5));
                    if(stringValue.charAt(0) == '%' && stringValue.charAt(stringValue.length() - 1) == '%') {
                        stringValue = stringValue.replaceAll("%","");
                        return (Pattern.matches(".*" + stringValue + ".*", ((String) map.get("lastname"))));
                    } else if(stringValue.charAt(0) == '%') {
                        stringValue = stringValue.replaceAll("%","");
                        return (Pattern.matches(".*" + stringValue, ((String) map.get("lastname"))));
                    } else if(stringValue.charAt(stringValue.length() - 1) == '%') {
                        stringValue = stringValue.replaceAll("%","");
                        return (Pattern.matches(stringValue + ".*", ((String) map.get("lastname"))));
                    } else {
                        return(Objects.equals(((String) map.get("lastname")), stringValue));
                    }
                } else if(Objects.equals(operator, "like")) {
                    stringValue = matcherGroup("%?[a-zA-Zа-яА-Я]+%?", position.substring(4));
                    if(stringValue.charAt(0) == '%' && stringValue.charAt(stringValue.length() - 1) == '%') {
                        stringValue = stringValue.replaceAll("%","");
                        return (Pattern.matches(".*" + stringValue + ".*", (String) map.get("lastname")));
                    } else if(stringValue.charAt(0) == '%') {
                        stringValue = stringValue.replaceAll("%","");
                        return (Pattern.matches(".*" + stringValue, (String) map.get("lastname")));
                    } else if(stringValue.charAt(stringValue.length() - 1) == '%') {
                        stringValue = stringValue.replaceAll("%","");
                        return (Pattern.matches(stringValue + ".*", (String) map.get("lastname")));
                    } else {
                        return(Objects.equals( map.get("lastname"), stringValue));
                    }
                }
                return false;
            } else if(position.toLowerCase().contains("active")) {
                if(map.get("active") == null) {
                    return false;
                }
                stringValue = matcherGroup("(?i)(true|false)", position);
                if(position.contains("=")) {
                    return ((boolean) map.get("active") == Boolean.parseBoolean(stringValue));
                } else if(position.contains("!=")) {
                    return ((boolean) map.get("active") != Boolean.parseBoolean(stringValue));
                }
            } else if(position.toLowerCase().contains("cost")) {
                if(map.get("cost") == null) {
                    return false;
                }
                stringValue = matcherGroup("[0-9]+(\\.[0-9]+)?", position);
                operator = matcherGroup("(=|!=|>=|<=|>|<)", position);
                if (Objects.equals(operator, "=")) {
                    return (((Double) map.get("cost")) == Double.parseDouble(stringValue));
                } else if (Objects.equals(operator, "!=")) {
                    return (((Double) map.get("cost")) != Double.parseDouble(stringValue));
                } else if (Objects.equals(operator, ">=")) {
                    return (((Double) map.get("cost")) >= Double.parseDouble(stringValue));
                } else if (Objects.equals(operator, "<=")) {
                    return (((Double) map.get("cost")) <= Double.parseDouble(stringValue));
                } else if (Objects.equals(operator, "<")) {
                    return (((Double) map.get("cost")) < Double.parseDouble(stringValue));
                } else if (Objects.equals(operator, ">")) {
                    return (((Double) map.get("cost")) > Double.parseDouble(stringValue));
                }
            }
        }
        return false;
    }
    public String matcherGroup(String pattern, String value) {
        String stringValue = "";
        Matcher matcher = (Pattern.compile(pattern)).matcher(value);
        if (matcher.find()) {
            stringValue = matcher.group();
        }
        return stringValue;
    }
}