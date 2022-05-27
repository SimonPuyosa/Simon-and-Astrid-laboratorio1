package ve.usb.libGrafo

import java.io.File

public class GrafoDirigido : Grafo {

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
    constructor(nombreArchivo: String)  {
    }

    /* Agrega un lado al digrafo. Si el lado a agregar contiene
     un vértice que no pertenece al grafo, entonces se lanza una
     RuntimeException. Si el lado no se encuentra 
     en el grafo se agrega y retorna true, en caso contrario 
     no se agraga al grafo y se retorna false.
     */
    fun agregarArco(a: Arco) : Boolean {
    }

    // Retorna el grado del grafo. Si el vértice no pertenece al grafo se lanza una RuntimeException
    override fun grado(v: Int) : Int {
    }

    // Retorna el grado exterior del grafo. Si el vértice no pertenece al grafo se lanza una RuntimeException
    fun gradoExterior(v: Int) : Int {
    }

    // Retorna el grado interior del grafo. Si el vértice no pertenece al grafo se lanza una RuntimeException
    fun gradoInterior(v: Int) : Int {
    }

    // Retorna el número de lados del grafo
    override fun obtenerNumeroDeLados() : Int {
    }

    // Retorna el número de vértices del grafo
    override fun obtenerNumeroDeVertices() : Int {
    }

    /* 
     Retorna los adyacentes de v, en este caso los lados que tienen como vértice inicial a v. 
     Si el vértice no pertenece al grafo se lanza una RuntimeException
     */
    override fun adyacentes(v: Int) : Iterable<Arco> {
    }

    /* Retorna los lados adyacentes de un lado l. 
     Se tiene que dos lados son iguales si tiene los mismos extremos. 
     Si un lado no pertenece al grafo se lanza una RuntimeException.
     */
    fun ladosAdyacentes(l: Arco) : Iterable<Arco> {
    }
    
    // Retorna todos los lados del digrafo
    override operator fun iterator() : Iterator<Arco> {
    }
     
    // String que muestra el contenido del grafo
    override fun toString() : String {
     }
}
