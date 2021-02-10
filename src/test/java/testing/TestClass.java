package testing;

import com.beust.jcommander.JCommander;
import com.sun.tools.javac.Main;
import main.Editor;
import main.MinStringException;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class TestClass {

    @Test
    public void check1() throws Exception {
        String[] args = {"files/input1.txt"};
        Editor edit = new Editor();
        JCommander.newBuilder().
                addObject(edit)
                .build()
                .parse(args);

        edit.editing();
        assertEquals(edit.getAns(), "ABC\nAQE\nQWE\nqwe\nqu\ne\na");
    }

    @Test
    public void check2() throws Exception {
        String[] args = {"-i", "files/input2.txt"};
        Editor edit = new Editor();
        JCommander.newBuilder().
                addObject(edit)
                .build()
                .parse(args);

        edit.editing();
        assertEquals(edit.getAns(), "Abc\nqwe\nQqq\nrtQ");
    }

    @Test
    public void check3() throws Exception {
        String[] args = {"-i", "-c", "files/input3.txt"};
        Editor edit = new Editor();
        JCommander.newBuilder().
                addObject(edit)
                .build()
                .parse(args);

        edit.editing();
        assertEquals(edit.getAns(), "1 qwe\n1 Qwee\n3 Ewwq\n2 o\n4 eh\n1 e\n1 a");
    }

    @Test
    public void check4() throws Exception {
        String[] args = {"-i", "-c", "-s", "2","files/input4.txt"};
        Editor edit = new Editor();
        JCommander.newBuilder().
                addObject(edit)
                .build()
                .parse(args);

        edit.editing();
        assertEquals(edit.getAns(), "3 qwAbc\n2 url\n1 opiurl\n1 opqwer");
    }

    @Test
    public void check5() throws Exception {
        String[] args = {"-i", "-u", "-s", "2","files/input5.txt"};
        Editor edit = new Editor();
        JCommander.newBuilder().
                addObject(edit)
                .build()
                .parse(args);

        edit.editing();
        assertEquals("url\nhttp\nIEEE", edit.getAns());
    }
    @Test
    public void check6() throws Exception {
        String[] args = {"-i", "-u", "-s", "2","-o", "files/output.txt", "files/input5.txt"};
        Editor edit = new Editor();
        JCommander.newBuilder().
                addObject(edit)
                .build()
                .parse(args);

        edit.editing();
        assertEquals(
                String.join("\n",Files.readAllLines(Path.of("files/output.txt"), StandardCharsets.UTF_8)), "url\nhttp\nIEEE"
        );
    }

    @Test
    public void check7() throws Exception {
        String[] args = {"-i", "-u", "-s", "2","-o", "files/output.txt", "files/input6.txt"};
        Editor edit = new Editor();
        JCommander.newBuilder().
                addObject(edit)
                .build()
                .parse(args);

        assertThrows(MinStringException.class, () -> {
            edit.editing();
        });

    }
}
