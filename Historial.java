import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

public class Historial {
    private List<String> enfermedades;
    private double peso;
    private List<String> vacunas;
    private LocalDate ultimaDesparasitacion;

    public Historial(List<String> enfermedades, double peso, List<String> vacunas, LocalDate ultimaDesparasitacion) {
        this.enfermedades = enfermedades;
        this.peso = peso;
        this.vacunas = vacunas;
        this.ultimaDesparasitacion = ultimaDesparasitacion;
    }

    public List<String> getEnfermedades() {
        return enfermedades;
    }

    public double getPeso() {
        return peso;
    }

    public List<String> getVacunas() {
        return vacunas;
    }

    public LocalDate getUltimaDesparasitacion() {
        return ultimaDesparasitacion;
    }


}
