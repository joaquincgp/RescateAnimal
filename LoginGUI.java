import Exceptions.CampoVacioException;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JFrame{
    private JTextField userField;
    private JTextField passwordField;
    private JPanel panelLogin;
    private JButton accederButton;

    public LoginGUI(){
        setTitle("Inicio de sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setResizable(false);
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla
        setContentPane(panelLogin);
        accederButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user = userField.getText();
                String password = passwordField.getText();
                try{
                    if(user.equals("admin") && password.equals("admin")){
                        MainGUI mainGUI = new MainGUI();
                        mainGUI.setVisible(true);
                        dispose(); // Cerrar la ventana de inicio de sesión
                    } else if (user.isEmpty() || password.isEmpty()) {
                        throw new CampoVacioException("Campo vacio");
                    }else{
                        JOptionPane.showMessageDialog(LoginGUI.this, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (CampoVacioException ex) {
                    JOptionPane.showMessageDialog(LoginGUI.this, "Algun campo esta vacio", "Error", JOptionPane.ERROR_MESSAGE);
                }


            }
        });
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                LoginGUI loginGUI = new LoginGUI();
                loginGUI.setVisible(true);
            }
        });

}
}
