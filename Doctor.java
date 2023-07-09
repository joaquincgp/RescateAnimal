public class Doctor extends Persona {
    private String especialidad;

    public Doctor(String nombrePersona, String celular, String cedula, String correo, String genero, String estadoCivil, String direccion, String especialidad) {
        super(nombrePersona, celular, cedula, correo, genero, estadoCivil, direccion);
        this.especialidad = especialidad;
    }
}
