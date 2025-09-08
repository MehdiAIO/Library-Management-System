package storage;

import exceptions.BookNotAvailableException;
import models.Book;
import models.Member;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

public class BooksManagement {
    public static String path = "data/books.ser";
    public static void saveBooks(ArrayList<Book> books){
        try(ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path))){
            out.writeObject(books);
            System.out.println("the books have been serialized");
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<Book> loadBooks(){
        ArrayList<Book> loadedBooks = new ArrayList<>();
        try(ObjectInputStream in = new ObjectInputStream(new FileInputStream(path))){
            loadedBooks = (ArrayList<Book>) in.readObject();
        }catch (IOException | ClassNotFoundException e){
            System.out.println(e.getMessage());
        }
        return loadedBooks;
    }

    public static void addBook(Book book) throws BookNotAvailableException {
        ArrayList<Book> books = loadBooks();

        for (Book b : books) {
            if (b.getIsbn().equals(book.getIsbn())) {
                throw new BookNotAvailableException(
                        "Book with ISBN " + book.getIsbn() + " already exists!"
                );
            }
        }

        books.add(book);
        saveBooks(books);
    }

    public static void removeBook(String isbn) {
        ArrayList<Book> books = loadBooks();
        Book target = null;

        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                target = book;
                break;
            }
        }

        if (target != null) {
            books.remove(target);
            saveBooks(books);
            System.out.println("Book removed successfully.");
        } else {
            System.out.println("No book found with ISBN: " + isbn);
        }
    }


    public static void updateBook(Book updatedBook) {
        ArrayList<Book> books = loadBooks();
        boolean found = false;

        for (int i = 0; i < books.size(); i++) {
            if (Objects.equals(books.get(i).getTitle(), updatedBook.getTitle())) {
                books.set(i, updatedBook);
                found = true;
                break;
            }
        }

        if (!found) {
            books.add(updatedBook);
        }

        saveBooks(books);
    }
}
