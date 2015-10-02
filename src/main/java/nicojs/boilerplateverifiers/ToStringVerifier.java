package nicojs.boilerplateverifiers;

import nicojs.boilerplateverifiers.internals.JavaValueFactoryArchitect;
import nicojs.boilerplateverifiers.internals.ValueFactories;
import nicojs.boilerplateverifiers.internals.tostring.GraphAccessor;
import nicojs.boilerplateverifiers.internals.tostring.ToStringConfiguration;
import nicojs.boilerplateverifiers.internals.tostring.VerificationContext;
import nicojs.boilerplateverifiers.internals.valuefactories.GraphCreationContext;

/**
 * Verifier class for the toString builderplate method.
 * Created by nicoj on 8/12/2015.
 */
public class ToStringVerifier {

    private final ToStringConfiguration configuration;
    private ValueFactories valueFactories;
    private Object instance;
    private String result;
    private GraphAccessor graphAccessor;

    private ToStringVerifier(Class<?> targetClass) {
        configuration = ToStringConfiguration.of(targetClass);
    }

    public static ToStringVerifier forClass(Class<?> targetClass) {
        return new ToStringVerifier(targetClass);
    }

    public ToStringVerifier withGraphStrategy(GraphStrategy graphStrategy) {
        configuration.setGraphStrategy(graphStrategy);
        return this;
    }

    public void verify() {
        valueFactories = new ValueFactories();
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
        try {
            result = instance.toString();
        } catch (StackOverflowError stackOverflowError) {
            throw new AssertionError(String.format("The invocation of the toString resulted in a StackOverflow error. An object of type \"%s\" was created with a graph (structure of objects) which contain looping references back to objects earlier specified." +
                            System.lineSeparator() +
                            "For example Parent <==> Child, where the ToString of the parent calls the ToString of the child, the ToString of the child in turn calls the ToString of the parent, etc." +
                            System.lineSeparator() +
                            "Consider making your ToString implementation survive these loops in graphs, or use the withGraphStrategy method to change the graph strategy to a non-looping one.",
                    configuration.getTargetClass().getSimpleName(), configuration.getTargetClass().getSimpleName()), stackOverflowError);
        }
    }

    private void populateNewInstance() {
        instance = valueFactories.provideNextValue(configuration.getTargetClass(), new GraphCreationContext(configuration.getGraphStrategy()));
    }
}
