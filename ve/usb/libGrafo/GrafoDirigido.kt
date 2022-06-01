package ve.usb.libGrafo
import ve.usb.libGrafo.linkedList.LinkedList
import ve.usb.libGrafo.linkedList.Vertice
import java.io.File


public class GrafoDirigido : Grafo {
    // atributos agregados por nosotros
    var ListaDeAdyacencia: Array<LinkedList?>? = null           // Arreglo, que puede ser nulo, de elementos que puden ser listas enlazadas o nulos
    var numeroDeLados: Int = 0
    lateinit var ListaDeVertices: LinkedList                    // lista enlazada la cual contiene todos los vertices sin repeticiones
    var numeDeVertices = 0


    // Se construye un grafo a partir del número de vértices
    constructor(numDeVertices: Int) {
        // Entrada: un entero que determina el numero de vertices que tendra el grafo
        ListaDeAdyacencia = arrayOfNulls(numDeVertices)
    }



    /** Se construye un grafo a partir de un archivo. El formato es como sigue.
     *  La primera línea es el número de vértices.
     *  La segunda línea es el número de lados. Las siguientes líneas
     *  corresponden a los lados, con los vértices de un lado separados por un espacio en blanco.
     *  Se asume que los datos del archivo están correctos, no se verifican.
     */
    constructor(nombreArchivo: String) {
        /** Entrada: un string de la ubicacion del archivo a buscar
         */

        val a = File(nombreArchivo).readLines()                 // Se guarda el archivo en la variable a de tipo List<String>
        numeDeVertices = nombreArchivo[0].toInt()               // Se obtiene de la primera linea linea el numero de vertices
        //if (ListaDeAdyacencia == null) { ListaDeAdyacencia = arrayOfNulls(numeDeVertices) }
        ListaDeAdyacencia = arrayOfNulls(numeDeVertices)        // Se genera la lista de adyacencia (array de listas enlazadas)
        ListaDeVertices = LinkedList()                          // Se genera la lista de vertices la cual sera una lista enlazada
        numeroDeLados = nombreArchivo[1].toInt()                // Se obtiene de la segunda linea el numero de lados

        var i = 2
        var x: Vertice?
        var temp: List<String>

        while (a[i] != ""){                                     // se itera por las demas lineas del archivo hasta que este se acabe
            temp = a[i].split(" ").filter {it != ""}  // se separa cada linea por espacios

            if (ListaDeAdyacencia!![temp[0].toInt()] == null){
                ListaDeAdyacencia!![temp[0].toInt()] = LinkedList()
            }

            ListaDeAdyacencia!![temp[0].toInt()]!!.List_Insert(ListaDeAdyacencia!![temp[0].toInt()]!!,
                temp[1].toInt(), false)             // se agrega el vertice final en el arreglo especificamente en la posicion del vertice de inicio
            ListaDeVertices.List_Insert(ListaDeVertices, temp[0].toInt(), true)     // se agregan los dos vertices a la lista de vertices
            ListaDeVertices.List_Insert(ListaDeVertices, temp[1].toInt(), true)     // si estos ya se encuentran en la lista el programa continua y no los agrega

            x = ListaDeVertices.List_Search(ListaDeVertices, temp[0].toInt())                   // se buscan los vertices y se aumentan sus respectivos grados interiores y exteriores
            x!!.gradoExterior += 1
            x = ListaDeVertices.List_Search(ListaDeVertices, temp[1].toInt())
            x!!.gradoInterior += 1
            i++
        }
    }

    /** Agrega un lado al digrafo. Si el lado a agregar contiene
     * un vértice que no pertenece al grafo, entonces se lanza una
     * RuntimeException. Si el lado no se encuentra
     * en el grafo se agrega y retorna true, en caso contrario
     * no se agraga al grafo y se retorna false.
     */
    fun agregarArco(a: Arco) : Boolean {
        /** Entrada: un arco a agregar
         *  Salida: Un booleano que indica si el arco ya estaba agregado previamente o no
         */
        val vertice1 = ListaDeVertices.List_Search(ListaDeVertices,a.inicio)
        val vertice2 = ListaDeVertices.List_Search(ListaDeVertices,a.fin)
        if (vertice1 == null || vertice2 == null){          // se verifica que los vertices existan
            throw RuntimeException("El lado a agregar contiene un vertice que no pertenece al grafo")
        }
        if (ListaDeAdyacencia!![a.inicio]!!.List_Search(ListaDeAdyacencia!![a.inicio]!!,a.fin) == null){
            vertice1.gradoExterior += 1
            vertice2.gradoInterior += 1
            this.numeroDeLados += 1
        }
        return ListaDeAdyacencia!![a.inicio]!!.List_Insert(ListaDeAdyacencia!![a.inicio]!!,a.fin, true)
    }

    // Retorna el grado del grafo. Si el vértice no pertenece al grafo se lanza una RuntimeException
    override fun grado(v: Int) : Int {
        /** Entrada: una integral del valor del vertice del cual se pide el grado
         *  Salida: una integral del grado del vertice pedido
         */
        val x = ListaDeVertices.List_Search(ListaDeVertices,v) // se busca el vector
        if (x == null) throw RuntimeException("El lado a agregar contiene un vertice que no pertenece al grafo")
        return x.gradoExterior + x.gradoInterior               // se retorna la suma de los grados interiores y exteriores
    }

    // Retorna el grado exterior del grafo. Si el vértice no pertenece al grafo se lanza una RuntimeException
    fun gradoExterior(v: Int) : Int {
        /** Entrada: una integral del valor del vertice del cual se pide el grado
         *  Salida: una integral del grado del vertice pedido
         */
        val vertice = ListaDeVertices.List_Search(ListaDeVertices,v)    // se busca el vector
        if (vertice == null) throw RuntimeException("El lado a agregar contiene un vertice que no pertenece al grafo")
        return vertice.gradoExterior                                    // se retorna el grado exterior
    }

    // Retorna el grado interior del grafo. Si el vértice no pertenece al grafo se lanza una RuntimeException
    fun gradoInterior(v: Int) : Int {
        /** Entrada: una integral del valor del vertice del cual se pide el grado
         *  Salida: una integral del grado del vertice pedido
         */
        val vertice = ListaDeVertices.List_Search(ListaDeVertices,v)    // se busca el vector
        if (vertice == null) throw RuntimeException("El lado a agregar contiene un vertice que no pertenece al grafo")
        return vertice.gradoInterior                                    // se retorna el grado interior
    }

    // Retorna el número de lados del grafo
    override fun obtenerNumeroDeLados() : Int {
        /**  Salida: una integral del numero de lados del grafo
         */
        return numeroDeLados
    }

    // Retorna el número de vértices del grafo
    override fun obtenerNumeroDeVertices() : Int {
        /**  Salida: una integral del numero de vertices del grafo
         */
        return numeDeVertices
    }

    /** Clase interna que sobreescribe el metodo iterator y lo iguala a AdyacenIterato
     */
    inner class AdyacenIterable(private val G: GrafoDirigido, private val v: Int) : Iterable<Arco>{
        /** Entrada:
         *      El grafo dirigido sobre el que se va a trabajar
         *      una integral la cual se buscaran los vertices adyacentes a esta
         *  Salida: un iterable de arco
         */
        override fun iterator(): Iterator<Arco> = AdyacenIterato(G, v)
    }

    /**
     * Retorna los adyacentes de v, en este caso los lados que tienen como vértice inicial a v.
     * Si el vértice no pertenece al grafo se lanza una RuntimeException
     */
    override fun adyacentes(v: Int) : Iterable<Arco>{
        /** Entrada: una integral la cual se buscaran los vertices adyacentes a esta
         *  Salida: un iterable de los arcos adyacentes a v
         */
        if (ListaDeVertices.List_Search(ListaDeVertices, v) == null) throw RuntimeException("no se encuentra el vertice en el grafo")
        return AdyacenIterable(this, v)
    }

    /** Clase interna que sobreescribe el metodo iterator y lo iguala a LadAdyacenIterato
     */
    inner class LadAdyacenIterable(private val G: GrafoDirigido, private val l: Arco) : Iterable<Arco>{
        /** Entrada:
         *      El grafo dirigido sobre el que se va a trabajar
         *      un arco el cual se buscara los lados adyacentes a este
         *  Salida: un iterable de arco
         */
        override fun iterator(): Iterator<Arco> = LadAdyacenIterato(G, l)
    }


    /** Retorna los lados adyacentes de un lado l.
     * Se tiene que dos lados son iguales si tiene los mismos extremos.
     * Si un lado no pertenece al grafo se lanza una RuntimeException.
     */
    fun ladosAdyacentes(l: Arco) : Iterable<Arco> {
        /** Entrada: un arco el cual se buscara los lados adyacentes a este
         *  Salida: un iterable de los arcos adyacentes al arco l
         */
        // Se revisa si los vertices pertenecen al grafo
        if (ListaDeVertices.List_Search(ListaDeVertices, l.inicio) == null) throw RuntimeException("no se encuentra el vertice en el grafo")
        if (ListaDeVertices.List_Search(ListaDeVertices, l.fin) == null) throw RuntimeException("no se encuentra el vertice en el grafo")

        val temp = ListaDeAdyacencia!![l.inicio]!! // se verifica si el arco existe
        if (temp.List_Search(temp, l.fin) == null) throw RuntimeException("no se encuentra el lado en el grafo")

        return LadAdyacenIterable(this, l)
    }
    
    // Retorna todos los lados del digrafo
    override operator fun iterator() : Iterator<Arco> = LadosIterato(this)
    /** Salida: un iterador de arcos que iterara sobre todos los lados del grafo
     */
     
    // Retorna un String que muestra el contenido del grafo
    override fun toString() : String {
        /** Salida: un string que muestra el contenido del grafo
         */
        val it = iterator()
        var result = "[ "
        while (it.hasNext()){
            result += it.next().toString()
        }
        result += "]"
        return result
     }
}
