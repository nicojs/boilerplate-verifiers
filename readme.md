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
  4. ~~Support for inheritance in the builder classes themselves~~
  5. ~~Verify that no attribute can be set after build.~~
  6. ~~Verify the builder constructor is not accessible from the outside.~~
  7. Support for more scenarios using settings
    1. ~~Support for attribute blacklist~~
    2. ~~Support for builder class method blacklist~~
    3. ~~Force don't use getter's to verify attribute values~~
    4. ~~Support for custom value factories~~
    5. ~~Support for not buildable Super classes~~
    6. ~~Support for prefixed methods on builder class (i.e. "withAge()", "withName()", etc)~~
    7. Support for changing variables after build.
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
            // ...  boilerplate ...
        }
    }
    
    // BookTest.java
     @Test
    public void verifyBuilder() {
        BuilderVerifier.of(Book.class).verify();
    }
```
