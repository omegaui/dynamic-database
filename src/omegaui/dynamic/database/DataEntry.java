/*
 * DataEntry
 * Copyright (C) 2021 Omega UI

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

import java.util.LinkedList;
import java.util.Scanner;

public class DataEntry{

    private final String dataSetName; // The name of the dataset to which the entry belongs
    private String value; // The value held by the entry

    public LinkedList<String> lines = new LinkedList<>(); // Holds the value in split form if the value is multi lined

    /**
     * Constructor accepts the dataset's name to which it belongs and its value
     */
    public DataEntry(String dataSetName, String value){
        this.dataSetName = dataSetName;
        setValue(value);
    }

    /**
     * Parses value and stores it in lines list
     */
    public void evaluateLines(){
        if(value == null)
            return;
        lines.clear();
        if(value.contains("\n")){
            lines.add(value.substring(0, value.indexOf("\n")));
            try(Scanner reader = new Scanner(value.substring(value.indexOf("\n") + 1))){
                while(reader.hasNextLine()){
                    String text = reader.nextLine();
                    lines.add(text);
                }
            }
        }
        else
            lines.add(value);
    }

    /**
     * Setter of value
     */
    protected void setValue(String value){
        this.value = value;
        evaluateLines();
    }

    /**
     * Getter of dataSetName
     */
    public String getDataSetName(){
        return dataSetName;
    }

    /**
     * Getter of value
     */
    public String getValue(){
        return value;
    }

    /**
     * Getter of lines
     */
    public LinkedList<String> getLines(){
        return lines;
    }

    /**
     * Returns the value after parsing it to long
     */
    public long getValueAsLong(){
        return Long.parseLong(value);
    }

    /**
     * Returns the value after parsing it to int
     */
    public int getValueAsInt(){
        return Integer.parseInt(value);
    }

    /**
     * Returns the value after parsing it to double
     */
    public double getValueAsDouble(){
        return Double.parseDouble(value);
    }

    /**
     * Returns the value after parsing it to float
     */
    public double getValueAsFloat(){
        return Float.parseFloat(value);
    }

    /**
     * Returns the value after parsing it to char
     */
    public char getValueAsChar(){
        return value.charAt(0);
    }

    /**
     * Returns the value after parsing it to boolean
     */
    public boolean getValueAsBoolean(){
        return Boolean.parseBoolean(value);
    }

    /**
     * Checks for equality with an DataEntry object
     */
    @Override
    public boolean equals(Object obj){
        if(obj instanceof DataEntry)
            return obj.toString().equals(toString());
        return super.equals(obj);
    }

    /**
     * Checks for equality of dataSetName and value
     */
    public boolean equals(String dataSetName, String value){
        return this.dataSetName.equals(dataSetName) && this.value.equals(value);
    }

    /**
     * Returns String form of the DataEntry
     */
    @Override
    public String toString(){
        return dataSetName + " -> " + value;
    }

    /**
     * Returns the writable form of the value.
     * Used by the DataBase class to write data to file
     */
    public String toDataForm(){
        if(lines.isEmpty())
            return null;
        StringBuilder result = new StringBuilder(DataBase.VALUE_PREFIX + lines.get(0));
        for(int i = 1; i < lines.size(); i++){
            result.append("\n" + DataBase.LINE_PREFIX).append(lines.get(i));
        }
        return result.toString();
    }
}