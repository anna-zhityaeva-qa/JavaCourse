package smoke;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.assertj.core.api.Assertions;

import java.util.List;
import java.util.Random;

// Добавлен тег для фильтрации в Gradle задаче
@Tag("homework")
public class Test1 {

    private static final Random random = new Random();

    // Метод будет выполняться перед КАЖДЫМ тестом
    @BeforeEach
    void setUp(TestInfo testInfo) {
        System.out.println("Test method start");
        String methodName = testInfo.getTestMethod().map(m -> m.getName()).orElse("unknown");
        System.out.println("Running: " + methodName);
    }

    // Метод будет выполняться после КАЖДОГО теста
    @AfterEach
    void tearDown() {
        System.out.println("Test method end");
        System.out.println("===");
    }

    // --- ЗАДАЧА 1 (ЛЕКЦИЯ): Тесты с выводом сообщений и случайными данными ---

    // Тест проверки четности числа (возвращает boolean)
    // Запускается 10 раз
    @RepeatedTest(10)
    @DisplayName("Тест isEven со случайным числом")
    void testIsEvenRandom(RepetitionInfo repetitionInfo) {
        int n = random.nextInt(100) + 1;
        boolean expected = n % 2 == 0;
        boolean actual = methods.HomeworkMethods1.isEven(n);

        // AssertJ для информативного сообщения
        Assertions.assertThat(actual)
                .as("Проверка четности числа %d: ожидалось %b, получено %b", n, expected, actual)
                .isEqualTo(expected);
    }

    // Тест проверки доступа по возрасту
    // Запускается 10 раз
    @RepeatedTest(10)
    @DisplayName("Тест checkAccess со случайным возрастом")
    void testCheckAccess(RepetitionInfo repetitionInfo) {
        int age = random.nextInt(100);
        String expected = age > 18 ? "Allowed" : "Denied";
        String actual = methods.HomeworkMethods1.checkAccess(age);

        Assertions.assertThat(actual)
                .as("Проверка доступа для возраста %d: ожидалось '%s', получено '%s'", age, expected, actual)
                .isEqualTo(expected);
    }

    // Тест определения оценки по баллам
    // Используем ParameterizedTest с большим количеством значений, чтобы имитировать многократный запуск
    @ParameterizedTest
    @ValueSource(ints = {0, 10, 25, 35, 45, 55, 65, 75, 85, 95, 100})
    @DisplayName("Тест getGrade с параметризованными данными")
    void testGetGradeParameterized(int score) {
        String expected;
        if (score >= 0 && score <= 20) expected = "E";
        else if (score >= 21 && score <= 40) expected = "D";
        else if (score >= 41 && score <= 60) expected = "C";
        else if (score >= 61 && score <= 80) expected = "B";
        else if (score >= 81 && score <= 100) expected = "A";
        else expected = "Error";

        String actual = methods.HomeworkMethods1.getGrade(score);

        Assertions.assertThat(actual)
                .as("Оценка для балла %d: ожидалась '%s', получена '%s'", score, expected, actual)
                .isEqualTo(expected);
    }

    // --- ЗАДАЧА 2 (ВЕБИНАР): Дополнительные тесты ---
    // Все тесты помечены тегом "homework" для фильтрации.
    // Все тесты запущены 10 раз через @RepeatedTest.

    // Тест проверки положительности числа (boolean)
    // Диапазон - 1..100, чтобы исключить 0 и избежать неоднозначности.
    // Логика: положительное число > 0.
    @RepeatedTest(10)
    @DisplayName("Тест isPositive со случайным числом")
    void testIsPositive(RepetitionInfo repetitionInfo) {
        // Генерируем число от 1 до 100
        int n = random.nextInt(100) + 1;
        boolean expected = n > 0;
        boolean actual = methods.HomeworkMethods1.isPositive(n);

        Assertions.assertThat(actual)
                .as("Проверка положительности числа %d: ожидалось %b, получено %b", n, expected, actual)
                .isEqualTo(expected);
    }

    // Тест функции обратного отсчета
    @RepeatedTest(10)
    @DisplayName("Тест blastOff со случайным стартом")
    void testBlastOff(RepetitionInfo repetitionInfo) {
        int start = random.nextInt(10) + 1; // От 1 до 10
        String result = methods.HomeworkMethods1.blastOff(start);

        // Проверяем, что результат содержит ожидаемую фразу
        Assertions.assertThat(result)
                .as("Результат blastOff(%d) должен содержать 'Поехали!'. Получено: %s", start, result)
                .contains("Поехали!");
    }

    // Тест вычисления суммы чисел от 1 до N
    @RepeatedTest(10)
    @DisplayName("Тест sumToN со случайным N")
    void testSumToN(RepetitionInfo repetitionInfo) {
        int n = random.nextInt(50) + 1; // От 1 до 50
        int expected = n * (n + 1) / 2;
        int actual = methods.HomeworkMethods1.sumToN(n);

        Assertions.assertThat(actual)
                .as("Сумма от 1 до %d: ожидалось %d, получено %d", n, expected, actual)
                .isEqualTo(expected);
    }

    // Тест поиска наличия слова "Bug" в массиве
    @RepeatedTest(10)
    @DisplayName("Тест hasBug со случайным массивом")
    void testHasBug(RepetitionInfo repetitionInfo) {
        // Генерируем массив случайной длины от 1 до 5
        int len = random.nextInt(5) + 1;
        String[] arr = new String[len];
        // Гарантируем, что хотя бы один элемент будет "Bug"
        int bugIndex = random.nextInt(len);
        for (int i = 0; i < len; i++) {
            if (i == bugIndex) {
                arr[i] = "Bug";
            } else {
                arr[i] = "Word" + random.nextInt(100);
            }
        }

        boolean actual = methods.HomeworkMethods1.hasBug(arr);

        Assertions.assertThat(actual)
                .as("Наличие 'Bug' в массиве должно быть true")
                .isTrue();
    }

    // Тест получения списка четных чисел в диапазоне
    @RepeatedTest(10)
    @DisplayName("Тест getEvenInRange со случайным диапазоном")
    void testGetEvenInRange(RepetitionInfo repetitionInfo) {
        int start = random.nextInt(10);
        int end = start + random.nextInt(10) + 1; // end всегда > start
        String actual = methods.HomeworkMethods1.getEvenInRange(start, end);

        // Проверяем, что результат не пустой
        Assertions.assertThat(actual)
                .as("Результат getEvenInRange(%d, %d) не должен быть пустым", start, end)
                .isNotEmpty();
    }

    // Тест поиска максимального элемента в массиве
    @RepeatedTest(10)
    @DisplayName("Тест findMax со случайным массивом")
    void testFindMax(RepetitionInfo repetitionInfo) {
        int len = random.nextInt(5) + 1;
        int[] arr = new int[len];
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < len; i++) {
            arr[i] = random.nextInt(100);
            if (arr[i] > max) {
                max = arr[i];
            }
        }

        int actual = methods.HomeworkMethods1.findMax(arr);

        Assertions.assertThat(actual)
                .as("Максимальный элемент в массиве должен быть %d", max)
                .isEqualTo(max);
    }

    // Тест реверсирования массива строк
    @RepeatedTest(10)
    @DisplayName("Тест reverse со случайным массивом")
    void testReverse(RepetitionInfo repetitionInfo) {
        int len = random.nextInt(5) + 1;
        String[] original = new String[len];
        for (int i = 0; i < len; i++) {
            original[i] = "Str" + i;
        }

        // Создаем ожидаемый перевернутый массив
        String[] expected = new String[len];
        for (int i = 0; i < len; i++) {
            expected[i] = original[len - 1 - i];
        }

        String[] actual = methods.HomeworkMethods1.reverse(original);

        Assertions.assertThat(actual)
                .as("Перевернутый массив должен совпадать с ожидаемым")
                .isEqualTo(expected);
    }

    // Тест вычисления среднего арифметического списка (возвращает double)
    @RepeatedTest(10)
    @DisplayName("Тест calcAverage со случайным списком")
    void testCalcAverage(RepetitionInfo repetitionInfo) {
        int size = random.nextInt(5) + 1;
        List<Integer> list = new java.util.ArrayList<>();
        double sum = 0;
        for (int i = 0; i < size; i++) {
            int val = random.nextInt(100);
            list.add(val);
            sum += val;
        }
        double expected = sum / size;

        double actual = methods.HomeworkMethods1.calcAverage(list);

        Assertions.assertThat(actual)
                .as("Среднее арифметическое списка %s должно быть %f", list, expected)
                .isCloseTo(expected, Assertions.within(0.001));
    }

    // Тест удаления конкретного имени из списка (возвращает List)
    @RepeatedTest(10)
    @DisplayName("Тест removeSpecificName со случайным списком")
    void testRemoveSpecificName(RepetitionInfo repetitionInfo) {
        int size = random.nextInt(5) + 2; // Минимум 2 элемента
        List<String> list = new java.util.ArrayList<>();
        for (int i = 0; i < size; i++) {
            list.add("Name" + i);
        }

        // Удаляем первый элемент
        String nameToRemove = list.get(0);
        List<String> expectedList = new java.util.ArrayList<>(list);
        expectedList.remove(nameToRemove);

        List<String> actual = methods.HomeworkMethods1.removeSpecificName(list, nameToRemove);

        Assertions.assertThat(actual)
                .as("Список после удаления '%s' должен совпадать с ожидаемым", nameToRemove)
                .isEqualTo(expectedList);
    }
}