import java.util.*;

class Book {
    String title;
    String author;
    String genre;
    boolean isAvailable;

    Book(String title, String author, String genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.isAvailable = true;
    }
}

class Member {
    String username;
    List<Book> borrowedBooks;

    Member(String username) {
        this.username = username;
        this.borrowedBooks = new ArrayList<>();
    }

    boolean borrowSelectedBook(Book book) {
        if (borrowedBooks.size() < 5 && book.isAvailable) {
            borrowedBooks.add(book);
            book.isAvailable = false;
            return true;
        }
        return false;
    }

    void returnSelectedBook(Book book) {
        if (borrowedBooks.remove(book)) {
            book.isAvailable = true;
        }
    }
}

public class LibraryManagementSystem {

    static Scanner scanner = new Scanner(System.in);

    static List<Book> books = new ArrayList<>();
    static List<Member> members = new ArrayList<>();

    static Member loggedInMember = null;

    static final String adminUsername = "admin";
    static final String adminPassword = "password";

    public static void main(String[] args) {

        // Adding sample books
        books.add(new Book("Science", "John Doe", "Educational"));
        books.add(new Book("Social Studies", "Jane Doe", "Educational"));

        while (true) {

            System.out.print("\nEnter role (admin/user): ");
            String role = scanner.nextLine();

            if (role.equalsIgnoreCase("admin")) {

                checkAdminLogin();

            } else if (role.equalsIgnoreCase("user")) {

                checkUserLogin();

            } else {

                System.out.println("Sorry, that role is not available.");
            }
        }
    }

    private static void checkAdminLogin() {

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (username.equals(adminUsername)
                && password.equals(adminPassword)) {

            System.out.println("\nAdmin login completed.");
            openAdminMenu();

        } else {

            System.out.println("\nLogin details are incorrect.");
        }
    }

    private static void checkUserLogin() {

        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        loggedInMember = findOrAddMember(username);

        System.out.println("\nUser login completed.");
        openUserMenu();
    }

    private static Member findOrAddMember(String username) {

        for (Member member : members) {

            if (member.username.equalsIgnoreCase(username)) {
                return member;
            }
        }

        Member newMember = new Member(username);
        members.add(newMember);

        System.out.println("New member has been added.");

        return newMember;
    }

    private static void openAdminMenu() {

        while (true) {

            System.out.println("\n========== LIBRARY MENU ==========");
            System.out.println("1. Add Book");
            System.out.println("2. Update Book");
            System.out.println("3. Remove Book");
            System.out.println("4. Add Member");
            System.out.println("5. Display Books");
            System.out.println("6. Display Members");
            System.out.println("7. Exit Admin Menu");
            System.out.println("==================================");

            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    addBookRecord();
                    break;

                case 2:
                    updateBookRecord();
                    break;

                case 3:
                    removeBookRecord();
                    break;

                case 4:
                    addMemberRecord();
                    break;

                case 5:
                    displayBookList();
                    break;

                case 6:
                    displayMemberList();
                    break;

                case 7:
                    System.out.println("\nYou have left the admin menu.");
                    return;

                default:
                    System.out.println("\nPlease enter a valid option.");
            }
        }
    }

    private static void openUserMenu() {

        while (true) {

            System.out.println("\n========== USER MENU ==========");
            System.out.println("1. Borrow Book");
            System.out.println("2. Return Book");
            System.out.println("3. Display Books");
            System.out.println("4. Display Members");
            System.out.println("5. Exit User Menu");
            System.out.println("===============================");

            System.out.print("Choose an option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    borrowBookRecord();
                    break;

                case 2:
                    returnBookRecord();
                    break;

                case 3:
                    displayBookList();
                    break;

                case 4:
                    displayMemberList();
                    break;

                case 5:
                    System.out.println("\nYou have left the user menu.");
                    loggedInMember = null;
                    return;

                default:
                    System.out.println("\nPlease enter a valid option.");
            }
        }
    }

    private static void addBookRecord() {

        System.out.println("\n--- Add Book ---");

        System.out.print("Enter book title: ");
        String title = scanner.nextLine();

        System.out.print("Enter book author: ");
        String author = scanner.nextLine();

        System.out.print("Enter book genre: ");
        String genre = scanner.nextLine();

        books.add(new Book(title, author, genre));

        System.out.println("\nNew book has been added to the library.");
    }

    private static void updateBookRecord() {

        System.out.println("\n--- Update Book ---");

        System.out.print("Enter book title to update: ");
        String title = scanner.nextLine();

        Book book = findBookRecord(title);

        if (book != null) {

            System.out.print("Enter new author: ");
            book.author = scanner.nextLine();

            System.out.print("Enter new genre: ");
            book.genre = scanner.nextLine();

            System.out.println("\nBook information has been changed.");

        } else {

            System.out.println("\nNo book with that title was found.");
        }
    }

    private static void removeBookRecord() {

        System.out.println("\n--- Remove Book ---");

        System.out.print("Enter book title to remove: ");
        String title = scanner.nextLine();

        Book book = findBookRecord(title);

        if (book == null) {

            System.out.println("\nNo book with that title was found.");
            return;
        }

        if (!book.isAvailable) {

            System.out.println(
                    "\nThis book is currently borrowed, so it cannot be removed."
            );
            return;
        }

        books.remove(book);

        System.out.println("\nThe book has been removed from the library.");
    }

    private static Book findBookRecord(String title) {

        for (Book book : books) {

            if (book.title.equalsIgnoreCase(title)) {
                return book;
            }
        }

        return null;
    }

    private static void addMemberRecord() {

        System.out.println("\n--- Add Member ---");

        System.out.print("Enter member username: ");
        String username = scanner.nextLine();

        for (Member member : members) {

            if (member.username.equalsIgnoreCase(username)) {

                System.out.println(
                        "\nThis username is already in the member list."
                );
                return;
            }
        }

        members.add(new Member(username));

        System.out.println("\nNew member has been added to the library.");
    }

    private static void borrowBookRecord() {

        System.out.println("\n--- Borrow Book ---");

        System.out.print("Enter book title to borrow: ");
        String title = scanner.nextLine();

        Book book = findBookRecord(title);

        if (book == null) {

            System.out.println("\nThe book was not found.");
            return;
        }

        if (!book.isAvailable) {

            System.out.println("\nThis book is already borrowed.");
            return;
        }

        if (loggedInMember.borrowedBooks.size() >= 5) {

            System.out.println(
                    "\nYou already have 5 books. Please return a book first."
            );
            return;
        }

        loggedInMember.borrowSelectedBook(book);

        System.out.println(
                "\nYou have successfully borrowed: " + book.title
        );
    }

    private static void returnBookRecord() {

        System.out.println("\n--- Return Book ---");

        System.out.print("Enter book title to return: ");
        String title = scanner.nextLine();

        Book book = findBookRecord(title);

        if (book == null) {

            System.out.println("\nThe book was not found.");
            return;
        }

        if (!loggedInMember.borrowedBooks.contains(book)) {

            System.out.println(
                    "\nYou have not borrowed this book."
            );
            return;
        }

        loggedInMember.returnSelectedBook(book);

        System.out.println(
                "\nYou have successfully returned: " + book.title
        );
    }

    private static void displayBookList() {

        if (books.isEmpty()) {

            System.out.println("\nThere are no books in the library.");

        } else {

            System.out.println("\n========== BOOK LIST ==========");

            for (Book book : books) {

                System.out.println(
                        "Title: " + book.title
                                + " | Author: " + book.author
                                + " | Genre: " + book.genre
                                + " | Status: "
                                + (book.isAvailable ? "Available" : "Borrowed")
                );
            }

            System.out.println("===============================");
        }
    }

    private static void displayMemberList() {

        if (members.isEmpty()) {

            System.out.println("\nThere are no members yet.");

        } else {

            System.out.println("\n========= MEMBER LIST =========");

            for (Member member : members) {

                System.out.println(
                        "Username: " + member.username
                                + " | Books borrowed: "
                                + member.borrowedBooks.size()
                );
            }

            System.out.println("===============================");
        }
    }
}
