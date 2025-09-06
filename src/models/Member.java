package models;

import interfaces.Borrowable;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class Member extends User implements Borrowable, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public Member(int id,String name){
        super(id, name);
    }

    ArrayList<Book> borrowedBooks = new ArrayList<>();

    public void borrowBook(Book book){
        borrowedBooks.add(book);
        book.borrow();
    }

    public void returnBook(Book book){
        borrowedBooks.remove(book);
        book.returnBook();
    }

    public ArrayList<Book> viewBorrowedBooks(){
        return borrowedBooks;
    }

}
