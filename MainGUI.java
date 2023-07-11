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
    private JButton nuestrosPequeñosButton;
    private JTextArea listaAnimales;
    //DefaultListModel mod = new DefaultListModel<>();
    private JList lista;
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



    /*public void insertar() throws SQLException {
        conectar();
        ps = connection.prepareStatement("INSERT INTO animales_rescatados VALUES (?, ?, ?, ?, ?, ?)");
        ps.setString(1, nombreInscripcionlField.getText());
        ps.setString(2, String.valueOf(Date.valueOf(fechaNacimientoField.getText())));
        ps.setString(3, idField.getText());
        ps.setString(4, colorField.getText());
        ps.setString(5, pabellonField.getText());
        ps.setString(6, especieField.getText());

        if (ps.executeUpdate() > 0) {
            mod.removeAllElements();

            nombreInscripcionlField.setText("");
            fechaNacimientoField.setText("");
            idField.setText("");
            colorField.setText("");
            pabellonField.setText("");
            especieField.setText("");
        }
    }

     */
    public static void main(String[] args) {
            MainGUI panel = new MainGUI();
            panel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
                    String idAdopcion = idAdopcionField.getText();
                    String nombreAdoptante = nombreResponsableField.getText();
                    String celularAdoptante = celularResponsableField.getText();
                    String cedulaAdoptante = cedulaField.getText();
                    String correoAdoptante = correoField.getText();
                    String direccionAdoptante = direccionField.getText();
                    String generoAdoptante = (String)comboBoxSexo.getSelectedItem();
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
                }
            }
        });
        agendarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String pacienteID = idpacienteField.getText();
                    String fechaAgenda = fechaCitaField.getText();
                    LocalDate fechaCita = LocalDate.parse(fechaAgenda, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    String doctor = (String) comboBoxDoctor.getSelectedItem();
                    Doctor doctorAsignado = veterinaria.buscarDoctor(doctor);
                    Animal paciente = albergue.buscarAnimal(pacienteID);
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
                    String donante = donanteField.getText();
                    double monto = Double.parseDouble(montoField.getText());
                    cuenta.registrarDonacion(new Donacion(donante, monto));
                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(MainGUI.this, "Error al realizar donacion: Hay algun(os) campo(s) vacio(s) o un formato no es valido", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });


        nuestrosPequeñosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder sb = new StringBuilder();
                for (Animal animal : albergue.getListaAnimalesRescatados()) {
                    sb.append("Nombre: ").append(animal.getNombreAnimal()).append("\n");
                    sb.append("Especie: ").append(animal.getEspecie()).append("\n");
                    sb.append("ID: ").append(animal.getId()).append("\n");
                    sb.append("---------------------------\n");
                }
                listaAnimales.setText(sb.toString());
            }
        });
    }



}
