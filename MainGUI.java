import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private Albergue albergue = new Albergue();
    private Veterinaria veterinaria = new Veterinaria();
    private CuentaAhorros cuenta = new CuentaAhorros();
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
                    String nombreAnimal = nombreInscripcionlField.getText();
                    String inputFecha = fechaNacimientoField.getText();
                    String idAnimal = idField.getText();
                    String especieTexto = especieField.getText().toUpperCase();
                    String colorAnimal = colorField.getText();
                    String pabellon = pabellonField.getText().toLowerCase();

                    if (nombreAnimal.isEmpty() || inputFecha.isEmpty() || idAnimal.isEmpty() || especieTexto.isEmpty() || colorAnimal.isEmpty() || pabellon.isEmpty()) {
                        throw new CampoVacioException("Algun campo esta vacio");
                    }
                    LocalDate fechaNacimiento = LocalDate.parse(inputFecha, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    Animal.Especie especieAnimal = Animal.Especie.valueOf(especieTexto);

                    Animal animalRegistrado = new Animal(nombreAnimal, fechaNacimiento, idAnimal, colorAnimal, pabellon, especieAnimal);
                    albergue.agregarAnimal(animalRegistrado);
                } catch (CampoVacioException ex) {
                    JOptionPane.showMessageDialog(null, "Error al registrar animalito: Hay algun(os) campo(s) vacio(s) o un formato no es valido", "Error", JOptionPane.ERROR_MESSAGE);
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



    }



}
