//Dmitrii Babanov, 27.11.2019
//Java level 2, Lesson 3, Homework
//Создать массив с набором слов (10-20 слов, среди которых должны встречаться повторяющиеся).
//  Найти и вывести список уникальных слов, из которых состоит массив (дубликаты не считаем).
//  Посчитать, сколько раз встречается каждое слово.

package repeatingWords;

import java.util.HashMap;
import java.util.Map;

public class RepeatinWords {
    public static void main(String[] args) {
        String string = ("Создать массив с набором слов (10-20 слов, среди которых должны встречаться повторяющиеся). " +
                "Найти и вывести список уникальных слов, из которых состоит массив (дубликаты не считаем). " +
                "Посчитать, сколько раз встречается каждое слово.").toLowerCase();
        String[] text = string.split("[^а-яА-Я]+");
        Map<String, Integer> map = new HashMap<>();
        for (String str: text)
            map.put(str, map.getOrDefault(str, 0)+1);
        System.out.printf("%-15s%-5s%n", "слово", "частота");
        map.forEach((k, v)-> System.out.printf("%-15s%-5d%n", k, v));
    }
}