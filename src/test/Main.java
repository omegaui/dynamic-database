package test;

import omegaui.dynamic.database.DataBase;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        DataBase dataBase = new DataBase(new File("res/test.data")); // Auto-Reads the DataBase
        dataBase.getEntriesAsString("Location").forEach(System.out::println);
    }
}