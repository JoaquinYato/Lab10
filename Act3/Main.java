public class Main {
    public static void main(String[] args) {
        BTree<Integer> btree = new BTree<>(4); // Árbol B de orden 4

        // Insertamos los valores de forma que se genere la estructura similar a la imagen
        int[] valores = {
                3, 10, 13, 16, 12, 19, 22, 25, 28, 31, 33, 35, 40,
                41, 49, 52, 55, 57, 60, 62, 63, 67, 70, 72
        };

        for (int valor : valores) {
            btree.insert(valor);
        }

        // Imprimir el árbol en formato tabla
        System.out.println(btree); // usa toString() que ya llama a writeTree()
    }
}
