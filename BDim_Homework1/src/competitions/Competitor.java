package competitions;

public interface Competitor {
    void run (int distance);
    void jump (double height);
    boolean isOnDistance();
    void info();
    default void info(String type, String name, boolean onDistance){
        System.out.printf("%s %s %s%n", type, name, onDistance? "на дистанции": "выбыл из соревнований");
    }
}
