package models;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Mode class for customer specific information */
public class Customer {
    private String firstName;
    private String lastName;
    private String email;

    public Customer(String firstName, String lastName, String email) throws IllegalArgumentException{
        if(isValidEmail(email)){
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
        }else{
            throw new IllegalArgumentException("Invalid email.");
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }
    @Override
    public String toString(){
        return "Name: "+ firstName + " " + lastName +
                " Email: " + email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return email.equals(customer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    private boolean isValidEmail(String email){
        Pattern pattern = Pattern.compile("^.+@.+\\.com$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
