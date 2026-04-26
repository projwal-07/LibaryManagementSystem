package library;

public class Staff extends Person {
    private String position;

    public Staff(String id, String name, String email, String position) {
        super(id, name, email);
        this.position = position;
    }

    public String getPosition()          { return position; }
    public void setPosition(String pos)  { this.position = pos; }

    @Override
    public String getRole() { return "Staff - " + position; }

    public String toCSV() {
        return getId() + "," + getName() + "," + getEmail() + "," + position;
    }
}