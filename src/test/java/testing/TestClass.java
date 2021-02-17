package testing;

import com.beust.jcommander.JCommander;
import main.Editor;
import main.MinStringException;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;


import static org.junit.jupiter.api.Assertions.*;

public class TestClass {

    @Test
    public void check1() throws Exception {
        String[] args = {"files/in/input1.txt", "-o", "files/out/output1.txt"};
        Editor edit = new Editor();
        JCommander.newBuilder().
                addObject(edit)
                .build()
                .parse(args);

        edit.start();
        assertEquals(
                String.join("\n",Files.readAllLines(Path.of("files/out/output1.txt"), StandardCharsets.UTF_8)),
                "ABC\nAQE\nQWE\nqwe\nqu\ne\na");
    }

    @Test
    public void check2() throws Exception {
        String[] args = {"-i", "files/in/input2.txt", "-o", "files/out/output2.txt"};
        Editor edit = new Editor();
        JCommander.newBuilder().
                addObject(edit)
                .build()
                .parse(args);

        edit.start();
        assertEquals(
                String.join("\n",Files.readAllLines(Path.of("files/out/output2.txt"), StandardCharsets.UTF_8)),
                "Abc\nqwe\nQqq\nrtQ"
        );
    }

    @Test
    public void check3() throws Exception {
        String[] args = {"-i", "-c", "files/in/input3.txt", "-o", "files/out/output3.txt"};
        Editor edit = new Editor();
        JCommander.newBuilder().
                addObject(edit)
                .build()
                .parse(args);

        edit.start();
        assertEquals(
                String.join("\n",Files.readAllLines(Path.of("files/out/output3.txt"), StandardCharsets.UTF_8))
                ,"1 qwe\n1 Qwee\n3 Ewwq\n2 o\n4 eh\n1 e\n1 a");
    }

    @Test
    public void check4() throws Exception {
        String[] args = {"-i", "-c", "-s", "2","files/in/input4.txt",  "-o", "files/out/output4.txt"};
        Editor edit = new Editor();
        JCommander.newBuilder().
                addObject(edit)
                .build()
                .parse(args);

        edit.start();
        assertEquals(
                String.join("\n",Files.readAllLines(Path.of("files/out/output4.txt"), StandardCharsets.UTF_8))
                , "3 qwAbc\n2 url\n1 opiurl\n1 opqwer");
    }

    @Test
    public void check6() throws Exception {
        String[] args = {"-i", "-u", "-s", "2","-o", "files/out/output5.txt", "files/in/input5.txt"};
        Editor edit = new Editor();
        JCommander.newBuilder().
                addObject(edit)
                .build()
                .parse(args);

        edit.start();
        assertEquals(
                String.join("\n",Files.readAllLines(Path.of("files/out/output5.txt"), StandardCharsets.UTF_8)),
                "url\nhttp\nIEEE"
        );
    }

    @Test
    public void check7() throws Exception {
        String[] args = {"-i", "-u", "-s", "2","-o", "files/out/output6.txt", "files/in/input6.txt"};
        Editor edit = new Editor();
        JCommander.newBuilder().
                addObject(edit)
                .build()
                .parse(args);
        assertThrows(MinStringException.class, edit::start);
    }
}
 