package myExceptions;

public class MySizeArrayException extends RuntimeException {
    @Override
    public String getMessage(){
        return "некорректный размер массива!";
    }
}
