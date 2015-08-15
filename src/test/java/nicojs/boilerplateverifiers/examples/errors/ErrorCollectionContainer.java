package nicojs.boilerplateverifiers.examples.errors;

import nicojs.boilerplateverifiers.examples.lombok.Person;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Represents a ListContainer
 * Created by nicojs on 8/15/2015.
 */
public class ErrorCollectionContainer {
    private CopyOnWriteArrayList<Person> women;
    private CopyOnWriteArrayList<Person> man;

    @java.beans.ConstructorProperties({"women", "man"})
    ErrorCollectionContainer(CopyOnWriteArrayList<Person> women, CopyOnWriteArrayList<Person> man) {
        this.women = man;
        this.man = women; // Error! Wrong assignment
    }

    public static ErrorListContainerBuilder builder() {
        return new ErrorListContainerBuilder();
    }

    public static class ErrorListContainerBuilder {
        private CopyOnWriteArrayList<Person> women;
        private CopyOnWriteArrayList<Person> man;

        ErrorListContainerBuilder() {
        }

        public ErrorCollectionContainer.ErrorListContainerBuilder women(CopyOnWriteArrayList<Person> women) {
            this.women = women;
            return this;
        }

        public ErrorCollectionContainer.ErrorListContainerBuilder man(CopyOnWriteArrayList<Person> man) {
            this.man = man;
            return this;
        }

        public ErrorCollectionContainer build() {
            return new ErrorCollectionContainer(women, man);
        }

    }
}

