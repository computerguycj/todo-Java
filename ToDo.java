import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import com.google.gson.Gson;

public class ToDo {
    public static void main(String[] args) {
        //TODO: Get 'command' token from tokens
        var command = args.length > 0 ? args[0] : null;
        if (command == null)
        {
            System.err.println("Enter a command (e.g. 'todo add \"Buy Groceries\"')");
            return;
        }

        var taskManager = new ToDoItemManager();

        //TODO: This is a silly way to parse the args. We should do better.
        var itemString = args.length > 1 ? args[1] : null;
        int itemId;
        try {
            itemId = Integer.parseInt(itemString);
        } catch (NumberFormatException e) {
            itemId = -1;
        }
        var contextString = args[args.length - 1];

        //HACK: Because we don't truly delete items (we just change their status to 'deleted'), we can rely on the index being equal to the id - 1;
        int index = -1;

        ToDoItem[] items = null;
        int status = -1;
        ArrayList<ToDoItem> newItems = null;
        switch (command) {
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
                taskManager.printItems(status);
                break;
            case "add":
                taskManager.addItem(contextString);
                break;
            case "update":
                taskManager.updateItem(itemId, contextString);
                break;
            case "delete":
                taskManager.deleteItem(itemId);
                break;
            case "mark-in-progress":
                taskManager.markInProgress(itemId);
                break;
            case "mark-done":
                taskManager.markDone(itemId);
                break;
            default:
                break;
        }
    }
}