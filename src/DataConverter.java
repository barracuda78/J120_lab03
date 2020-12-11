package com.barracuda.tasks.politeh.labs.j120.lab03.src;

import com.barracuda.tasks.politeh.labs.j120.lab03.src.std.ConverterException;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataConverter implements IFileConverter{
    /**
     * Метод преобразует текстовый файл в двоичное
     * представление. Метод реализует следующий алгоритм:
     * - обычный текст преобразуется в байтовое представление с
     * использованием заданной кодировки charSet;
     * - текстовое представление чисел в интегральном и
     * вещественном формате преобразуется в двоичном
     * представлении.
     *
     * @param inputFileName имя исходного файла данных в
     * текстовом формате.
     * @param outputFileName имя выходного файла с данными в
     * двоичном формате.
     * Если файл с таким именем уже существует, то он
     * перезаписывается данным методом.
     * @param charSet используемая кодировка текста.
     * @return строку с именем выходного файла, если конвертация
     * завершена корректно, или сообщение с описанием исключения,
     * возникшего в ходе выполнения конвертации.
     */

    //Пояснения к задаче.
    //
    //1. Исходный текстовый файл для методов toBinary(...) и getSum(...) может содержать обычный текст,
    // целые числа и вещественные числа в любой комбинации.
    // Однако известно, что каждое число в таком файле (целое или вещественное) всегда отделено от текста пробельными символами.
    // Например,
    //This text contains 128 symbols in first line, and 64 symbols in second one.
    @Override
    public String toBinary(String inputFileName, String outputFileName, String charSet) {
        //читаем весь файл в один стрингбилдер:
        StringBuilder sb = new StringBuilder();
        try(
            //1. поток для чтения текстового файла:
            BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFileName));
            //2. поток для записи  в новый бинарный файл:
            DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(outputFileName, false))){
            //читаем весь файл в один стрингбилдер:
            while(bufferedReader.ready()){
                sb.append(bufferedReader.readLine());
                sb.append("\n");
            }
            //удаляем последний перевод строки:
            sb.deleteCharAt(sb.length()-1);

            //получаем строку.
            String s = sb.toString();
            //получаем массив строк разделенных пробелом:
            String[] strings = s.split(" ");
            //каждый элемент массива парсим регуляркой на строку иили число.
            //паттерн для вещественных чисел:
            Pattern p1 = Pattern.compile("-?\\d+\\.\\d+");
            //паттерн для целых чисел:
            Pattern p2 = Pattern.compile("-?\\d+");

            //сразу же записываем их в bufferedOutputStream
            for(int i = 0; i < strings.length; i++){
                Matcher m1 = p1.matcher(strings[i]);
                Matcher m2 = p2.matcher(strings[i]);

                 //если это вечественное число:
                if(m1.matches()){
                     //если это последний элементо массива - то пробел после него не записываем:
                    if (i == strings.length - 1) {
                        //- текстовое представление чисел в интегральном и вещественном формате преобразуется в двоичном представлении.
                        dataOutputStream.writeDouble(Double.parseDouble(strings[i]));
                    }

                     //если это НЕ последний элемент массива - то пробел после него записываем:
                    else{
                        //- текстовое представление чисел в интегральном и вещественном формате преобразуется в двоичном представлении.
                        //в конце дописывать пробел.
                        dataOutputStream.writeDouble(Double.parseDouble(strings[i]));
                        dataOutputStream.writeUTF(" ");
                    }
                }

                 //если это целое число:
                else if(m2.matches()){
                    //если это последний элементо массива - то пробел после него не записываем:
                    if (i == strings.length - 1){
                        dataOutputStream.writeInt(Integer.parseInt(strings[i]));
                    //если это НЕ последний элемент массива - то пробел после него записываем:
                    }else{
                        //в конце дописывать пробел.
                        dataOutputStream.writeInt(Integer.parseInt(strings[i]));
                        dataOutputStream.writeUTF(" ");
                    }
                }
                 //если это не число:
                else{
                    //если последний элементо массива - пробел после него не записываем:
                    if (i == strings.length - 1) {
                        dataOutputStream.writeUTF(strings[i]);
                    }
                    //если НЕ последний элементо массива - пробел после него записываем:
                    else{
                        //- обычный текст преобразуется в байтовое представление ;
                        dataOutputStream.writeUTF(strings[i]);
                        dataOutputStream.writeUTF(" ");
                    }
                }
            }

        } catch (FileNotFoundException e) {
            return e.getMessage();
        } catch (IOException e) {
            return e.getMessage();
        }
        return outputFileName;
    }


    /**
     * Метод преобразует двоичные данные исходного файла в их
     * текстовое представление. Метод реализует следующий
     * алгоритм:
     * 1. обычный текст преобразуется в строки с использованием
     * заданной кодировки charSet;
     * 2. числовые данные в интегральном или вещественном
     * формате преобразуется в их десятичное представление.
     * @param inputFileName имя исходного файла данных в
     * двоичном формате.
     * @param outputFileName имя выходного файла с данными в
     * текстовом формате.
     * Если файл с таким именем уже существует, то он
     * перезаписывается данным методом.
     * @param charSet используемая кодировка текста.
     * @return строку с именем выходного файла, если конвертация
     * завершена корректно, или сообщение с описанием исключения,
     * возникшего в ходе выполнения конвертации.
     */
    //2. Исходные двоичные файлы для метода toText(...) всегда содержат данные только одного вида:
    // только текст, только целые числа или только вещественные числа.
    // Для упрощения решения можно предположить,
    // что первые 4 байта такого файла содержат последовательность символов, определяющих тип данных этого файла.
    // Например,  TXT_ указывает на текстовое содержимое,
    // DBL_ - на вещественные числа типа double,
    // а INT_ - на целочисленные значения типа int.
    @Override
    public String toText(String inputFileName, String outputFileName, String charSet) {
        //читаем весь файл в один стрингбилдер:
        StringBuilder sb = new StringBuilder();

          try(DataInputStream dataInputStream = new DataInputStream(new FileInputStream(inputFileName));
              BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFileName, false))){
              //объявляю массив байтов для чтения заголовка файла (TXT_ или DBL_ или INT_):
              //(два первых байта - служебные, их пропускаем... - т.к. записывались методом writeUTF())
              byte[] b = new byte[6];
              //если метод read вернул 6 - значит прочитались 4 байта, тогда сравниваем их с разными вариантами заголовка файла:
              if(dataInputStream.read(b, 0, 6) == 6){
                  //делаю строку на основе массива из 6-х первых байтов файла, только два самых первых (служебных) не учитываю:
                  String fileType = new String(b, 2, 4);
                  //сравниваю эту строку с типом файла:
                  if("TXT_".equals(fileType)){
                    //значит это текстовый файл, читаем в стрингбилдер текст (метод readUTF возвращает строку:
                      sb.append(dataInputStream.readUTF());
                  }else if("DBL_".equals(fileType)){
                    //значит в файле - значения вещественные. Читаем только их, перемежая пробелами:
                      while(dataInputStream.available() > 0){
                          double d = dataInputStream.readDouble();
                          sb.append(d);
                          sb.append(" ");
                      }
                      //удаляем последний пробел в конце:
                      sb.deleteCharAt(sb.length()-1);

                  }else if("INT_".equals(fileType)){
                      //значит в файле - значения целочисленнные. Читаем только их, перемежая пробелами:
                      while(dataInputStream.available() > 0){
                          int n = dataInputStream.readInt();
                          sb.append(n);
                          sb.append(" ");
                      }
                      //удаляем последний пробел в конце:
                      sb.deleteCharAt(sb.length()-1);
                  }else{
                      throw new FileNotFoundException("неверный формат заголовка файла.");
                  }
              }else{
                  throw new FileNotFoundException("неверный формат заголовка файла.");
              }

              System.out.println("Вот наш стрингбилдер " + sb.toString());
              //теперь записываем весь стрингбилдер в текстовый файл:
              bufferedWriter.write(sb.toString());
              bufferedWriter.flush();

        } catch (FileNotFoundException e) {
            return e.getMessage();
        } catch (IOException e) {
            return e.getMessage();
        }
        return outputFileName;
    }


    /**
     * Метод подсчитывает и возвращает сумму всех чисел, которые
     * встречаются в тексте файла. Внимание! Метод работает
     * только с текстовыми файлами.
     * @param fileName имя исходного файла с данными.
     * @return подсчитанную сумму чисел.
     * //@throws ConverterException данное исключение
     * выбрасывается в случае,
     * если:
     * - имя файла fileName равно null;
     * - файл fileName не содержит данных.
     */
    @Override
    public double getSum(String fileName) throws ConverterException {
        double sum = 0.0;

        if(fileName == null)
            throw new ConverterException();

        StringBuilder sb = new StringBuilder();

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))){

            if(!bufferedReader.ready())
                throw new ConverterException();

            while(bufferedReader.ready()){
                sb.append(bufferedReader.readLine());
                sb.append("\n");
            }
            sb.deleteCharAt(sb.length()-1);

            String wholeFile = sb.toString();

            //паттерн для вещественных И ЦЕЛЫХ чисел:
            Pattern p1 = Pattern.compile("-?\\d+\\.?\\d*");
            Matcher m1 = p1.matcher(wholeFile);
            while(m1.find()){
                int begin = m1.start();
                int end = m1.end();
                //System.out.println(wholeFile.substring(begin, end)); <------------------для теста
                sum += Double.parseDouble(wholeFile.substring(begin, end));
            }

        }catch (FileNotFoundException e) {
            e.getStackTrace();
            //return 0;
        } catch (IOException e) {
            //return 0;
            e.getStackTrace();
        }
        return sum;
    }


    //не использую эти три метода, т.к. работаю с DataOutputStream, а не перевожу Double и Integer в двоичную систему счисления:
    //для метода toBinary(): 3 вспомогательных метода : перевод double из десятичной системы счисления в двоичную:
    //3. сборщик:
    public String doubleToBinary(double d, int precision) {
        long wholePart = (long) d;
        return wholeToBinary(wholePart) + '.' + fractionalToBinary(d - wholePart, precision);
    }
    //1. перевод целой части дабл:
    private String wholeToBinary(long l) {
        return Long.toBinaryString(l);
    }
    //2. перевод дробной части дабл:
    private String fractionalToBinary(double num, int precision) {
        StringBuilder binary = new StringBuilder();
        while (num > 0 && binary.length() < precision) {
            double r = num * 2;
            if (r >= 1) {
                binary.append(1);
                num = r - 1;
            } else {
                binary.append(0);
                num = r;
            }
        }
        return binary.toString();
    }

    ////////////////////////////метод для создания тестового файла для метода toText который содержит метку DBL_ и несколько double подряд в двоичном представлении./////////////////////////////////
    public String toBinaryTesting(String inputFileName, String outputFileName, String charSet) {
        int precision = 8;
        //читаем весь файл в один стрингбилдер:
        StringBuilder sb = new StringBuilder();
        try(
                //1. поток для чтения текстового файла:
                BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFileName));
                //2. Общий FileOutputStream
                FileOutputStream fis = new FileOutputStream(outputFileName, false);
                //3. поток для записи текста в новый бинарный файл:(использует один и тот же FileOutputStream)
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fis);
                //4. поток для записи Integer и Double в новый бинарный файл: (использует один и тот же FileOutputStream)
                DataOutputStream dataOutputStream = new DataOutputStream(fis)){
            //читаем весь файл в один стрингбилдер:
            while(bufferedReader.ready()){
                sb.append(bufferedReader.readLine());
                sb.append("\n");
            }
            //удаляем последний перевод строки:
            sb.deleteCharAt(sb.length()-1);

            //получаем строку.
            String s = sb.toString();
            //получаем массив строк разделенных пробелом:
            String[] strings = s.split(" ");
            //каждый элемент массива парсим регуляркой на строку иили число.
            //паттерн для вещественных чисел:
            Pattern p1 = Pattern.compile("-?\\d+\\.\\d+");
            //Matcher m1 = p1.matcher("234.098");

            //паттерн для целых чисел:
            Pattern p2 = Pattern.compile("-?\\d+");
            //Matcher m2 = p2.matcher("234.098");

            //сразу же записываем их в bufferedOutputStream

            for(int i = 0; i < strings.length; i++){
                Matcher m1 = p1.matcher(strings[i]);
                Matcher m2 = p2.matcher(strings[i]);

                //если это вечественное число:
                if(m1.matches()){
                    //если это последний элементо массива - то пробел после него не записываем:
                    if (i == strings.length - 1) {
                        //- текстовое представление чисел в интегральном и вещественном формате преобразуется в двоичном представлении.
                        //String l = Long.toBinaryString(Double.doubleToRawLongBits(Double.parseDouble(strings[i])));
                        //или
                        //String l = doubleToBinary(Double.parseDouble(strings[i]), precision);
                        //write(byte[] b, int off, int len):
                        //преобразовываю строку в массив байтов и пишу методом write. Кодировку тут не указываю.
                        //bufferedOutputStream.write(l.getBytes());
                        dataOutputStream.writeDouble(Double.parseDouble(strings[i]));
                    }

                    //если это НЕ последний элемент массива - то пробел после него записываем:
                    else{
                        //- текстовое представление чисел в интегральном и вещественном формате преобразуется в двоичном представлении.
                        //String l = doubleToBinary(Double.parseDouble(strings[i]), precision);
                        //String l = Long.toBinaryString(Double.doubleToRawLongBits(Double.parseDouble(strings[i])));
                        //write(byte[] b, int off, int len):
                        //преобразовываю строку в массив байтов и пишу методом write. Кодировку тут не указываю.
                        //bufferedOutputStream.write(l.getBytes());
                        //в конце дописывать пробел.
                        dataOutputStream.writeDouble(Double.parseDouble(strings[i]));
                        //dataOutputStream.writeUTF(" ");
                        //bufferedOutputStream.write(" ".getBytes(charSet)); ///------------------> тут может крыться ошибка, т.к. пишу в один файл с двух разных потоков.

                    }
                }

                //если это целое число:
                else if(m2.matches()){
                    //если это последний элементо массива - то пробел после него не записываем:
                    if (i == strings.length - 1){
//                        String l = Integer.toBinaryString(Integer.parseInt(strings[i]));
//                        bufferedOutputStream.write(l.getBytes());
                        dataOutputStream.writeInt(Integer.parseInt(strings[i]));
                        //если это НЕ последний элемент массива - то пробел после него записываем:
                    }else{
                        //String l = Integer.toBinaryString(Integer.parseInt(strings[i]));
                        //bufferedOutputStream.write(l.getBytes());
                        //в конце дописывать пробел.
                        dataOutputStream.writeInt(Integer.parseInt(strings[i]));
                        //dataOutputStream.writeUTF(" ");
                        //bufferedOutputStream.write(" ".getBytes(charSet));
                        //bufferedOutputStream.write(" ".getBytes(charSet));
                    }
                }
                //если это не число:
                else{
                    //если последний элементо массива - пробел после него не записываем:
                    if (i == strings.length - 1) {
                        //- обычный текст преобразуется в байтовое представление с использованием заданной кодировки charSet;
                        dataOutputStream.writeUTF(strings[i]);
                        //bufferedOutputStream.write(strings[i].getBytes(charSet));
                    }
                    //если НЕ последний элементо массива - пробел после него записываем:
                    else{
                        //- обычный текст преобразуется в байтовое представление с использованием заданной кодировки charSet;
                        //bufferedOutputStream.write(strings[i].getBytes(charSet));
                        //в конце дописывать пробел.
                        //bufferedOutputStream.write(" ".getBytes(charSet));
                        dataOutputStream.writeUTF(strings[i]);
                        //dataOutputStream.writeUTF(" ");
                    }
                }
            }

        } catch (FileNotFoundException e) {
            return e.getMessage();
        } catch (IOException e) {
            return e.getMessage();
        }
        return outputFileName;
    }

}
