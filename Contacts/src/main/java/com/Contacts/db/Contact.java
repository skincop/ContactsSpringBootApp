package com.Contacts.db;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import com.Contacts.Validator.ContactNumberConstraint;
import com.Contacts.db.User;
import org.springframework.format.annotation.NumberFormat;

@Entity
@Table()
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "firstName cannot be empty")
    private String firstName;
    private String secondName;
    private String email;
    @ContactNumberConstraint
    private String phoneNumber;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User author;


    public Contact(String firstName, String secondName, String email, String phoneNumber,User author) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.author = author;
    }
    public Contact(){

    }

    public Contact ReplaceAll(String firstName, String secondName, String email, String phoneNumber) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        return this;
    }

    @Override
    public String toString() {
        return "Name: " + firstName + " Second name:" + secondName + " Email: " + email +" Phone number: " + phoneNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
