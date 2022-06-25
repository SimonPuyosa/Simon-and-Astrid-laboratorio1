package ve.usb.libGrafo
import java.io.File
import java.util.*
import javax.management.openmbean.KeyAlreadyExistsException

/** Clase que dado la ubicacion de un archivo que contiene los datos de un grafo no dirigido,
 *  identificando a los vertices como string, genera un mapeo para crear un grafo no dirigido de
 *  enteros a traves de la tabla del mapeo
 */
public class TablaGrafoNoDirigido(val nombreArchivo: String) {
    /** Entrada: un string de la ubicacion del archivo
     *  Precondicion: Exist File(nombreArchivo)
     *  Postcondicion: grafoDeSalida.listaDeVertices.size = File(nombreArchivo).readLines()[0]
     *  Tiempo: O(|E|)
     */
    var listaDeAdyacencia: Array<LinkedList<Vertice>?> = arrayOf(null)
    var listaDeVertices: Array<Vertice>
    var numDeLados: Int = 0
    var numDeVertices: Int = 0
    var mapeo: Map<Int, String>
    var mapeoDeVuelta: Map<String, Int>
    var grafoDeSalida: GrafoNoDirigido

    init {
        val a = File(nombreArchivo).readLines()                 // Se guarda el archivo en la variable a de tipo List<String>
        numDeVertices = a[0].toInt()                            // Se obtiene de la primera linea linea el numero de vertices
        listaDeAdyacencia = arrayOfNulls(numDeVertices)         // Se genera la lista de adyacencia (array de listas enlazadas)
        listaDeVertices = Array(numDeVertices) { Vertice(it) }  // Se genera un arreglo de vertices
        numDeLados = a[1].toInt()                               // Se obtiene de la segunda linea el numero de lados

        var temp: List<String>
        temp = a[2].split("\t").filter {it != ""}   // se separa cada linea por tabulaciones
        mapeo = mutableMapOf<Int, String>().apply { for (i in 0 until numDeVertices) this[i] = temp[i] }
        mapeoDeVuelta = mutableMapOf<String, Int>().apply { for (i in 0 until numDeVertices) this[temp[i]] = i }
        grafoDeSalida = GrafoNoDirigido(numDeVertices)


        // Agregamos los lados a la lista de adyacencia
        var i = 3
        var v: Int
        var u: Int
        while (i < 3 + numDeLados && a[i] != ""){            // se itera por las demas lineas del archivo hasta que este se acabe
            temp = a[i].split("\t").filter {it != ""}   // se separa cada linea por espacios
            u = mapeoDeVuelta[temp[0]]!!                          // valor del vertice de inicio
            v = mapeoDeVuelta[temp[1]]!!                          // valor del vertice de llegada

            if (! grafoDeSalida.agregarArista(Arista(u, v))) throw KeyAlreadyExistsException("el objeto esta repetido")
            i++
        }
    }

    /** Metodo que dado un entero que representa un posible vertice del grafo no dirigido
     *  retorna true si el indice pertenece al grafo y false si no pertenece
     */
    fun contieneVertice(v: Int) : Boolean { return mapeo[v] != null }
        /** Entrada: un entero v que representa un posible vertice del grafo
         *  Postcondicion: v in mapeo
         *  Tiempo: O(1)
         */

    /** Metodo que dado un string que representa un vertice se retorna el indice del vertice.
     *  Si no existe un vertice con ese nombre se lanza un RuntimeException
     */
    fun indiceVertice(nombre: String) : Int {
        /** Entrada: un string que representa el nombre de un vertice
         *  Salida: un entero del indice del vertice
         *  Precondicion: nombre in mapeo
         *  Tiempo: O(1)
         */
        if (mapeoDeVuelta[nombre] == null) throw RuntimeException("No existe ningun vertice con ese nombre")
        return mapeoDeVuelta[nombre]!!
    }

    /** Metodo que dado un indice de un vertice retorna el nombre del vertice, si no se encuentra ningun
     *  vertice con ese indice, se lanza un RuntimeException
     */
    fun nombreVertice(v: Int) : String {
        /** Entrada: un entero v del indice del vertice
         *  Salida: el nombre del vertice
         *  Precondicion: v in mapeo
         *  Tiempo: O(1)
         */
        if (mapeo[v] == null) throw RuntimeException("No existe ningun vertice con ese nombre")
        return mapeo[v]!!
    }

    /** Metodo que retorna el grafoNoDirigido generado previamente con los nombres como vertices
     */
    fun obtenerGrafoNoDirigido() : GrafoNoDirigido { return grafoDeSalida }
        /** Salida: un grafoDirigido
         *  Postcondicion: grafoDeSalida.listaDeVertices.size > 0
         *  Tiempo: O(1)
         */
}
