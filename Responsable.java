import Exceptions.CedulaInvalidaException;
import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Responsable extends JFrame {

    private JPanel panelResponsable;
    private JTextField nombreResponsable;
    private JTextField cedulaField;
    private JTextField celularField;
    private JComboBox generoComboBox;
    private JTextField direccionField;
    private JTextField correoField;
    private JButton enviarButton;
    private Albergue albergue;
    private String idAdopcion;
    Connection connection;


    public void conectar(){
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/?user=root","root", "joaquincgp");
            connection.createStatement().execute("USE albergue");
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    public void setAlbergue(Albergue albergue) {
        this.albergue = albergue;
    }

    public void setIdAdopcion(String idAdopcion) {
        this.idAdopcion = idAdopcion;
    }

    public boolean validarCedulaEcuatoriana(String cedula) {
        int total = 0;
        int cantidadDigitos=10;
        int[] coeficientesMultiplicacion= {2,1,2,1,2,1,2,1,2};
        int nProvincias=24;
        int tercerDigito = 6;
        if (cedula.matches("[0-9]*")&&cedula.length() == cantidadDigitos ){
            int provincias = Integer.parseInt(cedula.charAt(0)+""+cedula.charAt(1));
            int digito3 = Integer.parseInt(cedula.charAt(2)+"");
            if ((provincias > 0 && provincias <= nProvincias)&& digito3 < tercerDigito) {
                int digitoVerificador = Integer.parseInt(cedula.charAt(9)+"");
                for (int i = 0; i < coeficientesMultiplicacion.length; i++) {
                    int valor = Integer.parseInt(coeficientesMultiplicacion[i]+"")*Integer.parseInt(cedula.charAt(i)+"");

                    total = valor >= 10? total + (valor-9) : total+valor;
                }
                int digitoVerificadorObtenido = total >= 10? (total%10) != 0 ? 10-(total%10): (total%10): total;
                if (digitoVerificadorObtenido==digitoVerificador){
                    return true;
                }

            }
            return false;

        }
        return false;


    }

    public Responsable() {
        setTitle("Responsable"); // Establece el título del JFrame
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cambio en esta línea
        setContentPane(panelResponsable); // Establece el panel principal del JFrame
        setResizable(false);
        pack(); // Ajusta automáticamente el tamaño del JFrame según su contenido
        setLocationRelativeTo(null); // Centra el JFrame en la pantalla
        enviarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    conectar();
                    String nombreAdoptante = nombreResponsable.getText(); //usar text field de Responsable
                    String celularAdoptante = celularField.getText();
                    String cedulaAdoptante = cedulaField.getText();
                    String correoAdoptante = correoField.getText();
                    String direccionAdoptante = direccionField.getText();
                    String generoAdoptante = (String) generoComboBox.getSelectedItem();

                    if(albergue.buscarUsuario(cedulaAdoptante)!= null){
                        JOptionPane.showMessageDialog(null,albergue.buscarUsuario(cedulaAdoptante).getNombrePersona()+" ya estas registrado! Puedes adoptar de nuevo!", "Bienvenido de nuevo", JOptionPane.INFORMATION_MESSAGE);
                    }

                    if (!validarCedulaEcuatoriana(cedulaAdoptante)) {
                        throw new CedulaInvalidaException("La cedula no tiene el formato permitido");
                    }
                    Animal animalAdoptado = albergue.buscarAnimal(idAdopcion);
                    Persona nuevoDuenio = new Persona(nombreAdoptante, celularAdoptante, cedulaAdoptante, correoAdoptante, generoAdoptante, direccionAdoptante );


                    animalAdoptado.setDuenio(nuevoDuenio);

                    JOptionPane.showMessageDialog(null,"Gracias a ti, "+nuevoDuenio.getNombrePersona()+", ahora "+ animalAdoptado.getNombreAnimal()+" encontro un nuevo hogar!", "Gracias", JOptionPane.INFORMATION_MESSAGE);
                    String sql = "INSERT INTO animales_adoptados VALUES (?, ?, ?, ?, ?)";



                    PreparedStatement ps = connection.prepareStatement(sql);
                    ps.setString(1, albergue.buscarAnimal(idAdopcion).getNombreAnimal());
                    ps.setString(2, albergue.buscarAnimal(idAdopcion).getId());
                    ps.setString(3, albergue.buscarAnimal(idAdopcion).getDuenio().getNombrePersona());
                    ps.setString(4, celularAdoptante);
                    ps.setString(5, cedulaAdoptante);

                    String sql2 = "DELETE FROM animales_rescatados WHERE id_animal = ?"; //Elimina los animales adoptados de la tabla de rescatados
                    PreparedStatement ps2 = connection.prepareStatement(sql2);
                    ps2.setString(1, idAdopcion);
                    albergue.adoptarAnimal(animalAdoptado);
                    albergue.getUsuarios().add(animalAdoptado.getDuenio());
                    int filasAfectadas2 = ps2.executeUpdate();
                    int filasAfectadas = ps.executeUpdate();
                    if (filasAfectadas > 0) {
                        JOptionPane.showMessageDialog(null, "Animal adoptado correctamente");
                        nombreResponsable.setText("");
                        celularField.setText("");
                        cedulaField.setText("");
                        correoField.setText("");
                        direccionField.setText("");
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al adoptar el animal", "Error", JOptionPane.ERROR_MESSAGE);
                    }


                } catch (CedulaInvalidaException ex) {
                    JOptionPane.showMessageDialog(null, "La cedula no es ecuatoriana", "Error", JOptionPane.ERROR_MESSAGE);
                }catch (SQLException ex){
                    JOptionPane.showMessageDialog(null, "Error al conectar base de datos", "Error", JOptionPane.ERROR_MESSAGE);

                }
            }
        });
    }

    public JTextField getNombreResponsable() {
        return nombreResponsable;
    }

    public JTextField getCedulaField() {
        return cedulaField;
    }

    public JTextField getCelularField() {
        return celularField;
    }

    public JPanel getPanelResponsable() {
        return panelResponsable;
    }

    public JComboBox getGeneroComboBox() {
        return generoComboBox;
    }

    public JTextField getDireccionField() {
        return direccionField;
    }

    public JTextField getCorreoField() {
        return correoField;
    }
}
