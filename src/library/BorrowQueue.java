package library;

import java.util.LinkedList;
import java.util.Queue;

/*
 * WHY Queue / LinkedList here instead of ArrayList?
 *
 * A Queue follows FIFO (First In, First Out) ordering — the first
 * member to join the waitlist gets the book first. This is fair
 * and logical for a library waitlist system.
 *
 * LinkedList implements Queue and offers O(1) insertion at the back
 * and O(1) removal from the front — much more efficient than ArrayList
 * which requires O(n) shifting when removing from the front.
 *
 * ArrayList would be better if we needed random access (get by index),
 * but for a waitlist we only ever care about the front and back,
 * making LinkedList/Queue the correct choice here.
 */

public class BorrowQueue {

    private Queue<String> waitlist;

    public BorrowQueue() {
        waitlist = new LinkedList<>();
    }

    // Add a member to the waitlist for a specific book
    public void addToWaitlist(String memberId, String bookId) {
        String entry = memberId + ":" + bookId;

        // Prevent duplicate entries
        if (waitlist.contains(entry)) {
            System.out.println("⚠️  " + memberId
                + " is already on the waitlist for book " + bookId);
            return;
        }

        waitlist.offer(entry);  // offer() adds to the back of the queue
        System.out.println("✅ " + memberId
            + " added to waitlist for book " + bookId);
        System.out.println("   Current waitlist size: " + waitlist.size());
    }

    // Process the next person at the front of the waitlist
    public void processNext(Library library) {
        if (waitlist.isEmpty()) {
            System.out.println("📭 Waitlist is empty. No entries to process.");
            return;
        }

        String entry = waitlist.poll();  // poll() removes from the front (FIFO)
        String[] parts = entry.split(":");
        String memberId = parts[0];
        String bookId   = parts[1];

        System.out.println("\nProcessing waitlist entry...");
        System.out.println("Member ID : " + memberId);
        System.out.println("Book ID   : " + bookId);

        Book book = library.findBookById(bookId);

        if (book != null && book.isAvailable()) {
            library.borrowBook(memberId, bookId);
            System.out.println("✅ Waitlist entry processed successfully.");
        } else {
            System.out.println("⚠️  Book " + bookId
                + " is still unavailable. Re-adding to back of queue.");
            waitlist.offer(entry);  // put back at end of queue
        }
    }

    // Display all current waitlist entries
    public void viewWaitlist() {
        if (waitlist.isEmpty()) {
            System.out.println("📭 Waitlist is currently empty.");
            return;
        }

        System.out.println("\n============ CURRENT WAITLIST ============");
        System.out.println("Position | Member ID | Book ID");
        System.out.println("------------------------------------------");

        int position = 1;
        for (String entry : waitlist) {
            String[] parts = entry.split(":");
            System.out.printf("   %-5d | %-10s | %s%n",
                position++, parts[0], parts[1]);
        }

        System.out.println("------------------------------------------");
        System.out.println("Total entries: " + waitlist.size());
        System.out.println("==========================================\n");
    }

    // Returns the number of entries currently in the waitlist
    public int getSize() {
        return waitlist.size();
    }

    // Check if a specific member+book combination is already in the waitlist
    public boolean isInWaitlist(String memberId, String bookId) {
        return waitlist.contains(memberId + ":" + bookId);
    }
}