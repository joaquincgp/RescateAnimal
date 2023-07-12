package Exceptions;

public class CaracteresNoValidosException extends Exception{
    public CaracteresNoValidosException(String mensaje){
        super(mensaje);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
