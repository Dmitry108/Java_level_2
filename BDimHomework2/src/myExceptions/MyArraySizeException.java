package myExceptions;

public class MyArraySizeException extends RuntimeException {
    @Override
    public String getMessage(){
        return "массив неправильного размера";
    }
}
