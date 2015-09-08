package nicojs.boilerplateverifiers.examples.lombok;

import lombok.Builder;
import lombok.Getter;

/**
 * Represents a Book
 * Created by nicojs on 8/13/2015.
 */
@Builder
@Getter
public class Book {

    private final Person author;
    private final String title;
}
