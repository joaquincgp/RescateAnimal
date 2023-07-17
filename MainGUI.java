import Exceptions.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
    private JButton atenderCitaButton;
    private JButton limpiarBaseDeDatosButton;
    private JButton historialesButton;
    private JTextField motivoTextField;
    private JTextField razaTextField;
    private JButton nuestrosPequenosButton;
    private JComboBox comboBoxEspecie;
    private JComboBox horaComboBox;
    private JTextPane panelLista;
    private JTextArea listaAnimales;
    private JScrollPane scrollPaneAnimales;
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
                    }
                    else{
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
                    nombreInscripcionlField.setText("");
                    fechaNacimientoField.setText("");
                    colorField.setText("");
                    pabellonField.setText("");
                    razaTextField.setText("");
                }  catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al inscribir el animal: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                } catch (FechaInvalidaException ex) {
                    JOptionPane.showMessageDialog(null, "La fecha de recibimiento no puede ser mayor a la actual: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    fechaNacimientoField.setText("");
                } catch (CaracteresNoValidosException ex) {
                    JOptionPane.showMessageDialog(null, "Algun campo contiene datos inadmisibles: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    nombreInscripcionlField.setText("");
                    fechaNacimientoField.setText("");
                    colorField.setText("");
                    pabellonField.setText("");
                    razaTextField.setText("");
                } catch (NoExisteException ex) {
                    JOptionPane.showMessageDialog(null, "No existe el pabellon, eliga entre A o B", "Error", JOptionPane.ERROR_MESSAGE);
                    pabellonField.setText("");
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
                        throw new NoExisteException("Animal no existe");
                    } else {
                        Responsable dialogResponsable = new Responsable();
                        dialogResponsable.setVisible(true);
                        dialogResponsable.setIdAdopcion(idAdopcion);
                        dialogResponsable.setAlbergue(albergue);
                    }
                } catch (CampoVacioException ex) {
                    JOptionPane.showMessageDialog(MainGUI.this, "El campo de ID esta vacio", "Error", JOptionPane.ERROR_MESSAGE);
                    idAdopcionField.setText("");
                } catch (NoExisteException ex) {
                    JOptionPane.showMessageDialog(MainGUI.this, "El animal no existe o ya fue adoptado", "Error", JOptionPane.ERROR_MESSAGE);
                    idAdopcionField.setText("");
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
                    String horaCita = (String) horaComboBox.getSelectedItem();
                    String especialidad = (String) comboBoxDoctor.getSelectedItem();
                    Doctor doctorAsignado = veterinaria.buscarDoctorEspecialidad(especialidad);
                    Animal paciente = albergue.buscarAnimal(pacienteID);
                    String nombrePaciente = paciente.getNombreAnimal();
                    Cita nuevaCita = new Cita(fechaCita,horaCita,paciente, doctorAsignado);
                    String sql = "INSERT INTO citas_programadas VALUES (?, ?, ?, ?, ?)";

                    if(fechaCita.isBefore(LocalDate.now())){
                        throw new FechaInvalidaException("La fecha de la cita no esta disponible. Ingresa una fecha valida");
                    }
                    if (veterinaria.existeCita(fechaCita, doctorAsignado.getNombrePersona(), horaCita)) {
                        throw new YaExisteException("Ya existe una cita programada para este paciente en ese horario");
                    }


                    if (!albergue.animalYaExiste(albergue.buscarAnimal(pacienteID))) {
                        throw new NoExisteException("El animal no se aloja en el albergue");
                    } else {
                        veterinaria.programarCita(nuevaCita);
                        JOptionPane.showMessageDialog(MainGUI.this, paciente.getNombreAnimal() + ", tu cita con el " + doctorAsignado.getNombrePersona() + " para las "+horaCita+ " del "+ fechaCita, "Cita agendada", JOptionPane.INFORMATION_MESSAGE);
                    }

                    PreparedStatement ps = connection.prepareStatement(sql);
                    ps.setDate(1, Date.valueOf(fechaCita));
                    ps.setString(2, nombrePaciente);
                    ps.setString(3, doctorAsignado.getNombrePersona());
                    ps.setString(4, doctorAsignado.getEspecialidad());
                    ps.setString(5, horaCita);


                    int filasAfectadas = ps.executeUpdate();
                    if (filasAfectadas > 0) {
                        JOptionPane.showMessageDialog(null, "Cita agendada correctamente");
                        idpacienteField.setText("");
                        fechaCitaField.setText("");
                    } else {
                        throw new SQLException("Error al agendar cita");
                    }
                }catch (DateTimeParseException ex){
                    JOptionPane.showMessageDialog(MainGUI.this, "Error al agendar cita: Hay algun(os) campo(s) vacio(s) o un formato no es valido", "Error", JOptionPane.ERROR_MESSAGE);

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Error al agendar cita", "Error", JOptionPane.ERROR_MESSAGE);

                } catch (NoExisteException ex) {
                    JOptionPane.showMessageDialog(MainGUI.this, "Error al agendar cita: El animal no pertenece al albergue", "Error", JOptionPane.ERROR_MESSAGE);
                    idpacienteField.setText("");
                } catch (FechaInvalidaException ex) {
                    JOptionPane.showMessageDialog(MainGUI.this, "Error al agendar cita: La fecha tiene que ser el dia de hoy o un dia posterior", "Error", JOptionPane.ERROR_MESSAGE);
                    fechaCitaField.setText("");
                }
                catch (YaExisteException ex) {
                    JOptionPane.showMessageDialog(MainGUI.this, "Ya existe una cita programada en este horario", "Error", JOptionPane.ERROR_MESSAGE);
                    idpacienteField.setText("");
                    fechaCitaField.setText("");
                }

            }
        });
        atenderCitaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    conectar();
                    String idPaciente = JOptionPane.showInputDialog(MainGUI.this, "Ingrese el ID del paciente");
                    Animal animalHistorial = albergue.buscarAnimal(idPaciente);
                    Cita citaCancelar = veterinaria.buscarCitaPorIdPaciente(idPaciente);
                    citaCancelar.setPaciente(animalHistorial);
                    if(idPaciente.isEmpty()){
                        throw new CampoVacioException("Campo vacio");
                    }else {
                        if (citaCancelar != null) {
                            AtenderCita frameEnfermedades = new AtenderCita(idPaciente, citaCancelar);
                            frameEnfermedades.setAlbergue(albergue);
                            frameEnfermedades.setVisible(true);
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
                    if(albergue.buscarUsuario(donante) == null){
                        throw new NoExisteException("Usuario no puede donar. No esta registrado");
                    }
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
                } catch (NoExisteException ex) {
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
                JTextArea listaCitas = new JTextArea();
                List<Cita> citas = veterinaria.getCitas();
                StringBuilder listaCitasText = new StringBuilder();

                for (Cita cita : citas) {
                    listaCitasText.append("Paciente: ").append(cita.getPaciente().getNombreAnimal()).append("\n");
                    listaCitasText.append("Fecha: ").append(cita.getFechaAgendada()).append("\n");
                    listaCitasText.append("Hora: ").append(cita.getHora()).append("\n");
                    listaCitasText.append("Doctor asignado: ").append(cita.getDoctorAsignado().getNombrePersona()).append("\n");
                    listaCitasText.append("Especialidad: ").append(cita.getDoctorAsignado().getEspecialidad()).append("\n");
                    if(cita.fueAtendido()){
                        listaCitasText.append("Atendida: ").append("Si").append("\n");
                    }else{
                        listaCitasText.append("Atendida: ").append("No").append("\n");
                    }
                    listaCitasText.append("----------------------------------\n");
                }
                listaCitas.setText(listaCitasText.toString());
                JScrollPane scrollPane = new JScrollPane(listaCitas);
                scrollPane.setPreferredSize(new Dimension(400, 300));
                JOptionPane.showMessageDialog(MainGUI.this, scrollPane, "Lista de Citas", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        nuestrosPequenosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextArea listaAnimalesTextArea = new JTextArea(); // Reemplaza "nombreDelTextAreaExistente" con el nombre del JTextArea creado en el diseñador de Swing
                List<Animal> animalesRescatados = albergue.getListaAnimalesRescatados();
                StringBuilder listaAnimalesText = new StringBuilder();

                for (Animal animal : animalesRescatados) {
                    listaAnimalesText.append("Nombre: ").append(animal.getNombreAnimal()).append("\n");
                    listaAnimalesText.append("Especie: ").append(animal.getEspecie()).append("\n");
                    listaAnimalesText.append("Fecha de llegada: ").append(animal.getFechaLlegada()).append("\n");
                    listaAnimalesText.append("ID: ").append(animal.getId()).append("\n");
                    listaAnimalesText.append("Color: ").append(animal.getColor()).append("\n");
                    listaAnimalesText.append("Pabellón: ").append(animal.getPabellon()).append("\n");
                    listaAnimalesText.append("Raza: ").append(animal.getRaza()).append("\n");
                    listaAnimalesText.append("----------------------------------\n");
                }
                listaAnimalesTextArea.setText(listaAnimalesText.toString());
                JScrollPane scrollPane = new JScrollPane(listaAnimalesTextArea); // Reemplaza "scrollPaneAnimales" con el nombre del JScrollPane creado en el diseñador de Swing
                scrollPane.setPreferredSize(new Dimension(400, 300));

                JOptionPane.showMessageDialog(MainGUI.this, scrollPane, "Lista de Animales Rescatados", JOptionPane.INFORMATION_MESSAGE);
            }
        });



    }



}
