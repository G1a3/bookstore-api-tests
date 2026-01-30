package bookstore.client;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class BookstoreApiClient extends ApiClient{
    private static final String BASE_URI = "https://fakerestapi.azurewebsites.net";
    protected static ThreadLocal<RequestSpecification> reqSpec = new ThreadLocal<>();

    public BookstoreApiClient() {
        if (reqSpec.get() == null) {
            configureApiClient();
        }
    }

    protected RequestSpecification requestSpec() {
        if (reqSpec.get() == null) {
            configureApiClient();
        }

        return reqSpec.get();
    }

    protected static void configureApiClient() {
        RequestSpecBuilder requestSpecBuilder = buildRequestSpec();
        reqSpec.set(requestSpecBuilder
                .setBaseUri(BASE_URI)
                .build()
        );
    }
}
