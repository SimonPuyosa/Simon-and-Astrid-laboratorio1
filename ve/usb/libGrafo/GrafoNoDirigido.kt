package ve.usb.libGrafo

import ve.usb.libGrafo.linkedList.ListaEnlazada
import java.io.File
import java.util.*
import javax.management.openmbean.KeyAlreadyExistsException

public class GrafoNoDirigido: Grafo {
    //atributos añadidos por nosotros
    var listaDeAdyacencia: Array<LinkedList<Int>?> = arrayOf(LinkedList())
    var numeroDeLados: Int = 0
    lateinit var listaDeVertices: Array<Int?>
    var numeDeVertices = 0

    // Se construye un grafo a partir del número de vértices
    constructor(numDeVertices: Int) {
        listaDeAdyacencia = arrayOfNulls(numDeVertices)
    }

    /*
     Se construye un grafo a partir de un archivo. El formato es como sigue.
     La primera línea es el número de vértices. 
     La segunda línea es el número de lados. Las siguientes líneas
     corresponden a los lados, con los vértices de un lado separados por un espacio en blanco.
     Se asume que los datos del archivo están correctos, no se verifican.
     */  
    constructor(nombreArchivo: String) {
        val a = File(nombreArchivo).readLines()
        numeDeVertices = a[0].toInt()
        //if (ListaDeAdyacencia == null) { ListaDeAdyacencia = arrayOfNulls(numeDeVertices) }
        listaDeAdyacencia = arrayOfNulls(numeDeVertices)
        listaDeVertices = arrayOfNulls(numeDeVertices)
        numeroDeLados = a[1].toInt()

        var i = 2
        var u: Int
        var v: Int
        var temp: List<String>


        while (i < 2 + numeroDeLados && a[i] != ""){
            temp = a[i].split(" ").filter {it != ""}

            if (listaDeAdyacencia[temp[0].toInt()] == null){
                listaDeAdyacencia[temp[0].toInt()] = LinkedList<Int>()
            }

            v = temp[1].toInt()
            u = temp[0].toInt()
            if (listaDeAdyacencia[temp[0].toInt()]!!.indexOf(v) != -1) throw KeyAlreadyExistsException("el objeto esta repetido")
            listaDeAdyacencia[temp[0].toInt()]!!.addFirst(v)
            if (listaDeVertices[v] == null) {
                listaDeVertices[v] = v
            }
            if (listaDeVertices[u] == null) {
                listaDeVertices[u] = u
            }

            i++
        }

    }

    /* Agrega un lado al grafo. Si el lado a agregar contiene
     un vértice que no pertenece al grafo, entonces se lanza una
     RuntimeException. Si el lado no se encuentra 
     en el grafo se agrega y retorna true, en caso contrario 
     no se agraga al grafo y se retorna false. 
     */
    fun agregarArista(a: Arista) : Boolean {

        val indexVertice1 = listaDeVertices[a.u]
        val indexVertice2 = listaDeVertices[a.v]
        if (indexVertice1 == null || indexVertice2 == null){
            throw RuntimeException("El lado a agregar contiene un vertice que no pertenece al grafo")
        }
        if (listaDeAdyacencia[a.u]!!.indexOf(a.v) == -1){
            this.numeroDeLados += 1
            listaDeAdyacencia[a.u]!!.add(a.v)
            return true
        }
        return false

    }

    // Retorna el número de lados del grafo
    override fun obtenerNumeroDeLados() : Int {
        return numeroDeLados
    }

    // Retorna el número de vértices del grafo
    override fun obtenerNumeroDeVertices() : Int {
        return numeDeVertices
    }

    private inner class AdyacenIterable(private val G: GrafoDirigido, private val v: Int) : Iterable<Arista>{

        override fun iterator(): Iterator<Arista> = AdyacenIterato(G, v)

    // Retorna los lados adyacentes al vértice v, es decir, los lados que contienen al vértice v
    override fun adyacentes(v: Int) : Iterable<Arista> {
        if (listaDeVertices[v] == null) throw RuntimeException("no se encuentra el vertice en el grafo")
        return AdyacenIterable(this, v)
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
