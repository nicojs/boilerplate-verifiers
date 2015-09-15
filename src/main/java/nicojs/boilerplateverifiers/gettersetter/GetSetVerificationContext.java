package nicojs.boilerplateverifiers.gettersetter;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetSetVerificationContext<T> {

    private Class<T> classToTest;
    private Fields fields;
    private Methods methods;

}
