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
    private ArrayList<StrIntPair> arr = new ArrayList<>();

    public void start() throws MinStringException, IOException {
        if (inputFile == null) {
            fillArr(new Scanner(System.in));
        } else {
            fillArr(new Scanner(inputFile));
        }
        writeOut();
    }

    private void fillArr(Scanner in) throws MinStringException {
        int curIndex = -1;
        while (in.hasNextLine()) {
            String line = in.nextLine();
            if (line.length() < sF) {
                throw new MinStringException("String length is less than -s flag");
            }
            if (curIndex == -1 || !checkForEq(arr.get(curIndex).getStr(), line)) {
                curIndex += 1;
                arr.add(new StrIntPair(line, 1));
            } else {
                arr.set(curIndex, arr.get(curIndex).incValue());
            }
        }
    }


    private void writeOut() throws IOException {
        if (outputFile == null) {
                for (StrIntPair pair: arr) {
                    if (checkUFlag(uF, pair, arr)) {
                        System.out.println(stringCFlag(cF, pair));
                    }
                }
            } else {
                FileWriter writer = new FileWriter(outputFile);
                boolean start = true;
                for (StrIntPair pair: arr) {
                    if (checkUFlag(uF, pair, arr)) {
                        if (start) {
                            writer.write(stringCFlag(cF, pair));
                            start = false;
                        }
                        else {
                            writer.write("\n"+stringCFlag(cF, pair));
                        }
                    }
                }
                writer.close();
            }
        }

        //    возвращает true, когда элемент уникальный для всего cписка arr или когда флаг uF отсутствует
    private boolean checkUFlag(boolean uF, StrIntPair pair, ArrayList<StrIntPair> arr) {
        if (!uF) return true;
        if (pair.getValue() != 1) return false;
        int check = -1;
        for (StrIntPair cPair: arr) {
            if (checkForEq(cPair.getStr(), pair.getStr())) {
                check += 1;
            }
        }
        return check == 0;
    }

    private String stringCFlag(boolean cF, StrIntPair pair) {
        if (cF) {
            return pair.getValue() + " "  + pair.getStr();
        } else {
            return pair.getStr();
        }
    }

    private boolean checkForEq(String line1, String line2) {
        return line1.equals(line2)
                || (iF && line1.equalsIgnoreCase(line2))
                || (sF != 0 && line1.substring(sF).equals(line2.substring(sF)))
                || (sF != 0 && iF && line1.substring(sF).equalsIgnoreCase(line2.substring(sF)));
    }
}