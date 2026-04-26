package library;

public class Member extends Person {
    private int borrowedBooks;
    private String membershipType; // e.g. "Standard" or "Premium"

    public Member(String id, String name, String email, String membershipType) {
        super(id, name, email); // calls Person constructor
        this.membershipType = membershipType;
        this.borrowedBooks = 0;
    }

    public int getBorrowedBooks()       { return borrowedBooks; }
    public String getMembershipType()   { return membershipType; }
    public void setMembershipType(String t) { this.membershipType = t; }

    public void borrowBook()  { borrowedBooks++; }
    public void returnBook()  { if (borrowedBooks > 0) borrowedBooks--; }

    @Override
    public String getRole() { return "Member (" + membershipType + ")"; }

    // Convert to CSV line for saving to file
    public String toCSV() {
        return getId() + "," + getName() + "," + getEmail() + "," + membershipType + "," + borrowedBooks;
    }
}