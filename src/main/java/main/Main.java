package main;

import com.beust.jcommander.JCommander;

import java.io.File;
import java.io.FileNotFoundException;

class Main {
    public static void main(String[] args) throws Exception {
        Editor arguments = new Editor();
        JCommander.newBuilder().
                addObject(arguments)
                .build()
                .parse(args);
        arguments.editing();
    }
}

