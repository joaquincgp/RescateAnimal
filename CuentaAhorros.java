import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CuentaAhorros {
    private List<Donacion> donaciones;
    private double total;
    public CuentaAhorros(){
        total = 0.0;
        donaciones = new ArrayList<>();
    }

    public void registrarDonacion(Donacion donacion){
        donaciones.add(donacion);
        total += donacion.getMonto();
        JOptionPane.showMessageDialog(null, "Gracias "+donacion.getDonante()+". Tu donacion ha sido aceptada");
    }



    public List<Donacion> getDonaciones() {
        return donaciones;
    }

    public String generarCodigo() {
        Random random = new Random();
        int codigo = random.nextInt(9000) + 1000; // Genera un número aleatorio entre 1000 y 9999
        return String.valueOf(codigo);
    }
}

