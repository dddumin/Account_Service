package account;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class AccountServiceApplication {
    public static List<String> breachedPasswords = new ArrayList<>();

    public static void main(String[] args) {
        SpringApplication.run(AccountServiceApplication.class, args);
        try {
            breachedPasswords = List.of(Files.readString(Path.of("C:\\Users\\Даниил\\Desktop\\PROTEI\\Account Service\\Account Service\\task\\src\\resources\\BreachedPassword.txt")).split(","));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}