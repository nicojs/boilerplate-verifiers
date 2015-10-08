package nicojs.boilerplateverifiers;

import nicojs.boilerplateverifiers.internals.JavaValueFactoryArchitect;
import nicojs.boilerplateverifiers.internals.ValueProvider;
import nicojs.boilerplateverifiers.internals.tostring.GraphNodeAccessor;
import nicojs.boilerplateverifiers.internals.tostring.ToStringConfiguration;
import nicojs.boilerplateverifiers.internals.tostring.VerificationContext;
import nicojs.boilerplateverifiers.internals.valuefactories.GraphCreationContext;

/**
 * Verifier class for the toString boilerplate method.
 */
public class ToStringVerifier {

    private final ToStringConfiguration configuration;
    private ValueProvider valueProvider;
    private Object instance;
    private String result;
    private GraphNodeAccessor graphAccessor;

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

    public ToStringVerifier useAllAttributesExcept(String... paths){
        configuration.setAttributesBlacklist(paths);
        return this;
    }

    public void verify() {
        valueProvider = new ValueProvider();
        JavaValueFactoryArchitect.fill(valueProvider);
        populateNewInstance();
        inspectGraph();
        stringify();
        verifyAllAttributesStringified();
    }

    private void verifyAllAttributesStringified() {
        graphAccessor.verifyAttributes(result, new VerificationContext(configuration.getAttributesBlacklist()));
    }

    private void inspectGraph() {
        graphAccessor = new GraphNodeAccessor(configuration.getTargetClass(), instance);
    }

    private void stringify() {
        try {
            result = instance.toString();
        } catch (StackOverflowError stackOverflowError) {
            throw new AssertionError(String.format("The invocation of the toString resulted in a StackOverflow error. An object of type \"%s\" was created with a graph (structure of objects) which contain looping references back to objects higher in the graph." +
                            System.lineSeparator() +
                            "For example Parent <==> Child, where the toString of the parent calls the toString of the child, the toString of the child in turn calls the toString of the parent, etc." +
                            System.lineSeparator() +
                            "Consider making your toString implementation survive these loops in graphs, or use the withGraphStrategy method to change the graph strategy to a non-looping one.",
                    configuration.getTargetClass().getSimpleName(), configuration.getTargetClass().getSimpleName()), stackOverflowError);
        }
    }

    private void populateNewInstance() {
        instance = valueProvider.provideNextValue(configuration.getTargetClass(), new GraphCreationContext(configuration.getGraphStrategy()));
    }
}
