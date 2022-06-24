package ve.usb.libGrafo

import java.io.File
import java.util.*
import javax.management.openmbean.KeyAlreadyExistsException

/*
 Esta clase recibe como entrada un archivo que contiene un grafo no
 dirigido, en donde los vértices están identificados con un String, en 
 lugar de un entero. Se crea un objeto que tiene una estructura de datos
 que permite asociar cada nombre de vértice, con un índice que es un número entero. 
 Si |V| = n, entonces a cada vértice se le asigna un índice que es número entero
 en el intervalo [0, n-1]. También se crea una estructura que dado un índice de un 
 vértice, permite obtener el nombre del vértice. Por último, cuando 
 se llama al constructor, Se crea un grafo no dirigido en el que los
 vértices están identificados con los índices de los vértices del grafo. 
*/
public class TablaGrafoNoDirigido(val nombreArchivo: String) {
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

    /*
     Retorna True si el índice de un vértice pertenece a un grafo no dirigido,
     de lo contrario retorna False.
     El tiempo de esta función es O(1).
     */
    fun contieneVertice(v: Int) : Boolean { return mapeo[v] != null }

    /*
     Dado el nombre de un vértice, retorna el índice del vertice. Si no existe ningún vértice
     con ese nombre, entonces se lanza una RuntimeException.
     El tiempo de esta función es O(1).
     */
    fun indiceVertice(nombre: String) : Int {
        if (mapeoDeVuelta[nombre] == null) throw RuntimeException("No existe ningun vertice con ese nombre")
        return mapeoDeVuelta[nombre]!!
    }

    /*
     Dado el índice de un vértice, retorna el nombre del vértice. Si el entero de la entrada
     no corresponde a ningún índice que corresponde a un vértice del grafo no dirigido,
     entonces, se lanza una RuntimeException. 
     El tiempo de esta función es O(1).
     */
    fun nombreVertice(v: Int) : String {
        if (mapeo[v] == null) throw RuntimeException("No existe ningun vertice con ese nombre")
        return mapeo[v]!!
    }

    /*
     Retorna el grafo no dirigido asociado al grafo con vértices con nombres, indicado
     en el archivo de entrada. 
     El tiempo de esta función es O(1).
     */
    fun obtenerGrafoNoDirigido() : GrafoNoDirigido { return grafoDeSalida }
    
}
