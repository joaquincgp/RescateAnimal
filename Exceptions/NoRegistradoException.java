package Exceptions;

public class NoRegistradoException extends Exception{
    public NoRegistradoException(String mensaje){
        super(mensaje);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
