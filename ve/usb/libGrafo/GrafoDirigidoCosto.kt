package ve.usb.libGrafo

import java.io.File

public class GrafoDirigidoCosto : Grafo {

    // Se construye un grafo a partir del número de vértices
    constructor(numDeVertices: Int) {
    }

    /*
     Se construye un grafo a partir de un archivo. El formato es como sigue.
     La primera línea es el número de vértices. La segunda línea es el número de lados. 
     Las siguientes líneas corresponden a los lados. Cada línea de los lados tiene a dos enteros
     que corresponden a los vértices inicial y final de los lados,
     y un número real que es el costo del lado.
     Se asume que los datos del archivo están correctos, no se verifican.
     */  
    constructor(nombreArchivo: String)  {
    }

    /* Agrega un lado al digrafo. Si el lado a agregar contiene
     un vértice que no pertenece al grafo, entonces se lanza una
     RuntimeException. Si el lado no se encuentra 
     en el grafo se agrega y retorna true, en caso contrario 
     no se agraga al grafo y se retorna false. 
     Si hay un arco en el grafo con los mismos extremos del arco
     que se quiere agregar, entonces se asume que el arco ya
     está en el grafo, sin importar que tengan diferentes costos.
     */
    fun agregarArcoCosto(a: ArcoCosto) : Boolean {
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
    override fun adyacentes(v: Int) : Iterable<ArcoCosto> {
    }

    /* Retorna los lados adyacentes de un lado l. 
     Se tiene que dos lados son iguales si tiene los mismos extremos. 
     Si un lado no pertenece al grafo se lanza una RuntimeException.
     */
    fun ladosAdyacentes(l: ArcoCosto) : Iterable<ArcoCosto> {
    }

    // Retorna todos los lados del digrafo con costo
    override operator fun iterator() : Iterator<ArcoCosto> {
     }

    
    // String que muestra el contenido del grafo
    override fun toString() : String {
    }

}
