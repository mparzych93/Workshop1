package pl.coderslab;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class TaskManager {
    public static void main(String[] args) {
        File file = new File("tasks.csv");
        String[][] arrOfTasks = new String[0][0];
        try (Scanner scanFile = new Scanner(file)) {
            while (scanFile.hasNextLine()) {
                String strLineOfTask = scanFile.nextLine();
                String[] lineOfTask = strLineOfTask.split(",");
                arrOfTasks = Arrays.copyOf(arrOfTasks, arrOfTasks.length + 1);
                arrOfTasks[arrOfTasks.length - 1] = lineOfTask;
            }
        } catch (FileNotFoundException e){
        e.printStackTrace();
        }
        System.out.println("Hello in TaskManager!");
        Scanner scanner = new Scanner(System.in);
        String input = "";
        while (!input.equals("exit")) {
            displayHeader();
            input = scanner.nextLine();
            if (input.equals("add")) {
                arrOfTasks = addTask(arrOfTasks);
            } else if (input.equals("remove")) {
                arrOfTasks = removeTask(arrOfTasks);
            } else if (input.equals("list")) {
                viewListOfTasks(arrOfTasks);
            } else  if (!input.equals("exit")) {
                System.out.println("There is no such option, please enter again:");
            }
        }
        System.out.println("Bye, bye! See you next time!");
        try (PrintWriter printWriter = new PrintWriter("tasks.csv")){
            for (String[] lineOfTaskAfterWorkDay : arrOfTasks){
                String strTaskAfterWorkDay = String.join(",", lineOfTaskAfterWorkDay);
                printWriter.println(strTaskAfterWorkDay);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File write error");
        }
    }
    public static void displayHeader(){
        System.out.println(ConsoleColors.BLUE + "Please select an option: ");
        System.out.println(ConsoleColors.RESET + "add" + "\n" + "remove" + "\n" + "list" + "\n" + "exit");
    }
    public static String[][] addTask(String[][] arrOfTasks){
        System.out.println("Please enter the name of task you want to add:");
        arrOfTasks = Arrays.copyOf(arrOfTasks, arrOfTasks.length + 1);
        Scanner scanNewTask = new Scanner(System.in);
        String nameTask = scanNewTask.nextLine();
        System.out.println("Please enter the deadline of task in following format: yyyy-mm-dd:");
        String deadlineOfTask = scanNewTask.nextLine();
        System.out.println("Please enter, if the task is valid (true or false):");
        String validOfTask = scanNewTask.nextLine();
        while (!(validOfTask.equalsIgnoreCase("true") || validOfTask.equalsIgnoreCase("false"))){
            System.out.println("The entered validity value does not exist. Enter again.");
            validOfTask = scanNewTask.nextLine();
        }
        String[] newLineOfTask = {nameTask, deadlineOfTask, validOfTask};
        arrOfTasks[arrOfTasks.length - 1] = newLineOfTask;
        return arrOfTasks;
    }
    public static String[][] removeTask(String[][] arrOfTasks){
        System.out.println("Please enter the index of the task you want to delete. Remember that the task numbering starts from 0!");
        Scanner scanTaskIndexToRemove = new Scanner(System.in);
        try {
            int taskIndexToRemove = scanTaskIndexToRemove.nextInt();
            while (taskIndexToRemove < 0 || taskIndexToRemove >= arrOfTasks.length) {
                System.out.println("The entered index does not represent any task. Enter again.");
                taskIndexToRemove = scanTaskIndexToRemove.nextInt();
            }
            for (int i = taskIndexToRemove; i < arrOfTasks.length - 1; i++) {
                arrOfTasks[i] = arrOfTasks[i + 1];
            }
            arrOfTasks = Arrays.copyOf(arrOfTasks, arrOfTasks.length - 1);
        } catch (InputMismatchException e){
            System.out.println("The text entered is not a number, you return to the menu.");
        }
        return arrOfTasks;
    }
    public static void viewListOfTasks(String[][] arrOfTasks){
        for (String[] lineOfTask : arrOfTasks) {
            String strLineOfTask = String.join(",", lineOfTask);
            System.out.println(strLineOfTask);
        }
    }
}