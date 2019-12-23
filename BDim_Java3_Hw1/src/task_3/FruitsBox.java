/*
Есть классы Fruit -> Apple, Orange (больше фруктов не надо);
        Класс Box, в который можно складывать фрукты.
        Коробки условно сортируются по типу фрукта,
        поэтому в одну коробку нельзя сложить и яблоки, и апельсины;
        Для хранения фруктов внутри коробки можно использовать ArrayList;
        Сделать метод getWeight(), который высчитывает вес коробки,
        зная количество фруктов и вес одного фрукта
        (вес яблока – 1.0f, апельсина – 1.5f.
        Не важно, в каких это единицах);
        Внутри класса Коробка сделать метод compare,
        который позволяет сравнить текущую коробку с той,
        которую подадут в compare в качестве параметра,
        true – если она равны по весу, false – в противном случае
        (коробки с яблоками мы можем сравнивать с коробками с апельсинами);
        Написать метод, который позволяет пересыпать фрукты из текущей коробки в другую
        (помним про сортировку фруктов: нельзя яблоки высыпать в коробку с апельсинами).
        Соответственно, в текущей коробке фруктов не остается,
        а в другую перекидываются объекты, которые были в этой коробке;
        Не забываем про метод добавления фрукта в коробку.
*/
package task_3;

import java.util.ArrayList;
import java.util.List;

public class FruitsBox<T extends Fruits>{
    private List<T> box;

    public FruitsBox(){
        box = new ArrayList<>();
    }
    public FruitsBox(List<T> frList){
        box = new ArrayList<>();
        this.addFruits(frList);
    }
    public void addFruits(List<T> frList){
        box.addAll(frList);
    }
    public void addFruits(T[] frArray){
        for (T aa:frArray) box.add(aa);
    }
    public void addFruit(T fruit){
        box.add(fruit);
    }
    public List<T> getBox(){
        return box;
    }
    public float getWeight(){
        float weight = 0f;
        for (T aa: box){
            weight+=aa.getWeight();
        }
        return weight;
    }
    public boolean compare(FruitsBox fruits){
        if (this.getWeight()==fruits.getWeight()) return true;
        else return false;
    }
    public float compareFloat (FruitsBox fruits){
        return this.getWeight()-fruits.getWeight();
    }
    public FruitsBox sprinkle(){
        FruitsBox<T> newFruitsBox = new FruitsBox<>();
        newFruitsBox.addFruits(this.getBox());
        this.getBox().clear();
        return newFruitsBox;
    }


}