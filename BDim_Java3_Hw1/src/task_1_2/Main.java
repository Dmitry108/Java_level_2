package task_1_2;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Integer[] a = {1, 2, 3, 4, 5, 6};
        Long[] l = {11L, 22L, 33L, 44L, 55L, 66L};
        String[] s = {"один", "два", "три", "четыре", "пять", "шесть"};
        printArray(a);
        method1(a, 1, 3);
        printArray(a);
        List<String> sss = method2(s);
        printList(sss);
    }
    public static <T> void method1(T[] array, int index1, int index2) throws IndexOutOfBoundsException {
        //метод меняет местами элементы по индексу index1 и index2 в массиве array
        if (index1<array.length && index2<array.length){
            T a = array[index1];
            array[index1]=array[index2];
            array[index2]=a;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }
    public static <T> ArrayList<T> method2(T[] array){
        ArrayList<T> list = new ArrayList<>();
        for (T aa: array) list.add(aa);
        return list;
    }
    public static <T> void printArray(T[] array){
        for (T aa: array) System.out.print(aa.toString() + " ");
        System.out.println("\n");
    }
    public static <T> void printList(List<T> al){
        al.forEach(aaa -> System.out.print(aaa + " "));
        System.out.println(" ");
    }
}