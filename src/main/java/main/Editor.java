/*
Вариант 10 -- uniq
Объединение последовательностей одинаковых идущих подряд строк в файле в одну:
file задаёт имя входного файла. Если параметр отсутствует, следует считывать текст с консоли.
Флаг -o ofile  задаёт имя выходного файла. Если параметр отсутствует, следует выводить результаты на консоль.
Флаг -i означает, что при сравнении строк следует не учитывать регистр символов.
Флаг -s N означает, что при сравнении строк следует игнорировать первые N символов каждой строки. Выводить нужно первую строку.
Флаг -u означает, что следует выводить в качестве результата только уникальные строки.
Флаг -с означает, что перед каждой строкой вывода следует вывести количество строк, которые были заменены данной (т.е. если во входных данных было 2 одинаковые строки, в выходных данных должна быть одна с префиксом “2”).

Command line: uniq [-i] [-u] [-c] [-s num] [-o ofile] [file]

В случае, когда какое-нибудь из имён файлов указано неверно, следует выдать ошибку.

Кроме самой программы, следует написать автоматические тесты к ней.
*/
package main;

import com.beust.jcommander.Parameter;

import java.io.*;
import java.util.*;

public class Editor {
    @Parameter(names = "-i")
    private boolean iF;
    @Parameter(names = "-u")
    private boolean uF;
    @Parameter(names = "-c")
    private boolean cF;
    @Parameter(names = "-s")
    private int sF;
    @Parameter(names = "-o", converter = FileConverter.class, validateWith = CheckExist.class)
    private File outputFile;
    @Parameter(converter = FileConverter.class, validateWith = CheckExist.class)
    private File inputFile;
    private ArrayList<StringBuffer> list = new ArrayList();

    public String getAns() {
        return String.join("\n", list);
    }

    public void editing() throws MinStringException, IOException {
        Scanner in;
        if (inputFile == null) {
            in = new Scanner(System.in);
        } else {
            in = new Scanner(inputFile);
        }
        int index = 0;
        String lineToWrite = "";
        int countOfLine = 1;
        LinkedHashMap<String, Integer> map = new LinkedHashMap();
        while (in.hasNextLine()) {
            String line = in.nextLine();
            if (line.length() < sF) {
                throw new MinStringException("String length is less than -s flag");
            }
            if (!uF) {
                if (lineToWrite.equals("") || !checkForEq(lineToWrite, line)) {
                    list.add(new StringBuffer(line));
                    if (cF && !lineToWrite.equals("")) {
                        list.set(index, list.get(index).insert(0, countOfLine + " "));
                        index++;
                        countOfLine = 1;
                    }
                    lineToWrite = line;
                } else if (cF) {
                    countOfLine++;
                }
            } else {
                boolean checkUniq = true;
                for (String key : map.keySet()) {
                    if (checkForEq(key, line)) {
                        map.put(key, map.get(key) + 1);
                        checkUniq = false;
                        break;
                    }
                }
                if (checkUniq) {
                    map.put(line, map.getOrDefault(line, 1));
                }
            }
        }
        if (cF && !uF) {
            list.set(index, list.get(index).insert(0, countOfLine + " "));
        }
        if (uF) {
            for (String key : map.keySet()) {
                if (map.get(key) == 1) {
                    if (cF) {
                        list.add(new StringBuffer("1 " + key));
                    } else {
                        list.add(new StringBuffer(key));
                    }
                }
            }
        }
        writeOut(String.join("\n", list));
    }

    private void writeOut(String inf) throws IOException {
        if (outputFile != null) {
            FileWriter writer = new FileWriter(outputFile);
            writer.write(inf);
            writer.close();
        } else {
            System.out.println(inf);
        }
    }

    private boolean checkForEq(String line1, String line2) {
        return line1.equals(line2)
                || (iF && line1.equalsIgnoreCase(line2))
                || (sF != 0 && line1.substring(sF).equals(line2.substring(sF)))
                || (sF != 0 && iF && line1.substring(sF).equalsIgnoreCase(line2.substring(sF)));
    }
}
