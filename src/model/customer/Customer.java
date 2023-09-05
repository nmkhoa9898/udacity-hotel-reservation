package model.customer;

import java.util.regex.Pattern;

public class Customer {

    private static final String EMAIL_PATTERN = "^(.+)@(.+).(.+)$"; // Pattern: name@domain.com
    private final String firstName;
    private final String lastName;
    private final String email;

    public Customer(final String firstName, final String lastName, final String email) {
        this.validateCustomerEmail(email);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    private void validateCustomerEmail(final String email) {
        if(Pattern.compile(EMAIL_PATTERN).matcher(email).matches()) {
            System.out.println("Email is valid");
            return;
        } else {
            throw new IllegalArgumentException("Invalid email pattern!");
        }
    }

    public String getEmail() {
        return this.email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    @Override
    public String toString() {
        return "Customer Information: " +
                "\nFirst Name: " + this.firstName + " Last Name: " + this.lastName + " Email: " + this.email;
    }
}