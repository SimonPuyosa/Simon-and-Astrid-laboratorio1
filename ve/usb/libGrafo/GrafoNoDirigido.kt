package ve.usb.libGrafo

import ve.usb.libGrafo.linkedList.LinkedList
import ve.usb.libGrafo.linkedList.Vertice
import java.io.File

public class GrafoNoDirigido: Grafo {
    //atributos añadidos por nosotros
    var ListaDeAdyacencia: Array<LinkedList?>? = null
    var numeroDeLados: Int = 0
    var ListaDeVertices: LinkedList()

    // Se construye un grafo a partir del número de vértices
    constructor(numDeVertices: Int) {
        ListaDeAdyacencia = arrayOfNulls(numDeVertices)
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
        val numVertices = nombreArchivo[0].toInt()
        if(ListaDeAdyacencia == null){ListaDeAdyacencia = arrayOfNulls(numVertices)}
        numeroDeLados = nombreArchivo[1].toInt()
        var i = 2
        var x: Vertice
        while(a[i] != ""){
            val helper = a[i].split(" ").filter{it!=""}
            if (ListaDeAdyacencia!![helper[0].toInt()] == null){
                ListaDeAdyacencia!![helper[0].toInt()] = LinkedList()
            }
            ListaDeAdyacencia!![helper[0].toInt()]!!.List_Insert(ListaDeAdyacencia!![helper[0].toInt()]!!, helper[1].toInt(), false)
            ListaDeVertices.List_Insert(ListaDeVertices, helper[0].toInt(), true)
            x = ListaDeVertices.List_Search(ListaDeVertices, helper[0].toInt())!!
            x.gradoExterior += 1
            x = ListaDeVertices.List_Search(ListaDeVertices, helper[1].toInt())!!
            x.gradoInterior += 1
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
        val vertice1 = ListaDeVertices.List_Search(ListaDeVertices, a.v)
        val vertice2 = ListaDeVertices.List_Search(ListaDeVertices, a.u)

    }

    // Retorna el número de lados del grafo
    override fun obtenerNumeroDeLados() : Int {
        return numeroDeLados
    }

    // Retorna el número de vértices del grafo
    override fun obtenerNumeroDeVertices() : Int {
        var n = 0
        var cabeza: Vertice? = ListaDeVertices.head
        while (cabeza != null && cabeza.valor != null){
            n += 1
            cabeza = cabeza.next
        }
        return n
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
