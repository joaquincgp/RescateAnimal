import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    private Albergue albergue2 = new Albergue();



public Enfermedades() {
    setTitle("Enfermedades"); // Establece el título del JFrame
    setSize(400, 600);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cambio en esta línea
    setContentPane(panelEnfermedades); // Establece el panel principal del JFrame

    pack(); // Ajusta automáticamente el tamaño del JFrame según su contenido
    setLocationRelativeTo(null); // Centra el JFrame en la pantalla
    ingresarButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String idHistorial = idHistorialTextField.getText();
                String enfermedad = enfermedadesField1.getText();
                String pesoString = pesoTextField.getText();
                double peso = Double.parseDouble(pesoString);
                String vacuna = vacunasTextField.getText();
                String fechaDesp = desparacitacionTextField.getText();
                LocalDate fechaUltimaDesparasitacion = LocalDate.parse(fechaDesp, DateTimeFormatter.ofPattern("dd/MM/yyyy"));

                List<String> enfermedades = new ArrayList<>();
                List<String> vacunas = new ArrayList<>();

                if (idHistorial.isEmpty() || enfermedad.isEmpty() || pesoString.isEmpty() || vacuna.isEmpty() || fechaDesp.isEmpty()) {
                    throw new CampoVacioException("Algun campo esta vacio");
                }else{
                    enfermedades.add(enfermedad);
                    vacunas.add(vacuna);
                    Animal animalHistorial = albergue2.buscarAnimal(idHistorial);
                    Historial historialNuevo = new Historial(enfermedades, peso, vacunas, fechaUltimaDesparasitacion);
                    animalHistorial.setPerfilMedico(historialNuevo);
                    JOptionPane.showMessageDialog(Enfermedades.this, "La informacion de "+animalHistorial.getNombreAnimal()+" fue actualizada correctamente");
                }
            }catch(CampoVacioException ex){
                throw new RuntimeException(ex);
            }
        }
    });
}
}
