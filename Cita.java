import java.time.LocalDate;

public class Cita {
    private LocalDate fechaAgendada;
    private Animal paciente;
    private Doctor doctorAsignado;

    public Cita(LocalDate fechaAgendada, Animal paciente, Doctor doctorAsignado) {
        this.fechaAgendada = fechaAgendada;
        this.paciente = paciente;
        this.doctorAsignado = doctorAsignado;
    }

    public LocalDate getFechaAgendada() {
        return fechaAgendada;
    }

    public Animal getPaciente() {
        return paciente;
    }

    public Doctor getDoctorAsignado() {
        return doctorAsignado;
    }
}
