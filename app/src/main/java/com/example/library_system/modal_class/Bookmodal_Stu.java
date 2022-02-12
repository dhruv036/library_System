package com.example.library_system.modal_class;

public class Bookmodal_Stu {

    String empName, bookName, bookId;
    Long issueDate;

    public Bookmodal_Stu(String empName, String bookName, String bookId,   Long issueDate) {
        this.empName = empName;
        this.bookName = bookName;
        this.bookId = bookId;
        this.issueDate = issueDate;
    }

    public Bookmodal_Stu() {
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public Long getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Long issueDate) {
        this.issueDate = issueDate;
    }
}
