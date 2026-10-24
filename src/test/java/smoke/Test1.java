package smoke;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Random;

public class Test1 {

    private static final Random random = new Random();

    private void printTestStart(String methodName) {
        System.out.println("========================Test method start");
        System.out.println("Running: " + methodName);
    }

    private void printTestEnd() {
        System.out.println("Test method end");
        System.out.println("========================");
    }

    // --- ЗАДАЧА 1 (ЛЕКЦИЯ): Тесты с выводом сообщений и случайными данными ---

    @Test
    @DisplayName("Тест isEven со случайным числом")
    void testIsEvenRandom() {
        printTestStart("isEven");

        int n = random.nextInt(100) + 1;
        boolean expected = n % 2 == 0;
        boolean actual = methods.HomeworkMethods1.isEven(n);

        if (actual == expected) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }

        printTestEnd();
    }

    @RepeatedTest(20)
    @DisplayName("Тест checkAccess 20 раз со случайным возрастом")
    void testCheckAccessRepeated() {
        printTestStart("checkAccess");

        int age = random.nextInt(100);
        String expected = age > 18 ? "Allowed" : "Denied";
        String actual = methods.HomeworkMethods1.checkAccess(age);

        if (actual.equals(expected)) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }

        printTestEnd();
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 25, 55, 75, 99, 100})
    @DisplayName("Тест getGrade с параметризованными данными")
    void testGetGradeParameterized(int score) {
        printTestStart("getGrade");

        String expected;
        if (score >= 0 && score <= 20) expected = "E";
        else if (score >= 21 && score <= 40) expected = "D";
        else if (score >= 41 && score <= 60) expected = "C";
        else if (score >= 61 && score <= 80) expected = "B";
        else if (score >= 81 && score <= 100) expected = "A";
        else expected = "Error";

        String actual = methods.HomeworkMethods1.getGrade(score);

        if (actual.equals(expected)) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }

        printTestEnd();
    }

    // --- ЗАДАЧА 2 (ВЕБИНАР): Дополнительные тесты для достижения 12 методов ---

    @Test
    void testIsPositive() {
        printTestStart("isPositive");
        int n = random.nextInt(200) - 100;
        boolean expected = n >= 0;
        boolean actual = methods.HomeworkMethods1.isPositive(n);

        if (actual == expected) System.out.println("TEST PASSED");
        else System.out.println("TEST FAILED");

        printTestEnd();
    }

    @Test
    void testBlastOff() {
        printTestStart("blastOff");
        int start = random.nextInt(10) + 1;
        String result = methods.HomeworkMethods1.blastOff(start);
        if (result.contains("Поехали!")) {
            System.out.println("TEST PASSED");
        } else {
            System.out.println("TEST FAILED");
        }

        printTestEnd();
    }

    @Test
    void testSumToN() {
        printTestStart("sumToN");
        int n = random.nextInt(50) + 1;
        int expected = n * (n + 1) / 2;
        int actual = methods.HomeworkMethods1.sumToN(n);

        if (actual == expected) System.out.println("TEST PASSED");
        else System.out.println("TEST FAILED");

        printTestEnd();
    }

    @Test
    void testHasBug() {
        printTestStart("hasBug");
        String[] arr = {"Hello", "World", "Bug"};
        boolean actual = methods.HomeworkMethods1.hasBug(arr);
        if (actual) System.out.println("TEST PASSED");
        else System.out.println("TEST FAILED");

        printTestEnd();
    }

    @Test
    void testGetEvenInRange() {
        printTestStart("getEvenInRange");
        int start = random.nextInt(10);
        int end = start + random.nextInt(10) + 1;
        String actual = methods.HomeworkMethods1.getEvenInRange(start, end);
        if (!actual.isEmpty()) System.out.println("TEST PASSED");
        else System.out.println("TEST FAILED");

        printTestEnd();
    }

    @Test
    void testFindMax() {
        printTestStart("findMax");
        int[] arr = {1, 5, 3, 9, 2};
        int actual = methods.HomeworkMethods1.findMax(arr);
        if (actual == 9) System.out.println("TEST PASSED");
        else System.out.println("TEST FAILED");

        printTestEnd();
    }

    @Test
    void testReverse() {
        printTestStart("reverse");
        String[] arr = {"One", "Two"};
        String[] actual = methods.HomeworkMethods1.reverse(arr);
        if (actual[0].equals("Two") && actual[1].equals("One")) System.out.println("TEST PASSED");
        else System.out.println("TEST FAILED");

        printTestEnd();
    }

    @Test
    void testCalcAverage() {
        printTestStart("calcAverage");
        var list = java.util.List.of(10, 20, 30);
        double actual = methods.HomeworkMethods1.calcAverage(list);
        if (Math.abs(actual - 20.0) < 0.001) System.out.println("TEST PASSED");
        else System.out.println("TEST FAILED");

        printTestEnd();
    }

    @Test
    void testRemoveSpecificName() {
        printTestStart("removeSpecificName");
        var list = java.util.List.of("Alice", "Bob", "Charlie");
        var actual = methods.HomeworkMethods1.removeSpecificName(list, "Bob");
        if (actual.size() == 2 && !actual.contains("Bob")) System.out.println("TEST PASSED");
        else System.out.println("TEST FAILED");

        printTestEnd();
    }
}