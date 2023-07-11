import java.time.LocalDate;

public class Vacuna {
    private String nombreVacuna;
    private LocalDate fechaUltimaDosis;

    public Vacuna(String nombreVacuna, LocalDate fechaUltimaDosis) {
        this.nombreVacuna = nombreVacuna;
        this.fechaUltimaDosis = fechaUltimaDosis;
    }

    public LocalDate getFechaUltimaDosis() {
        return fechaUltimaDosis;
    }

    public String getNombreVacuna() {
        return nombreVacuna;
    }
}
