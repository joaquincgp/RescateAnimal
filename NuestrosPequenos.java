import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class NuestrosPequenos extends JFrame{
    private JTable tablaAnimales;
    private JPanel panelAnimalRescue;
    private JTable tableAnimales;

    public NuestrosPequenos(List<Animal> animales){
        setTitle("Nuestros pequeños");
        setSize(1200, 1600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Cambio en esta línea
        setContentPane(panelAnimalRescue);
        setResizable(false);
        pack(); // Ajusta automáticamente el tamaño del JFrame según su contenido
        setLocationRelativeTo(null); // Centra el JFrame en la pantalla

        // Crear un modelo de tabla para los datos de los animales
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Nombre");
        model.addColumn("Fecha de Nacimiento");
        model.addColumn("ID");
        model.addColumn("Color");
        model.addColumn("Pabellón");
        model.addColumn("Especie");

        // Agregar los datos de los animales al modelo de tabla
        for (Animal animal : animales) {
            Object[] rowData = {
                    animal.getNombreAnimal(),
                    animal.getFechaLlegada(),
                    animal.getId(),
                    animal.getColor(),
                    animal.getPabellon(),
                    animal.getEspecie()
            };
            model.addRow(rowData);
        }

        // Crear la tabla con el modelo de datos
        tablaAnimales = new JTable(model);

        // Agregar la tabla a un JScrollPane para permitir el desplazamiento
        JScrollPane scrollPane = new JScrollPane(tablaAnimales);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }
}




