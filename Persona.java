public class Persona {
    private String nombrePersona;
    private String celular;
    private String cedula;
    private String correo;
    private String genero;
    private String direccion;


    public Persona(String nombrePersona, String celular, String cedula, String correo, String genero, String direccion) {
        this.nombrePersona = nombrePersona;
        this.celular = celular;
        this.cedula = cedula;
        this.correo = correo;
        this.genero = genero;
        this.direccion = direccion;
    }

    public String getNombrePersona() {
        return nombrePersona;
    }

    public String getCelular() {
        return celular;
    }

    public String getCedula() {
        return cedula;
    }

    public String getCorreo() {
        return correo;
    }

    public String getGenero() {
        return genero;
    }

    public String getDireccion() {
        return direccion;
    }



}
