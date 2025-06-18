public class BNode<E extends Comparable<E>> {
    protected ArrayList<E> keys;
    protected ArrayList<BNode<E>> childs;
    protected int count;
    protected int idNode;

    public BNode (int n){
        this.keys = new ArrayList<E>(n);
        this.childs = new ArrayList<BNode<E>>(n);
        this.count = 0;
        this.idNode = 0;
        for(int i=0; i < n;
            i++){ this.keys.add(null);
            this.childs.add(null);
        }
    }
    public boolean nodeFull () {
        return keys.size() == count;
    }

    public boolean nodeEmpty () {
        return count == 0;
    }

    public boolean searchNode ( ) {
        int x = 0;
        while(x<count && keys.get(i) != null && keys.get(i).compareTo(key) < 0){
            i++;
        }
        return i
    }

    public boolean keyExists(E key) {
        int pos = searchNode(key);
        return pos < count && keys.get(pos) != null && keys.get(pos).equals(key);
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

private E getPredecesor(BNode<E> node, int index){
    BNode<E> curretn = node.childs.get(index);
    while (current.childs.get(current.count) != null){
        current = current.childs.get(current.count);
    }
    return current.keys.get(current.count-1);
}
