import java.util.List;

public class Historial {
    private List<String> enfermedades;
    private double peso;
    private List<String> medicamentos;
    private List<Vacuna> vacunas;
    private boolean desparasitacionAlDia;
    private Cita ultimaCita;


    public List<String> getEnfermedades() {
        return enfermedades;
    }

    public double getPeso() {
        return peso;
    }

    public List<String> getMedicamentos() {
        return medicamentos;
    }

    public List<Vacuna> getVacunas() {
        return vacunas;
    }

    public boolean isDesparasitacionAlDia() {
        return desparasitacionAlDia;
    }

    public Cita getUltimaCita() {
        return ultimaCita;
    }
}
