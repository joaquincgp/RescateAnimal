import Exceptions.CampoVacioException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Enfermedades extends JFrame {
    private JPanel panelEnfermedades;
    private JTextField enfermedadesField1;
    private JTextField pesoTextField;
    private JButton ingresarButton;
    private JTextField vacunasTextField;
    private JTextField desparacitacionTextField;
    private JTextField idHistorialTextField;
    private Albergue albergue;
    public void setAlbergue(Albergue albergue) {
        this.albergue = albergue;
    }


public Enfermedades() {
    setTitle("Perfil medico"); // Establece el título del JFrame
    setSize(400, 600);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cambio en esta línea
    setContentPane(panelEnfermedades); // Establece el panel principal del JFrame
    setResizable(false);
    pack(); // Ajusta automáticamente el tamaño del JFrame según su contenido
    setLocationRelativeTo(null); // Centra el JFrame en la pantalla


    ingresarButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String idHistorial = idHistorialTextField.getText();
                String enfermedad = enfermedadesField1.getText();
                String pesoString = pesoTextField.getText();
                String vacuna = vacunasTextField.getText();

                List<String> enfermedades = new ArrayList<>();
                List<String> vacunas = new ArrayList<>();
                Animal animalHistorial = albergue.buscarAnimal(idHistorial);


                if (idHistorial.isEmpty() || enfermedad.isEmpty() || vacuna.isEmpty()  || pesoString.isEmpty()) {
                    throw new CampoVacioException("Algun campo esta vacio o el formato es incorrecto");
                }else{
                    enfermedades.add(enfermedad);
                    vacunas.add(vacuna);
                    Historial historialNuevo = new Historial(enfermedades, pesoString, vacunas);
                    animalHistorial.setPerfilMedico(historialNuevo);
                    JOptionPane.showMessageDialog(Enfermedades.this, "La informacion de "+animalHistorial.getNombreAnimal()+" fue actualizada correctamente");
                }
            }catch(CampoVacioException ex){
                JOptionPane.showMessageDialog(null, "Algun campo esta vacio");

            }
        }
    });
}
}
