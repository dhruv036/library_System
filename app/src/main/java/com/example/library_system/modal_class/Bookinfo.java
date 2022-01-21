package com.example.library_system.modal_class;

public class Bookinfo {

    private String Bookid, Bookname ;

    public Bookinfo(String bookid, String bookname) {
        Bookid = bookid;
        Bookname = bookname;
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

    public void setBookname(String bookname) {
        Bookname = bookname;
    }
}
