package methods;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // Примеры использования методов из HomeworkMethods1

        System.out.println("Задача 1: " + methods.HomeworkMethods1.isEven(4)); // true
        System.out.println("Задача 2: " + methods.HomeworkMethods1.checkAccess(20)); // Allowed
        System.out.println("Задача 3: " + methods.HomeworkMethods1.isPositive(-5)); // false
        System.out.println("Задача 4: " + methods.HomeworkMethods1.getGrade(55)); // C
        System.out.println("Задача 5: " + methods.HomeworkMethods1.blastOff(3)); // 3 2 1 Поехали!
        System.out.println("Задача 6: " + methods.HomeworkMethods1.sumToN(5)); // 15
        System.out.println("Задача 7: " + methods.HomeworkMethods1.hasBug(new String[]{"Hello", "Bug", "World"})); // true
        System.out.println("Задача 8: " + methods.HomeworkMethods1.getEvenInRange(2, 5)); // 2 4
        System.out.println("Задача 9: " + methods.HomeworkMethods1.findMax(new int[]{1, 5, 3, 9, 2})); // 9
        System.out.println("Задача 10: " + Arrays.toString(methods.HomeworkMethods1.reverse(new String[]{"One", "Two"}))); // [Two, One]

        List<Integer> numbers = Arrays.asList(10, 20, 30);
        System.out.println("Задача 11: " + methods.HomeworkMethods1.calcAverage(numbers)); // 20.0

        List<String> names = new ArrayList<>(Arrays.asList("Alice", "Bob", "Charlie"));
        System.out.println("Задача 12: " + methods.HomeworkMethods1.removeSpecificName(names, "Bob")); // [Alice, Charlie]
    }
}