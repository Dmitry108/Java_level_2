package phoneBook;

public class Main {
    public static void main(String[] args) {
        PhoneBook pb = new PhoneBook();
        pb.add("Борисов", "111-11-11");
        pb.add("Светина", "123-45-67");
        pb.add("Зайцев", "333-44-55");
        pb.add("Носова", "154-86-74");
        pb.add("Борисов", "777-37-33");
        pb.get("Борисов");
        //System.out.println(pb);
    }
}
