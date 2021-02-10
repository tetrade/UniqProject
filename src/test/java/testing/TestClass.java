package testing;

import com.beust.jcommander.JCommander;
import com.sun.tools.javac.Main;
import main.Editor;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

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
}
