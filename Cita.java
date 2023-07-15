import java.time.LocalDate;

public class Cita {
    private LocalDate fechaAgendada;
    private Animal paciente;
    private Doctor doctorAsignado;
    private String hora;

    public Cita(LocalDate fechaAgendada, String hora, Animal paciente, Doctor doctorAsignado) {
        this.fechaAgendada = fechaAgendada;
        this.paciente = paciente;
        this.doctorAsignado = doctorAsignado;
        this.hora= hora;
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

    public String getHora() {
        return hora;
    }
}
