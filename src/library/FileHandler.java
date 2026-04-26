package library;

import java.io.*;
import java.util.ArrayList;

public class FileHandler {

    // Load members from CSV file into the library
    public static void loadMembers(Library library, String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    Member m = new Member(parts[0].trim(), parts[1].trim(),
                                         parts[2].trim(), parts[3].trim());
                    library.addMember(m);
                }
            }
            System.out.println("✅ Members loaded from " + filename);
        } catch (FileNotFoundException e) {
            System.out.println("⚠️  File not found: " + filename + ". Starting fresh.");
        } catch (IOException e) {
            System.out.println("❌ Error reading file: " + e.getMessage());
        }
    }

    // Save all members to CSV file
    public static void saveMembers(Library library, String filename) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (Member m : library.getMembers()) {
                pw.println(m.toCSV());
            }
            System.out.println("✅ Members saved to " + filename);
        } catch (IOException e) {
            System.out.println("❌ Error saving file: " + e.getMessage());
        }
    }

    // Load books from CSV file
    public static void loadBooks(Library library, String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    Book b = new Book(parts[0].trim(), parts[1].trim(), parts[2].trim());
                    library.addBook(b);
                }
            }
            System.out.println("✅ Books loaded from " + filename);
        } catch (FileNotFoundException e) {
            System.out.println("⚠️  File not found: " + filename + ". Starting fresh.");
        } catch (IOException e) {
            System.out.println("❌ Error reading file: " + e.getMessage());
        }
    }

    // Save all books to CSV
    public static void saveBooks(Library library, String filename) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (Book b : library.getBooks()) {
                pw.println(b.toCSV());
            }
            System.out.println("✅ Books saved to " + filename);
        } catch (IOException e) {
            System.out.println("❌ Error saving file: " + e.getMessage());
        }
    }

    // Load staff from CSV file
    public static void loadStaff(Library library, String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    Staff s = new Staff(parts[0].trim(), parts[1].trim(),
                                       parts[2].trim(), parts[3].trim());
                    library.addStaff(s);
                }
            }
            System.out.println("✅ Staff loaded from " + filename);
        } catch (FileNotFoundException e) {
            System.out.println("⚠️  File not found: " + filename + ". Starting fresh.");
        } catch (IOException e) {
            System.out.println("❌ Error reading staff file: " + e.getMessage());
        }
    }

    // Save all staff to CSV
    public static void saveStaff(Library library, String filename) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
            for (Staff s : library.getStaffList()) {
                pw.println(s.toCSV());
            }
            System.out.println("✅ Staff saved to " + filename);
        } catch (IOException e) {
            System.out.println("❌ Error saving staff file: " + e.getMessage());
        }
    }
}