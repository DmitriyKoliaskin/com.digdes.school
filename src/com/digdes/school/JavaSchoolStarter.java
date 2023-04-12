package com.digdes.school;

import com.digdes.school.Exception.ExecuteException;
import com.digdes.school.Exception.InputException;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.digdes.school.JavaSchoolStarterCheck.checkIndex;
import static com.digdes.school.JavaSchoolStarterCheck.checkWhere;

public class JavaSchoolStarter {

    public List<Map<String, Object>> table = new ArrayList<>();
    public List<Map<String, Object>> actionTable;

    public JavaSchoolStarter() {

    }

    public List<Map<String, Object>> execute (String request) throws ExecuteException, InputException, IllegalArgumentException {
        String regex = "(?i)(?<action>INSERT VALUES|UPDATE VALUES|SELECT|DELETE)(?<values>.*)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(request);
        if(matcher.find()) {
            String action = matcher.group("action");
            String values = matcher.group("values")
                    .replaceAll("\\s+", "");
            ActionType actionType = ActionType.actionType(action);
            return switch (actionType) {
                case INSERT -> actionTable = insert(values);
                case UPDATE -> actionTable = update(values);
                case SELECT -> actionTable = select(values);
                case DELETE -> actionTable = delete(values);
            };
        }
        throw new ExecuteException("not found command");
    }

    private List<Map<String, Object>> insert(String input) throws InputException {
        Map<String, Object> row = new HashMap<>();
        List<Map<String, Object>> insertTable = new ArrayList<>();
        String[] indexs = input.split(",");
        for (String index : indexs) {
            checkIndex(index);
            index = index.replaceAll("'", "");
            String[] keyValue = index.split("=");
            for (int i = 0; i < keyValue.length-1; i++) {
                switch (keyValue[i].toLowerCase()) {
                    case "id", "age" -> row.put(keyValue[i].toLowerCase(), Integer.parseInt(keyValue[i + 1]));
                    case "lastname" -> row.put(keyValue[i].toLowerCase(), keyValue[i + 1]);
                    case "cost" -> row.put(keyValue[i].toLowerCase(), Double.parseDouble(keyValue[i + 1]));
                    case "active" -> row.put(keyValue[i].toLowerCase(), Boolean.parseBoolean(keyValue[i + 1]));
                }
            }
        }
        table.add(row);
        insertTable.add(row);
        return insertTable;
    }

    private List<Map<String, Object>> update(String input){
        List<Map<String, Object>> updateTable = new ArrayList<>();
        String[] indexValues = input.split("(?i)WHERE");

        for (Map<String, Object> row : table) {
            if (indexValues.length == 1 || checkWhere(row, indexValues[1])) {
                String[] indexs = indexValues[0].split(",");
                for (String index : indexs) {
                    index = index.replaceAll("'", "");
                    String[] keyValue = index.split("=");
                    switch (keyValue[0]) {
                        case "id", "age" -> row.put(keyValue[0], Integer.parseInt(keyValue[1]));
                        case "lastname" -> row.put(keyValue[0], keyValue[1]);
                        case "cost" -> row.put(keyValue[0], Double.parseDouble(keyValue[1]));
                        case "active" -> row.put(keyValue[0], Boolean.parseBoolean(keyValue[1]));

                    }
                }
                updateTable.add(row);
            }
        }
        return updateTable;
    }

    private List<Map<String, Object>> select(String request) {
        List<Map<String, Object>> selectTable = new ArrayList<>();
        if (request.length() == 0) {
            if (table.isEmpty()) {
                System.out.println("Table is empty: ");
            }
            return table;
        } else {
            String[] conditions = request.split("(?i)WHERE");
            for (Map<String, Object> row : table) {
                if (conditions.length == 1 && checkWhere(row, conditions[0])) {
                    selectTable.add(row);
                    continue;
                }
                if (conditions.length == 2 && checkWhere(row, conditions[1])) {
                    selectTable.add(row);
                }
            }
            return selectTable;
        }
    }
    private List<Map<String, Object>> delete(String request) {
        List<Map<String, Object>> deleteTable = new ArrayList<>();
        if (request.isEmpty()) {
            deleteTable.addAll(table);
            table.clear();
        } else {
            String[] conditions = request.split("(?i)WHERE");
            Iterator<Map<String, Object>> iterator = table.iterator();
            while (iterator.hasNext()) {
                Map<String, Object> row = iterator.next();
                if (conditions.length == 1 && checkWhere(row, conditions[0])) {
                    deleteTable.add(row);
                    iterator.remove();
                } else if (conditions.length == 2 && checkWhere(row, conditions[1])) {
                    deleteTable.add(row);
                    iterator.remove();
                }
            }
        }
        return deleteTable;
    }
}
