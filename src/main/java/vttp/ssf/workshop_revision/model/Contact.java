package vttp.ssf.workshop_revision.model;

import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class Contact {

    @NotBlank(message = "Name must not be empty")
    @Size(min = 3, max = 64, message = "Name must be between 3 and 64 characters")
    @Pattern(regexp = "[a-zA-Z][a-zA-Z ]+", message = "Name can only consist of alphabets")
    private String name;

    @NotBlank(message = "Email must not be empty")
    @Email(message = "Must be a valid email")
    private String email;

    @NotBlank(message = "Phone number must not be empty")
    @Positive(message = "Must be a valid number")
    @Min(value = 7, message = "Phone number must be at least 7 digits")
    private String phone;

    @NotNull(message = "Date of birth must not be empty")
    @Past(message = "Date of birth must be valid")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate dob;

    private String id;

    public void setId(String id) {
        this.id = id;
    }

    // constructor
    public Contact() {
    }

    // getters and setters
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    @Override
    public String toString() {
        return "Contact [name=" + name + ", email=" + email + ", phone=" + phone + ", dob=" + dob + ", id=" + id + "]";
    }

}
