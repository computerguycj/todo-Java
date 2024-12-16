import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.Gson;

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
        var item = items.get(itemId - 1);
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
        var ret = new ArrayList<ToDoItem>();
        
        // Handle no file (by returning an empty list)
        if (!Files.exists(Path.of(path))) {
            return ret;
        }

        var gson = new Gson();
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(path));
            var obj = gson.fromJson(br, ToDoItem[].class);
            if (null != obj) {
                for (var item : obj) {
                    ret.add(item);
                }
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return ret;
    }

    private void saveItems() {
        var gson = new Gson();
        FileWriter w = null;
        try {
            w = new FileWriter(path);
            w.write(gson.toJson(items));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (null != w) {
                try {
                    w.close();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        }
    }
}