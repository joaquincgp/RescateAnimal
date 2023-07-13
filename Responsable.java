import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Responsable extends JFrame {

    private JPanel panelResponsable;
    private JTextField nombreResponsable;
    private JTextField textField2;
    private JTextField textField3;
    private JComboBox comboBox1;
    private JTextField textField4;
    private JTextField textField5;
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

}
