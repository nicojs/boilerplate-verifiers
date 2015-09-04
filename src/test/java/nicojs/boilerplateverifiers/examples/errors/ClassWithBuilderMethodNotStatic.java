package nicojs.boilerplateverifiers.examples.errors;

/**
 * Represents a ClassWithBuilderMethodNotStatic
 * Created by nicojs
 */
public class ClassWithBuilderMethodNotStatic {

    public ClassWithBuilderMethodNotStaticBuilder builder(){
        return new ClassWithBuilderMethodNotStaticBuilder();
    }

    public class ClassWithBuilderMethodNotStaticBuilder{
        public ClassWithBuilderMethodNotStatic build(){
            return new ClassWithBuilderMethodNotStatic();
        }
    }
}
