import java.util.Date;

public class ToDoItem {
    public int id;
    public String description;
    public int status;
    public Date createdAt;
    public Date updatedAt;

    public ToDoItem(int id, String description, int status) {
        this.id = id;
        this.description = description;
        this.status = status;
        this.createdAt = this.updatedAt = new Date();
    }

    @Override
    public String toString() {
        return String.format("Id: %d, Name: %s, %s (%s)", id, description, toStatusString(status), updatedAt);
    }

    private String toStatusString(int status) {
        switch (status) {
            case 0:
                return "to-do";
            case 1:
                return "in-progress";
            case 2:
                return "done";
            case 3:
                return "deleted";
            default:
                return null;
        }
    }
}
