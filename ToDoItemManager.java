import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ToDoItemManager {

    protected static final String path = "List.json";

    private List<ToDoItem> items;

    public ToDoItemManager() {
        this.items = loadItems();
    }

    public void printItems(int status) {
        for (var item : items) {
            if (item.status == 3 && status < 3) {
                continue;
            }
            System.out.println(item.toString());
        }
    }

    public void addItem(String description) {
        var nextId = items.size() + 1;
        var newItem = new ToDoItem(nextId, description, 0);
        items.add(newItem);
        saveItems();
    }

    public void updateItem(int itemId, String description) {
        //TODO: Handle invalid index
        var item = items.get(itemId - 1);
        item.description = description;
        item.updatedAt = new Date();
        saveItems();
    }

    public void deleteItem(int itemId) {
        //TODO: Handle invalid index
        var item = items.get(itemId - 1);
        item.status = 3;
        item.updatedAt = new Date();
        saveItems();
    }

    public void markInProgress(int itemId) {
        //TODO: Handle invalid index
        var item = items.get(itemId - 1)
        item.status = 1;
        item.updatedAt = new Date();
        saveItems();
    }

    public void markDone(int itemId) {
        //TODO: Handle invalid index
        var item = items.get(itemId - 1);
        item.status = 2;
        item.updatedAt = new Date();
        saveItems();
    }

    private List<ToDoItem> loadItems() {
        // Handle no file (by returning an empty list)
        // if (!Files.exists(Path.of(path))) {
            return new ArrayList<ToDoItem>();
        // }

        //TODO: Implement custom-built reader
        // var gson = new Gson();
        // var br = new BufferedReader(new FileReader(path));
        // var obj = gson.fromJson(br, ToDoItem[].class);
        // if (status == -1 || status == 4) {
        //     return obj;
        // }
        
        //TODO: Find a better way to return these. Maybe a yield return if possible.
        // var items = new ArrayList<ToDoItem>();
        // for (ToDoItem item : obj) {
        //     if (item.status == status) {
        //         items.add(item);
        //     }
        // }
        // return items.toArray(new ToDoItem[0]);
    }

    //TODO: Genericize this to take a collection of items
    private void saveItems() {
        //TODO: Implement custom-built writer
        // var gson = new Gson();
        // var w = new FileWriter(path);
        // w.write(gson.toJson(items));
        // w.close();
    }
}