
import java.time.LocalDate;
import java.util.Random;

public class Donacion {
    private String donante;
    private double monto;
    private String codigo; //se genera uno aleatorio
    private LocalDate fechaDonacion;

    public Donacion(String donante, double monto) {
        this.donante = donante;
        this.monto = monto;
        this.fechaDonacion = LocalDate.now();
        codigo = generarCodigoAleatorio();
    }

    public String getDonante() {
        return donante;
    }

    public double getMonto() {
        return monto;
    }

    public LocalDate getFecha() {
        return fechaDonacion;
    }
    public String getCodigo(){
        return codigo;
    }

    private String generarCodigoAleatorio() {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < 5; i++) {
            int cifra = random.nextInt(10);
            sb.append(cifra);
        }

        codigo = sb.toString();
        return codigo;
    }
}

