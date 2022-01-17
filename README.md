# dynamic-database

A fully code integrated minimal database management system for Java, Scala, Kotlin or Groovy projects.

It is written in Java and can be used in any other programming language that can run on the JVM.

Since, the database is **fully code integrated**,
this means **NO** other program or piece of software is needed to use it.
There is no need for any installation procedure 😉.
Its just some java class reading and writing data to a file in a formatted manner.

So, All you need is either the source code or the tiny [precompiled-jar](https://raw.githubusercontent.com/omegaui/dynamic-database/master/bin/dynamic-database.jar)

## How it works

Let's first understand **how it works**,

- DataSetNames and Entries(i.e their values) are the building blocks
- No Keywords are used
- What the files contain is just the data you defined
- Follows a small, simple & easy structure to define data

It is so simple that you can even modify the data easily within a text editor.


## How Data is DEFINED

Let's take an example:

There is a file named **test.data**
that contains this data in the following format,
```ignorelang
>User Name
-omegaui
>Age
-19
>Location
-I live in my city
|and in my house
>Repo Info
-22
-Git
-GitHub
``` 

In this example, there are four data set names namely

**"User Name"** holding value **"omegaui"**

**"Age"** holding value **"19"**

**"Location"** holding multi lined value **"I live in my city\nand in my house"** 

**"Repo Info"** holding values **"22"**, **"Git"** and **"GitHub"**

The Structure followed here is as follows:

- **>** defines the data set name

- **-** defines the values/entries which correspond to the nearest upper data set

- **|** defines a multi lined value, this character gets converted to '\n' during the read operation

Multiple Values can be defined in a single data set by adding new lines followed by a **-** character 
like in case of the **"Repo Info"** data set in this example.

It is useful when suppose you need to store the font information selected by the user

like:

```ignorelang
>Editor Font
-Ubuntu Mono
-BOLD
-14
```

## How Data is READ

We just need an object of the `omegaui.dynamic.database.DataBase` class
to read data from this file

```java
package test;

import omegaui.dynamic.database.DataBase;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        DataBase dataBase = new DataBase(new File("test.data")); // Auto-Reads the DataBase
        dataBase.getEntriesAsString("Location").forEach(System.out::println);
    }
}
```

The Code above produces the following output

```ignorelang
I live in my city
and in my house
```

The `DataBase` class has more functions to try, have a look at its documentation.

## How Data is STORED

If you have a large data to store, then, instead typing each value one by one, you can utilize the `DataBase` class to write it to the file with correct structure.

The `DataBase` class not only reads the database but also it is capable of modifying it.

Lets take an example:

```java
package test;

import omegaui.dynamic.database.DataBase;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        DataBase dataBase = new DataBase(".some-data"); // Auto-Creates the file if it does n't already exists!
        dataBase.addEntry("Do you like this?","Yes!\nIts incredible.");
        dataBase.save();
    }
}
```

This code will generate a file called **".some-data"** with the following data:
```ignorelang
>Do you like this?
-Yes!
|Its incredible.

```

# MORE EXAMPLES

I recommend taking a look at its documentation.

OR

See [omegaide](https://www.github.com/omegaui/omegaide),

It is an excellent example of how you can utilize **dynamic-database** for your projects.

It contains multiple classes(they have a Manager.java suffix) which use `omegaui.dynamic.database.DataBase` to store and read user data like IDE Settings and Project Info. 
