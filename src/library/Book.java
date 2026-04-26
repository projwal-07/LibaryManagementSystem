package library;

public class Book {
    private String bookId;
    private String title;
    private String author;
    private boolean isAvailable;

    public Book(String bookId, String title, String author) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.isAvailable = true;
    }

    public String getBookId()   { return bookId; }
    public String getTitle()    { return title; }
    public String getAuthor()   { return author; }
    public boolean isAvailable(){ return isAvailable; }

    public void setAvailable(boolean available) { this.isAvailable = available; }

    public String toCSV() {
        return bookId + "," + title + "," + author + "," + isAvailable;
    }

    @Override
    public String toString() {
        return "BookID: " + bookId + " | Title: " + title + " | Author: " + author +
               " | Available: " + (isAvailable ? "Yes" : "No");
    }
}