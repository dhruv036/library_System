package com.example.library_system.modal_class;

public class Bookinfo {

    private String Bookid, Bookname ;
    private  boolean Bookstatus;

    public Bookinfo(String bookid, String bookname, boolean bookstatus) {
        Bookid = bookid;
        Bookname = bookname;
        Bookstatus = bookstatus;
    }

    public Bookinfo() {
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

    public boolean getBookstatus() {
        return Bookstatus;
    }

    public void setBookstatus(boolean bookstatus) {
        Bookstatus = bookstatus;
    }

    public void setBookname(String bookname) {
        Bookname = bookname;
    }
}
