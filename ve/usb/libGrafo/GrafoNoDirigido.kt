package ve.usb.libGrafo

import java.io.File

public class GrafoNoDirigido: Grafo {

    // Se construye un grafo a partir del número de vértices
    constructor(numDeVertices: Int) {
    }

    /*
     Se construye un grafo a partir de un archivo. El formato es como sigue.
     La primera línea es el número de vértices. 
     La segunda línea es el número de lados. Las siguientes líneas
     corresponden a los lados, con los vértices de un lado separados por un espacio en blanco.
     Se asume que los datos del archivo están correctos, no se verifican.
     */  
    constructor(nombreArchivo: String) {
    }

    /* Agrega un lado al grafo. Si el lado a agregar contiene
     un vértice que no pertenece al grafo, entonces se lanza una
     RuntimeException. Si el lado no se encuentra 
     en el grafo se agrega y retorna true, en caso contrario 
     no se agraga al grafo y se retorna false. 
     */
    fun agregarArista(a: Arista) : Boolean {
    }

    // Retorna el número de lados del grafo
    override fun obtenerNumeroDeLados() : Int {
    }

    // Retorna el número de vértices del grafo
    override fun obtenerNumeroDeVertices() : Int {
    }

    // Retorna los lados adyacentes al vértice v, es decir, los lados que contienen al vértice v
    override fun adyacentes(v: Int) : Iterable<Arista> {
    }

    /* Retorna los lados adyacentes de un lado l. 
     Se tiene que dos lados son iguales si tiene los mismos extremos. 
     Si un lado no pertenece al grafo se lanza una RuntimeException.
     */
    fun ladosAdyacentes(l: Arista) : Iterable<Arista> {
    }
    
    // Retorna todos los lados del grafo no dirigido
    override operator fun iterator() : Iterator<Arista> {
    }

    // Grado del grafo
    override fun grado(v: Int) : Int {
    }

    // Retorna un string con una representación del grafo, en donde se nuestra todo su contenido
    override fun toString() : String {
    }
}
