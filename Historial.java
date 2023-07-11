import java.time.LocalDate;
import java.util.List;

public class Historial {
    private List<String> enfermedades;
    private String peso;
    private List<String> vacunas;

    public Historial(List<String> enfermedades, String peso, List<String> vacunas) {
        this.enfermedades = enfermedades;
        this.peso = peso;
        this.vacunas = vacunas;
    }

    public List<String> getEnfermedades() {
        return enfermedades;
    }

    public String getPeso() {
        return peso;
    }

    public List<String> getVacunas() {
        return vacunas;
    }




}
