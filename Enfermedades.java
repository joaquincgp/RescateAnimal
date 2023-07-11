import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Enfermedades extends JFrame {
    private JPanel panelEnfermedades;
    private JTextField enfermedadesField1;
    private JTextField pesoTextField;
    private JButton ingresarButton;
    private JTextField vacunasTextField;
    private JTextField desparacitacionTextField;
    private JTextField idHistorialTextField;




public Enfermedades() {
    setTitle("Enfermedades"); // Establece el título del JFrame
    setSize(400, 600);
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cambio en esta línea
    setContentPane(panelEnfermedades); // Establece el panel principal del JFrame

    pack(); // Ajusta automáticamente el tamaño del JFrame según su contenido
    setLocationRelativeTo(null); // Centra el JFrame en la pantalla
    ingresarButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    });
}
}
