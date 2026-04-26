package library;

import java.util.ArrayList;

public class EvaluationSystem {

    private static final double FINE_PER_DAY = 0.50;
    private static final int FREE_BORROW_DAYS = 14;
    private static final int MAX_BORROW_STANDARD = 3;
    private static final int MAX_BORROW_PREMIUM = 6;

    // Check if member can borrow more books
    // Premium members get higher borrow limit (reward)
    public static boolean canBorrow(Member member) {
        int limit = member.getMembershipType().equalsIgnoreCase("Premium")
                    ? MAX_BORROW_PREMIUM : MAX_BORROW_STANDARD;
        if (member.getBorrowedBooks() >= limit) {
            System.out.println("❌ Borrow limit reached for " + member.getName()
                + " | Type: " + member.getMembershipType()
                + " | Limit: " + limit + " books");
            return false;
        }
        return true;
    }

    // Calculate overdue fine (penalty for Standard, waived for Premium)
    public static double calculateFine(Member member, int overdueDays) {
        if (overdueDays <= 0) {
            System.out.println("✅ No overdue days. No fine applied for " + member.getName());
            return 0.0;
        }

        // Premium members get fine waived as a reward benefit
        if (member.getMembershipType().equalsIgnoreCase("Premium")) {
            System.out.println("🎁 Premium benefit applied for " + member.getName()
                + " — overdue fine waived!");
            return 0.0;
        }

        // Standard members pay fine
        double fine = overdueDays * FINE_PER_DAY;
        System.out.printf("⚠️  Fine for %s: %d overdue day(s) x $%.2f/day = $%.2f%n",
            member.getName(), overdueDays, FINE_PER_DAY, fine);
        return fine;
    }

    // Run a full periodic evaluation on all members in the system
    public static void runPeriodicEvaluation(ArrayList<Member> members) {
        System.out.println("\n============================================");
        System.out.println("         PERIODIC MEMBER EVALUATION         ");
        System.out.println("============================================");

        if (members.isEmpty()) {
            System.out.println("No members found in the system.");
            return;
        }

        int goodStanding = 0;
        int flagged = 0;
        int noActivity = 0;

        for (Member m : members) {
            System.out.println("\nMember  : " + m.getName());
            System.out.println("ID      : " + m.getId());
            System.out.println("Type    : " + m.getMembershipType());
            System.out.println("Borrows : " + m.getBorrowedBooks());

            if (m.getBorrowedBooks() == 0) {
                System.out.println("Status  : No active borrows.");
                noActivity++;
            } else if (m.getBorrowedBooks() > 2) {
                System.out.println("Status  : ⚠️  FLAGGED — High borrow count. Reminder sent.");
                flagged++;
            } else {
                System.out.println("Status  : ✅ Good standing.");
                goodStanding++;
            }

            // Trigger reward event for Premium members
            if (m.getMembershipType().equalsIgnoreCase("Premium")) {
                System.out.println("Reward  : 🎁 Premium perks active — no fines, extended limit.");
            }

            // Trigger penalty event for Standard members with high borrows
            if (m.getMembershipType().equalsIgnoreCase("Standard")
                    && m.getBorrowedBooks() > 2) {
                System.out.println("Penalty : ❌ Penalty notice issued — return books to avoid fines.");
            }

            System.out.println("--------------------------------------------");
        }

        // Summary report
        System.out.println("\n========== EVALUATION SUMMARY ==========");
        System.out.println("Total Members   : " + members.size());
        System.out.println("Good Standing   : " + goodStanding);
        System.out.println("Flagged         : " + flagged);
        System.out.println("No Activity     : " + noActivity);
        System.out.println("=========================================\n");
    }
}