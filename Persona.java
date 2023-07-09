public class Persona {
    private String nombrePersona;
    private String celular;
    private String cedula;
    private String correo;
    private String genero;
    private String estadoCivil;
    private String direccion;
    private Animal adopcion;


    public Persona(String nombrePersona, String celular, String cedula, String correo, String genero,
                   String estadoCivil, String direccion) {
        this.nombrePersona = nombrePersona;
        this.celular = celular;
        this.cedula = cedula;
        this.correo = correo;
        this.genero = genero;
        this.estadoCivil = estadoCivil;
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

    public String getEstadoCivil() {
        return estadoCivil;
    }

    public String getDireccion() {
        return direccion;
    }

    public Animal getAdopcion() {
        return adopcion;
    }


}
