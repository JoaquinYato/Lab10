import java.util.*;

public class BNode<E extends Comparable<E>> {
    protected ArrayList<E> keys;
    protected ArrayList<BNode<E>> childs;
    protected int count;
    protected int idNode;

    public BNode(int n) {
        this.keys = new ArrayList<>(n);
        this.childs = new ArrayList<>(n + 1); // n claves => n+1 hijos
        this.count = 0;
        this.idNode = 0;

        for (int i = 0; i < n; i++) {
            this.keys.add(null);
        }
        for (int i = 0; i < n + 1; i++) {
            this.childs.add(null);
        }
    }

    public boolean nodeFull(int maxKeys) {
        return count == maxKeys;
    }

    public boolean nodeEmpty() {
        return count == 0;
    }

    public boolean searchNode(E key, int[] pos) {
        int i = 0;
        while (i < count && keys.get(i) != null && keys.get(i).compareTo(key) < 0) {
            i++;
        }
        pos[0] = i;
        return (i < count && keys.get(i) != null && keys.get(i).compareTo(key) == 0);
    }

    public boolean keyExists(E key) {
        int[] pos = new int[1];
        boolean found = searchNode(key, pos);
        return found;
    }

    public ArrayList<E> getKeys() {
        return keys;
    }

    public ArrayList<BNode<E>> getChilds() {
        return childs;
    }

    public int getCount() {
        return count;
    }

    public int getIdNode() {
        return idNode;
    }

    public boolean isLeaf() {
        for (int i = 0; i <= count; i++) {
            if (childs.get(i) != null) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Node ").append(idNode).append(": [");
        for (int i = 0; i < count; i++) {
            if (keys.get(i) != null) {
                sb.append(keys.get(i));
                if (i < count - 1) {
                    sb.append(", ");
                }
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
