package bookstore.preconditions.book;

import bookstore.controllers.BooksController;
import bookstore.models.books.BookRequest;
import bookstore.utils.DateUtils;
import com.google.inject.Inject;
import net.datafaker.Faker;

import java.util.concurrent.ThreadLocalRandom;

public class BookDataFactory {

    @Inject
    private BooksController booksController;
    @Inject
    private Faker faker;

    public BookRequest generateBookRequestWithAllRequestParameters() {

        return BookRequest.builder()
                .id(ThreadLocalRandom.current().nextInt(1, 1_000_000))
                .title(faker.book().title())
                .description(faker.lorem().paragraph(2))
                .pageCount(ThreadLocalRandom.current().nextInt(1, 1200))
                .excerpt(faker.lorem().sentence(12))
                .publishDate(DateUtils.nowUtcMillis())
                .build();
    }

    public BookRequest generateBookRequestWithMandatoryRequestParameters() {

        return BookRequest.builder()
                .id(ThreadLocalRandom.current().nextInt(1, 1_000_000))
                .pageCount(ThreadLocalRandom.current().nextInt(1, 1200))
                .publishDate(DateUtils.nowUtcMillis())
                .build();
    }
}