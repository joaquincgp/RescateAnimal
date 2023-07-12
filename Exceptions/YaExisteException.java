package Exceptions;

public class YaExisteException extends Exception{
    public YaExisteException(String mensaje){
        super(mensaje);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
