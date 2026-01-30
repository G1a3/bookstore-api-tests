package bookstore.modules;

import bookstore.controllers.BooksController;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class ApiModule extends AbstractModule {
    protected void configure() {
        bind(BooksController.class).in(Singleton.class);
    }
}
