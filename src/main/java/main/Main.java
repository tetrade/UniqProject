package main;

import com.beust.jcommander.JCommander;

class Main {
    public static void main(String[] args) throws Exception {
        Editor arguments = new Editor();
        JCommander.newBuilder().
                addObject(arguments)
                .build()
                .parse(args);
        arguments.start();
    }
}

