import java.util.ArrayList;
import java.util.List;

public class Veterinaria {
    /**
     * Lista de doctores que atienden
     */
    private List<Doctor> doctores;
    /**
     * Lista de citas reservadas
     */
    private List<Cita> citas;
    public Veterinaria() {
        doctores = new ArrayList<>();
        citas = new ArrayList<>();
    }

    /**
     * Agendar cita y agregar al array
     * @param cita Detalles de la cita
     */
    public void programarCita(Cita cita) {
        citas.add(cita);
    }

    public void cancelarCita(Cita cita) {
        citas.remove(cita);
    }
    public void agregarVeterinario(Doctor doctor) {
        doctores.add(doctor);
    }


    public void verCitas() {
        for (Cita cita : citas) {
            System.out.println("Información de la cita:");
            System.out.println("Fecha: " + cita.getFechaAgendada());
            System.out.println("Animal: " + cita.getPaciente().getNombreAnimal());
            System.out.println("Dueño: " + cita.getPaciente().getDuenio().getNombrePersona());
            System.out.println("Veterinario asignado: " + cita.getDoctorAsignado().getNombrePersona());
            System.out.println("---------------------------");
        }
    }

}
