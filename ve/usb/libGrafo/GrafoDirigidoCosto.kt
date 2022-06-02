package ve.usb.libGrafo

import java.io.File
import java.util.*
import javax.management.openmbean.KeyAlreadyExistsException

public class GrafoDirigidoCosto : Grafo {
    // Propiedades del grafo dirigido
    var listaDeAdyacencia: Array<LinkedList<Vertice>?> = arrayOf(LinkedList())       // Arreglo, que puede ser nulo, de elementos que puden ser listas enlazadas o nulos
    var numDeLados: Int = 0
    lateinit var listaDeVertices: Array<Vertice?>                 // arreglo que contiene todos los vertices sin repeticiones
    var numDeVertices = 0

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
    constructor(nombreArchivo: String)  {
        /** Entrada: un string de la ubicacion del archivo a buscar
         *  Precondicion: el string debe tener una ubicacion real y valida del archivo
         *  Postcondicion:
         *      numDeVertice = Archivo[0]
         *      numDeLados = Archivo[1]
         *      listaDeAdyacencia.sizes == listaDeVertices.size == numDeVertices
         *  Tiempo de operacion: O(|V| + |E|)
         */
        val a = File(nombreArchivo).readLines()                 // Se guarda el archivo en la variable a de tipo List<String>
        numDeVertices = a[0].toInt()                            // Se obtiene de la primera linea linea el numero de vertices
        listaDeAdyacencia = arrayOfNulls(numDeVertices)         // Se genera la lista de adyacencia (array de listas enlazadas)
        listaDeVertices = arrayOfNulls(numDeVertices)           // Se genera la lista de vertices la cual sera una lista enlazada
        numDeLados = a[1].toInt()                               // Se obtiene de la segunda linea el numero de lados

        // Agregamos los lados a la lista de adyacencia
        var i = 2
        var v: Int
        var u: Int
        var temp: List<String>
        var vertice1: Vertice
        var vertice2: Vertice
        var j: Int

        while (i < 2 + numDeLados && a[i] != ""){                // se itera por las demas lineas del archivo hasta que este se acabe
            temp = a[i].split(" ").filter {it != ""}   // se separa cada linea por espacios

            if (listaDeAdyacencia[temp[0].toInt()] == null){     // si el elemento del arreglo es nulo se le asigna un linked list vacio
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

            j = listaDeVertices.indexOf(vertice1)              // se buscan los vertices y se aumentan sus respectivos grados interiores y exteriores
            listaDeVertices[j]!!.gradoExterior +=1
            j = listaDeVertices.indexOf(vertice2)
            listaDeVertices[j]!!.gradoInterior +=1
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
        return numDeLados
    }

    // Retorna el número de vértices del grafo
    override fun obtenerNumeroDeVertices() : Int {
        return numDeLados
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
