package ve.usb.libGrafo

import java.io.File
import linkedList.LinkedList
import linkedList.Vertice

public class GrafoDirigidoCosto : Grafo {
    //atributos agregados por nosotros
    var ListaDeAdyacencia: Array<LinkedList?>? = null
    var NumeroDeLados: Int = 0
    var ListaDeVertices = LinkedList()
    var ListaDeCostos = LinkedList()

    // Se construye un grafo a partir del número de vértices
    constructor(numDeVertices: Int) {
        ListaDeAdyacencia = arrayOfNulls(numDeVertices)
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
        val a = File(nombreArchivo).readLines()
        val numDeVertices = nombreArchivo[0].toInt()
        if (ListaDeAdyacencia == null) { ListaDeAdyacencia = arrayOfNulls(numDeVertices) }
        NumeroDeLados = nombreArchivo[1].toInt()
        var i = 2
        var x: Vertice
        while (a[i] != ""){
            val temp = a[i].split(" ").filter {it != ""}
            if (ListaDeAdyacencia!![temp[0].toInt()] == null){
                ListaDeAdyacencia!![temp[0].toInt()] = LinkedList()
            }
            ListaDeAdyacencia!![temp[0].toInt()]!!.List_Insert(ListaDeAdyacencia!![temp[0].toInt()]!!, temp[1].toInt(), false)
            ListaDeVertices.List_Insert(ListaDeVertices, temp[0].toInt(), true)
            ListaDeVertices.List_Insert(ListaDeVertices, temp[1].toInt(), true)
            x = ListaDeVertices.List_Search(ListaDeVertices, temp[0].toInt())!!
            x.gradoExterior += 1
            x = ListaDeVertices.List_Search(ListaDeVertices, temp[1].toInt())!!
            x.gradoInterior += 1
            ListaDeCostos.List_Insert(ListaDeCostos, temp[2].toInt(), true)
            i++
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
        val vertice1 = ListaDeVertices.List_Search(ListaDeVertices,a.x)
        val vertice2 = ListaDeVertices.List_Search(ListaDeVertices,a.y)
        if (vertice1 == null){
            throw RuntimeException("El lado a agregar contiene un vertice que no pertenece al grafo")
        }
        if (vertice2 == null){
            throw RuntimeException("El lado a agregar contiene un vertice que no pertenece al grafo")
        }
        if(ListaDeAdyacencia!![a.x]!!.List_Search(ListaDeAdyacencia!![a.x]!!, a.y)?.valor != a.y ){
            vertice1.gradoExterior += 1
            vertice2.gradoInterior += 1
            NumeroDeLados += 1
            ListaDeCostos.List_Insert(ListaDeCostos, a.costo, true)
        }
        return ListaDeAdyacencia!![a.x]!!.List_Insert(ListaDeAdyacencia!![a.x]!!,a.y, false)
    }

    // Retorna el grado del grafo. Si el vértice no pertenece al grafo se lanza una RuntimeException
    override fun grado(v: Int) : Int {
    }

    // Retorna el grado exterior del grafo. Si el vértice no pertenece al grafo se lanza una RuntimeException
    fun gradoExterior(v: Int) : Int {
        val vertice = ListaDeVertices.List_Search(ListaDeVertices, v)
        if(vertice == null){
            throw RuntimeException("El vertice no pertenece al grafo")
        }
        return vertice.gradoExterior
    }

    // Retorna el grado interior del grafo. Si el vértice no pertenece al grafo se lanza una RuntimeException
    fun gradoInterior(v: Int) : Int {
        val vertice = ListaDeVertices.List_Search(ListaDeVertices, v)
        if(vertice == null){
            throw RuntimeException("El vertice no pertenece al grafo")
        }
        return vertice.gradoInterior
    }

    // Retorna el número de lados del grafo
    override fun obtenerNumeroDeLados() : Int {
        return NumeroDeLados
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
