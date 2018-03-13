# lambda-validator
validator with lambda, using java9

please confirm.
https://github.com/Kwanil/lambda-validator/blob/master/src/test/java/validate/ValidatorTest.java

###Sample Code
```
User user = new User("Rabin", null);

String result = valid(
                    when(() -> user.name.length() > 10)
                    .then("name is too long")
                )
                andValid(
                    when(user::isEmpty)
                    .then("user is empty")
                )
                .thenReturn();

System.out.println(result); //"user is empty"
```
