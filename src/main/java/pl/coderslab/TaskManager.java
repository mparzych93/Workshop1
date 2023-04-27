package pl.coderslab;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class TaskManager {
    public static void main(String[] args) {

        File file = new File("tasks.csv");
        String[] listOfTasks = new String[0];
        String task = "";

        try (Scanner scanFile = new Scanner(file)){
            while (scanFile.hasNextLine()) {
                task = scanFile.nextLine();
                listOfTasks = Arrays.copyOf(listOfTasks, listOfTasks.length + 1);
                listOfTasks[listOfTasks.length - 1] = task;
            }
            System.out.println("Hello in TaskManager!");
            Scanner scanner = new Scanner(System.in);
            String input = "";

            while (!input.equals("exit")) {
                displayHeader();
                input = scanner.nextLine();
                if (input.equals("add")) {
                    listOfTasks = addTask(listOfTasks);
                } else if (input.equals("remove")) {
                    listOfTasks = removeTask(listOfTasks);
                } else if (input.equals("list")) {
                    viewListOfTasks(listOfTasks);
                } else {
                    if (!input.equals("exit")) {
                        System.out.println("There is no such option, please enter again:");
                    }
                }
            }
            System.out.println("Bye, bye! See you next time!");

            try (PrintWriter printWriter = new PrintWriter("tasks.csv")){
                for (String taskAfterWorkDay : listOfTasks){
                    printWriter.println(taskAfterWorkDay);
                }
            } catch (FileNotFoundException e) {
                System.out.println("File write error");
            }

        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public static void displayHeader(){
        System.out.println(ConsoleColors.BLUE + "Please select an option: ");
        System.out.println(ConsoleColors.RESET + "add" + "\n" + "remove" + "\n" + "list" + "\n" + "exit");
    }

    public static String[] addTask(String[] listOfTasks){
        System.out.println("Please enter the name of task you want to add:");
        listOfTasks = Arrays.copyOf(listOfTasks, listOfTasks.length + 1);
        Scanner scanNewTask = new Scanner(System.in);
        String nameTask = scanNewTask.nextLine();
        System.out.println("Please enter the deadline of task in following format: yyyy-mm-dd:");
        String deadlineOfTask = scanNewTask.nextLine();
        System.out.println("Please enter, if the task is valid (true or false):");
        String validOfTask = scanNewTask.nextLine();
        while (!(validOfTask.equals("true") || validOfTask.equals("false"))){
            System.out.println("The entered validity value does not exist. Enter again.");
            validOfTask = scanNewTask.nextLine();
        }
        String newTask = String.join(", ", nameTask, deadlineOfTask, validOfTask);
        listOfTasks[listOfTasks.length - 1] = newTask;
        return listOfTasks;
    }

    public static String[] removeTask(String[] listOfTasks){
        System.out.println("Please enter the index of the task you want to delete. Remember that the task numbering starts from 0!");
        Scanner scanTaskIndexToRemove = new Scanner(System.in);
        int taskIndexToRemove = scanTaskIndexToRemove.nextInt();
        while (taskIndexToRemove < 0 || taskIndexToRemove >= listOfTasks.length){
            System.out.println("The entered index does not represent any task. Enter again.");
            taskIndexToRemove = scanTaskIndexToRemove.nextInt();
        }
        listOfTasks[taskIndexToRemove] = null;
        for (int i = 0; i < listOfTasks.length - 1; i++) {
            if (listOfTasks[i] == null) {
                listOfTasks[i] = listOfTasks[i + 1];
                listOfTasks[i + 1] = null;
            }
        }
        listOfTasks = Arrays.copyOf(listOfTasks, listOfTasks.length - 1);
        return listOfTasks;
    }

    public static void viewListOfTasks(String[] listOfTasks){
        System.out.println(String.join("\n", listOfTasks));
    }
}