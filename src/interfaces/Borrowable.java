package interfaces;

import models.Book;

public interface Borrowable {
    void borrowBook(Book book);
    void returnBook(Book book);
}
