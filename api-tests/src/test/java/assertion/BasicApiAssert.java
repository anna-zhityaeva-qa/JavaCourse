package assertion;

import io.restassured.response.Response;
import org.assertj.core.api.Assertions;

import java.util.List;
import java.util.Map;

public class BasicApiAssert {

    private final Response response;

    public BasicApiAssert(Response response) {
        this.response = response;
    }

    public static BasicApiAssert assertThat(Response response) {
        return new BasicApiAssert(response);
    }

    public BasicApiAssert statusCodeIsEqual(int expectedCode) {
        Assertions.assertThat(response.getStatusCode())
                .as("Код ответа должен быть %d", expectedCode)
                .isEqualTo(expectedCode);
        return this;
    }

    public BasicApiAssert bodyFieldIsExists(String fieldName) {
        Object value = response.path(fieldName);
        Assertions.assertThat(value)
                .as("Поле '%s' должно существовать в теле ответа", fieldName)
                .isNotNull();
        return this;
    }

    public BasicApiAssert listContainsValue(String listPath, String fieldToCheck, Object expectedValue) {
        Object rawList = response.path(listPath);

        Assertions.assertThat(rawList)
                .as("Путь '%s' должен существовать", listPath)
                .isNotNull();

        List<?> list;
        if (rawList instanceof List) {
            list = (List<?>) rawList;
        } else {
            throw new AssertionError("Путь '" + listPath + "' указывает не на массив. Получено: " + rawList.getClass());
        }

        Assertions.assertThat(list)
                .as("Список по пути '%s' не должен быть пустым для проверки вхождения", listPath)
                .isNotEmpty();

        boolean found = false;
        for (Object item : list) {
            if (item instanceof Map) {
                Map<?, ?> map = (Map<?, ?>) item;
                if (map.containsKey(fieldToCheck) && map.get(fieldToCheck).equals(expectedValue)) {
                    found = true;
                    break;
                }
            }
        }

        Assertions.assertThat(found)
                .as("В списке '%s' должен найтись элемент с полем '%s' равным '%s'",
                        listPath, fieldToCheck, expectedValue)
                .isTrue();

        return this;
    }
}