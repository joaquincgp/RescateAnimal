import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class MainGUI extends JFrame {
    private JTextField nombreInscripcionlField;
    private JPanel panelPrincipal;
    private JTextField fechaNacimientoField;
    private JTextField idField;
    private JTextField especieField;
    private JTextField colorField;
    private JTextField pabellonField;
    private JButton botonInscribir;
    private JButton adoptarButton;
    private JComboBox comboBoxDoctor;
    private JTextField fechaCitaField;
    private JTextField idpacienteField;
    private JButton agendarButton;
    private JTextField idAdopcionField;
    private JTextField nombreResponsableField;
    private JButton donarButton;
    private JButton verDonacionesButton;
    private JTextField celularResponsableField;
    private JTextField cedulaField;
    private JTextField correoField;
    private JTextField direccionField;

    private JTextField donanteField;
    private JTextField montoField;
    private JComboBox comboBoxSexo;
    private JButton cancelarCitaButton;
    private JButton limpiarBaseDeDatosButton;
    private JButton historialesButton;
    private JTextField motivoTextField;
    private JButton nuestrosPequeñosButton;
    private JTextArea listaAnimales;
    private Albergue albergue = new Albergue();
    private Veterinaria veterinaria = new Veterinaria();
    private CuentaAhorros cuenta = new CuentaAhorros();

    Connection connection;
    //PreparedStatement ps;

    public void conectar(){
        try{
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/?user=root","root", "joaquincgp");
            connection.createStatement().execute("USE albergue");
            JOptionPane.showMessageDialog(MainGUI.this, "Conectado a la base de datos");
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
            MainGUI panel = new MainGUI();
            panel.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cambio en esta línea
            panel.setContentPane(panel.panelPrincipal);
            panel.setSize(1900, 1000);
            panel.setVisible(true);
    }



    public MainGUI() {
        botonInscribir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    conectar();
                    String nombreAnimal = nombreInscripcionlField.getText();
                    String inputFecha = fechaNacimientoField.getText();
                    String idAnimal = idField.getText();
                    String especieTexto = especieField.getText().toUpperCase();
                    String colorAnimal = colorField.getText();
                    String pabellon = pabellonField.getText().toLowerCase();
                    LocalDate fechaNacimiento = LocalDate.parse(inputFecha, DateTimeFormatter.ofPattern("dd/MM/yyyy"));


                    String sql = "INSERT INTO animales_rescatados VALUES (?, ?, ?, ?, ?, ?)";
                    PreparedStatement ps = connection.prepareStatement(sql);
                    ps.setString(1, nombreAnimal);
                    ps.setDate(2, Date.valueOf(fechaNacimiento));
                    ps.setString(3, idField.getText());
                    ps.setString(4, especieField.getText());
                    ps.setString(5, colorField.getText());
                    ps.setString(6, pabellonField.getText());

                    int filasAfectadas = ps.executeUpdate();
                    if (filasAfectadas > 0) {
                        JOptionPane.showMessageDialog(null, "Animal inscrito correctamente");
                        nombreInscripcionlField.setText("");
                        fechaNacimientoField.setText("");
                        idField.setText("");
                        especieField.setText("");
                        colorField.setText("");
                        pabellonField.setText("");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al inscribir el animal", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    if (nombreAnimal.isEmpty() || inputFecha.isEmpty() || idAnimal.isEmpty() || especieTexto.isEmpty() || colorAnimal.isEmpty() || pabellon.isEmpty()) {
                        throw new CampoVacioException("Algun campo esta vacio");
                    }
                    Animal.Especie especieAnimal = Animal.Especie.valueOf(especieTexto);
                    Animal animalRegistrado = new Animal(nombreAnimal, fechaNacimiento, idAnimal, colorAnimal, pabellon, especieAnimal);
                    albergue.agregarAnimal(animalRegistrado);
                } catch (CampoVacioException ex) {
                    JOptionPane.showMessageDialog(null, "Error al registrar animalito: Hay algun(os) campo(s) vacio(s) o un formato no es valido", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(null, "Error al registrar animalito: Formato de fecha incorrecto", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al inscribir el animal: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        });

        adoptarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    conectar();
                    String idAdopcion = idAdopcionField.getText();
                    String nombreAdoptante = nombreResponsableField.getText();
                    String celularAdoptante = celularResponsableField.getText();
                    String cedulaAdoptante = cedulaField.getText();
                    String correoAdoptante = correoField.getText();
                    String direccionAdoptante = direccionField.getText();
                    String generoAdoptante = (String)comboBoxSexo.getSelectedItem();

                    String sql = "INSERT INTO animales_adoptados VALUES (?, ?, ?, ?)";
                    PreparedStatement ps = connection.prepareStatement(sql);
                    ps.setString(1, albergue.buscarAnimal(idAdopcion).getNombreAnimal());
                    ps.setString(2, idAdopcion);
                    ps.setString(3, nombreAdoptante);
                    ps.setString(4, celularAdoptante);

                    int filasAfectadas = ps.executeUpdate();
                    if (filasAfectadas > 0) {
                        JOptionPane.showMessageDialog(null, "Animal adoptado correctamente");
                        nombreResponsableField.setText("");
                        celularResponsableField.setText("");
                        cedulaField.setText("");
                        correoField.setText("");
                        direccionField.setText("");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al adoptar el animal", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    if (idAdopcion.isEmpty() || nombreAdoptante.isEmpty() || cedulaAdoptante.isEmpty() || celularAdoptante.isEmpty() || correoAdoptante.isEmpty() ||direccionAdoptante.isEmpty()) {
                        throw new CampoVacioException("Algun campo esta vacio");
                    }else{
                        Animal animalAdoptado = albergue.buscarAnimal(idAdopcion);
                        Persona nuevoDuenio = new Persona(nombreAdoptante, celularAdoptante, cedulaAdoptante, correoAdoptante, generoAdoptante, direccionAdoptante );
                        albergue.adoptarAnimal(animalAdoptado);
                        animalAdoptado.setDuenio(nuevoDuenio);
                        JOptionPane.showMessageDialog(MainGUI.this,"Gracias a ti "+nuevoDuenio.getNombrePersona()+" ahora "+ animalAdoptado.getNombreAnimal()+" encontro un nuevo hogar!", "Gracias", JOptionPane.INFORMATION_MESSAGE);
                    }
                }catch (CampoVacioException ex){
                    JOptionPane.showMessageDialog(MainGUI.this, "Error al adoptar animalito: Hay algun(os) campo(s) vacio(s) o un formato no es valido", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        agendarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    conectar();
                    String pacienteID = idpacienteField.getText();
                    String fechaAgenda = fechaCitaField.getText();
                    LocalDate fechaCita = LocalDate.parse(fechaAgenda, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    String doctor = (String) comboBoxDoctor.getSelectedItem();
                    Doctor doctorAsignado = veterinaria.buscarDoctor(doctor);
                    Animal paciente = albergue.buscarAnimal(pacienteID);
                    String nombrePaciente = paciente.getNombreAnimal();

                    String sql = "INSERT INTO citas_programadas VALUES (?, ?, ?)";

                    PreparedStatement ps = connection.prepareStatement(sql);
                    ps.setDate(1, Date.valueOf(fechaCita));
                    ps.setString(2, nombrePaciente);
                    ps.setString(3, doctor);

                    int filasAfectadas = ps.executeUpdate();
                    if (filasAfectadas > 0) {
                        JOptionPane.showMessageDialog(null, "Cita agendada correctamente");
                        idpacienteField.setText("");
                        fechaCitaField.setText("");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al agendar cita", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                        if (doctorAsignado != null) {
                            if (!albergue.animalYaExiste(albergue.buscarAnimal(pacienteID))) {
                                JOptionPane.showMessageDialog(MainGUI.this, "Error al agendar cita: El animal no pertenece al albergue", "Error", JOptionPane.ERROR_MESSAGE);
                            } else {
                                veterinaria.programarCita(new Cita(fechaCita, paciente, doctorAsignado));
                                JOptionPane.showMessageDialog(MainGUI.this, paciente.getNombreAnimal() + ", tu cita con el " + doctorAsignado.getNombrePersona() + " esta agendada!", "Cita agendada", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                }catch (DateTimeParseException ex){
                    JOptionPane.showMessageDialog(MainGUI.this, "Error al agendar cita: Hay algun(os) campo(s) vacio(s) o un formato no es valido", "Error", JOptionPane.ERROR_MESSAGE);

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });
        cancelarCitaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String idPaciente = JOptionPane.showInputDialog(MainGUI.this, "Ingrese el ID del paciente");
                    Cita citaCancelar = veterinaria.buscarCitaPorIdPaciente(idPaciente);
                    if(idPaciente.isEmpty()){
                        throw new CampoVacioException("Campo vacio");
                    }else{
                        if (citaCancelar != null) {
                            veterinaria.cancelarCita(citaCancelar);
                        }
                    }
                }catch (CampoVacioException ex){
                    JOptionPane.showMessageDialog(MainGUI.this, "Error al cancelar cita: Hay algun(os) campo(s) vacio(s) o un formato no es valido", "Error", JOptionPane.ERROR_MESSAGE);

                }
            }

        });
        donarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    conectar();
                    String donante = donanteField.getText();
                    double monto = Double.parseDouble((montoField.getText()));
                    String motivo = motivoTextField.getText();
                    cuenta.registrarDonacion(new Donacion(donante, monto, motivo));

                    String sql = "INSERT INTO donaciones VALUES (?, ?, ?, ?)";

                    PreparedStatement ps = connection.prepareStatement(sql);

                    ps.setString(1, cuenta.generarCodigo());
                    ps.setString(2, donante);
                    ps.setString(3, motivo);
                    ps.setDouble(4, monto);
                    int filasAfectadas = ps.executeUpdate();
                    if (filasAfectadas > 0) {
                        JOptionPane.showMessageDialog(null, "Donacion recibida exitosamente");
                        donanteField.setText("");
                        montoField.setText("");
                        motivoTextField.setText("");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al recibir donacion", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(MainGUI.this, "Error al realizar donacion: Hay algun(os) campo(s) vacio(s) o un formato no es valido", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });


        limpiarBaseDeDatosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    conectar();
                    // Sentencia SQL para eliminar todos los registros de la tabla
                    String sql = "DELETE FROM animales_rescatados";
                    String sql2 = "DELETE FROM animales_adoptados";
                    String sql3 = "DELETE FROM citas_programadas";
                    String sql4 = "DELETE FROM donaciones";
                    PreparedStatement ps = connection.prepareStatement(sql);
                    PreparedStatement ps2 = connection.prepareStatement(sql2);
                    PreparedStatement ps3 = connection.prepareStatement(sql3);
                    PreparedStatement ps4 = connection.prepareStatement(sql4);


                    // Ejecutar la sentencia SQL de eliminación
                    int filasAfectadas = ps.executeUpdate();
                    int filasAfectadas2 = ps2.executeUpdate();
                    int filasAfectadas3 = ps3.executeUpdate();
                    int filasAfectadas4 = ps4.executeUpdate();

                    if (filasAfectadas > 0 || filasAfectadas2 > 0 || filasAfectadas3 > 0 || filasAfectadas4 > 0) {
                        JOptionPane.showMessageDialog(null, "Tablas limpiadas correctamente");
                    } else {
                        JOptionPane.showMessageDialog(null, "Error al limpiar las tablas. Las tablas no tienen datos!", "Error", JOptionPane.ERROR_MESSAGE);
                    }


                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al limpiar la tabla: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        historialesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Enfermedades frameEnfermedades = new Enfermedades();
                frameEnfermedades.setVisible(true);
                frameEnfermedades.show();
            }
        });
    }



}
