import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import com.google.gson.Gson;

public class ToDo {

    static String path = "List.json";
    public static void main(String[] args) throws IOException {
//        boolean exit = false;
//        var tokens = new Tokens(args);
//        var scanner = new Scanner(System.in);
//        while(!exit) {
//            if (tokens == null) {
//                args = scanner.nextLine().split("[\\s]");
//            }

            //TODO: Get 'command' token from tokens
            var command = args.length > 0 ? args[0] : null;
            if (command == null)
            {
                System.err.println("Enter a command (e.g. 'todo add \"Buy Groceries\"')");

            }

            //TODO: This is a silly way to parse the args. We should do better.
            var itemString = args.length > 1 ? args[1] : null;
            var contextString = args[args.length - 1];

            //HACK: Because we don't truly delete items (we just change their status to 'deleted'), we can rely on the index being equal to the id - 1;
            int index = -1;

            ToDoItem[] items = null;
            int status = -1;
            ArrayList<ToDoItem> newItems = null;
            switch (command) {
//                case "exit":
//                    exit = true;
//                    break;
                case "list":
                    switch (contextString) {
                        case "todo":
                            status = 0;
                            break;
                        case "in-progress":
                            status = 1;
                            break;
                        case "done":
                            status = 2;
                            break;
                        case "deleted":
                            status = 3;
                            break;
                        case "all":
                            //TODO: There's a better way to do this. Find it.
                            status = 4;
                            break;
                        default:
                            break;
                    }
                    for (var item : loadItems(status)) {
                        if (item.status == 3 && status < 3) {
                            continue;
                        }
                        System.out.println(item.toString());
                    }
                    break;
                case "add":
                    // Our implementation always keeps items in order, so we'll find the last id by looking at the last item.
                    items = loadItems(-1);
                    var nextId = items.length == 0 ? 1 : items[items.length - 1].id + 1;
                    var newItem = new ToDoItem(nextId, contextString, 0);
                    newItems = new ArrayList<ToDoItem>(Arrays.asList(items));
                    newItems.add(newItem);
                    saveItems(newItems.toArray(new ToDoItem[0]));
                    break;
                case "update":
                    // Our implementation always keeps items in order, so we'll find the id by its index number.
                    items = loadItems(-1);
                    //TODO: Handle NumberFormatException
                    index = Integer.valueOf(itemString) - 1;
                    //TODO: Handle invalid index
                    items[index].description = contextString;
                    items[index].updatedAt = new Date();
                    saveItems(items);
                    break;
                case "delete":
                    // Our implementation always keeps items in order, so we'll find the id by its index number.
                    items = loadItems(-1);
                    //TODO: Handle NumberFormatException
                    index = Integer.valueOf(itemString) - 1;
                    //TODO: Handle invalid index
                    items[index].status = 3;
                    items[index].updatedAt = new Date();
                    saveItems(items);
                    break;
                case "mark-in-progress":
                    // Our implementation always keeps items in order, so we'll find the id by its index number.
                    items = loadItems(-1);
                    //TODO: Handle NumberFormatException
                    index = Integer.valueOf(itemString) - 1;
                    //TODO: Handle invalid index
                    items[index].status = 1;
                    items[index].updatedAt = new Date();
                    saveItems(items);
                    break;
                case "mark-done":
                    // Our implementation always keeps items in order, so we'll find the id by its index number.
                    items = loadItems(-1);
                    //TODO: Handle NumberFormatException
                    index = Integer.valueOf(itemString) - 1;
                    //TODO: Handle invalid index
                    items[index].status = 2;
                    items[index].updatedAt = new Date();
                    saveItems(items);
                    break;
                default:
                    break;
            }
//        }
    }

    private static ToDoItem[] loadItems(int status) throws IOException {
        // Handle no file (either by creating it now, or simply return an empty array)
        if (!new File(path).exists()) {
            return new ToDoItem[0];
        }

        var gson = new Gson();
        var br = new BufferedReader(new FileReader(path));
        var obj = gson.fromJson(br, ToDoItem[].class);
        if (status == -1 || status == 4) {
            return obj;
        }
        
        //TODO: Find a better way to return these. Maybe a yield return if possible.
        var items = new ArrayList<ToDoItem>();
        for (ToDoItem item : obj) {
            if (item.status == status) {
                items.add(item);
            }
        }
        return items.toArray(new ToDoItem[0]);
    }

    //TODO: Genericize this to take a collection of items
    private static void saveItems(ToDoItem[] items) throws IOException {
        var gson = new Gson();
        var w = new FileWriter(path);
        w.write(gson.toJson(items));
        w.close();
    }
}