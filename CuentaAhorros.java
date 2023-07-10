import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

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

    public void cancelarDonacion(String codigo){
        for(Donacion d: donaciones){
            if(codigo == d.getCodigo()){
                donaciones.remove(d);
                System.out.println("Donacion numero "+d.getCodigo()+ " eliminada");
            }else{
                System.out.println("No se encontro la donacion con el codigo proporcionado");
            }
        }

    }

    public List<Donacion> getDonaciones() {
        return donaciones;
    }

    public void verDonaciones(){
        System.out.println("----Donaciones----");
        for(Donacion d: donaciones){
            System.out.println("Donante: "+d.getDonante()+"\n Monto: "+d.getMonto());
        }
    }
}

