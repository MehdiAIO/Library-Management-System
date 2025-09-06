package models;

import java.io.Serial;
import java.io.Serializable;

public class Book implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String title;
    private String author;
    private String isbn;
    private String category;
    private double price;
    private boolean isBorrowed;

    public Book(String title,String author,String isbn,String category,double price){
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.category = category;
        this.price = price;
        this.isBorrowed = false;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getIsbn() {
        return isbn;
    }

    public double getPrice() {
        return price;
    }

    public boolean isBorrowed() {
        return isBorrowed;
    }

    public void borrow(){
        this.isBorrowed = true;
    }

    public void returnBook(){
        this.isBorrowed = false;
    }

    public String toString(){
        return "\n"+this.title+' '+this.author+' '+this.isbn+' '+this.category+' '+this.price+' '+this.isBorrowed;
    }
}
