package account;

import java.util.regex.Pattern;

public class ForTest {
    public static void main(String[] args) {
        Pattern pattern = Pattern.compile(".+@@acme.com");
        System.out.println(("abc@acme.com").matches(".+@acme.com"));
        System.out.println(("abc@google.com").matches(".+@acme.com"));
        System.out.println(("abc@asd@acme.com").matches(".\\w@acme.com"));
        System.out.println(("abcv.asd@acme.com").matches(".\\w+@acme.com"));

    }
}
