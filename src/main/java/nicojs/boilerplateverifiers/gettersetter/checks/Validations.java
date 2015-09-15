package nicojs.boilerplateverifiers.gettersetter.checks;

import lombok.Getter;
import nicojs.boilerplateverifiers.gettersetter.GetterSetterCheck;

@Getter
public enum Validations {
    GETTER_SHOULD_REFERENCE_FIELD(new GetMethodShouldReferenceAField()),
    SETTER_SHOULD_REFERENCE_FIELD(new SetMethodShouldReferenceField()),
    GETTER_SHOULD_BE_PUBLIC(new GetterShouldBePublic()),
    SETTER_SHOULD_BE_PUBLIC(new SetterShouldBePublic()),
    GETTER_SHOULD_NOT_HAVE_A_PARAMETER(new GetterShouldNotHaveParameter()),
    SETTER_SHOULD_NOT_HAVE_EXACTLY_ONE_PARAMETER(new SetterShouldHaveExactlyOneParameter()),
    SETTER_PARAMETER_SHOULD_BE_OF_SAME_TYPE_AS_FIELD(new ParameterOfSetterTypeCheck()),
    SETTER_SHOULD_HAVE_VOID_RETURN_TYPE(new SetterShouldHaveVoidReturnType());

    private GetterSetterCheck getterSettercheck;

    Validations(GetterSetterCheck getterSettercheck) {
        this.getterSettercheck = getterSettercheck;
    }


}
