package account.model.dto;

public enum HttpDescription {
    USER_EXIST("User exist!"),
    PASSWORD_SHORT("Password length must be 12 chars minimum!"),
    PASSWORD_BRANCHED("The password is in the hacker's database!"),
    PASSWORD_MATCH("The passwords must be different!"),
    PASSWORD_SUCCESSFULLY_CHANGE("The password has been updated successfully");


    private final String message;


    HttpDescription(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
