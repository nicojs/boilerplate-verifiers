package nicojs.boilerplateverifiers.internals;

import lombok.Getter;

/**
 * Represents a ValueFactory
 * Created by nicojs on 8/12/2015.
 */
public abstract class ValueFactory<T> {

    @Getter
    private Class<T> targetClass;


    public ValueFactory(Class<T> targetClass) {
        this.targetClass = targetClass;
    }

    public abstract T next();
}
