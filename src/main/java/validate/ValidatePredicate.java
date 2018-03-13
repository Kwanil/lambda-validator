package validate;

/**
 * The type ValidatePredicate.
 *
 * @author Kwanil, Lee
 */
@FunctionalInterface
public interface ValidatePredicate {
    boolean test();

    default ValidatePredicate and(ValidatePredicate other) {
        return () -> this.test() && other.test();
    }

    default ValidatePredicate or(ValidatePredicate other) {
        return () -> this.test() || other.test();
    }

    default ValidatePredicate negate() {
        return () -> !this.test();
    }

}
