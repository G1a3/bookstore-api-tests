package bookstore.tests.books;

import bookstore.controllers.BooksController;
import bookstore.models.books.BookRequest;
import bookstore.models.books.BookResponse;
import bookstore.models.errors.ErrorResponse;
import bookstore.preconditions.book.BookDataFactory;
import bookstore.tests.BaseApiTest;
import bookstore.utils.DateUtils;
import com.google.inject.Inject;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;

import io.restassured.response.Response;
import net.datafaker.Faker;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;

@Feature("Create Books API Tests")
public class CreateBookTest extends BaseApiTest {

    @Inject
    private BooksController booksController;
    @Inject
    private BookDataFactory bookDataFactory;
    @Inject
    private Faker faker;

    @Test(groups = "smoke")
    @Description("POST /api/v1/Books: 200 when executing create books request with all request parameters")
    public void userIsAbleToCreateBookWithAllRequestParameters(){
        BookRequest bookRequest = bookDataFactory.generateBookRequestWithAllRequestParameters();
        Response response = booksController.bookPOST(bookRequest, HttpStatus.SC_OK);
        BookResponse bookResponse = response.as(BookResponse.class);

        assertThat(bookResponse.getId()).as("'id' value mismatch").isEqualTo(bookRequest.getId());
        assertThat(bookResponse.getTitle()).as("'title' value mismatch").isEqualTo(bookRequest.getTitle());
        assertThat(bookResponse.getDescription()).as("'description' value mismatch").isEqualTo(bookRequest.getDescription());
        assertThat(bookResponse.getPageCount()).as("'pageCount' value mismatch").isEqualTo(bookRequest.getPageCount());
        assertThat(bookResponse.getExcerpt()).as("'excerpt' value mismatch").isEqualTo(bookRequest.getExcerpt());
        assertThat(bookResponse.getPublishDate()).as("'publishDate' value mismatch").isEqualTo(bookRequest.getPublishDate());
    }

    @Test(groups = "smoke")
    @Description("POST /api/v1/Books: 200 when executing create books request with only mandatory request parameters")
    public void userIsAbleToCreateBookWithMandatoryRequestParameters(){
        BookRequest bookRequest = bookDataFactory.generateBookRequestWithMandatoryRequestParameters();
        Response response = booksController.bookPOST(bookRequest, HttpStatus.SC_OK);
        BookResponse bookResponse = response.as(BookResponse.class);

        assertThat(bookResponse.getId()).as("'id' value mismatch").isEqualTo(bookRequest.getId());
        assertThat(bookResponse.getTitle()).as("'title' value mismatch").isEqualTo(bookRequest.getTitle()).isNull();
        assertThat(bookResponse.getDescription()).as("'description' value mismatch").isEqualTo(bookRequest.getDescription()).isNull();
        assertThat(bookResponse.getPageCount()).as("'pageCount' value mismatch").isEqualTo(bookRequest.getPageCount());
        assertThat(bookResponse.getExcerpt()).as("'excerpt' value mismatch").isEqualTo(bookRequest.getExcerpt()).isNull();
        assertThat(bookResponse.getPublishDate()).as("'publishDate' value mismatch").isEqualTo(bookRequest.getPublishDate());
    }

    @Test
    @Description("POST /api/v1/Books: 400 when executing create books request without 'id' request parameter")
    public void userIsNotAbleToCreateBookWithoutIdRequestParameter(){
        var expectedErrorMessage = "The JSON value could not be converted to System.Int32.";

        BookRequest bookRequest = BookRequest.builder()
               .title(faker.book().title())
               .description(faker.lorem().paragraph(2))
               .pageCount(ThreadLocalRandom.current().nextInt(1, 1200))
               .excerpt(faker.lorem().sentence(12))
               .publishDate(DateUtils.nowUtcMillis())
               .build();

        Response response = booksController.bookPOST(bookRequest, HttpStatus.SC_BAD_REQUEST);
        ErrorResponse errorResponse = response.as(ErrorResponse.class);

        assertThat(errorResponse.getErrors().values().stream()
                .flatMap(Collection::stream)
                .anyMatch(msg -> msg.contains(expectedErrorMessage)))
                .as("Expected error message to contain '%s' but was: %s", expectedErrorMessage, errorResponse.getErrors())
                .isTrue();
    }

    @Test
    @Description("POST /api/v1/Books: 400 when executing create books request without 'pageCount' request parameter")
    public void userIsNotAbleToCreateBookWithoutPageCountRequestParameter(){
        var expectedErrorMessage = "The JSON value could not be converted to System.Int32.";

        BookRequest bookRequest = BookRequest.builder()
                .id(0)
                .title(faker.book().title())
                .description(faker.lorem().paragraph(2))
                .excerpt(faker.lorem().sentence(12))
                .publishDate(DateUtils.nowUtcMillis())
                .build();

        Response response = booksController.bookPOST(bookRequest, HttpStatus.SC_BAD_REQUEST);
        ErrorResponse errorResponse = response.as(ErrorResponse.class);

        assertThat(errorResponse.getErrors().values().stream()
                .flatMap(Collection::stream)
                .anyMatch(msg -> msg.contains(expectedErrorMessage)))
                .as("Expected error message to contain '%s' but was: %s", expectedErrorMessage, errorResponse.getErrors())
                .isTrue();
    }

    @Test
    @Description("POST /api/v1/Books: 400 when executing create books request without 'publishDate' request parameter")
    public void userIsNotAbleToCreateBookWithoutPublishDateRequestParameter(){
        var expectedErrorMessage = "The JSON value could not be converted to System.DateTime.";

        BookRequest bookRequest = BookRequest.builder()
                .id(0)
                .title(faker.book().title())
                .description(faker.lorem().paragraph(2))
                .pageCount(ThreadLocalRandom.current().nextInt(1, 1200))
                .excerpt(faker.lorem().sentence(12))
                .build();

        Response response = booksController.bookPOST(bookRequest, HttpStatus.SC_BAD_REQUEST);
        ErrorResponse errorResponse = response.as(ErrorResponse.class);

        assertThat(errorResponse.getErrors().values().stream()
                .flatMap(Collection::stream)
                .anyMatch(msg -> msg.contains(expectedErrorMessage)))
                .as("Expected error message to contain '%s' but was: %s", expectedErrorMessage, errorResponse.getErrors())
                .isTrue();
    }
}
