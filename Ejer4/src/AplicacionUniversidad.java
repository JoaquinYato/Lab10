public class AplicacionUniversidad {

    public static void main(String[] args) {
        BTree<RegistroEstudiante> arbol = new BTree<>(4);

        // Insertar estudiantes
        insertar(arbol, 103, "Ana");
        insertar(arbol, 110, "Luis");
        insertar(arbol, 101, "Carlos");
        insertar(arbol, 120, "Lucía");
        insertar(arbol, 115, "David");
        insertar(arbol, 125, "Jorge");
        insertar(arbol, 140, "Camila");
        insertar(arbol, 108, "Rosa");
        insertar(arbol, 132, "Ernesto");
        insertar(arbol, 128, "Denis");
        insertar(arbol, 145, "Enrique");
        insertar(arbol, 122, "Karina");
        insertar(arbol, 108, "Juan"); // Clave duplicada (no se insertará)

        System.out.println("Árbol B tras inserciones:\n" + arbol);

        // Buscar estudiantes
        buscarNombre(arbol, 115); // David
        buscarNombre(arbol, 132); // Ernesto
        buscarNombre(arbol, 999); // No encontrado

        // Eliminar estudiante con código 101
        arbol.remove(new RegistroEstudiante(101, ""));
        System.out.println("\nTras eliminar código 101:");
        System.out.println(arbol);

        // Insertar nuevo estudiante
        insertar(arbol, 106, "Sara");

        // Buscar nuevo estudiante
        buscarNombre(arbol, 106); // Sara
    }

    // Inserción rápida
    private static void insertar(BTree<RegistroEstudiante> arbol, int codigo, String nombre) {
        arbol.insert(new RegistroEstudiante(codigo, nombre));
    }

    // Búsqueda de nombre
    private static void buscarNombre(BTree<RegistroEstudiante> arbol, int codigo) {
        String nombre = buscarNombre(arbol.getRoot(), codigo);
        System.out.println("Buscar código " + codigo + ": " + (nombre != null ? nombre : "No encontrado"));
    }

    // Método recursivo para buscar nombre
    private static String buscarNombre(BNode<RegistroEstudiante> nodo, int codigo) {
        if (nodo == null) return null;

        for (int i = 0; i < nodo.getCount(); i++) {
            RegistroEstudiante actual = nodo.getKeys().get(i);
            if (codigo == actual.getCodigo()) return actual.getNombre();
            if (codigo < actual.getCodigo()) return buscarNombre(nodo.getChilds().get(i), codigo);
        }

        return buscarNombre(nodo.getChilds().get(nodo.getCount()), codigo);
    }
}