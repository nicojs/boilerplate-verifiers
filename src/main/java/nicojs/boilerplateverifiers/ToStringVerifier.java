package nicojs.boilerplateverifiers;

import nicojs.boilerplateverifiers.internals.JavaValueFactoryArchitect;
import nicojs.boilerplateverifiers.internals.tostring.ClassAccessor;
import nicojs.boilerplateverifiers.internals.tostring.ToStringConfiguration;
import nicojs.boilerplateverifiers.internals.ValueFactories;
import nicojs.boilerplateverifiers.internals.tostring.VerificationContext;

/**
 *
 * Created by nicoj on 8/12/2015.
 */
public class ToStringVerifier {

    private final ToStringConfiguration configuration;
    private final ValueFactories valueFactories;
    private Object instance;
    private String result;
    private ClassAccessor classAccessor;

    private ToStringVerifier(Class<?> targetClass){
        configuration = ToStringConfiguration.of(targetClass);
        valueFactories = new ValueFactories();
    }

    public static ToStringVerifier forClass(Class<?> targetClass){
        return new ToStringVerifier(targetClass);
    }

    public void verify(){
        JavaValueFactoryArchitect.fill(valueFactories);
        inspectTargetClass();
        populateNewInstance();
        stringify();
        verifyAllAttributesStringified();
    }

    private void verifyAllAttributesStringified() {
        classAccessor.verifyAttributes(instance, result, new VerificationContext());
    }

    private void inspectTargetClass() {
        classAccessor = new ClassAccessor(configuration.getTargetClass());
    }

    private void stringify() {
        result = instance.toString();
    }

    private void populateNewInstance() {
        instance = valueFactories.provideNextValue(configuration.getTargetClass());
    }
}
