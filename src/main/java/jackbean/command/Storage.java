package jackbean.command;

import jackbean.task.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Storage class is responsible for reading and writing to the storage file.
 * This JavaDoc was written by GitHub Copilot.
 */
public class Storage {
    /**
     * Fetches the storage file and populates the given taskList.
     * This JavaDoc was written by GitHub Copilot.
     *
     * @param taskList The taskList to populate.
     */
    public static void fetchStorage(TaskList taskList) {
        // create the file if it doesn't exit
        Path storageFilePath = Paths.get("./data/JackBeanStorage.txt");
        if (!Files.exists(storageFilePath)) {
            try {
                Files.createFile(storageFilePath);
            } catch (IOException e) {
                System.out.println("An error occurred while reading your file homie.");
                e.printStackTrace();
            }
        }

        // read the file and populate the taskList
        try {
            File storage = new File("./data/JackBeanStorage.txt");
            Scanner storageReader = new Scanner(storage);
            while (storageReader.hasNextLine()) {
                String data = storageReader.nextLine();
                String[] splitData = data.split(" \\| ");
                if (splitData[0].equals("T")) {
                    taskList.addTask(new Todo(splitData[1]));
                } else if (splitData[0].equals("D")) {
                    taskList.addTask(new Deadline(splitData[1], splitData[2]));
                } else if (splitData[0].equals("E")) {
                    taskList.addTask(new Event(splitData[1], splitData[2], splitData[3]));
                }
                if (splitData[splitData.length - 1].equals("X")) {
                    taskList.markTaskAsDone(taskList.getSize());
                }
            }
            storageReader.close();
        } catch (
                FileNotFoundException e) {
            System.out.println("I could not find your file homie.");
            e.printStackTrace();
        }
    }

    /**
     * Updates the storage file with the given taskList.
     * This JavaDoc was written by GitHub Copilot.
     * This method was generated by GitHub Copilot, except for a few modifications.
     *
     * @param taskList The taskList to update the storage file with.
     */
    public static void updateStorage(TaskList taskList) {
        try {
            FileWriter storageWriter = new FileWriter("./data/JackBeanStorage.txt");
            for (int i = 0; i < taskList.getSize(); i++) {
                Task task = taskList.getTask(i + 1);
                if (task instanceof Todo) {
                    storageWriter.write("T | " + task.getDescription() + " | " + task.getStatusIcon() + "\n");
                } else if (task instanceof Deadline) {
                    storageWriter.write("D | " + task.getDescription() + " | " + ((Deadline) task).getBy() + " | " + task.getStatusIcon() + "\n");
                } else if (task instanceof Event) {
                    storageWriter.write("E | " + task.getDescription() + " | " + ((Event) task).getFrom() + " | " + ((Event) task).getTo() + " | " + task.getStatusIcon() + "\n");
                }
            }
            storageWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred while writing to your file homie.");
            e.printStackTrace();
        }
    }
}
