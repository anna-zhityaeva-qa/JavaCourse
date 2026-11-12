package assertion;

import io.restassured.response.Response;

public class GoodsApiAssert extends BasicApiAssert {

    public GoodsApiAssert(Response response) {
        super(response);
    }
}