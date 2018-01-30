package com.example.acdajsonclase.ui.contactosGSon;

/**
 * Created by usuario on 30/01/18.
 */

import java.util.List;

import com.example.acdajsonclase.ui.contactosGSon.Contact;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Person {

    @SerializedName("contacts")
    @Expose
    private List<Contact> contacts = null;

    public List<Contact> getContacts() {
        return contacts;
    }

    public void setContacts(List<Contact> contacts) {
        this.contacts = contacts;
    }

}
