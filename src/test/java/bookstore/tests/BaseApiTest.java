package bookstore.tests;

import bookstore.modules.ApiModule;
import org.testng.annotations.Guice;

@Guice(modules = { ApiModule.class })
public class BaseApiTest {
}
