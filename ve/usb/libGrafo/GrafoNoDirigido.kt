package ve.usb.libGrafo
import java.io.File
import java.util.*
import javax.management.openmbean.KeyAlreadyExistsException

public class GrafoNoDirigido: Grafo {
    // Propiedades del grafo no dirigido
    var listaDeAdyacencia: Array<LinkedList<Vertice>?> = arrayOf(null)
    lateinit var listaDeVertices: Array<Vertice?>
    var numeroDeLados: Int = 0
    var numeDeVertices: Int = 0

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

        val a = File(nombreArchivo).readLines()                   // Se guarda el archivo en la variable a de tipo List<String>
        numeDeVertices = a[0].toInt()                             // Se obtiene de la primera linea linea el numero de vertices
        listaDeAdyacencia = arrayOfNulls(numeDeVertices)          // Se genera la lista de adyacencia (array de listas enlazadas)
        listaDeVertices = arrayOfNulls(numeDeVertices)            // Se genera la lista de vertices la cual sera una lista enlazada
        numeroDeLados = a[1].toInt()                              // Se obtiene de la segunda linea el numero de lados

        var i = 2
        var u: Int
        var v: Int
        var temp: List<String>
        var vertice1: Vertice
        var vertice2: Vertice

        while (i < 2 + numeroDeLados && a[i] != ""){                // se itera por las demas lineas del archivo hasta que este se acabe
            temp = a[i].split(" ").filter {it != ""}      // se separa cada linea por espacios

            u = temp[0].toInt()
            v = temp[1].toInt()
            vertice1 = Vertice(u)
            vertice2 = Vertice(v)

            if (listaDeAdyacencia[u] == null){        // si el elemento del arreglo es nulo se le asigna un linked list vacio
                listaDeAdyacencia[u] = LinkedList<Vertice>()
            }
            if (listaDeAdyacencia[v] == null){        // si el elemento del arreglo es nulo se le asigna un linked list vacio
                listaDeAdyacencia[v] = LinkedList<Vertice>()
            }

            if (listaDeAdyacencia[u]!!.indexOf(vertice2) != -1) throw KeyAlreadyExistsException("el objeto esta repetido")
            listaDeAdyacencia[u]!!.addFirst(vertice2)
            if (listaDeAdyacencia[v]!!.indexOf(vertice1) != -1) throw KeyAlreadyExistsException("el objeto esta repetido")
            listaDeAdyacencia[v]!!.addFirst(vertice1)
            if (listaDeVertices[u] == null) {
                listaDeVertices[u] = vertice1
            }
            if (listaDeVertices[v] == null) {
                listaDeVertices[v] = vertice2
            }

            listaDeVertices[u]!!.gradoExterior += 1       // se buscan los vertices y se aumentan sus respectivos grados interiores y exteriores
            listaDeVertices[v]!!.gradoExterior += 1
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
        if (listaDeVertices[a.v] == null || listaDeVertices[a.u] == null){
            throw RuntimeException("El lado a agregar contiene un vertice que no pertenece al grafo")
        }
        if (listaDeAdyacencia[a.v]!!.indexOf(Vertice(a.u)) == -1){
            listaDeVertices[a.v]!!.gradoExterior += 1
            listaDeVertices[a.u]!!.gradoExterior += 1
            this.numeroDeLados += 1
            listaDeAdyacencia[a.v]!!.addFirst(Vertice(a.u))
            listaDeAdyacencia[a.u]!!.addFirst(Vertice(a.v))
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

    /**
     *  clase que dada una tabla de hash retorna un iterador
     */
    private inner class AdyacenIterato(G: GrafoNoDirigido, private val v: Int) : Iterator<Arista> {

        private val adyacentes = G.listaDeAdyacencia[v]!!
        private var i = 0

        override fun hasNext(): Boolean {
            if (adyacentes.size <= i) return false
            return true
        }

        override fun next(): Arista {
            val result = Arista(v, adyacentes[i].valor)
            i += 1
            return result
        }
    }

    /** Clase interna que sobreescribe el metodo iterator y lo iguala a AdyacenIterato
     */
    private inner class AdyacenIterable(private val G: GrafoNoDirigido, private val v: Int) : Iterable<Arista> {

        override fun iterator(): Iterator<Arista> = AdyacenIterato(G, v)
    }

    // Retorna los lados adyacentes al vértice v, es decir, los lados que contienen al vértice v
    override fun adyacentes(v: Int) : Iterable<Arista> {
        if (listaDeVertices[v] == null) throw RuntimeException("no se encuentra el vertice en el grafo")
        return AdyacenIterable(this, v)
    }

    /** clase que dada una tabla de hash retorna un iterador
    */
    private inner class LadAdyacenIterato(private val G: GrafoNoDirigido, private val n: Arista) : Iterator<Arista> {

        private var temp: Array<LinkedList<Vertice>?> = G.listaDeAdyacencia
        private var actual: LinkedList<Vertice>? = null
        private var i = 0
        private var j: Int = 0

        override fun hasNext(): Boolean {
            while (i < G.numeDeVertices){
                if (temp[i] != null){
                    j = 0
                    actual = temp[i]
                    while(actual != null && j < actual!!.size){
                        if (actual!![j].valor == n.v) return true
                        j += 1
                    }
                }
                i++
            }
            return false
        }

        override fun next(): Arista {
            val result = Arista(i, n.v)
            i += 1
            return result
        }
    }

    /** Clase interna que sobreescribe el metodo iterator y lo iguala a LadAdyacenIterato
     */
    private inner class LadAdyacenIterable(private val G: GrafoNoDirigido, private val l: Arista) : Iterable<Arista>{

        override fun iterator(): Iterator<Arista> = LadAdyacenIterato(G, l)
    }

    /* Retorna los lados adyacentes de un lado l. 
     Se tiene que dos lados son iguales si tiene los mismos extremos. 
     Si un lado no pertenece al grafo se lanza una RuntimeException.
     */
    fun ladosAdyacentes(l: Arista) : Iterable<Arista> {
        // Se revisa si los vertices pertenecen al grafo
        if (listaDeVertices[l.v] == null) throw RuntimeException("no se encuentra el vertice en el grafo")
        if (listaDeVertices[l.u] == null) throw RuntimeException("no se encuentra el vertice en el grafo")

        val temp = listaDeAdyacencia[l.v]!! // se verifica si el arco existe
        if (temp.indexOf(Vertice(l.u)) == -1) throw RuntimeException("no se encuentra el lado en el grafo")

        return LadAdyacenIterable(this, l)
    }

    /**
     *  clase que dada una tabla de hash retorna un iterador
     */
    class LadosIterato(private val G: GrafoNoDirigido) : Iterator<Arista> {

        private val temp: Array<LinkedList<Vertice>?> = G.listaDeAdyacencia
        private var actual: LinkedList<Vertice>? = null
        private var i = 0
        private var j: Int = 0

        override fun hasNext(): Boolean {
            while (i < G.numeDeVertices){
                if (temp[i] != null){
                    actual = temp[i]
                    if (actual != null && j < actual!!.size){
                        return true
                    }
                }
                j = 0
                i++
            }
            return false
        }

        override fun next(): Arista {
            val result = Arista(i, actual!![j].valor)
            j += 1
            return result
        }
    }

    // Retorna todos los lados del grafo no dirigido
    override operator fun iterator() : Iterator<Arista> = LadosIterato(this)

    // Grado del grafo
    override fun grado(v: Int) : Int {

        if (listaDeVertices[v] == null) throw RuntimeException("El lado a agregar contiene un vertice que no pertenece al grafo")
        if (listaDeAdyacencia[v] == null) return 0
        return listaDeVertices[v]!!.gradoExterior                                    // se retorna el grado exterior
    }


    // Retorna un string con una representación del grafo, en donde se nuestra todo su contenido
    override fun toString() : String {
        val it = iterator()
        var result = "[ "
        while (it.hasNext()){
            result += it.next().toString()
        }
        result += "]"
        return result
    }
}
