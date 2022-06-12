package libGrafoKt.ve.usb.libGrafo
import java.util.*

interface Grafo : Iterable<Lado> {
    var listaDeAdyacencia: Array<LinkedList<Vertice>?>
    var listaDeVertices: Array<Vertice>
    var numDeLados: Int
    var numDeVertices: Int

    /** Metodo en el que retorna un entero que representa el numero de lados del grafo
     */
    fun obtenerNumeroDeLados() : Int

    /** Metodo en el que retorna un entero que representa el numero de vertices del grafo
     */
    fun obtenerNumeroDeVertices() : Int

    /** Metodo que dado un entero que representa un vertice, retorna un iterable de todos los lados en los que
     *  el vertice dado es adyacente. Si el vertice no se encuentra entonces se lanza un RuntimeException
     */
    fun adyacentes(v: Int) : Iterable<Lado>

    /** Metodo que dado un entero que representa un vertice del grafo, retorna el grado de este vertice, si el
     *  vertice no se encuentra en el grafo entonces se lanza un RuntimeException
     */
    fun grado(v: Int) : Int

    /** Metodo que retorna un iterator de todos los lados que pertenecen al grafo
     */
    override operator fun iterator() : Iterator<Lado> 
}
