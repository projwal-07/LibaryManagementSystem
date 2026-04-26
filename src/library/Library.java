package library;

import java.util.ArrayList;

public class Library {
    private ArrayList<Member> members;
    private ArrayList<Book> books;
    private ArrayList<Staff> staffList;
    private BorrowQueue borrowQueue;

    public Library() {
        members = new ArrayList<>();
        books = new ArrayList<>();
        staffList = new ArrayList<>();
        borrowQueue = new BorrowQueue();
    }

    // ── MEMBER OPERATIONS ──────────────────────────────

    public void addMember(Member m) {
        members.add(m);
        System.out.println("✅ Member added: " + m.getName());
    }

    public Member findMemberById(String id) {
        for (Member m : members) {
            if (m.getId().equalsIgnoreCase(id)) return m;
        }
        return null;
    }

    public Member findMemberByName(String name) {
        for (Member m : members) {
            if (m.getName().equalsIgnoreCase(name)) return m;
        }
        return null;
    }

    public boolean deleteMember(String id) {
        Member m = findMemberById(id);
        if (m != null) { members.remove(m); return true; }
        return false;
    }

    public void viewAllMembers() {
        if (members.isEmpty()) { System.out.println("No members found."); return; }
        System.out.println("\n========== ALL MEMBERS ==========");
        for (Member m : members) System.out.println(m);
        System.out.println("=================================");
    }

    public ArrayList<Member> getMembers() { return members; }

    // ── BOOK OPERATIONS ────────────────────────────────

    public void addBook(Book b) {
        books.add(b);
        System.out.println("✅ Book added: " + b.getTitle());
    }

    public Book findBookById(String id) {
        for (Book b : books) {
            if (b.getBookId().equalsIgnoreCase(id)) return b;
        }
        return null;
    }

    public boolean deleteBook(String id) {
        Book b = findBookById(id);
        if (b != null) { books.remove(b); return true; }
        return false;
    }

    public void viewAllBooks() {
        if (books.isEmpty()) { System.out.println("No books found."); return; }
        System.out.println("\n========== ALL BOOKS ==========");
        for (Book b : books) System.out.println(b);
        System.out.println("================================");
    }

    public ArrayList<Book> getBooks() { return books; }

    // ── STAFF OPERATIONS ───────────────────────────────

    public void addStaff(Staff s) {
        staffList.add(s);
        System.out.println("✅ Staff added: " + s.getName());
    }

    public Staff findStaffById(String id) {
        for (Staff s : staffList) {
            if (s.getId().equalsIgnoreCase(id)) return s;
        }
        return null;
    }

    public Staff findStaffByName(String name) {
        for (Staff s : staffList) {
            if (s.getName().equalsIgnoreCase(name)) return s;
        }
        return null;
    }

    public boolean deleteStaff(String id) {
        Staff s = findStaffById(id);
        if (s != null) { staffList.remove(s); return true; }
        return false;
    }

    public void viewAllStaff() {
        if (staffList.isEmpty()) { System.out.println("No staff found."); return; }
        System.out.println("\n========== ALL STAFF ==========");
        for (Staff s : staffList) System.out.println(s);
        System.out.println("================================");
    }

    public ArrayList<Staff> getStaffList() { return staffList; }

    // ── BORROW / RETURN ────────────────────────────────

    public void borrowBook(String memberId, String bookId) {
        Member m = findMemberById(memberId);
        Book b = findBookById(bookId);
        if (m == null) { System.out.println("❌ Member not found."); return; }
        if (b == null) { System.out.println("❌ Book not found."); return; }

        // Check borrow limit via EvaluationSystem
        if (!EvaluationSystem.canBorrow(m)) return;

        if (!b.isAvailable()) {
            System.out.println("📋 Book unavailable. Adding " + m.getName() + " to waitlist.");
            borrowQueue.addToWaitlist(memberId, bookId);
            return;
        }
        b.setAvailable(false);
        m.borrowBook();
        System.out.println("✅ " + m.getName() + " borrowed \"" + b.getTitle() + "\"");
    }

    public void returnBook(String memberId, String bookId) {
        Member m = findMemberById(memberId);
        Book b = findBookById(bookId);
        if (m == null || b == null) { System.out.println("❌ Invalid ID."); return; }
        b.setAvailable(true);
        m.returnBook();
        System.out.println("✅ Book returned successfully.");
    }

    // ── BORROW QUEUE & EVALUATION ──────────────────────

    public BorrowQueue getBorrowQueue() { return borrowQueue; }

    public void runEvaluation() {
        EvaluationSystem.runPeriodicEvaluation(members);
    }
}