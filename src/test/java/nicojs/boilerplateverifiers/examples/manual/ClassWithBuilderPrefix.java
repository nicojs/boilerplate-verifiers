package nicojs.boilerplateverifiers.examples.manual;

/**
 * Represents a ClassWithBuilderPrefix
 * Created by nicojs
 */
public class ClassWithBuilderPrefix {
    private final String string;

    public ClassWithBuilderPrefix(String string) {
        this.string = string;
    }

    public static ClassWithBuilderPrefixBuilder builder(){
        return new ClassWithBuilderPrefixBuilder();
    }

    public static class ClassWithBuilderPrefixBuilder{
        private String string;

        ClassWithBuilderPrefixBuilder(){}

        public ClassWithBuilderPrefixBuilder withString(String string){
            this.string = string;
            return this;
        }

        public ClassWithBuilderPrefix build(){
            return new ClassWithBuilderPrefix(string);
        }
    }
}
