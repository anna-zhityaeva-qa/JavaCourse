package smoke;

import methods.HomeworkMethods1;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

@Tag("homework") // Тег для фильтрации
public class Test1 {

    @BeforeEach
    void setUp(TestInfo testInfo) {
        System.out.println("\n========================");
        System.out.println("START: " + testInfo.getDisplayName());
        System.out.println("========================");
    }

    @AfterEach
    void tearDown() {
        System.out.println("END: Test finished");
        System.out.println("========================\n");
    }

    // ==========================================
    // ЗАДАЧА 1 (4 сценария)
    // ==========================================

    @Test
    @DisplayName("ДЗ3.1.1: Boolean check")
    void testBoolean() {
        boolean result = HomeworkMethods1.isEven(4);
        assertTrue(result, "isEven(4) должно быть true");
    }

    @Test
    @DisplayName("ДЗ3.1.2: List check")
    void testList() {
        List<String> input = List.of("Anna", "Bob", "Alice");
        List<String> expected = List.of("Anna", "Alice");
        List<String> actual = HomeworkMethods1.getNamesStartingWithA(input);
        assertEquals(expected, actual, "getNamesStartingWithA вернул неверный список");
    }

    @Test
    @DisplayName("ДЗ3.1.3: String check")
    void testString() {
        String result = HomeworkMethods1.checkAccess(20);
        assertEquals("Allowed", result, "checkAccess(20) должно быть Allowed");
    }

    @Test
    @DisplayName("ДЗ3.1.4: Intentional Fail")
    void testFail() {
        // Намеренный сбой для демонстрации
        int expected = 10;
        int actual = 5;
        assertEquals(expected, actual, "ОШИБКА: Ожидалось " + expected + ", пришло " + actual);
    }

    // ==========================================
    // ЗАДАЧА 2 (12 методов, каждый запустить 10 раз)
    // ==========================================

    @RepeatedTest(10)
    @DisplayName("ДЗ3.2.1: isEven")
    void testIsEven() {
        int n = ThreadLocalRandom.current().nextInt(1, 101);
        boolean expected = n % 2 == 0;
        boolean actual = HomeworkMethods1.isEven(n);
        assertEquals(expected, actual, "isEven error for n=" + n);
    }

    @RepeatedTest(10)
    @DisplayName("ДЗ3.2.2: checkAccess")
    void testCheckAccess() {
        int age = ThreadLocalRandom.current().nextInt(0, 100);
        String expected = age > 18 ? "Allowed" : "Denied";
        String actual = HomeworkMethods1.checkAccess(age);
        assertEquals(expected, actual, "checkAccess error for age=" + age);
    }

    // Для getGrade используем ParameterizedTest с 10 значениями,
    // эквивалентно 10 запускам
    @ParameterizedTest
    @ValueSource(ints = {0, 10, 20, 21, 40, 50, 60, 61, 80, 100})
    @DisplayName("ДЗ3.2.3: getGrade")
    void testGetGrade(int score) {
        String actual = HomeworkMethods1.getGrade(score);
        String expected;
        if (score <= 20) expected = "E";
        else if (score <= 40) expected = "D";
        else if (score <= 60) expected = "C";
        else if (score <= 80) expected = "B";
        else if (score <= 100) expected = "A";
        else expected = "Error";

        assertEquals(expected, actual, "getGrade error for score=" + score);
    }

    @RepeatedTest(10)
    @DisplayName("ДЗ3.2.4: isPositive")
    void testIsPositive() {
        int n = ThreadLocalRandom.current().nextInt(-50, 51);
        boolean expected = n >= 0;
        boolean actual = HomeworkMethods1.isPositive(n);
        assertEquals(expected, actual, "isPositive error for n=" + n);
    }

    @RepeatedTest(10)
    @DisplayName("ДЗ3.2.5: blastOff")
    void testBlastOff() {
        int start = ThreadLocalRandom.current().nextInt(1, 11);
        StringBuilder sb = new StringBuilder();
        for (int i = start; i >= 1; i--) {
            if (i != start) sb.append(" ");
            sb.append(i);
        }
        sb.append(" Поехали!");
        String expected = sb.toString();

        String actual = HomeworkMethods1.blastOff(start);
        assertEquals(expected, actual, "blastOff error for start=" + start);
    }

    @RepeatedTest(10)
    @DisplayName("ДЗ3.2.6: sumToN")
    void testSumToN() {
        int n = ThreadLocalRandom.current().nextInt(1, 20);
        int expected = (n * (n + 1)) / 2;
        int actual = HomeworkMethods1.sumToN(n);
        assertEquals(expected, actual, "sumToN error for n=" + n);
    }

    @RepeatedTest(10)
    @DisplayName("ДЗ3.2.7: hasBug")
    void testHasBug() {
        // Случайно выбираем наличие "Bug"
        boolean shouldBeTrue = ThreadLocalRandom.current().nextBoolean();
        String[] arr = shouldBeTrue ? new String[]{"Test", "Bug"} : new String[]{"Test", "NoBug"};

        boolean actual = HomeworkMethods1.hasBug(arr);
        assertEquals(shouldBeTrue, actual, "hasBug error");
    }

    @RepeatedTest(10)
    @DisplayName("ДЗ3.2.8: getEvenInRange")
    void testGetEvenInRange() {
        int start = ThreadLocalRandom.current().nextInt(1, 10);
        int end = start + ThreadLocalRandom.current().nextInt(1, 10);

        // Строим ожидаемый результат
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (int i = start; i <= end; i++) {
            if (i % 2 == 0) {
                if (!first) sb.append(" ");
                sb.append(i);
                first = false;
            }
        }
        String expected = sb.toString();

        String actual = HomeworkMethods1.getEvenInRange(start, end);
        assertEquals(expected, actual, "getEvenInRange error for range [" + start + ", " + end + "]");
    }

    @RepeatedTest(10)
    @DisplayName("ДЗ3.2.9: findMax")
    void testFindMax() {
        int size = ThreadLocalRandom.current().nextInt(1, 6);
        int[] arr = new int[size];
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < size; i++) {
            arr[i] = ThreadLocalRandom.current().nextInt(1, 100);
            if (arr[i] > max) max = arr[i];
        }

        int actual = HomeworkMethods1.findMax(arr);
        assertEquals(max, actual, "findMax error");
    }

    @RepeatedTest(10)
    @DisplayName("ДЗ3.2.10: reverse")
    void testReverse() {
        int size = ThreadLocalRandom.current().nextInt(1, 6);
        String[] arr = new String[size];
        String[] expected = new String[size];
        for (int i = 0; i < size; i++) {
            arr[i] = "Item" + i;
            expected[i] = "Item" + (size - 1 - i);
        }

        String[] actual = HomeworkMethods1.reverse(arr);
        assertArrayEquals(expected, actual, "reverse error");
    }

    @RepeatedTest(10)
    @DisplayName("ДЗ3.2.11: calcAverage")
    void testCalcAverage() {
        int size = ThreadLocalRandom.current().nextInt(1, 6);
        List<Integer> list = List.of(generateNumbers(size));

        int sum = 0;
        for (int n : list) sum += n;
        double expected = (double) sum / size;

        double actual = HomeworkMethods1.calcAverage(list);
        assertEquals(expected, actual, 0.01, "calcAverage error");
    }

    @RepeatedTest(10)
    @DisplayName("ДЗ3.2.12: removeSpecificName")
    void testRemoveName() {
        List<String> list = List.of("A", "B", "C");
        // Случайно выбираем, кого удаляем
        String toRemove = List.of("A", "B", "C").get(ThreadLocalRandom.current().nextInt(3));

        List<String> expected = new java.util.ArrayList<>(list);
        expected.remove(toRemove);

        List<String> actual = HomeworkMethods1.removeSpecificName(list, toRemove);
        assertEquals(expected, actual, "removeSpecificName error");
    }

    // Вспомогательный метод для генерации случайных чисел
    private Integer[] generateNumbers(int size) {
        Integer[] nums = new Integer[size];
        for (int i = 0; i < size; i++) {
            nums[i] = ThreadLocalRandom.current().nextInt(1, 100);
        }
        return nums;
    }
}