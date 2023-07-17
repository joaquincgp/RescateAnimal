import Exceptions.CampoVacioException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AtenderCita extends JFrame {
    private JPanel panelEnfermedades;
    private JTextField enfermedadesField1;
    private JTextField pesoTextField;
    private JButton ingresarButton;
    private JTextField vacunasTextField;
    private JTextField desparacitacionTextField;
    private JTextField idHistorialTextField;
    private Albergue albergue;
    private Veterinaria veterinaria;
    Connection connection;

    public void setAlbergue(Albergue albergue) {
        this.albergue = albergue;
    }

    public void conectar(){
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/?user=root","root", "joaquincgp");
            connection.createStatement().execute("USE albergue");
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

public AtenderCita(String idPaciente, Cita citaCancelar) {
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
                conectar();
                String enfermedad = enfermedadesField1.getText();
                String pesoString = pesoTextField.getText();
                String vacuna = vacunasTextField.getText();

                List<String> enfermedades = new ArrayList<>();
                List<String> vacunas = new ArrayList<>();
                Animal animalHistorial = albergue.buscarAnimal(idPaciente);

                if (enfermedad.isEmpty() || vacuna.isEmpty()  || pesoString.isEmpty()) {
                    throw new CampoVacioException("Algun campo esta vacio o el formato es incorrecto");
                }else{
                    enfermedades.add(enfermedad);
                    vacunas.add(vacuna);
                    Historial historialNuevo = new Historial(enfermedades, pesoString, vacunas);
                    animalHistorial.setPerfilMedico(historialNuevo);
                    dispose();
                    String sql = "DELETE FROM citas_programadas WHERE nombre_paciente = ?";
                    PreparedStatement ps = connection.prepareStatement(sql);
                    ps.setString(1, animalHistorial.getNombreAnimal());
                    int filasAfectadas = ps.executeUpdate();
                    if (filasAfectadas > 0) {
                        JOptionPane.showMessageDialog(null,"Cita atendida");
                    } else {
                        throw new SQLException("Error al eliminar la cita de la base de datos");
                    }
                    citaCancelar.setAtendido(true);
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch(CampoVacioException ex){
                enfermedadesField1.setText("");
                pesoTextField.setText("");
                vacunasTextField.setText("");
                JOptionPane.showMessageDialog(null, "Algun campo esta vacio");

            }
        }
    });

}

    public JTextField getEnfermedadesField1() {
        return enfermedadesField1;
    }

    public JTextField getPesoTextField() {
        return pesoTextField;
    }

    public JTextField getVacunasTextField() {
        return vacunasTextField;
    }

    public JTextField getDesparacitacionTextField() {
        return desparacitacionTextField;
    }
}
