package library;

import java.util.Scanner;

public class Main {
    static Library library = new Library();
    static Scanner sc = new Scanner(System.in);
    static final String MEMBERS_FILE = "members.csv";
    static final String BOOKS_FILE   = "books.csv";
    static final String STAFF_FILE   = "staff.csv";

    public static void main(String[] args) {
        // Load data from files on startup
        FileHandler.loadMembers(library, MEMBERS_FILE);
        FileHandler.loadBooks(library, BOOKS_FILE);
        FileHandler.loadStaff(library, STAFF_FILE);

        int choice;
        do {
            printMainMenu();
            choice = getIntInput();
            switch (choice) {
                case 1 -> memberMenu();
                case 2 -> bookMenu();
                case 3 -> borrowReturnMenu();
                case 4 -> waitlistMenu();
                case 5 -> staffMenu();
                case 6 -> library.runEvaluation();
                case 7 -> calculateFineMenu();
                case 0 -> {
                    FileHandler.saveMembers(library, MEMBERS_FILE);
                    FileHandler.saveBooks(library, BOOKS_FILE);
                    FileHandler.saveStaff(library, STAFF_FILE);
                    System.out.println("👋 Goodbye! All data saved.");
                }
                default -> System.out.println("❌ Invalid option. Please try again.");
            }
        } while (choice != 0);
    }

    // ── MAIN MENU ─────────────────────────────────────

    static void printMainMenu() {
        System.out.println("\n=============================");
        System.out.println("   LIBRARY MANAGEMENT SYSTEM ");
        System.out.println("=============================");
        System.out.println("1. Member Management");
        System.out.println("2. Book Management");
        System.out.println("3. Borrow / Return Book");
        System.out.println("4. Waitlist Management");
        System.out.println("5. Staff Management");
        System.out.println("6. Run Periodic Evaluation");
        System.out.println("7. Calculate Member Fine");
        System.out.println("0. Save & Exit");
        System.out.print("Enter choice: ");
    }

    // ── MEMBER MENU ───────────────────────────────────

    static void memberMenu() {
        System.out.println("\n-- Member Management --");
        System.out.println("1. Add Member");
        System.out.println("2. View All Members");
        System.out.println("3. Search by ID");
        System.out.println("4. Search by Name");
        System.out.println("5. Update Member");
        System.out.println("6. Delete Member");
        System.out.print("Choice: ");
        int c = getIntInput();
        switch (c) {
            case 1 -> addMember();
            case 2 -> library.viewAllMembers();
            case 3 -> searchMemberById();
            case 4 -> searchMemberByName();
            case 5 -> updateMember();
            case 6 -> deleteMember();
            default -> System.out.println("❌ Invalid option.");
        }
    }

    static void addMember() {
        System.out.print("Enter Member ID: ");
        String id = sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Email: ");
        String email = sc.nextLine();
        System.out.print("Membership Type (Standard/Premium): ");
        String type = sc.nextLine();
        library.addMember(new Member(id, name, email, type));
    }

    static void searchMemberById() {
        System.out.print("Enter Member ID: ");
        String id = sc.nextLine();
        Member m = library.findMemberById(id);
        System.out.println(m != null ? m : "❌ Member not found.");
    }

    static void searchMemberByName() {
        System.out.print("Enter Member Name: ");
        String name = sc.nextLine();
        Member m = library.findMemberByName(name);
        System.out.println(m != null ? m : "❌ Member not found.");
    }

    static void updateMember() {
        System.out.print("Enter Member ID to update: ");
        String id = sc.nextLine();
        Member m = library.findMemberById(id);
        if (m == null) { System.out.println("❌ Member not found."); return; }
        System.out.print("New Name (press Enter to skip): ");
        String name = sc.nextLine();
        if (!name.isEmpty()) m.setName(name);
        System.out.print("New Email (press Enter to skip): ");
        String email = sc.nextLine();
        if (!email.isEmpty()) m.setEmail(email);
        System.out.print("New Membership Type (press Enter to skip): ");
        String type = sc.nextLine();
        if (!type.isEmpty()) m.setMembershipType(type);
        System.out.println("✅ Member updated successfully.");
    }

    static void deleteMember() {
        System.out.print("Enter Member ID to delete: ");
        String id = sc.nextLine();
        System.out.println(library.deleteMember(id)
            ? "✅ Member deleted." : "❌ Member not found.");
    }

    // ── BOOK MENU ─────────────────────────────────────

    static void bookMenu() {
        System.out.println("\n-- Book Management --");
        System.out.println("1. Add Book");
        System.out.println("2. View All Books");
        System.out.println("3. Search Book by ID");
        System.out.println("4. Delete Book");
        System.out.print("Choice: ");
        int c = getIntInput();
        switch (c) {
            case 1 -> addBook();
            case 2 -> library.viewAllBooks();
            case 3 -> searchBookById();
            case 4 -> deleteBook();
            default -> System.out.println("❌ Invalid option.");
        }
    }

    static void addBook() {
        System.out.print("Book ID: ");
        String id = sc.nextLine();
        System.out.print("Title: ");
        String title = sc.nextLine();
        System.out.print("Author: ");
        String author = sc.nextLine();
        library.addBook(new Book(id, title, author));
    }

    static void searchBookById() {
        System.out.print("Enter Book ID: ");
        String id = sc.nextLine();
        Book b = library.findBookById(id);
        System.out.println(b != null ? b : "❌ Book not found.");
    }

    static void deleteBook() {
        System.out.print("Enter Book ID to delete: ");
        String id = sc.nextLine();
        System.out.println(library.deleteBook(id)
            ? "✅ Book deleted." : "❌ Book not found.");
    }

    // ── BORROW / RETURN MENU ──────────────────────────

    static void borrowReturnMenu() {
        System.out.println("\n-- Borrow / Return --");
        System.out.println("1. Borrow Book");
        System.out.println("2. Return Book");
        System.out.print("Choice: ");
        int c = getIntInput();
        System.out.print("Enter Member ID: ");
        String mid = sc.nextLine();
        System.out.print("Enter Book ID: ");
        String bid = sc.nextLine();
        if (c == 1)      library.borrowBook(mid, bid);
        else if (c == 2) library.returnBook(mid, bid);
        else             System.out.println("❌ Invalid option.");
    }

    // ── WAITLIST MENU ─────────────────────────────────

    static void waitlistMenu() {
        System.out.println("\n-- Waitlist Management --");
        System.out.println("1. Add Member to Waitlist");
        System.out.println("2. Process Next in Waitlist");
        System.out.println("3. View Full Waitlist");
        System.out.print("Choice: ");
        int c = getIntInput();
        switch (c) {
            case 1 -> {
                System.out.print("Enter Member ID: ");
                String mid = sc.nextLine();
                System.out.print("Enter Book ID: ");
                String bid = sc.nextLine();
                library.getBorrowQueue().addToWaitlist(mid, bid);
            }
            case 2 -> library.getBorrowQueue().processNext(library);
            case 3 -> library.getBorrowQueue().viewWaitlist();
            default -> System.out.println("❌ Invalid option.");
        }
    }

    // ── STAFF MENU ────────────────────────────────────

    static void staffMenu() {
        System.out.println("\n-- Staff Management --");
        System.out.println("1. Add Staff");
        System.out.println("2. View All Staff");
        System.out.println("3. Search Staff by ID");
        System.out.println("4. Search Staff by Name");
        System.out.println("5. Update Staff");
        System.out.println("6. Delete Staff");
        System.out.print("Choice: ");
        int c = getIntInput();
        switch (c) {
            case 1 -> addStaff();
            case 2 -> library.viewAllStaff();
            case 3 -> searchStaffById();
            case 4 -> searchStaffByName();
            case 5 -> updateStaff();
            case 6 -> deleteStaff();
            default -> System.out.println("❌ Invalid option.");
        }
    }

    static void addStaff() {
        System.out.print("Enter Staff ID: ");
        String id = sc.nextLine();
        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Email: ");
        String email = sc.nextLine();
        System.out.print("Enter Position (e.g. Librarian/Manager/Assistant): ");
        String position = sc.nextLine();
        library.addStaff(new Staff(id, name, email, position));
    }

    static void searchStaffById() {
        System.out.print("Enter Staff ID: ");
        String id = sc.nextLine();
        Staff s = library.findStaffById(id);
        System.out.println(s != null ? s : "❌ Staff not found.");
    }

    static void searchStaffByName() {
        System.out.print("Enter Staff Name: ");
        String name = sc.nextLine();
        Staff s = library.findStaffByName(name);
        System.out.println(s != null ? s : "❌ Staff not found.");
    }

    static void updateStaff() {
        System.out.print("Enter Staff ID to update: ");
        String id = sc.nextLine();
        Staff s = library.findStaffById(id);
        if (s == null) { System.out.println("❌ Staff not found."); return; }
        System.out.print("New Name (press Enter to skip): ");
        String name = sc.nextLine();
        if (!name.isEmpty()) s.setName(name);
        System.out.print("New Email (press Enter to skip): ");
        String email = sc.nextLine();
        if (!email.isEmpty()) s.setEmail(email);
        System.out.print("New Position (press Enter to skip): ");
        String position = sc.nextLine();
        if (!position.isEmpty()) s.setPosition(position);
        System.out.println("✅ Staff updated successfully.");
    }

    static void deleteStaff() {
        System.out.print("Enter Staff ID to delete: ");
        String id = sc.nextLine();
        System.out.println(library.deleteStaff(id)
            ? "✅ Staff deleted." : "❌ Staff not found.");
    }

    // ── FINE CALCULATOR ───────────────────────────────

    static void calculateFineMenu() {
        System.out.println("\n-- Calculate Member Fine --");
        System.out.print("Enter Member ID: ");
        String id = sc.nextLine();
        Member m = library.findMemberById(id);
        if (m == null) { System.out.println("❌ Member not found."); return; }
        System.out.print("Enter number of overdue days: ");
        try {
            int days = Integer.parseInt(sc.nextLine().trim());
            double fine = EvaluationSystem.calculateFine(m, days);
            if (fine > 0) {
                System.out.printf("💰 Total fine due: $%.2f%n", fine);
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid number entered. Please enter digits only.");
        }
    }

    // ── HELPER ────────────────────────────────────────

    static int getIntInput() {
        try {
            return Integer.parseInt(sc.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("⚠️  Please enter a valid number.");
            return -1;
        }
    }
}