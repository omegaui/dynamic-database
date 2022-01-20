package test;

import omegaui.dynamic.database.DataBase;

import java.io.File;

public class Main {
    public static void testRead(){
        System.out.println("Attempting to Read from a file named 'test.data'");
        DataBase dataBase = new DataBase(new File("res/test.data")); // Auto-Reads the DataBase
        dataBase.getEntriesAsString("Location").forEach(System.out::println);
    }

    public static void testWrite(){
        System.out.println("Attempting to write data to a file named '.some-data'");
        DataBase dataBase = new DataBase(".some-data");
        dataBase.addEntry("Do you like this?","Yes!\nIts incredible.");
        dataBase.save();
    }

    public static void main(String[] args) {
        testRead();
        testWrite();
    }
}