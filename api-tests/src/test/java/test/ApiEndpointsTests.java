package test;

import api.RestApiBuilder;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue; // Добавлен импорт

@TestMethodOrder(MethodOrderer.MethodName.class)
class ApiEndpointsTests {

    private static final long FAKE_ID = 99999999L;
    private static final String TEST_PREFIX = "TestItem_";
    private static final List<Long> createdIds = new ArrayList<>();

    private String generateUniqueName() {
        return TEST_PREFIX + System.nanoTime() + "_" + Thread.currentThread().getId();
    }

    private Long createTestItem(String name, double price) {
        Map<String, Object> product = new HashMap<>();
        product.put("name", name);
        product.put("price", price);

        Response response = given()
                .spec(RestApiBuilder.getBuilder())
                .body(product)
                .when()
                .post("/goods/add")
                .then()
                .statusCode(200)
                .body("message", equalTo("success"))
                .extract().response();

        Number idNumber = (Number) response.path("data.id");
        long idLong = idNumber.longValue();
        createdIds.add(idLong);
        return idLong;
    }

    @AfterAll
    static void cleanupTestData() {
        System.out.println("=== CLEANUP ApiEndpoints: Removing test data ===");
        for (Long id : createdIds) {
            try {
                given()
                        .spec(RestApiBuilder.getBuilder())
                        .when()
                        .delete("/goods/" + id);
            } catch (Exception e) {
                System.err.println("Failed to cleanup ID: " + id + " - " + e.getMessage());
            }
        }
        createdIds.clear();
        System.out.println("=== CLEANUP ApiEndpoints: Finished ===");
    }

    @Test
    @DisplayName("1. POST /goods/add: Успешное добавление")
    void testPostAddSuccess() {
        String uniqueName = generateUniqueName();
        Map<String, Object> validProduct = new HashMap<>();
        validProduct.put("name", uniqueName);
        validProduct.put("price", 99.99);

        Response response = given()
                .spec(RestApiBuilder.getBuilder())
                .body(validProduct)
                .when()
                .post("/goods/add")
                .then()
                .statusCode(200)
                .body("message", equalTo("success"))
                .body("data.id", notNullValue())
                .extract().response();

        Number idNumber = (Number) response.path("data.id");
        createdIds.add(idNumber.longValue());
    }

    @Test
    @DisplayName("2. POST /goods/add: Валидация цены (отрицательная)")
    void testPostAddValidationPrice() {
        Map<String, Object> invalidProduct = new HashMap<>();
        invalidProduct.put("name", generateUniqueName());
        invalidProduct.put("price", -10.0);

        given()
                .spec(RestApiBuilder.getBuilder())
                .body(invalidProduct)
                .when()
                .post("/goods/add")
                .then()
                .statusCode(400);
    }

    @Test
    @DisplayName("3. POST /goods/add: Пустое имя (Валидация)")
    void testPostAddValidationName() {
        Map<String, Object> invalidProduct = new HashMap<>();
        invalidProduct.put("name", "");
        invalidProduct.put("price", 10.0);

        given()
                .spec(RestApiBuilder.getBuilder())
                .body(invalidProduct)
                .when()
                .post("/goods/add")
                .then()
                .statusCode(400);
    }

    @Test
    @DisplayName("4. POST /goods/add: Проверка уникальности имени")
    void testPostAddDuplicateName() {
        String uniqueName = generateUniqueName();
        Map<String, Object> product = new HashMap<>();
        product.put("name", uniqueName);
        product.put("price", 100.0);

        Response firstResponse = given()
                .spec(RestApiBuilder.getBuilder())
                .body(product)
                .when()
                .post("/goods/add")
                .then()
                .statusCode(200)
                .extract().response();

        Number idNumber = (Number) firstResponse.path("data.id");
        long id = idNumber.longValue();
        createdIds.add(id);

        Map<String, Object> duplicateProduct = new HashMap<>();
        duplicateProduct.put("name", uniqueName);
        duplicateProduct.put("price", 200.0);

        given()
                .spec(RestApiBuilder.getBuilder())
                .body(duplicateProduct)
                .when()
                .post("/goods/add")
                .then()
                .statusCode(400)
                .body("message", is("Good with name '" + uniqueName + "' already exists!"));
    }

    @Test
    @DisplayName("5. GET /goods/{id}: Успешное получение")
    void testGetByIdSuccess() {
        Long id = createTestItem(generateUniqueName(), 50.0);

        given()
                .spec(RestApiBuilder.getBuilder())
                .when()
                .get("/goods/" + id)
                .then()
                .statusCode(200)
                .body("id", equalTo(id.intValue()));
    }

    @Disabled
    @Test
    @DisplayName("6. GET /goods/{id}: Товар не найден (404)")
    void testGetByIdNotFound() {
        given()
                .spec(RestApiBuilder.getBuilder())
                .when()
                .get("/goods/" + FAKE_ID)
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("9. PATCH /goods/{id}: Успешное обновление")
    void testPatchUpdateSuccess() {
        Long id = createTestItem(generateUniqueName(), 10.0);

        Map<String, Object> updateBody = new HashMap<>();
        updateBody.put("name", "TestItem_updated");
        updateBody.put("price", 999.99);

        Response response = given()
                .spec(RestApiBuilder.getBuilder())
                .body(updateBody)
                .when()
                .patch("/goods/" + id)
                .then()
                .statusCode(200)
                .extract().response();

       Number actualPriceNumber = (Number) response.path("price");

        assertTrue(actualPriceNumber != null, "Цена не найдена в ответе");

        double expectedPrice = 999.99;
        double actualPrice = actualPriceNumber.doubleValue();

        assertTrue(Math.abs(expectedPrice - actualPrice) < 0.01,
                "Цена не совпала. Ожидается: " + expectedPrice + ", Получено: " + actualPrice);
    }

    @Test
    @DisplayName("10. PATCH /goods/{id}: Валидация при обновлении")
    void testPatchUpdateValidation() {
        Long id = createTestItem(generateUniqueName(), 30.0);

        Map<String, Object> updateBody = new HashMap<>();
        updateBody.put("price", -1.0);

        given()
                .spec(RestApiBuilder.getBuilder())
                .body(updateBody)
                .when()
                .patch("/goods/" + id)
                .then()
                .statusCode(400);
    }

    @Test
    @DisplayName("11. PATCH /goods/{id}: Товар не найден (404)")
    void testPatchUpdateNotFound() {
        Map<String, Object> updateBody = new HashMap<>();
        updateBody.put("name", "AnyName");
        updateBody.put("price", 10.0);

        given()
                .spec(RestApiBuilder.getBuilder())
                .body(updateBody)
                .when()
                .patch("/goods/" + FAKE_ID)
                .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("7. DELETE /goods/{id}: Успешное удаление")
    void testDeleteSuccess() {
        Long id = createTestItem(generateUniqueName(), 10.0);

        given()
                .spec(RestApiBuilder.getBuilder())
                .when()
                .delete("/goods/" + id)
                .then()
                .statusCode(200);

        createdIds.remove(id);
    }

    @Test
    @DisplayName("8. DELETE /goods/{id}: Товар не найден (404)")
    void testDeleteNotFound() {
        given()
                .spec(RestApiBuilder.getBuilder())
                .when()
                .delete("/goods/" + FAKE_ID)
                .then()
                .statusCode(404);
    }

    @Disabled
    @Test
    @DisplayName("12. GET /goods/list: Успешный запрос списка")
    void testGetListSuccess() {
        given()
                .spec(RestApiBuilder.getBuilder())
                .queryParam("page", 0)
                .queryParam("size", 10)
                .when()
                .get("/goods/list")
                .then()
                .statusCode(200);
    }

    @Disabled
    @Test
    @DisplayName("13. Интеграционный сценарий: Полный цикл CRUD")
    void testFullCrudCycle() {
        String itemName = "Cycle_" + System.nanoTime();
        Long id = createTestItem(itemName, 100.0);

        given()
                .spec(RestApiBuilder.getBuilder())
                .when()
                .get("/goods/" + id)
                .then()
                .statusCode(200)
                .body("name", equalTo(itemName));

        Map<String, Object> updateBody = new HashMap<>();
        updateBody.put("name", itemName + "_modified");
        updateBody.put("price", 150.0);

        Response patchResponse = given()
                .spec(RestApiBuilder.getBuilder())
                .body(updateBody)
                .when()
                .patch("/goods/" + id)
                .then()
                .statusCode(200)
                .extract().response();

        given()
                .spec(RestApiBuilder.getBuilder())
                .when()
                .get("/goods/" + id)
                .then()
                .statusCode(200)
                .body("name", equalTo(itemName + "_modified"));

        given()
                .spec(RestApiBuilder.getBuilder())
                .when()
                .delete("/goods/" + id)
                .then()
                .statusCode(200);

        createdIds.remove(id);
    }
}