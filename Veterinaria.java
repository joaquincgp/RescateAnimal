import javax.swing.*;
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

        doctores.add(new Doctor("Dr. Juan Pérez", "092234890", "3676378162","juanperez@hotmail.com","Masculino","Quito","Especialidad en Medicina Interna Canina"));
        doctores.add(new Doctor("Dra. María Gómez", "0987654321", "589364242","mariagomez@hotmail.com","Femenino","Quito", "Especialidad en Dermatología Felina"));
        doctores.add(new Doctor("Dr. Carlos Rodríguez", "0992673633", "0832633232", "carlorodri@hotmail.com","Masculino","Quito","Especialidad en Ortopedia y Traumatología Canina"));
        doctores.add(new Doctor("Dra. Laura Vargas", "0987654321", "7326837232", "dralauvargas@hotmail.com","Femenino","Quito","Especialidad en Cardiología Felina"));
        doctores.add(new Doctor("Dr. Andrés López", "094321098", "3784728963", "docandres@hotmail.com","Masculino","Quito","Especialidad en Odontología Canina"));
    }



    /**
     * Agendar cita y agregar al array
     * @param cita Detalles de la cita
     */
    public void programarCita(Cita cita) {
        citas.add(cita);
    }

    public void cancelarCita(Cita cita) {
        if(buscarCitaPorIdPaciente(cita.getPaciente().getId()) != null){
            citas.remove(cita);
            JOptionPane.showMessageDialog(null,"La cita con "+cita.getPaciente().getNombreAnimal()+" para el "+cita.getFechaAgendada()+ " se cancelo");
        }else{
            JOptionPane.showMessageDialog(null,"No hay citas asociadas con este animal");

        }

    }

    public List<Doctor> getDoctores() {
        return doctores;
    }
    public Doctor buscarDoctor(String nombreDoctor) {
        for (Doctor doctor : doctores) {
            if (doctor.getNombrePersona().equals(nombreDoctor)) {
                return doctor;
            }
        }
        return null; // Retornar null si no se encuentra el doctor
    }

    public Cita buscarCitaPorIdPaciente(String idPaciente) {
        Cita encontrada = null;

        for (Cita cita : citas) {
            String idPacienteCita = cita.getPaciente().getId();

            if (idPacienteCita.equals(idPaciente)) {
                encontrada = cita;
            }
        }
        return encontrada;
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

    public List<Cita> getCitas() {
        return citas;
    }
}
