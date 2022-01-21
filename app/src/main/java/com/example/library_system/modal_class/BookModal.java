package com.example.library_system.modal_class;

public class BookModal {
    String stu_name, stu_email,book_name,book_id;
    long issue_date;

    public BookModal(String stu_name, String stu_email, String book_name, String book_id, long issue_date) {
        this.stu_name = stu_name;
        this.stu_email = stu_email;
        this.book_name = book_name;
        this.book_id = book_id;
        this.issue_date = issue_date;
    }

    public BookModal() {
    }

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public String getStu_name() {
        return stu_name;
    }

    public void setStu_name(String stu_name) {
        this.stu_name = stu_name;
    }

    public String getStu_email() {
        return stu_email;
    }

    public void setStu_email(String stu_email) {
        this.stu_email = stu_email;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public long getIssue_date() {
        return issue_date;
    }

    public void setIssue_date(long issue_date) {
        this.issue_date = issue_date;
    }
}
