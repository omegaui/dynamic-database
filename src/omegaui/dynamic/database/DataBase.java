/**
 * DataBase
 * Copyright (C) 2021 - 2022 Omega UI

 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package omegaui.dynamic.database;
import java.util.Scanner;
import java.util.LinkedList;

import java.io.File;
import java.io.PrintWriter;
public class DataBase {

    public static final char SET_PREFIX = '>'; // The prefix character to identify a data set
    public static final char VALUE_PREFIX = '-'; // The prefix character to identify a value in a set
    public static final char LINE_PREFIX = '|'; // The prefix character to identify multi-line value

    private final File file; // The file in which reading and writing data takes place

    private final LinkedList<DataEntry> entries = new LinkedList<>(); // List containing the values
    private final LinkedList<String> dataSetNames = new LinkedList<>(); // List containing the available data sets

    /*
        Constructor to create file object from its path and pass it to the main constructor
     */
    public DataBase(String filePath){
        this(new File(filePath));
    }

    /*
        Constructor to initialize the file object and start reading the database
     */
    public DataBase(File file){
        this.file = file;
        triggerLoad();
    }

    /*
        Loads the database if the file exists else gives exception
     */
    public void triggerLoad(){
        if(file != null && !file.exists()){
            System.err.println(file.getName() + " does n't exists.");
            System.out.println("Attempting to create a new file.");
            try {
                boolean result = file.createNewFile();
                if(result)
                    System.out.println("Successfully Created the new file");
                else
                    System.err.println("Unable to Create a new file");
            }
            catch (Exception e){
                System.err.println("An exception occurred when creating an non-existing data base file!");
                e.printStackTrace();
            }
        }
        load();
    }

    /*
        The Actual Loading of the DataBase takes place here
     */
    public void load(){
        try(Scanner reader = new Scanner(file)){
            String text;
            String dataSetName = "";
            String value = "";
            boolean canRecord = false;
            while(reader.hasNextLine()){
                text = reader.nextLine();
                if(text.trim().equals(""))
                    continue;

                if(canRecord){
                    if(text.charAt(0) == VALUE_PREFIX){
                        if(!value.equals(""))
                            addEntry(dataSetName, value);
                        value = text.length() == 1 ? "" : text.substring(1);

                        if(!reader.hasNextLine() && !value.equals("")){
                            addEntry(dataSetName, value);
                            canRecord = false;
                        }
                        else
                            continue;
                    }
                    else if(text.charAt(0) == LINE_PREFIX){
                        value += '\n' + (text.length() == 1 ? "" : text.substring(1));
                        if(!reader.hasNextLine()){
                            addEntry(dataSetName, value);
                            break;
                        }
                        continue;
                    }
                    else if(text.charAt(0) == SET_PREFIX){
                        canRecord = false;
                        addEntry(dataSetName, value);
                        dataSetName = "";
                        value = "";
                    }
                }

                if(!canRecord && text.charAt(0) == SET_PREFIX){
                    dataSetName = text.substring(1);
                    canRecord = true;
                }
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    /*
        Performs the write operation to the file
     */
    public void save(){
        try(PrintWriter writer = new PrintWriter(file)){
            getDataSetNames().forEach(dataSetName->{
                writer.println(DataBase.SET_PREFIX + dataSetName);
                getEntries(dataSetName).forEach((entry)-> writer.println(entry.toDataForm()));
            });
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    /*
        Getter of File
     */
    public File getFile() {
        return file;
    }

    /*
        Resets the database by losing all the data
     */
    public void clear(){
        entries.clear();
        dataSetNames.clear();
    }

    /*
        Updates the specified data set 's value at the specified index if found else creates a new entry
     */
    public void updateEntry(String dataSetName, String value, int index){
        DataEntry entry = getEntryAt(dataSetName, index);
        if(entry != null)
            entry.setValue(value);
        else{
            addEntry(dataSetName, value);
        }
    }

    /*
        Creates a new entry in a dataset
     */
    public void addEntry(String dataSetName, String value){
        if(!dataSetNames.contains(dataSetName))
            dataSetNames.add(dataSetName);
        entries.add(new DataEntry(dataSetName, value));
    }

    /*
        Checks for a value's existence in the database
     */
    public boolean hasEntry(String dataSetName, String value){
        for(DataEntry entryX : entries){
            if(entryX.equals(dataSetName, value))
                return true;
        }
        return false;
    }

    /*
        Checks for an entry's existence in the database
     */
    public boolean hasEntry(DataEntry entry){
        for(DataEntry entryX : entries){
            if(entryX.equals(entry))
                return true;
        }
        return false;
    }

    /*
        Getter of entries
     */
    public LinkedList<DataEntry> getEntries() {
        return entries;
    }

    /*
        Returns the entries of a dataset in form of a String list
     */
    public LinkedList<String> getEntriesAsString(String dataSetName) {
        LinkedList<String> results = new LinkedList<>();
        for(DataEntry entry : entries){
            if(entry.getDataSetName().equals(dataSetName))
                results.add(entry.getValue());
        }
        return results;
    }

    /*
        Returns the entries of a dataset
     */
    public LinkedList<DataEntry> getEntries(String dataSetName) {
        LinkedList<DataEntry> results = new LinkedList<>();
        for(DataEntry entry : entries){
            if(entry.getDataSetName().equals(dataSetName))
                results.add(entry);
        }
        return results;
    }

    /*
        Returns the entry of a dataset at specified index
     */
    public DataEntry getEntryAt(String dataSetName, int index){
        LinkedList<DataEntry> dataSetNames = getEntries(dataSetName);
        if(index == dataSetNames.size())
            return null;
        return dataSetNames.get(index);
    }

    /*
        Returns the first entry of a dataset
     */
    public DataEntry getEntryAt(String dataSetName){
        LinkedList<DataEntry> dataSetNames = getEntries(dataSetName);
        if(dataSetNames.size() == 0)
            return null;
        return dataSetNames.get(0);
    }

    /*
        Getter of datasets
     */
    public LinkedList<String> getDataSetNames(){
        return dataSetNames;
    }
}