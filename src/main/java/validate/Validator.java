package validate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.Map.Entry;
import static java.util.Map.entry;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toList;


/**
 * The type Validator.
 *
 * @author Kwanil, Lee
 * @see Validator
 * @see ValidatorEntry
 */
public class Validator {
    private final List<Entry<ValidatePredicate,String>> entries = new ArrayList<>();

    private Validator(Entry<ValidatePredicate,String> entry) {
        andValid(entry);
    }

    /**
     * Valid validator.
     *
     * @param when the when
     * @param then the then
     * @return the validator
     */
    public static Validator valid(ValidatePredicate when, String then) {
        return valid(entry(when, then));
    }

    /**
     * Valid validator.
     *
     * @param entry the entry, builder by {@link ValidatorEntry}
     * @return the validator
     */
    public static Validator valid(Entry<ValidatePredicate,String> entry) {
        return new Validator(entry);
    }

    /**
     * And valid validator.
     *
     * @param when the when
     * @param then the then
     * @return the validator
     */
    public Validator andValid(ValidatePredicate when, String then) {
        andValid(entry(when, then));
        return this;
    }

    /**
     * And valid validator.
     *
     * @param entry the entry, builder by {@link ValidatorEntry}
     * @return the validator
     */
    public Validator andValid(Entry<ValidatePredicate, String> entry) {
        entries.add(requireNonNull(entry));
        return this;
    }

    /**
     * Then return optional.
     *
     * @return the optional
     */
    public Optional<String> thenReturn() {
        return stream().findFirst();
    }

    /**
     * Then returns list.
     *
     * @return the list
     */
    public List<String> thenReturns() {
        return stream().distinct().collect(toList());
    }

    private Stream<String> stream() {
        return this.entries.stream().filter(e -> e.getKey().test()).map(Entry::getValue);
    }

    /**
     * Then throw.
     *
     * @param <E>               the type parameter
     * @param exceptionSupplier the exception supplier
     * @throws E the e
     */
    public <E extends Exception> void thenThrow(Function<String, E> exceptionSupplier) throws E {
        Optional<String> result = thenReturn();
        if(result.isPresent()) {
            throw exceptionSupplier.apply(result.get());
        }
    }
}
