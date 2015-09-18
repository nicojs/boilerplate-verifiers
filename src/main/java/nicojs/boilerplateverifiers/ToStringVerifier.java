package nicojs.boilerplateverifiers;

import nicojs.boilerplateverifiers.internals.GraphStrategy;
import nicojs.boilerplateverifiers.internals.JavaValueFactoryArchitect;
import nicojs.boilerplateverifiers.internals.tostring.GraphAccessor;
import nicojs.boilerplateverifiers.internals.tostring.ToStringConfiguration;
import nicojs.boilerplateverifiers.internals.ValueFactories;
import nicojs.boilerplateverifiers.internals.tostring.VerificationContext;
import nicojs.boilerplateverifiers.internals.valuefactories.InvocationContext;

/**
 *
 * Created by nicoj on 8/12/2015.
 */
public class ToStringVerifier {

    private final ToStringConfiguration configuration;
    private final ValueFactories valueFactories;
    private Object instance;
    private String result;
    private GraphAccessor graphAccessor;

    private ToStringVerifier(Class<?> targetClass){
        configuration = ToStringConfiguration.of(targetClass);
        valueFactories = new ValueFactories(new InvocationContext(10));
    }

    public static ToStringVerifier forClass(Class<?> targetClass){
        return new ToStringVerifier(targetClass);
    }

    public ToStringVerifier withGraphStrategy(GraphStrategy graphStrategy){
        configuration.setGraphStrategy(graphStrategy);
        return this;
    }

    public void verify(){
        JavaValueFactoryArchitect.fill(valueFactories);
        inspectTargetClass();
        populateNewInstance();
        stringify();
        verifyAllAttributesStringified();
    }

    private void verifyAllAttributesStringified() {
        graphAccessor.verifyAttributes(instance, result, new VerificationContext());
    }

    private void inspectTargetClass() {
        graphAccessor = new GraphAccessor(configuration.getTargetClass());
    }

    private void stringify() {
        result = instance.toString();
    }

    private void populateNewInstance() {
        instance = valueFactories.provideNextValue(configuration.getTargetClass());
    }
}
