import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Responsable extends JFrame {

    private JPanel panelResponsable;
    private JTextField nombreResponsable;
    private JTextField cedulaField;
    private JTextField celularField;
    private JComboBox generoComboBox;
    private JTextField direccionField;
    private JTextField correoField;
    private JButton enviarButton;

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
