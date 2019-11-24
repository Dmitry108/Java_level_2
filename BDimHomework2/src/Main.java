//Dmitrii Babanov, 24.11.19
//Java_level_2, lesson 2, Homework
//
//1. Напишите метод, на вход которого подается двумерный строковый массив
//    размером 4х4, при подаче массива другого размера
//    необходимо бросить исключение MyArraySizeException.
//2. Далее метод должен пройтись по всем элементам массива,
//    преобразовать в int, и просуммировать.
//    Если в каком-то элементе массива преобразование не удалось
//    (например, в ячейке лежит символ или текст вместо числа),
//    должно быть брошено исключение MyArrayDataException –
//    с детализацией, в какой именно ячейке лежат неверные данные.
//3. В методе main() вызвать полученный метод,
//    обработать возможные исключения MySizeArrayException и
//    MyArrayDataException и вывести результат расчета.

import myExceptions.MyArrayDataException;
import myExceptions.MyArraySizeException;

import java.util.Scanner;

public class Main {
    final static int SIZE = 4;

    public static int sumArray4x4(String[][] strArray) {
        //проверка на соответствие размерности массива 4 х 4
        boolean flag;
        if (flag = strArray.length == SIZE) //условие проверки количества строк в массиве
            for (String[] s : strArray)
                if (s.length != SIZE) {flag=false; break;} //проверка длины строк
        if (!flag) throw new MyArraySizeException();

        //вычисление суммы элементов массива
        int sum = 0;
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                try {
                    sum += Integer.parseInt(strArray[i][j]);
                } catch (NumberFormatException e) {
                    throw new MyArrayDataException(j, i, strArray[i][j]); //конструктор принимает координаты и некорректное значение
                }
        return sum;
    }

    public static void printArray(String[][] strArray) {
        for (String[] str : strArray) {
            for (String s : str) System.out.print(s + " ");
            System.out.println();
        }
    }

    public static void main(String[] args) {
        String[][] array = {{"1", "2", "3", "4"},
                            {"5", "6", "7", "8"},
                            {"9", "1o0", "1gh1", "12"},
                            {"13", "14", "15", "17"}};
        boolean isCorrect;
        int sum=0;
        //программа будет пытаться вычислить сумму до тех пор, пока не будут введены корректные данные
        do {
            try {
                sum=sumArray4x4(array);
                isCorrect=true;
            }
            catch (MyArraySizeException e){
                //обработка исключения неправильной размерности
                //создается новый массив правильного размера и предлагается пользователю ввести новые значения из консоли
                System.out.printf("%s%nВведите корректный массив: ",e.getMessage());
                array=new String[SIZE][SIZE];
                Scanner sc = new Scanner(System.in);
                for (int i=0; i<SIZE*SIZE; i++) array[i/SIZE][i%SIZE]=sc.next();
                isCorrect=false;
            }
            catch (MyArrayDataException e) {
                //обработка исключения некорректных значений
                //программа запрашивает новое значение из консоли
                System.out.printf("%s%nВведите корректное значение:%n", e.getMessage());
                Scanner sc = new Scanner(System.in);
                array[e.getY()][e.getX()] = sc.nextLine();
                isCorrect = false;
            }
        } while (!isCorrect);
        System.out.printf("Сумма элементов массива равна %d%n", sum);
    }
}