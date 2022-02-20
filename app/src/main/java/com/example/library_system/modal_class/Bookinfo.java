package com.example.library_system.modal_class;

public class Bookinfo {

    private String Bookid, Bookname ,BookAuthor,stuemail;
    Long issuedate;
    private  boolean Bookstatus;

    public Bookinfo() {
    }

    public Bookinfo(String bookid, String bookname, String bookAuthor, boolean bookstatus, String stuemail) {
        Bookid = bookid;
        Bookname = bookname;
        BookAuthor = bookAuthor;
        Bookstatus = bookstatus;
        this.stuemail=stuemail;
    }

    public String getStuemail() {
        return stuemail;
    }

    public void setStuemail(String stuemail) {
        this.stuemail = stuemail;
    }

    public Long getIssuedate() {
        return issuedate;
    }

    public void setIssuedate(Long issuedate) {
        this.issuedate = issuedate;
    }

    public String getBookid() {
        return Bookid;
    }

    public void setBookid(String bookid) {
        Bookid = bookid;
    }

    public String getBookname() {
        return Bookname;
    }

    public void setBookname(String bookname) {
        Bookname = bookname;
    }

    public String getBookAuthor() {
        return BookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        BookAuthor = bookAuthor;
    }

    public boolean isBookstatus() {
        return Bookstatus;
    }

    public void setBookstatus(boolean bookstatus) {
        Bookstatus = bookstatus;
    }
}
