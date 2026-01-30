package bookstore.models.books;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookResponse {
    private Integer id;
    private String title;
    private String description;
    private Integer pageCount;
    private String excerpt;
    private OffsetDateTime publishDate;
}
