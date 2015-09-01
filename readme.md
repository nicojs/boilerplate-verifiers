[![Build Status](https://travis-ci.org/nicojs/boilerplate-verifiers.svg)](https://travis-ci.org/nicojs/boilerplate-verifiers)

Boilerplate verifiers for Java
==============================

*Unit test your boilerplate without more boilerplate*

**Boilerplate verifiers** can be used in Java unit tests to verify whether 
the **builder**, **toString**, **getters/setters** are correctly implemented.

Inspired by the awesome [**EqualsVerifier**](https://github.com/jqno/equalsverifier).
This is also the reason why equals and hashcode verifiers are notably absent.

Work in progress:
----------------
1. Implement BuilderVerifier
  1. ~~Support for POJOS~~
  2. ~~Support for inheritance in the classes to build~~
  3. ~~Verify that all attributes are being build.~~
  4. Support for inheritance in the builder classes themselves
  5. Verify that no attribute can be set after build.
  6. Support for more scenarios using settings
    1. Support for attribute blacklist
    2. Support for builder method blacklist
2. Implement ToStringVerifier
3. Implement GetterSetterVerifier

Builder
------
```java

    // Book.java
    public class Book {
    
        private Person author;
        private String title;
    
        Book(Person author, String title) {
            this.author = author;
            this.title = title;
        }
    
        public static BookBuilder builder() {
            return new BookBuilder();
        }
    
        public static class BookBuilder {
            private Person author;
            private String title;
    
            public Book.BookBuilder author(Person author) {
                this.author = author;
                return this;
            }
    
            public Book.BookBuilder title(String title) {
                this.title = title;
                return this;
            }
    
            public Book build() {
                return new Book(author, title);
            }
        }
    }
    
    // BookTest.java
     @Test
    public void verifyBuilder() {
        BuilderVerifier.of(Book.class).verify();
    }
```
