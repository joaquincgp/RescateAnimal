package Exceptions;

public class CampoVacioException extends Exception{
    public CampoVacioException(String mensaje){
        super(mensaje);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}