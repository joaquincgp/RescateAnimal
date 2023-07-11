import java.time.LocalDate;

public class Animal {
    public enum Especie{
        /**
         * Representa si el animal es gato
         */
        GATO,
        /**
         * Representa si el animal es gato
         */
        PERRO
    }
    private String nombreAnimal;
    private LocalDate fechaNacimiento;
    private Historial perfilMedico;
    private String id;
    private String color;
    private String pabellon;
    private Persona duenio;
    private Especie especie;

    public Animal(String nombreAnimal, LocalDate fechaNacimiento, String id, String color, String pabellon,Especie especie) {
        this.nombreAnimal = nombreAnimal;
        this.fechaNacimiento = fechaNacimiento;
        this.id = id;
        this.color = color;
        this.pabellon = pabellon;
        this.especie = especie;
    }

    public String getNombreAnimal() {
        return nombreAnimal;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public Historial getPerfilMedico() {
        return perfilMedico;
    }

    public void setPerfilMedico(Historial perfilMedico) {
        this.perfilMedico = perfilMedico;
    }

    public String getId() {
        return id;
    }

    public String getColor() {
        return color;
    }

    public String getPabellon() {
        return pabellon;
    }

    public Persona getDuenio() {
        return duenio;
    }

    public Especie getEspecie() {
        return especie;
    }

    public void setDuenio(Persona duenio) {
        this.duenio = duenio;
    }


}
