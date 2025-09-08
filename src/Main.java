import exceptions.BookNotAvailableException;
import exceptions.InvalidUserException;
import models.Book;
import models.Librarian;
import models.Member;
import storage.BooksManagement;
import storage.LibrariansManagement;
import storage.MembersManagement;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    static final Scanner keyboard = new Scanner(System.in);

    public static void main(String[] args) {

//        Member member1 = new Member(123,"mehdi");
//        Member member2 = new Member(456,"imad");
//        ArrayList<Member> arrayList = new ArrayList<>();
//        arrayList.add(member1);
//        arrayList.add(member2);
//        MembersManagement.saveMembers(arrayList);

//        ArrayList<Book> books = new ArrayList<>();

//        books.add(new Book("The Hobbit", "J.R.R. Tolkien", "978-0547928227", "Fantasy", 15.99, false));
//        books.add(new Book("1984", "George Orwell", "978-0451524935", "Dystopian", 12.50, false));
//        books.add(new Book("Clean Code", "Robert C. Martin", "978-0132350884", "Programming", 35.00, false));
//        books.add(new Book("Atomic Habits", "James Clear", "978-0735211292", "Self-help", 18.99, false));
//        books.add(new Book("The Alchemist", "Paulo Coelho", "978-0061122415", "Fiction", 10.99, false));
//
//        BooksManagement.saveBooks(books);

//        Librarian librarian = new Librarian(890,"amine");
//        Librarian librarian1 = new Librarian(839,"islam");
//        ArrayList<Librarian> arrayList = new ArrayList<>();
//        arrayList.add(librarian);
//        arrayList.add(librarian1);
//        LibrariansManagement.saveLibrarians(arrayList);

        mainMenu();
    }

    public static void mainMenu() {
        boolean condition = true;
        do {
            System.out.println("1. Create an account");
            System.out.println("2. Login as Member");
            System.out.println("3. Login as Librarian");
            System.out.println("4. Exit");

            int decision = keyboard.nextInt();
            keyboard.nextLine();

            switch (decision) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    try {
                        Member member = memberAuth();
                        memberMenu(member);
                    } catch (InvalidUserException e) {
                        System.out.println(e.getMessage());
                    } catch (InputMismatchException e) {
                        System.out.println("Please enter a valid number for ID!");
                        keyboard.nextLine();
                    }
                    break;
                case 3:
                    try {
                        Librarian librarian = librarianAuth();
                        librarianMenu(librarian);
                    } catch (InvalidUserException e) {
                        System.out.println(e.getMessage());
                    } catch (InputMismatchException e) {
                        System.out.println("Please enter a valid number for ID!");
                        keyboard.nextLine();
                    }
                    break;
                case 4:
                    condition = false;
                    break;
                default:
                    System.out.println("Invalid option, try again!");
            }

        } while (condition);
    }

    public static void createAccount() {
        boolean condition = true;
        while (condition){
            try {
                System.out.println("Enter the ID : ");
                int newId = keyboard.nextInt();
                keyboard.nextLine();
                System.out.println("Enter the name : ");
                String newName = keyboard.nextLine();

                Member newMember = new Member(newId, newName);
                MembersManagement.addMember(newMember);

                System.out.println("The account is created!");
                condition = false; // ✅ only exits if everything succeeds

            } catch (InputMismatchException e) {
                System.out.println("Please type a valid ID!");
                keyboard.nextLine(); // clear buffer
            } catch (InvalidUserException e) {
                System.out.println(e.getMessage());
                // loop continues, so user can try again
            }
        }
    }


    public static Member memberAuth() throws InvalidUserException {
        System.out.println("Enter the ID :");
        int id = keyboard.nextInt();
        keyboard.nextLine();

        ArrayList<Member> members = MembersManagement.loadMembers();

        for (Member m : members) {
            if (m.getId() == id) {
                System.out.println("Found: " + m.getName());
                return m;
            }
        }
        throw new InvalidUserException("This user doesn't exist");
    }

    public static Librarian librarianAuth() throws InvalidUserException {
        System.out.println("Enter the ID :");
        int id = keyboard.nextInt();
        keyboard.nextLine();

        ArrayList<Librarian> librarians = LibrariansManagement.loadLibrarians();

        for (Librarian l : librarians) {
            if (l.getId() == id) {
                System.out.println("Found: " + l.getName());
                return l;
            }
        }
        throw new InvalidUserException("This user doesn't exist");
    }

    public static void memberMenu(Member member) {
        boolean condition = true;

        while (condition) {
            System.out.println(member.getName() + " logged in :");
            System.out.println("1. Borrow a book");
            System.out.println("2. Return a book");
            System.out.println("3. Display your books");
            System.out.println("4. Exit");

            int choice = keyboard.nextInt();
            keyboard.nextLine();

            try {
                switch (choice) {
                    case 1:
                        ArrayList<Book> availableBooks = BooksManagement.loadBooks();
                        for (Book book : availableBooks) {
                            if(!book.isBorrowed()){
                                System.out.println(book.getTitle() + " - " + book.getCategory() + " - " + book.getPrice());

                            }
                        }
                        System.out.println("Type the book name :");
                        String name = keyboard.nextLine();
                        boolean found = false;
                        for (Book book : availableBooks) {
                            if (Objects.equals(book.getTitle(), name)) {
                                member.borrowBook(book);
                                System.out.println("The book has been borrowed");
                                MembersManagement.updateMember(member);
                                BooksManagement.updateBook(book);
                                found = true;
                                break;
                            }
                        }
                        if (!found) {
                            throw new BookNotAvailableException("This book is not available");
                        }
                        break;

                    case 2:
                        boolean returned = false;
                        ArrayList<Book> borrowedBooks = member.viewBorrowedBooks();
                        System.out.println("Type the book name :");
                        name = keyboard.nextLine();
                        for (Book book : borrowedBooks) {
                            if (Objects.equals(book.getTitle(), name)) {
                                member.returnBook(book);
                                System.out.println("The book has been returned");
                                MembersManagement.updateMember(member);
                                BooksManagement.saveBooks(borrowedBooks);
                                returned = true;
                                break;
                            }
                        }
                        if (!returned) {
                            throw new BookNotAvailableException("This book is not found in your borrowed list");
                        }
                        break;

                    case 3:
                        borrowedBooks = member.viewBorrowedBooks();
                        if (borrowedBooks.isEmpty()) {
                            System.out.println("You have no borrowed books.");
                        } else {
                            for (Book book : borrowedBooks) {
                                System.out.println(book.getTitle() + " " + book.getCategory());
                            }
                        }
                        break;

                    case 4:
                        condition = false;
                        break;

                    default:
                        System.out.println("Invalid option, try again!");
                }
            } catch (BookNotAvailableException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void librarianMenu(Librarian librarian) {
        boolean condition = true;
        while(condition){
            System.out.println(librarian.getName() + " logged in :");
            System.out.println("1. Add a new book");
            System.out.println("2. remove a book");
            System.out.println("3. View all members");
            System.out.println("4. Log out");

            int choice = keyboard.nextInt();
            keyboard.nextLine();

            switch (choice) {
                case 1:
                    Book newBook = null;
                    double price = 0;
                    boolean validPrice = false;

                    System.out.println("Book name :");
                    String title = keyboard.nextLine();
                    System.out.println("Author name :");
                    String author = keyboard.nextLine();
                    System.out.println("International Standard Book Number :");
                    String isbn = keyboard.nextLine();
                    System.out.println("Book category :");
                    String category = keyboard.nextLine();

                    while (!validPrice) {
                        try {
                            System.out.println("Book price :");
                            price = keyboard.nextDouble();
                            keyboard.nextLine(); // clear buffer
                            validPrice = true;
                            newBook = new Book(title,author,isbn,category,price);
                            BooksManagement.addBook(newBook);
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid price. Please enter a number:");
                            keyboard.nextLine(); // clear the invalid input
                        } catch (BookNotAvailableException e) {
                            System.out.println(e.getMessage());
                        }
                    }

                    newBook = new Book(title, author, isbn, category, price);
                    break;
                case 2:
                    System.out.println("Enter book isbn :");
                    isbn = keyboard.nextLine();
                    BooksManagement.removeBook(isbn);
                    break;
                case 3:
                    ArrayList<Member> members = MembersManagement.loadMembers();
                    for (Member member : members){
                        System.out.println(member.toString());
                    }
                    break;
                case 4:
                    condition = false;
                    break;
                default:
                    System.out.println("Invalid option, try again!");
            }
        }
    }


}
