package Exceptions;

public class PabellonIncorrectoException extends Exception{
    public PabellonIncorrectoException(String mensaje){
        super(mensaje);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
