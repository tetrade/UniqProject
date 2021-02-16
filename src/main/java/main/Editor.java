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
    public ArrayList<StrIntPair> arr = new ArrayList<>();

    public void ans(){
        for (StrIntPair pair: arr) {
            System.out.println(pair.getValue() + " " + pair.getStr());
        }
    }


    public void editing() throws MinStringException, IOException {
        Scanner in;
        if (inputFile == null) {
            in = new Scanner(System.in);
        } else {
            in = new Scanner(inputFile);
        }
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

    private void writeOut(String inf) throws IOException {

        if (outputFile != null) {
            FileWriter writer = new FileWriter(outputFile);
            writer.close();
        } else {
            if (uF) {
                for (int i = 0; i < arr.size(); i++) {
                    boolean uniq = true;
                    for (int j = 0; j < arr.size(); j++) {
                        if (i != j
                                && (arr.get(i).getValue() != 1)
                                && checkForEq(arr.get(i).getStr(), arr.get(j).getStr())) {
                            uniq = false;
                            break;
                        }
                    }
                    System.out.println(arr.get(i).getValue());
                }
                System.out.println(inf);
            } else {
                for (StrIntPair pair: arr) {
                    if (cF) {
                        System.out.println(pair.getValue() + ""  + pair.getStr());
                    }
                    else {
                        System.out.println(pair.getStr());
                    }
                    
                }
            }
        }
    }

    private boolean checkForEq(String line1, String line2) {
        return line1.equals(line2)
                || (iF && line1.equalsIgnoreCase(line2))
                || (sF != 0 && line1.substring(sF).equals(line2.substring(sF)))
                || (sF != 0 && iF && line1.substring(sF).equalsIgnoreCase(line2.substring(sF)));
    }
}
