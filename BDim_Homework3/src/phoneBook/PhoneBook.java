//Dmitrii Babanov, 27.11.2019
//Java level 2, lesson 3, Homework
//Написать простой класс ТелефонныйСправочник, который хранит в себе список фамилий и телефонных номеров.
//  В этот телефонный справочник с помощью метода add() можно добавлять записи.
//  С помощью метода get() искать номер телефона по фамилии.
//  Следует учесть, что под одной фамилией может быть несколько телефонов (в случае однофамильцев),
//  тогда при запросе такой фамилии должны выводиться все телефоны.

package phoneBook;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class PhoneBook {
    private Map<String, ArrayList<String>> book;

    public PhoneBook(){
        this.book = new TreeMap<>();
    }

    public void add(String LastName, String number){
        book.put(LastName, book.getOrDefault(LastName, new ArrayList<>()));
        book.get(LastName).add(number);
    }

    public void get(String lastName){
        List list = book.get(lastName);
        System.out.printf("%s %s%n", lastName,
                list==null?"- в списке нет такой записи":list);
    }

    @Override
    public String toString(){
        String text="";
        for (Map.Entry<String, ArrayList<String>> m: book.entrySet())
            text+=String.format("%s %s%n", m.getKey(), m.getValue());
        return text;
    }
}