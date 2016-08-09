package com.augmentis.ayp.contact_project;

import java.util.UUID;

/**
 * Created by Theerawuth on 8/9/2016.
 */
public class Contact {
    private UUID id;
    private String name;
    private String phoneNumber;
    private String email;

    public Contact() {
        this(UUID.randomUUID());
    }

    public Contact(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
