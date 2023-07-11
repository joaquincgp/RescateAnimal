
import java.time.LocalDate;
import java.util.Random;

public class Donacion {
    private String donante;
    private double monto;
    private LocalDate fechaDonacion;
    private String motivo;
    public Donacion(String donante, double monto, String  motivo) {
        this.donante = donante;
        this.monto = monto;
        this.fechaDonacion = LocalDate.now();
        this.motivo =motivo;
    }

    public String getDonante() {
        return donante;
    }

    public double getMonto() {
        return monto;
    }

    public LocalDate getFechaDonacion() {
        return fechaDonacion;
    }

    public String getMotivo() {
        return motivo;
    }




}

