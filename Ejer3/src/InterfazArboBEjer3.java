import javax.swing.*;
import java.awt.*;
import java.io.File;

public class InterfazArboBEjer3 extends JFrame{
    private BTree bt;
    private DibujoPanel panel;

    public InterfazArboBEjer3(BTree bt){
        this.bt = bt;

        setTitle("Arbol B");
        setSize(1000, 770);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        panel = new DibujoPanel();
        add(panel, BorderLayout.CENTER);

        JPanel botones = new JPanel();

        JButton btnAgregarClave = new JButton("Agregar clave");
        btnAgregarClave.addActionListener(e -> agregarKey());

        JButton btnEliminarClave = new JButton("Eliminar clave");
        btnEliminarClave.addActionListener(e -> eliminarKey());

        JButton btnBuscarClave = new JButton("Buscar clave");
        btnBuscarClave.addActionListener(e -> buscarKey());
        botones.add(btnBuscarClave);

        botones.add(btnAgregarClave);
        botones.add(btnEliminarClave);
        add(botones, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void agregarKey(){
        String valor = JOptionPane.showInputDialog(this, "Ingrese clave a agregar:");
        if (valor != null && !valor.trim().isEmpty() ){
            bt.insert(valor);
            panel.repaint();
        }
    }
    private void buscarKey() {
        String valor = JOptionPane.showInputDialog(this, "Ingrese la clave a buscar:");
        if (valor != null && !valor.trim().isEmpty()) {
            boolean encontrado = bt.search(valor);
            if (!encontrado) {
                JOptionPane.showMessageDialog(this, "La clave '" + valor + "' NO se encuentra en el árbol.");
            }
            // Si la clave se encuentra, el método search ya imprime el mensaje con idNode y posición.
        }
    }

    private void eliminarKey(){
        String valor = JOptionPane.showInputDialog(this, "Ingrese el valor de la clave:");
        if (valor != null && !valor.trim().isEmpty() ){
            bt.remove(valor);
            panel.repaint();
        }
    }

    class DibujoPanel extends JPanel {
        private int ANCHO = 40;
        private int ALTO = 30;
        private int VERTICAL = 70;

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            // Verificar si hay vértices para dibujar
            if (!bt.isEmpty()) {
                drawTree(g, bt.getRoot(), getWidth() / 2, 30, getWidth() / 4);
            } else {
                g.setColor(Color.GRAY);
                g.setFont(new Font("Arial", Font.BOLD, 18));
                g.drawString("Arbol B vacio", 300, getHeight() / 2);
            }
        }

        public void drawTree(Graphics g, BNode<String> node, int x, int y, int offset) {
            if (node == null) return;

            int claves = node.count;
            int ancho = claves * ANCHO + (claves - 1) * 5;
            int inicio = x - ancho / 2;

            // Dibujo del nodo
            g.setColor(Color.YELLOW);
            g.fillRoundRect(inicio, y, ancho, ALTO, 10, 10);
            g.setColor(Color.BLACK);
            g.drawRoundRect(inicio, y, ancho, ALTO, 10, 10);

            // Dibujado de las claves
            for (int i = 0; i < claves; i++) {
                g.drawString(node.keys.get(i), inicio + i * (ANCHO + 5) + 10, y + 20);
            }

            if (!node.isLeaf()) {
                int hijos = claves + 1;
                int espaciadoHijo = offset / hijos;

                for (int i = 0; i < hijos; i++) {
                    BNode<String> hijo = node.childs.get(i);
                    if (hijo != null) {
                        // Ajuste para que el subárbol no se sobreponga
                        int childX = x - offset / 2 + i * espaciadoHijo;
                        int childY = y + VERTICAL;

                        g.drawLine(x, y + ALTO, childX, childY);
                        drawTree(g, hijo, childX, childY, espaciadoHijo);
                    }
                }
            }
        }
    }

    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            int opcion = JOptionPane.showOptionDialog(
                null,
                "¿Cómo desea iniciar el árbol B?",
                "Inicio del Árbol B",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                new Object[]{"Desde archivo", "Vacío"},
                "Desde archivo"
            );
            BTree<String> arbol = null;

            if (opcion == 0) { // Desde archivo
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Seleccione el archivo del arbol B");
                int userSelection = fileChooser.showOpenDialog(null);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    arbol = BTree.building_Btree(file.getAbsolutePath());
                    if (arbol == null) {
                        JOptionPane.showMessageDialog(null, "No se pudo construir el arbol");
                        return;
                    }
                } else {
                    return; // Cancelado
                }
            } else if (opcion == 1) { // Vacío
                int orden = mostrarPanelIngresoOrden();
                if (orden >= 3) {
                    arbol = new BTree<>(orden);
                } else {
                    return;
                }
            } else {
                return; // Cancelado
            }

            new InterfazArboBEjer3(arbol);
        });
    }

    private static int mostrarPanelIngresoOrden() {
        JTextField campo = new JTextField(5);
        JPanel panel = new JPanel(new GridLayout(2, 1));
        panel.add(new JLabel("Ingrese el orden del Árbol B (mínimo 3):"));
        panel.add(campo);

        int orden = -1;
        while (orden < 3) {
            int opcion = JOptionPane.showConfirmDialog(null, panel, "Orden del Árbol B",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (opcion != JOptionPane.OK_OPTION) {
                System.exit(0); // Cancelado
            }

            try {
                orden = Integer.parseInt(campo.getText().trim());
                if (orden < 3) {
                    JOptionPane.showMessageDialog(null, "El orden debe ser al menos 3");
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Ingrese un número válido");
            }
        }
        return orden;
    }
}
