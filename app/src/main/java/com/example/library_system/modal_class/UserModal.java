package com.example.library_system.modal_class;

public class UserModal {
    public String user_name,user_email,user_pass;

    public UserModal(String user_name, String user_email, String user_pass) {
        this.user_name = user_name;
        this.user_email = user_email;
        this.user_pass = user_pass;
    }

    public UserModal() {
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_pass() {
        return user_pass;
    }

    public void setUser_pass(String user_pass) {
        this.user_pass = user_pass;
    }
}
