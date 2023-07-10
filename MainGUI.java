import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
    private JScrollBar scrollBar1;

    private JTextField donanteField;
    private JTextField montoField;
    private JComboBox comboBoxSexo;
    private Albergue albergue = new Albergue();
    private Veterinaria veterinaria = new Veterinaria();
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
                String pacienteID = idpacienteField.getText();
                if (!albergue.animalYaExiste(albergue.buscarAnimal(pacienteID))) {
                    JOptionPane.showMessageDialog(MainGUI.this, "Error al agendar cita: El animal no pertenece al albergue", "Error", JOptionPane.ERROR_MESSAGE);
                }else{

                }
            }
        });

        donarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        verDonacionesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }



}
