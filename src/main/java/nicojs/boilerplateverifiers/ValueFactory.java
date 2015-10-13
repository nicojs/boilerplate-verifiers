package nicojs.boilerplateverifiers;

import lombok.Getter;
import nicojs.boilerplateverifiers.internals.valuefactories.GraphCreationContext;

/**
 * Represents a factory which is responsible for creating new instances of a class.
 * The new instances generally are expected to be unique.
 * If there are only a fixed number of values (for example: an enum class), you should extend the {@link nicojs.boilerplateverifiers.internals.valuefactories.ChoiceValueFactory}
 * Created by nicojs on 8/12/2015.
 */
public abstract class ValueFactory<T> {

    @Getter
    private Class<T> targetClass;


    public ValueFactory(Class<T> targetClass) {
        this.targetClass = targetClass;
    }

    public abstract T next(GraphCreationContext graphCreationContext);
}
