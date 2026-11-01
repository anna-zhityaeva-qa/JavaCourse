package methods;

import java.util.ArrayList;
import java.util.List;

public class HomeworkMethods1 {

    // ... (методы 1-12) ...

    public static boolean isEven(int n) {
        return n % 2 == 0;
    }

    public static String checkAccess(int age) {
        if (age > 18) {
            return "Allowed";
        } else {
            return "Denied";
        }
    }

    public static boolean isPositive(int n) {
        return n >= 0 ? true : false;
    }

    public static String getGrade(int score) {
        if (score >= 0 && score <= 20) {
            return "E";
        } else if (score >= 21 && score <= 40) {
            return "D";
        } else if (score >= 41 && score <= 60) {
            return "C";
        } else if (score >= 61 && score <= 80) {
            return "B";
        } else if (score >= 81 && score <= 100) {
            return "A";
        } else {
            return "Error";
        }
    }

    public static String blastOff(int start) {
        StringBuilder sb = new StringBuilder();
        for (int i = start; i >= 1; i--) {
            sb.append(i);
            if (i > 1) {
                sb.append(" ");
            }
        }
        sb.append(" Поехали!");
        return sb.toString();
    }

    public static int sumToN(int n) {
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            sum += i;
        }
        return sum;
    }

    public static boolean hasBug(String[] messages) {
        for (String msg : messages) {
            if ("Bug".equalsIgnoreCase(msg)) {
                return true;
            }
        }
        return false;
    }

    public static String getEvenInRange(int start, int end) {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (int i = start; i <= end; i++) {
            if (i % 2 == 0) {
                if (!first) {
                    sb.append(" ");
                }
                sb.append(i);
                first = false;
            }
        }
        return sb.toString();
    }

    public static int findMax(int[] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("Array is empty or null");
        }
        int max = arr[0];
        for (int num : arr) {
            if (num > max) {
                max = num;
            }
        }
        return max;
    }

    public static String[] reverse(String[] arr) {
        if (arr == null) return null;
        String[] reversed = new String[arr.length];
        for (int i = 0; i < arr.length; i++) {
            reversed[i] = arr[arr.length - 1 - i];
        }
        return reversed;
    }

    public static double calcAverage(List<Integer> list) {
        if (list == null || list.isEmpty()) {
            return 0.0;
        }
        int sum = 0;
        for (Integer num : list) {
            sum += num;
        }
        return (double) sum / list.size();
    }

    public static List<String> removeSpecificName(List<String> list, String nameToRemove) {
        List<String> newList = new ArrayList<>();
        if (list != null) {
            for (String name : list) {
                if (!name.equals(nameToRemove)) {
                    newList.add(name);
                }
            }
        }
        return newList;
    }

    // === НОВЫЙ МЕТОД ДЛЯ 3-Й РАБОТЫ (возвращает List) ===
    // Возвращает список имен, начинающихся с буквы "A"
    public static List<String> getNamesStartingWithA(List<String> names) {
        List<String> result = new ArrayList<>();
        if (names != null) {
            for (String name : names) {
                if (name != null && name.startsWith("A")) {
                    result.add(name);
                }
            }
        }
        return result;
    }
}