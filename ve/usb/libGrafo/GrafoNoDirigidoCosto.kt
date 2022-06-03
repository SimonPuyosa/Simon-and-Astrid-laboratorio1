package ve.usb.libGrafo

import java.io.File
import java.util.*
import javax.management.openmbean.KeyAlreadyExistsException

public class GrafoNoDirigidoCosto: Grafo {
    // Propiedades del grafo no dirigido
    var listaDeAdyacencia: Array<LinkedList<Vertice>?> = arrayOf(LinkedList())       // Arreglo, que puede ser nulo, de elementos que puden ser listas enlazadas o nulos
    var numeroDeLados: Int = 0
    lateinit var listaDeVertices: Array<Vertice?>                 // arreglo que contiene todos los vertices sin repeticiones
    var numeDeVertices = 0

    // Se construye un grafo a partir del número de vértices
    constructor(numDeVertices: Int) {
        listaDeAdyacencia = arrayOfNulls(numDeVertices)
    }

    /*
     Se construye un grafo a partir de un archivo. El formato es como sigue.
     La primera línea es el número de vértices. La segunda línea es el número de lados. 
     Las siguientes líneas corresponden a los lados. Cada línea de los lados tiene a dos enteros
     que corresponden a los vértices inicial y final de los lados,
     y un número real que es el costo del lado.
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
        var j: Int

        while (i < 2 + numeroDeLados && a[i] != ""){                // se itera por las demas lineas del archivo hasta que este se acabe
            temp = a[i].split(" ").filter {it != ""}      // se separa cada linea por espacios

            if (listaDeAdyacencia[temp[0].toInt()] == null){        // si el elemento del arreglo es nulo se le asigna un linked list vacio
                listaDeAdyacencia[temp[0].toInt()] = LinkedList<Vertice>()
            }

            v = temp[1].toInt()
            u = temp[0].toInt()
            vertice2 = Vertice(v)
            vertice1 = Vertice(u)

            if (listaDeAdyacencia[u]!!.indexOf(vertice1) != -1) throw KeyAlreadyExistsException("el objeto esta repetido")
            listaDeAdyacencia[temp[u].toInt()]!!.addFirst(vertice2)       // se agrega el vertice 2, al inicio del arreglo
            listaDeAdyacencia[temp[u].toInt()]!![0].Costo = temp[2].toDouble()
            if (listaDeVertices[u] == null) {
                listaDeVertices[u] = vertice1             // se agregan los dos vertices a la lista de vertices
            }
            if (listaDeVertices[v] == null) {             // si estos ya se encuentran en la lista de vectores el programa continua y no los agrega
                listaDeVertices[v] = vertice2
            }

            j = listaDeVertices.indexOf(vertice1)            // se buscan los vertices y se aumentan sus respectivos grados interiores y exteriores
            listaDeVertices[j]!!.gradoExterior +=1
            j = listaDeVertices.indexOf(vertice2)
            listaDeVertices[j]!!.gradoInterior +=1
            i++
        }
    }

    /* Agrega un lado al grafo. Si el lado a agregar contiene
     un vértice que no pertenece al grafo, entonces se lanza una
     RuntimeException. Si el lado no se encuentra 
     en el grafo se agrega y retorna true, en caso contrario 
     no se agraga al grafo y se retorna false. 
     Si hay un lado en el grafo con los mismos extremos del lado
     que se quiere agregar, entonces se asume que el lado ya
     está en el grafo, sin importar que tengan diferentes costos.
     */
    fun agregarAristaCosto(a: AristaCosto) : Boolean {
        val indexVertice1 = listaDeVertices.indexOf(Vertice(a.x))
        val indexVertice2 = listaDeVertices.indexOf(Vertice(a.y))
        if (indexVertice1 == -1 || indexVertice2 == -1){
            throw RuntimeException("El lado a agregar contiene un vertice que no pertenece al grafo")
        }
        if (listaDeAdyacencia[a.x]!!.indexOf(Vertice(a.y)) == -1){
            listaDeVertices[indexVertice1]!!.gradoExterior += 1
            listaDeVertices[indexVertice2]!!.gradoInterior += 1
            this.numeroDeLados += 1
            listaDeAdyacencia[a.x]!!.add(Vertice(a.y))
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
    private inner class AdyacenIterato(G: GrafoNoDirigidoCosto, private val v: Int) : Iterator<AristaCosto> {

        private val adyacentes = G.listaDeAdyacencia[v]!!
        private var i = 0

        override fun hasNext(): Boolean {
            if (adyacentes.size <= i) return false
            return true
        }

        override fun next(): AristaCosto {
            val result = AristaCosto(v, adyacentes[i].valor)
            i += 1
            return result
        }
    }

    /** Clase interna que sobreescribe el metodo iterator y lo iguala a AdyacenIterato
     */
    private inner class AdyacenIterable(private val G: GrafoNoDirigidoCosto, private val v: Int) : Iterable<AristaCosto> {

        override fun iterator(): Iterator<AristaCosto> = AdyacenIterato(G, v)
    }

    // Retorna los lados adyacentes al vértice v, es decir, los lados que contienen al vértice v
    override fun adyacentes(v: Int) : Iterable<AristaCosto> {
        if (listaDeVertices[v] == null) throw RuntimeException("no se encuentra el vertice en el grafo")
        return AdyacenIterable(this, v)
    }

    /** clase que dada una tabla de hash retorna un iterador
     */
    private inner class LadAdyacenIterato(private val G: GrafoNoDirigidoCosto, private val n: AristaCosto) : Iterator<AristaCosto> {

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
                        if (actual!![j].valor == n.x) return true
                        j += 1
                    }
                }
                i++
            }
            return false
        }

        override fun next(): AristaCosto {
            val result = AristaCosto(i, n.x)
            i += 1
            return result
        }
    }

    /** Clase interna que sobreescribe el metodo iterator y lo iguala a LadAdyacenIterato
     */
    private inner class LadAdyacenIterable(private val G: GrafoNoDirigidoCosto, private val l: AristaCosto) : Iterable<AristaCosto>{

        override fun iterator(): Iterator<AristaCosto> = LadAdyacenIterato(G, l)
    }

    /* Retorna los lados adyacentes de un lado l. 
     Se tiene que dos lados son iguales si tiene los mismos extremos. 
     Si un lado no pertenece al grafo se lanza una RuntimeException.
     */
    fun ladosAdyacentes(l: AristaCosto) : Iterable<AristaCosto> {
        if (listaDeVertices[l.x] == null) throw RuntimeException("no se encuentra el vertice en el grafo")
        if (listaDeVertices[l.y] == null) throw RuntimeException("no se encuentra el vertice en el grafo")

        val temp = listaDeAdyacencia[l.x]!!
        if (temp.indexOf(Vertice(l.y)) == -1) throw RuntimeException("no se encuentra el lado en el grafo")

        return LadAdyacenIterable(this, l)
    }

    /**
     *  clase que dada una tabla de hash retorna un iterador
     */
    class LadosIterato(private val G: GrafoNoDirigidoCosto) : Iterator<AristaCosto> {

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

        override fun next(): AristaCosto {
            val result = AristaCosto(i, actual!![j].valor)
            j += 1
            return result
        }
    }

    // Retorna todos los lados del grafo no dirigido
     override operator fun iterator() : Iterator<AristaCosto> = LadosIterato(this)
    
    // Grado del grafo
    override fun grado(v: Int) : Int {
        if (listaDeVertices[v] == null) throw RuntimeException("El lado a agregar contiene un vertice que no pertenece al grafo")
        if (listaDeAdyacencia[v] == null) return 0
        return listaDeAdyacencia[v]!!.size
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
