package com.example.library_system;

import com.example.library_system.modal_class.UserModal;

public class FeatureController {

   private int user_type;
   private String emp_name,emp_email;
    private UserModal user;
    // 0 student 1 Employee

    static FeatureController controller;

    public static FeatureController getController() {
        if(controller == null)
        {
           controller =  new FeatureController();
        }
        return controller;
    }

    public String getEmp_name() {
        return emp_name;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }

    public UserModal getUser() {
        return user;
    }

    public String getEmp_email() {
        return emp_email;
    }

    public void setEmp_email(String emp_email) {
        this.emp_email = emp_email;
    }

    public void setUser(UserModal user) {
        this.user = user;
    }

    public static void setController(FeatureController controller) {
        FeatureController.controller = controller;
    }

    public int getUser_type() {
        return user_type;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }
}
