package bookstore.controllers;

import bookstore.client.BookstoreApiClient;
import bookstore.models.books.BookRequest;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class BooksController extends BookstoreApiClient {

    protected static final String BOOK_PATH = "/api/v1/Books";

    public Response bookPOST(BookRequest bookRequest, int statusCode) {
        return given().spec(requestSpec())
                .basePath(BOOK_PATH)
                .body(bookRequest)
                .post()
                .then()
                .statusCode(statusCode)
                .extract()
                .response();
    }
}
