package bookstore.report;

import io.qameta.allure.attachment.DefaultAttachmentContent;
import io.qameta.allure.attachment.DefaultAttachmentProcessor;
import io.qameta.allure.attachment.FreemarkerAttachmentRenderer;
import io.qameta.allure.attachment.http.HttpRequestAttachment;
import io.qameta.allure.attachment.http.HttpResponseAttachment;
import io.restassured.filter.FilterContext;
import io.restassured.filter.OrderedFilter;
import io.restassured.internal.NameAndValue;
import io.restassured.internal.support.Prettifier;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AllureRestAssured implements OrderedFilter {
    private String requestTemplatePath = "http-request.ftl";
    private String responseTemplatePath = "http-response.ftl";

    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public Response filter(FilterableRequestSpecification requestSpec,
                           FilterableResponseSpecification responseSpec,
                           FilterContext filterContext) {
        Prettifier prettifier = new Prettifier();
        String basePath = requestSpec.getBasePath();

        if (basePath.isBlank()) {
            basePath = requestSpec.getUserDefinedPath();
        }

        HttpRequestAttachment.Builder requestAttachtmentBuilder = HttpRequestAttachment.Builder
                .create(String.format("Request: %s to %s", requestSpec.getMethod(), basePath), requestSpec.getURI())
                .setMethod(requestSpec.getMethod())
                .setHeaders(toMapConverter(requestSpec.getHeaders()))
                .setCookies(toMapConverter(requestSpec.getCookies()));

        if (Objects.nonNull(requestSpec.getBody())) {
            requestAttachtmentBuilder.setBody(prettifier.getPrettifiedBodyIfPossible(requestSpec));
        }

        HttpRequestAttachment requestAttachment = requestAttachtmentBuilder.build();

        new DefaultAttachmentProcessor().addAttachment(requestAttachment, new FreemarkerAttachmentRenderer(requestTemplatePath));

        Response response = filterContext.next(requestSpec, responseSpec);
        HttpResponseAttachment responseAttachment = HttpResponseAttachment.Builder.create(response.getStatusLine())
                .setResponseCode(response.getStatusCode())
                .setHeaders(toMapConverter(response.getHeaders()))
                .setBody(prettifier.getPrettifiedBodyIfPossible(response, response.getBody()))
                .build();

        new DefaultAttachmentProcessor().addAttachment(responseAttachment, new FreemarkerAttachmentRenderer(responseTemplatePath));

        return response;
    }

    private static Map<String, String> toMapConverter(Iterable<? extends NameAndValue> items) {
        Map<String, String> result = new HashMap<>();
        items.forEach(i -> result.put(i.getName(), i.getValue()));

        return result;
    }
}
