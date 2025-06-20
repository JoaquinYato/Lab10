public class Main {
    public static void main(String[] args) {
        BTree<Integer> arbolB = new BTree<>(4); // Orden 4

        // Inserciones
        int[] datosInsertar = {10, 20, 5, 6, 12, 30, 7, 17};
        System.out.println(">>> INSERTANDO DATOS EN EL ÁRBOL B");
        for (int dato : datosInsertar) {
            System.out.println("Insertando: " + dato);
            arbolB.insert(dato);
            System.out.println(arbolB);
            System.out.println("-------------------------------");
        }

        // Eliminaciones
        int[] datosEliminar = {6, 13, 7, 4};
        System.out.println(">>> ELIMINANDO DATOS DEL ÁRBOL B");
        for (int dato : datosEliminar) {
            System.out.println("Eliminando: " + dato);
            arbolB.remove(dato);
            System.out.println(arbolB);
            System.out.println("-------------------------------");
        }
    }
}
