package Exceptions;

public class CedulaInvalidaException extends Exception{
    public CedulaInvalidaException(String mensaje){
        super(mensaje);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
