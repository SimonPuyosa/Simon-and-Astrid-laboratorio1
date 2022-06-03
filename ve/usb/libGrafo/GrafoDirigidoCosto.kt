package ve.usb.libGrafo
import java.io.File
import java.util.*
import javax.management.openmbean.KeyAlreadyExistsException

public class GrafoDirigidoCosto : Grafo {
    // Propiedades del grafo dirigido
    var listaDeAdyacencia: Array<LinkedList<Vertice>?> = arrayOf(null)     // Arreglo, que puede ser nulo, de elementos que puden ser listas enlazadas o nulos
    lateinit var listaDeVertices: Array<Vertice?>                 // arreglo que contiene todos los vertices sin repeticiones
    var numDeLados: Int = 0
    var numDeVertices: Int = 0

    // Se construye un grafo a partir del número de vértices
    constructor(numDeVertices: Int) {
        /** Entrada: un entero que determina el numero de vertices que tendra el grafo
         *  Precondicion: numDeVertices > 0
         *  Postcondicion: listaDeAdyacencia.size() == numDeVertices
         *  Tiempo: de operacion: O(|V|)
         */
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
        /** Entrada: un string de la ubicacion del archivo a buscar
         *  Precondicion: el string debe tener una ubicacion real y valida del archivo
         *  Postcondicion:
         *      numDeVertice = Archivo[0]
         *      numDeLados = Archivo[1]
         *      listaDeAdyacencia.sizes == listaDeVertices.size == numDeVertices
         *  Tiempo de operacion: O(|V| + |E|)
         */
        val a =
            File(nombreArchivo).readLines()                 // Se guarda el archivo en la variable a de tipo List<String>
        numDeVertices =
            a[0].toInt()                            // Se obtiene de la primera linea linea el numero de vertices
        listaDeAdyacencia =
            arrayOfNulls(numDeVertices)         // Se genera la lista de adyacencia (array de listas enlazadas)
        listaDeVertices =
            arrayOfNulls(numDeVertices)           // Se genera la lista de vertices la cual sera una lista enlazada
        numDeLados = a[1].toInt()                               // Se obtiene de la segunda linea el numero de lados

        // Agregamos los lados a la lista de adyacencia
        var i = 2
        var v: Int
        var u: Int
        var temp: List<String>
        var vertice1: Vertice
        var vertice2: Vertice

        while (i < 2 + numDeLados && a[i] != "") {                // se itera por las demas lineas del archivo hasta que este se acabe
            temp = a[i].split(" ").filter { it != "" }   // se separa cada linea por espacios

            u = temp[0].toInt()
            v = temp[1].toInt()
            vertice1 = Vertice(u)
            vertice2 = Vertice(v)

            if (listaDeAdyacencia[u] == null) {     // si el elemento del arreglo es nulo se le asigna un linked list vacio
                listaDeAdyacencia[u] = LinkedList<Vertice>()
            }

            if (listaDeAdyacencia[u]!!.indexOf(vertice2) != -1) throw KeyAlreadyExistsException("el objeto esta repetido")
            listaDeAdyacencia[u]!!.addFirst(vertice2)
            listaDeAdyacencia[u]!![0].Costo = temp[2].toDouble() // buenisima no se me hubiera ocurrido
            if (listaDeVertices[u] == null) {
                listaDeVertices[u] = vertice1
            }
            if (listaDeVertices[v] == null) {
                listaDeVertices[v] = vertice2
            }

            listaDeVertices[u]!!.gradoExterior += 1       // se buscan los vertices y se aumentan sus respectivos grados interiores y exteriores
            listaDeVertices[v]!!.gradoInterior += 1
            i++
        }
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
    fun agregarArcoCosto(a: ArcoCosto): Boolean {
        if (listaDeVertices[a.inicio] == null || listaDeVertices[a.fin] == null) {
            throw RuntimeException("El lado a agregar contiene un vertice que no pertenece al grafo")
        }
        if (listaDeAdyacencia[a.inicio]!!.indexOf(Vertice(a.fin)) == -1) {
            this.numDeLados += 1
            listaDeVertices[a.inicio]!!.gradoExterior += 1
            listaDeVertices[a.fin]!!.gradoInterior += 1
            listaDeAdyacencia[a.inicio]!!.addFirst(Vertice(a.fin))
            listaDeAdyacencia[a.inicio]!![0].Costo = a.costo // te falto esta pero la copie de la de arriba
            return true
        }
        return false
    }

    // Retorna el grado del grafo. Si el vértice no pertenece al grafo se lanza una RuntimeException
    override fun grado(v: Int): Int {
        if (listaDeVertices[v] == null) throw RuntimeException("El lado a agregar contiene un vertice que no pertenece al grafo")
        val x = listaDeVertices[v]
        return x!!.gradoExterior + x.gradoInterior
    }

    // Retorna el grado exterior del grafo. Si el vértice no pertenece al grafo se lanza una RuntimeException
    fun gradoExterior(v: Int): Int {
        if (listaDeVertices[v] == null) throw RuntimeException("El lado a agregar contiene un vertice que no pertenece al grafo")
        val x = listaDeVertices[v]
        return x!!.gradoExterior
    }

    // Retorna el grado interior del grafo. Si el vértice no pertenece al grafo se lanza una RuntimeException
    fun gradoInterior(v: Int): Int {
        if (listaDeVertices[v] == null) throw RuntimeException("El lado a agregar contiene un vertice que no pertenece al grafo")
        val x = listaDeVertices[v]
        return x!!.gradoInterior
    }

    // Retorna el número de lados del grafo
    override fun obtenerNumeroDeLados(): Int {
        return numDeLados
    }

    // Retorna el número de vértices del grafo
    override fun obtenerNumeroDeVertices(): Int {
        return numDeLados
    }

    /**  clase interna y privada que dado un digrafo y un entero que representa el valor de un vertice del digrafo,
     *   se retorna un iterador de arcos en los que el integral dado es el valor del vertice adyacente
     */
    private inner class AdyacenIterato(G: GrafoDirigidoCosto, private val v: Int) : Iterator<ArcoCosto> {

        private val adyacentes = G.listaDeAdyacencia[v]!!
        private var i = 0

        override fun hasNext(): Boolean {
            if (adyacentes.size <= i) return false
            return true
        }

        override fun next(): ArcoCosto {
            val result = ArcoCosto(v, adyacentes[i].valor, adyacentes[i].Costo)
            i += 1
            return result
        }
    }

    /** Clase interna y privada que sobreescribe el metodo iterator y lo iguala a la clase AdyacenIterato
     */
    private inner class AdyacenIterable(private val G: GrafoDirigidoCosto, private val v: Int) : Iterable<ArcoCosto> {

        override fun iterator(): Iterator<ArcoCosto> = AdyacenIterato(G, v)
    }

    /* 
     Retorna los adyacentes de v, en este caso los lados que tienen como vértice inicial a v. 
     Si el vértice no pertenece al grafo se lanza una RuntimeException
     */
    override fun adyacentes(v: Int): Iterable<ArcoCosto> {
        if (listaDeVertices[v] == null) throw RuntimeException("no se encuentra el vertice en el grafo")
        return AdyacenIterable(this, v)
    }


    /**  clase interna y privada que dado un digrafo y un Arco que representa un lado del digrafo,
     *   se retorna un iterador de Arcos adyacentes al Arco dado
     */
    private inner class LadAdyacenIterato(private val G: GrafoDirigidoCosto, private val v: ArcoCosto) :
        Iterator<ArcoCosto> {

        private var adyacList: Array<LinkedList<Vertice>?> = G.listaDeAdyacencia
        private var actual: LinkedList<Vertice>? = null
        private var i = 0
        private var j: Int = 0

        override fun hasNext(): Boolean {
            while (i < G.numDeVertices) {
                if (adyacList[i] != null) {
                    j = 0
                    actual = adyacList[i]
                    while (actual != null && j < actual!!.size) {
                        if (actual!![j].valor == v.x) return true
                        j += 1
                    }
                }
                i++
            }
            return false
        }

        override fun next(): ArcoCosto {
            val result = ArcoCosto(i, v.x, actual!![j].Costo)
            i += 1
            return result
        }
    }

    /** Clase interna y privada que sobreescribe el metodo iterator y lo iguala a la clase LadAdyacenIterato
     */
    private inner class LadAdyacenIterable(private val G: GrafoDirigidoCosto, private val l: ArcoCosto) :
        Iterable<ArcoCosto> {

        override fun iterator(): Iterator<ArcoCosto> = LadAdyacenIterato(G, l)
    }


    /* Retorna los lados adyacentes de un lado l. 
     Se tiene que dos lados son iguales si tiene los mismos extremos. 
     Si un lado no pertenece al grafo se lanza una RuntimeException.
     */
    fun ladosAdyacentes(l: ArcoCosto): Iterable<ArcoCosto> {
        if (listaDeVertices[l.x] == null) throw RuntimeException("no se encuentra el vertice en el grafo")
        if (listaDeVertices[l.y] == null) throw RuntimeException("no se encuentra el vertice en el grafo")

        val temp = listaDeAdyacencia[l.x]!!
        if (temp.indexOf(Vertice(l.y)) == -1) throw RuntimeException("no se encuentra el lado en el grafo")

        return LadAdyacenIterable(this, l)
    }

    /**  clase interna y privada que dado un digrafo retorna un iterador de todos los Arcos pertenecientes al digrafo
     */
    private inner class LadosIterato(private val G: GrafoDirigidoCosto) : Iterator<ArcoCosto> {

        private val temp: Array<LinkedList<Vertice>?> = G.listaDeAdyacencia
        private var actual: LinkedList<Vertice>? = null
        private var i = 0
        private var j: Int = 0

        override fun hasNext(): Boolean {
            while (i < G.numDeVertices) {                            // se itera sobre el numero de vertices
                if (temp[i] != null) {                               // Si la casilla del arreglo listaDeAdyaciencia es nula es porque ese vertice no tiene un vertice adyacente
                    actual = temp[i]
                    if (actual != null && j < actual!!.size) {       // Se itera a lo largo de la linkedlist hasta conseguir un vertice nulo
                        return true
                    }
                }
                j = 0
                i++
            }
            return false
        }

        override fun next(): ArcoCosto {
            val result = ArcoCosto(i, actual!![j].valor, actual!![j].Costo)                      // se prepara la variable de salida
            j += 1                                                        // una vez encontrado un vertice adyacente se busca otro vertice adyacente en el otro linkedlist del arreglo
            return result
        }
    }

    // Retorna todos los lados del digrafo con costo
    override operator fun iterator(): Iterator<ArcoCosto> = LadosIterato(this)

    // String que muestra el contenido del grafo
    override fun toString(): String {
        val it = iterator()
        var result = "[ "
        while (it.hasNext()) {
            result += it.next().toString()
        }
        result += "]"
        return result
    }
}

