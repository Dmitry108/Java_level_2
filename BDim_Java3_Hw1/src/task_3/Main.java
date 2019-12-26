package task_3;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Orange> oranges = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            oranges.add(new Orange());
        }
        FruitsBox<Orange> orangeBox = new FruitsBox<>(oranges);
        List<Apple> apples = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            apples.add(new Apple());
        }
        FruitsBox<Apple> appleBox = new FruitsBox<>(apples);
        float fff = appleBox.compareFloat(orangeBox);
        System.out.printf("Корзина с яблоками весит %s корзина с апельсинами",
                fff<0?"меньше, чем":fff==0?"столько же, как":"больше, чем");
    }
}