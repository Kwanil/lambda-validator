package validate;

import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static validate.Validator.valid;
import static validate.ValidatorEntry.both;
import static validate.ValidatorEntry.when;

public class ValidatorTest {

    public Validator validator() {
        User user = new User(0,"Rabin", 50);
        return valid(both(() -> user.height < 100, ()-> user.height > 0).then("height is too small"))
                .andValid(when(() -> user.name.length() > 10).then("name is too long"))
                .andValid(() -> user.id <= 0,"id is not valid");
    }

    @Test
    public void thenReturns() throws Exception {
        List<String> validationResult = validator().thenReturns();

        assertThat(validationResult, hasItems("height is too small", "id is not valid"));
    }


    @Test
    public void thenReturn() throws Exception {
        Optional<String> validationResult = validator().thenReturn();

        assertThat(validationResult.get(), is("height is too small"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void thenThrow() throws Exception {
        validator().thenThrow(IllegalArgumentException::new);
    }

    static class User {
        private int id;
        private String name;
        private int height;

        public User(int id, String name, int height) {
            this.id = id;
            this.name = name;
            this.height = height;
        }
    }
}
