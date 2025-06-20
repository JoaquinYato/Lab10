public class BTree<E extends Comparable<E>> {
    private BNode<E> root;
    private int orden;
    private boolean up;
    private BNode<E> nDes;

    public BTree(int orden) {
        this.orden = orden;
        this.root = null;
    }

    public BNode<E> getRoot(){
        return root;
    }

    public boolean isEmpty() {
        return this.root == null;
    }

    public void insert(E cl) {
        up = false;
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

    private E push(BNode<E> current, E cl) {
        int pos[] = new int[1];
        E mediana;

        if (current == null) {
            up = true;
            nDes = null;
            return cl;
        } else {
            boolean fl;
            fl = current.searchNode(cl, pos);

            if (fl) {
                System.out.println("Item duplicado\n");
                up = false;
                return null;
            }

            mediana = push(current.childs.get(pos[0]), cl);

            if (up) {
                if (current.nodeFull(this.orden - 1)) {
                    mediana = dividedNode(current, mediana, pos[0]);
                } else {
                    putNode(current, mediana, nDes, pos[0]);
                    up = false;
                }
            }

            return mediana;
        }
    }

    private void putNode(BNode<E> current, E cl, BNode<E> rd, int k) {
        int i;
        for (i = current.count - 1; i >= k; i--) {
            current.keys.set(i + 1, current.keys.get(i));
            current.childs.set(i + 2, current.childs.get(i + 1));
        }
        current.keys.set(k, cl);
        current.childs.set(k + 1, rd);
        current.count++;
    }

    public void remove(E cl) {
        delete(root, cl);
        if (root != null && root.count == 0) {
            root = root.childs.get(0);
        }
    }

    public boolean delete(BNode<E> node, E key) {
        if (node == null) {
            return false;
        }

        int pos[] = new int[1];
        boolean found = node.searchNode(key, pos);

        if (found) {
            if (node.childs.get(pos[0]) != null) {
                removeKey(node, pos[0]);
                return true;
            } else {
                E pred = getPredecessor(node, pos[0]);
                node.keys.set(pos[0], pred);
                return delete(node.childs.get(pos[0]), pred);
            }
        } else {
            if (node.childs.get(pos[0]) != null) {
                return false;
            } else {
                boolean isDeleted = delete(node.childs.get(pos[0]), key);
                if (node.childs.get(pos[0]).count < (orden - 1) / 2) {
                    fix(node, pos[0]);
                }
                return isDeleted;
            }
        }
    }

    private void removeKey(BNode<E> node, int index) {
        for(int i = index; i < node.count - 1; i++) {
            node.keys.set(i, node.keys.get(i + 1));
        }
        node.keys.set(node.count - 1, null);
        node.count--;
    }

    private E getPredecessor(BNode<E> node, int index) {
        BNode<E> current = node.childs.get(index);
        while (current.childs.get(index + 1) != null) {
            current = current.childs.get(index + 1);
        }
        return current.keys.get(current.count - 1);
    }

    private E dividedNode(BNode<E> current, E cl, int k) {
        BNode<E> rd = nDes;
        int i, posMdna;

        posMdna = (k <= this.orden / 2) ? this.orden / 2 : this.orden / 2 + 1;
        nDes = new BNode<>(this.orden);

        for (i = posMdna; i < this.orden - 1; i++) {
            nDes.keys.set(i - posMdna, current.keys.get(i));
            nDes.childs.set(i - posMdna + 1, current.childs.get(i + 1));
        }

        nDes.count = (this.orden - 1) - posMdna;
        current.count = posMdna;

        if (k <= this.orden / 2) {
            putNode(current, cl, rd, k);
        } else {
            putNode(nDes, cl, rd, k - posMdna);
        }

        E median = current.keys.get(current.count - 1);
        nDes.childs.set(0, current.childs.get(current.count));
        current.count--;

        return median;
    }



    private void fix(BNode<E> parent, int index) {
        if (index > 0 && parent.childs.get(index - 1).count > (orden - 1) / 2) {
            borrowFromLeft(parent, index);
        }
        else if (index < parent.count && parent.childs.get(index + 1).count > (orden - 1) / 2) {
            borrowFromRight(parent, index);
        }
        else {
            if (index > 0) {
                merge(parent, index - 1);
            } else {
                merge(parent, index);
            }
        }
    }

    private void borrowFromLeft(BNode<E> parent, int index){
        BNode<E> left = parent.childs.get(index-1);
        BNode<E> current = parent.childs.get(index);
        for(int i = current.count-1; i>=0; i--){
            current.keys.set(i+1, current.keys.get(i));
        }
        current.keys.set(0, parent.keys.get(index-1));
        parent.keys.set(index-1, left.keys.get(left.count-1));
        left.keys.set(left.count-1, null);
        if(left.childs.get(left.count) != null){
            for(int i= current.count; i>=0; i--){
                current.childs.set(i+1, current.childs.get(i));
            }
            current.childs.set(0, left.childs.get(left.count));
            left.childs.set(left.count,null);
        }
        current.count++;
        left.count--;
    }

    private void borrowFromRight(BNode<E> parent, int index){
        BNode<E> right = parent.childs.get(index+1);
        BNode<E> current = parent.childs.get(index);
        current.keys.set(current.count, parent.keys.get(index));
        parent.keys.set(index, right.keys.get(0));
        for(int i = 0; i < right.count-1; i++){
            current.keys.set(i, right.keys.get(i+1));
        }
        right.keys.set(right.count-1, null);
        if(right.childs.get(0) != null){
            current.childs.set(current.count+1, right.childs.get(0));
            for(int i= 0; i<right.count; i++){
                current.childs.set(i+1, current.childs.get(i));
            }
            right.childs.set(right.count,null);
        }
        current.count++;
        right.count--;
    }

    private void merge(BNode<E> parent, int index){
        BNode<E> left = parent.childs.get(index);
        BNode<E> rigth = parent.childs.get(index+1);
        left.keys.set(left.count, parent.keys.get(index));
        left.count++;
        for(int i = 0; i < rigth.count; i++ ){
            left.keys.set(left.count+i, rigth.keys.get(i));
        }
        for(int i = 0; i < rigth.count; i++ ){
            left.childs.set(left.count+i, rigth.childs.get(i));
        }
        left.count += rigth.count;
        for(int i=index; i < parent.count-1; i++){
            parent.keys.set(i, parent.keys.get(i+1));
            parent.childs.set(i+1, parent.childs.get(i+2));
        }
        parent.keys.set(parent.count-1, null);
        parent.childs.set(parent.count, null);
        parent.count--;
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
        sb.append(String.format("%-10s %-20s %-10s %-10s\n", "Id.Nodo", "Claves Nodo", "Id.Padre", "Id.Hijos"));
        sb.append(recorrerYFormatear(current, null));
        return sb.toString();
    }

    private String recorrerYFormatear(BNode<E> nodo, BNode<E> padre) {
        if (nodo == null) return "";

        StringBuilder sb = new StringBuilder();

        // Id del nodo
        String idNodo = String.valueOf(nodo.idNode);

        // Claves del nodo
        String claves = "(";
        for (int i = 0; i < nodo.count; i++) {
            claves += nodo.keys.get(i);
            if (i < nodo.count - 1) claves += ", ";
        }
        claves += ")";

        // Id del padre
        String idPadre = (padre == null) ? "--" : "[" + padre.idNode + "]";

        // Ids de los hijos
        StringBuilder hijos = new StringBuilder();
        boolean tieneHijos = false;
        for (int i = 0; i <= nodo.count; i++) {
            if (nodo.childs.get(i) != null) {
                if (tieneHijos) hijos.append(", ");
                hijos.append(nodo.childs.get(i).idNode);
                tieneHijos = true;
            }
        }
        String idHijos = tieneHijos ? "[" + hijos.toString() + "]" : "--";

        sb.append(String.format("%-10s %-20s %-10s %-10s\n", idNodo, claves, idPadre, idHijos));

        // Recorrer hijos
        for (int i = 0; i <= nodo.count; i++) {
            if (nodo.childs.get(i) != null) {
                sb.append(recorrerYFormatear(nodo.childs.get(i), nodo));
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