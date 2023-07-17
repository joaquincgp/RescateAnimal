import java.time.LocalDate;

public class Cita {
    private LocalDate fechaAgendada;
    private Animal paciente;
    private Doctor doctorAsignado;
    private String hora;
    private boolean atendido;

    public Cita(LocalDate fechaAgendada, String hora, Animal paciente, Doctor doctorAsignado) {
        this.fechaAgendada = fechaAgendada;
        this.paciente = paciente;
        this.doctorAsignado = doctorAsignado;
        this.hora= hora;
        atendido = false;
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

    public boolean fueAtendido() {
        return atendido;
    }

    public void setAtendido(boolean atendido) {
        this.atendido = atendido;
    }

    public void setPaciente(Animal paciente) {
        this.paciente = paciente;
    }
}
