import Exceptions.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    private JTextField razaTextField;
    private JButton nuestrosPequenosButton;
    private JComboBox comboBoxEspecie;
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
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }



    public MainGUI() {
        setTitle("Sistema de albergue");
        setSize(1900, 1000);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cambio en esta línea
        setContentPane(panelPrincipal);
        setResizable(false);
        pack(); // Ajusta automáticamente el tamaño del JFrame según su contenido
        setLocationRelativeTo(null); // Centra el JFrame en la pantalla
        botonInscribir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    conectar();
                    String nombreAnimal = nombreInscripcionlField.getText();
                    String inputFecha = fechaNacimientoField.getText();
                    //Id unico para cada animal ingresado
                    Random random = new Random();
                    int codigo = random.nextInt(9000) + 1000; // Genera un número aleatorio entre 1000 y 9999
                    String idAnimal = String.valueOf(codigo);

                    String especieTexto = (String)(comboBoxEspecie.getSelectedItem());
                    especieTexto = especieTexto.toUpperCase();
                    String colorAnimal = colorField.getText();
                    String pabellon = pabellonField.getText().toLowerCase();
                    String raza = razaTextField.getText();
                    LocalDate fechaLlegada = LocalDate.parse(inputFecha, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    LocalDate fechaActual = LocalDate.now();

                    String caracteresPermitidos = "^[a-zA-Z]+$";

                    if (nombreAnimal.isEmpty() || inputFecha.isEmpty() || idAnimal.isEmpty() || especieTexto.isEmpty() || colorAnimal.isEmpty() || pabellon.isEmpty() || raza.isEmpty()) {
                        throw new CampoVacioException("Algun campo esta vacio");
                    } else  if (fechaLlegada.isAfter(fechaActual)) {
                        throw new FechaInvalidaException("La fecha de nacimiento debe ser menor a la fecha actual");
                    } else if (!nombreAnimal.matches(caracteresPermitidos) || !colorAnimal.matches(caracteresPermitidos) || !pabellon.matches(caracteresPermitidos) || !raza.matches(caracteresPermitidos)){
                        throw new CaracteresNoValidosException("Caracteres incorrectos");
                    } else if (!pabellon.equals("a") && !pabellon.equals("b")) {
                        throw new NoExisteException("No existe el pabellon");
                    } else if (pabellon.equals("a") && especieTexto.equals("GATO")) {
                        throw new PabellonIncorrectoException("Pabellon incorrecto");
                    } else if (pabellon.equals("b") && especieTexto.equals("PERRO")) {
                        throw new PabellonIncorrectoException("Pabellon incorrecto");
                    } else if (!especieTexto.equals("PERRO") && !especieTexto.equals("GATO")) {
                        throw new NoExisteCategoriaException("No existe categoria");

                    } else{
                        Animal.Especie especieAnimal = Animal.Especie.valueOf(especieTexto);
                        Animal animalRegistrado = new Animal(nombreAnimal, fechaLlegada, idAnimal, colorAnimal, pabellon, especieAnimal, raza);
                        if(albergue.animalYaExiste(animalRegistrado)){
                            throw new YaExisteException("Animal ya existe en la base de datos");
                        }
                        albergue.agregarAnimal(animalRegistrado);
                        animalRegistrado.setId(idAnimal);
                        String sql = "INSERT INTO animales_rescatados VALUES (?, ?, ?, ?, ?, ?,?)";
                        PreparedStatement ps = connection.prepareStatement(sql);
                        ps.setString(1, nombreAnimal);
                        ps.setDate(2, Date.valueOf(fechaLlegada));
                        ps.setString(3, idAnimal);
                        ps.setString(4, colorField.getText());
                        ps.setString(5, pabellonField.getText().toUpperCase());
                        ps.setString(6, especieTexto);
                        ps.setString(7, razaTextField.getText());

                        int filasAfectadas = ps.executeUpdate();
                        if (filasAfectadas > 0) {
                            JOptionPane.showMessageDialog(null, "Animal inscrito correctamente");
                            nombreInscripcionlField.setText("");
                            fechaNacimientoField.setText("");
                            colorField.setText("");
                            pabellonField.setText("");
                            razaTextField.setText("");
                        } else {
                            JOptionPane.showMessageDialog(null, "Error al inscribir el animal", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                } catch (CampoVacioException | DateTimeParseException ex) {
                    JOptionPane.showMessageDialog(null, "Error al registrar animalito: Hay algun(os) campo(s) vacio(s) o un formato no es valido", "Error", JOptionPane.ERROR_MESSAGE);
                }  catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al inscribir el animal: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                } catch (FechaInvalidaException ex) {
                    JOptionPane.showMessageDialog(null, "La fecha de recibimiento no puede ser mayor a la actual: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } catch (CaracteresNoValidosException ex) {
                    JOptionPane.showMessageDialog(null, "Algun campo contiene datos inadmisibles: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } catch (NoExisteException ex) {
                    JOptionPane.showMessageDialog(null, "No existe el pabellon, eliga entre A o B", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (YaExisteException ex) {
                    JOptionPane.showMessageDialog(null, "Ya existe el animal", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (NoExisteCategoriaException ex) {
                    JOptionPane.showMessageDialog(null, "Solo aceptamos Gatos o Perros", "Error", JOptionPane.ERROR_MESSAGE);
                } catch (PabellonIncorrectoException ex) {
                    JOptionPane.showMessageDialog(null, "El animal no puede estar en este pabellon", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        adoptarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String idAdopcion = idAdopcionField.getText();
                    if (idAdopcionField.getText().isEmpty()) {
                        throw new CampoVacioException("Campo Vacio");
                    }
                    if (albergue.buscarAnimal(idAdopcion) == null) {
                        JOptionPane.showMessageDialog(MainGUI.this, "El animal no existe o ya fue adoptado", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        Responsable dialogResponsable = new Responsable();
                        dialogResponsable.setVisible(true);
                        dialogResponsable.setIdAdopcion(idAdopcion);
                        dialogResponsable.setAlbergue(albergue);
                    }
                } catch (CampoVacioException ex) {
                    JOptionPane.showMessageDialog(MainGUI.this, "El campo de ID esta vacio", "Error", JOptionPane.ERROR_MESSAGE);
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
                    JOptionPane.showMessageDialog(MainGUI.this, "Ya se han recolectado $"+cuenta.getTotal());
                    String sql = "INSERT INTO donaciones VALUES (?, ?, ?, ?, ?)";

                    PreparedStatement ps = connection.prepareStatement(sql);

                    ps.setString(1, cuenta.generarCodigo());
                    ps.setString(2, donante);
                    ps.setString(3, motivo);
                    ps.setDouble(4, monto);
                    ps.setDate(5, Date.valueOf(LocalDate.now()));

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
                frameEnfermedades.setAlbergue(albergue);
                frameEnfermedades.setVisible(true);
                frameEnfermedades.show();
            }
        });
        nuestrosPequenosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Animal> animales = albergue.getListaAnimalesRescatados();
                NuestrosPequenos pequenos = new NuestrosPequenos(animales);
                pequenos.setVisible(true);
            }
        });
    }



}
