package com.digdes.school;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        JavaSchoolStarter starter = new JavaSchoolStarter();
        try {
            List<Map<String, Object>> result1 = starter.execute("INSERT VALUES  'id' = 1, 'Lastname' = 'Карасев', 'cost' = 25, 'age' = 33, 'active' = true");
            System.out.println("INSERT1: " + result1);
            List<Map<String, Object>> result2 = starter.execute("iNSERT VALUES  'id' = 2, 'lastname' = 'Иванов', 'age' = 18, 'active' = true");
            System.out.println("INSERT2: " + result2);
            List<Map<String, Object>> result3 = starter.execute("INSERT VALUES  'id' = 5, 'Lastname' = 'Карасев', 'cost' = 25, 'age' = 33, 'active' = true");
            System.out.println("INSERT3: " + result3);
            List<Map<String, Object>> result4 = starter.execute("INSERT VALUES  'id' = 3,'lastname' = 'Зотов',  'cost' = 10014100.0, 'age' = 100, 'active' = false");
            System.out.println("INSERT4: " + result4);
            List<Map<String, Object>> result5 = starter.execute("INSERT VALUES  'id' = 4, 'cost' = 4.04, 'age' = 60, 'active' = true");
            System.out.println("INSERT5: " + result5);
            List<Map<String, Object>> result6 = starter.execute("INSERT VALUES  'id' = 5");
            System.out.println("INSERT6: " + result6);
            List<Map<String, Object>> result7 = starter.execute("select WHERE 'LASTname' ilike 'Ива%'");
            System.out.println("SELECT1: " + result7);
            List<Map<String, Object>> result8 = starter.execute("select WHERE 'lastname' ilike 'Кар%' or 'id' = 4 or 'active' = false");
            System.out.println("SELECT2: " + result8);
            List<Map<String, Object>> result9 = starter.execute("select");
            System.out.println("SELECT3: " + result9);
            List<Map<String, Object>> result10 = starter.execute("select wHERE 'cost' >= 10000.0");
            System.out.println("SELECT4: " + result10);
            List<Map<String, Object>> result11 = starter.execute("uPDATE VALUES 'active' = false, WHERE 'lastname' ilike 'Ка%'");
            System.out.println("UPDATE1: " + result11);
            List<Map<String, Object>> result12 = starter.execute("UPDATE VALUES 'age' = 65, 'active'= false");
            System.out.println("UPDATE2: " + result12);
            List<Map<String, Object>> result13 = starter.execute("delete where 'cost' >= 100000.0");
            System.out.println("DELETE1: " + result13);
            List<Map<String, Object>> result14 = starter.execute("delete where 'age'=33");
            System.out.println("DELETE2: " + result14);
            List<Map<String, Object>> result15 = starter.execute("delete");
            System.out.println("DELETE3: " + result15);
            List<Map<String, Object>> result16 = starter.execute("safag");
            System.out.println("DELETE4: " + result16);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}