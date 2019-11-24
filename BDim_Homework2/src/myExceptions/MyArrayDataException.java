package myExceptions;

public class MyArrayDataException extends RuntimeException {
    private int x;
    private int y;
    private String message;

    public MyArrayDataException(int x, int y, String str){
        this.x=x;
        this.y=y;
        this.message=String.format("В ячейке %d - %d некорректоное значение %s", y, x, str);
    }

    public int getX(){return x;}
    public int getY(){return y;}

    @Override
    public String getMessage(){
        return message;}
}
