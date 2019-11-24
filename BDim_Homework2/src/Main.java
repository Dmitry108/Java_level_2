//Dmitrii Babanov, 24.11.2019
//Java Level 2, Homework
//1. Напишите метод, на вход которого подается двумерный строковый массив размером 4х4,
//  при подаче массива другого размера необходимо бросить исключение MyArraySizeException.
//2. Далее метод должен пройтись по всем элементам массива, преобразовать в int, и просуммировать.
//  Если в каком-то элементе массива преобразование не удалось (например, в ячейке лежит символ или текст вместо числа),
//  должно быть брошено исключение MyArrayDataException – с детализацией, в какой именно ячейке лежат неверные данные.
//3. В методе main() вызвать полученный метод,
//  обработать возможные исключения MySizeArrayException и MyArrayDataException и вывести результат расчета.

import myExceptions.MyArrayDataException;
import myExceptions.MySizeArrayException;

import java.util.Scanner;

public class Main {
    final static int SIZE = 4;

    public static void main(String[] args) {
        String[][] array = {{"1","2","3","4"},
                            {"5","6","7","8"},
                            {"9","1o0","11","12"},
                            {"1uy3","14","15","16"}};
        int sum=0;
        boolean isCorrect;
        //программа выполняется до тех пор, пока не будет произведен произведен подсчет суммы
        do {
            try {
                sum = sumArray4x4(array);
                isCorrect = true;
            }
            catch (MySizeArrayException e){
                //программа создает новый массив корректной размерности
                //и запрашивает его из консоли
                System.out.printf("%s%nВведите значения массива 4 х 4: ", e.getMessage());
                Scanner sc = new Scanner(System.in);
                array = new String[4][4];
                for (int i=0; i<SIZE*SIZE; array[i/SIZE][i++%SIZE]=sc.next());
                isCorrect = false;
            }
            catch (MyArrayDataException e){
                //при возникновении исключения программа запрашивает корректное значение
                System.out.printf("%s%nВведите корректное значение: ", e.getMessage());
                Scanner sc = new Scanner(System.in);
                array[e.getY()][e.getX()]=sc.next();
                isCorrect = false;
            }
        } while (!isCorrect);
        System.out.printf("Сумма элементов массива равна %d", sum);
    }

    public static int sumArray4x4(String[][] strArray) {
        int sum=0;
        //проверка соответствия размерности 4 х 4
        boolean flag;
        if (flag = strArray.length==SIZE)
            for (String[] str:strArray)
                if (str.length!=SIZE){flag=false; break;}
        if (!flag) throw new MySizeArrayException();
        //подсчет суммы
        //если имеется некорректное значение, исключению передаются координаты и значение        //
        for (int i=0; i<SIZE; i++){
            for (int j=0; j<SIZE; j++){
                try {
                    sum+=Integer.parseInt(strArray[i][j]);
                }
                catch (NumberFormatException e){
                    throw new MyArrayDataException(j, i, strArray[i][j]);
                }
            }
        }
        return sum;
    }
    public static void printArray(String[][] str){
        for (String[] ss:str) {
            for (String s : ss) System.out.print(s + " ");
            System.out.println();
        }
    }
}
