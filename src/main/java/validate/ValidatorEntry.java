package validate;

import java.util.Objects;
import java.util.function.BinaryOperator;

import static java.util.AbstractMap.SimpleEntry;
import static java.util.Map.Entry;

/**
 * The type ValidatePredicate.
 *
 * @author Kwanil, Lee
 * @see Validator
 * @see ValidatePredicate
 */
public interface ValidatorEntry {

    /**
     * one predicate
     *
     * @param predicate
     * @return {@link Then}
     */
    static Then when(ValidatePredicate predicate) {
        return then -> createEntry(predicate, then);
    }

    /**
     * predicate 'and' operator
     * @param predicate1
     * @param predicate2
     * @return {@link Then}
     */
    static Then both(ValidatePredicate predicate1, ValidatePredicate predicate2) {
        return then -> createEntry(predicate1.and(predicate2), then);
    }

    /**
     * all 'and' predicate
     * @param predicates
     * @return {@link Then}
     */
    static Then allAnd(ValidatePredicate... predicates) {
        return then -> createEntry(allOf(predicates, ValidatePredicate::and), then);
    }

    /**
     * predicate 'or' operator
     * @param predicate1
     * @param predicate2
     * @return {@link Then}
     */
    static Then either(ValidatePredicate predicate1, ValidatePredicate predicate2) {
        return then -> createEntry(predicate1.or(predicate2), then);
    }

    /**
     * all 'or' predicate
     * @param predicates
     * @return {@link Then}
     */
    static Then allOr(ValidatePredicate... predicates) {
        return then -> createEntry(allOf(predicates, ValidatePredicate::or), then);
    }

    /**
     * EntryBuilder
     *
     * @author Kwanil, Lee
     * @see Validator
     */
    interface Then {
        Entry<ValidatePredicate, String> then(String then);
    }

    /**
     * private chaining operator
     * @param predicates
     * @param operator
     * @return {@link ValidatePredicate}
     */
    private static ValidatePredicate allOf(ValidatePredicate[] predicates, BinaryOperator<ValidatePredicate> operator) {
        ValidatePredicate predicate = null;
        for(ValidatePredicate p : Objects.requireNonNull(predicates)) {
            Objects.requireNonNull(p);
            predicate = Objects.isNull(predicate)? p : operator.apply(predicate, p);
        }
        return predicate;
    }

    /**
     * create {@link java.util.AbstractMap.SimpleEntry}
     * @param predicate
     * @param then
     * @return Entry<ValidatePredicate, String>
     */
    private static Entry<ValidatePredicate, String> createEntry(ValidatePredicate predicate, String then) {
        return new SimpleEntry<>(predicate, then);
    }
}
