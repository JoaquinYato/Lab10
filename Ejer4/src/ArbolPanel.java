import javax.swing.*;
import java.awt.*;

public class ArbolPanel extends JPanel {
    private BTree<RegistroEstudiante> arbol;
    private final int NODE_HEIGHT = 40;
    private final int BOX_WIDTH = 40;
    private final int BOX_GAP = 5;
    private final int VERTICAL_SPACING = 80;

    public void setArbol(BTree<RegistroEstudiante> arbol) {
        this.arbol = arbol;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (arbol != null && arbol.getRoot() != null) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(2));
            drawNode(g2, arbol.getRoot(), getWidth() / 2, 40, getWidth() / 4);
        }
    }

    private void drawNode(Graphics2D g, BNode<RegistroEstudiante> node, int x, int y, int horizontalGap) {
        if (node == null) return;

        int numKeys = node.getCount();
        int totalWidth = numKeys * BOX_WIDTH + (numKeys - 1) * BOX_GAP;
        int startX = x - totalWidth / 2;

        // Dibujar claves como cajas separadas
        for (int i = 0; i < numKeys; i++) {
            int boxX = startX + i * (BOX_WIDTH + BOX_GAP);
            g.setColor(new Color(200, 230, 255));
            g.fillRect(boxX, y, BOX_WIDTH, NODE_HEIGHT);
            g.setColor(Color.BLACK);
            g.drawRect(boxX, y, BOX_WIDTH, NODE_HEIGHT);

            String text = String.valueOf(node.getKeys().get(i).getCodigo());
            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth(text);
            g.drawString(text, boxX + (BOX_WIDTH - textWidth) / 2, y + NODE_HEIGHT / 2 + 5);
        }

        // Dibujar hijos y líneas
        for (int i = 0; i <= numKeys; i++) {
            BNode<RegistroEstudiante> child = node.getChilds().get(i);
            if (child != null) {
                int childX = x - horizontalGap + i * (horizontalGap * 2 / Math.max(1, numKeys));
                int childY = y + VERTICAL_SPACING;

                // Línea
                g.setColor(Color.BLACK);
                g.drawLine(x, y + NODE_HEIGHT, childX, childY);

                // Recursivamente dibujar hijo
                drawNode(g, child, childX, childY, horizontalGap / 2);
            }
        }
    }
}
