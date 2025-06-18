public class BTree<E extends Comparable<E>> {
    private BNode<E> root;
    private int orden;
    private boolean up;
    private BNode<E> nDes;
    public BTree(int orden)
    { this.orden = orden;
        this.root = null;
    }
    public boolean isEmpty() {
        return this.root == null;
    }
    public void insert(E cl)
    { up = false;
        E mediana;
        BNode<E> pnew;
        mediana = push(this.root, cl);
        if (up) {
            pnew = new BNode<E>(this.orden);
            pnew.count = 1;
            pnew.keys.set(0, mediana);
            pnew.childs.set(0, this.root);
            pnew.childs.set(1, nDes);
            this.root = pnew;
        }
    }
    private E push(BNode<E> current,E cl){
        int pos[] = new int[1];
        E mediana;
        if(current == null){
            up = true;
            nDes = null;
            return cl;
        }
        else{
            boolean fl;
            fl = current.searchNode(cl, pos);
            if(fl){
                System.out.println("Item duplicado\n");
                up = false;
                return null;
            }
            mediana = push(current.childs.get(pos[0]),cl);
            if(up){
                if(current.nodeFull(this.orden-1))
                    mediana = dividedNode(current,mediana,pos[0]);else{
                }
            }
            up = false;
            putNode(current,mediana,nDes,pos[0]);
            return mediana;
        }
    }
    private void putNode(BNode<E> current,E cl,BNode<E> rd,int k){
        int i;
        for(i = current.count-1; i >= k; i--)
        { current.keys.set(i+1,current.keys.get(i));
            current.childs.set(i+2,current.childs.get(i+1));
        }
        current.keys.set(k,cl);
        current.childs.set(k+1,rd);
        current.count++;
    }
    private E dividedNode(BNode<E> current,E cl,int
            k){ BNode<E> rd = nDes;
        int i, posMdna;
        posMdna = (k <= this.orden/2) ? this.orden/2 : this.orden/2+1;
        nDes = new BNode<E>(this.orden);
        for(i = posMdna; i < this.orden-1; i++)
        { nDes.keys.set(iposMdna,current.keys.get(i));
            nDes.childs.set(i-posMdna+1,current.childs.get(i+1));
        }
        nDes.count = (this.orden - 1) - posMdna;
        current.count = posMdna;
        if(k <= this.orden/2)
            putNode(current,cl,rd,k);
        else
            putNode(nDes,cl,rd,k-posMdna);
        E median = current.keys.get(current.count-1);
        nDes.childs.set(0,current.childs.get(current.count));
        current.count--;
        return median;
    }

    // Métodos agregados para toString
    public String toString() {
        String s = "";
        if (isEmpty()) {
            s += "BTree is empty...";
        } else {
            s = writeTree(this.root);
        }
        return s;
    }

    private String writeTree(BNode<E> current) {
        StringBuilder sb = new StringBuilder();

        if (current != null) {
            // Agregar información del nodo actual
            sb.append("Id.Nodo: ").append(current.idNode).append("\n");

            // Agregar las claves del nodo
            sb.append("Claves Nodo: (");
            for (int i = 0; i < current.count; i++) {
                if (i > 0) sb.append(", ");
                sb.append(current.keys.get(i));
            }
            sb.append(")\n");

            // Agregar el ID del padre (si no es raíz)
            if (current == this.root) {
                sb.append("Id.Padre: --\n");
            } else {
                // Buscar el padre de este nodo
                BNode<E> parent = findParent(this.root, current);
                if (parent != null) {
                    sb.append("Id.Padre: [").append(parent.idNode).append("]\n");
                } else {
                    sb.append("Id.Padre: --\n");
                }
            }

            // Agregar los IDs de los hijos
            sb.append("Id.Hijos: ");
            boolean hasChildren = false;
            StringBuilder childrenIds = new StringBuilder();

            for (int i = 0; i <= current.count; i++) {
                if (current.childs.get(i) != null) {
                    if (hasChildren) childrenIds.append(", ");
                    childrenIds.append(current.childs.get(i).idNode);
                    hasChildren = true;
                }
            }

            if (hasChildren) {
                sb.append("[").append(childrenIds.toString()).append("]\n");
            } else {
                sb.append("--\n");
            }

            sb.append("\n"); // Línea en blanco entre nodos

            // Recursivamente agregar los hijos
            for (int i = 0; i <= current.count; i++) {
                if (current.childs.get(i) != null) {
                    sb.append(writeTree(current.childs.get(i)));
                }
            }
        }

        return sb.toString();
    }

    // Método auxiliar para encontrar el padre de un nodo
    private BNode<E> findParent(BNode<E> current, BNode<E> target) {
        if (current == null || current == target) {
            return null;
        }

        // Verificar si alguno de los hijos es el nodo objetivo
        for (int i = 0; i <= current.count; i++) {
            if (current.childs.get(i) == target) {
                return current;
            }
        }

        // Buscar recursivamente en los hijos
        for (int i = 0; i <= current.count; i++) {
            if (current.childs.get(i) != null) {
                BNode<E> parent = findParent(current.childs.get(i), target);
                if (parent != null) {
                    return parent;
                }
            }
        }

        return null;
    }
}