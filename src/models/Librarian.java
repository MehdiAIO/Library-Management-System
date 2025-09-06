package models;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

public class Librarian extends User implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public Librarian(int id,String name){
        super(id, name);
    }

    ArrayList<Book> availableBooks = new ArrayList<>();

    public void addBook(Book book){
        availableBooks.add(book);
    }

    public void removeBook(String isbn){
        Book target = availableBooks.get(Integer.parseInt(isbn));
        availableBooks.remove(target);
    }

    public void viewAllBooks(){
        for(Book book : availableBooks){
            System.out.println(book.toString());
        }
    }

}
