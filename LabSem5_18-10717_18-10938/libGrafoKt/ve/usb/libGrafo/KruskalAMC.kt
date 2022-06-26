package ve.usb.libGrafo

import java.util.*

/** Método que dado un grafo no dirigido determina el Árbol Mínimo Cobertor empleando el algoritmo de Kruskal.
 *  Si el grafo ingresado no es conexo, entonces se lanza un RunTimeException.
 */

public class KruskalAMC(val g: GrafoNoDirigidoCosto) {
    /** Entrada: g: grafo no dirigido con costo no vacío
     *  Salida: Árbol Mínimo Cobertor
     *  Precondición: (g.listaDeVertices.size > 0) && nCC() == 1
     *  Postcondición: AMC.isNotEmpty() && obtenerCosto() > 0.0
     *  Tiempo: O(|E|*log(|V|))
     */
    //Variables del programa
    private val AMC = LinkedList<AristaCosto>()                     //Lista enlazada donde se almacena el Árbol Mínimo Cobertor
    private val CD = ConjuntosDisjuntos(g.numDeVertices)    //Inicialización de los vértices como conjuntos disjuntos. Esto equivale a ejecutar |V| veces make-set()
    private val ladosGNDC = LinkedList<AristaCosto>()               //Lista enlazada donde se almacena los lados del grafo no dirigido

    init{
        // Se verifica si g es conexo usando ComponentesConexasCD
        val compConexas = ConjuntosDisjuntos(g.numDeVertices)       //Se inicializa cada vértice como un conjunto disjunto
        val e = g.iterator()
        while(e.hasNext()){                                     //Iteramos sobre las aristas del grafo
            val arista = e.next()
            if(compConexas.encontrarConjunto(arista.u) != compConexas.encontrarConjunto(arista.v)){     //y si los vértices que conforman dicha arista se encuentran en diferentes conjuntos
                compConexas.union(arista.u, arista.v)                                                   //se realiza la unión de estos, obteniendo así las componentes conexas del grafo
            }
        }
        if (compConexas.numConjuntosDisjuntos() != 1) throw RuntimeException("el grafo ingresado no es un grafo conexo")

        //Algoritmo de Kruskal
        //Se inicializa la lista con los lados del grafo
        val it = g.iterator()
        while (it.hasNext()){
            val v = it.next()
            ladosGNDC.add(v)
        }
        ladosGNDC.sortBy{it.costo}                                            //Y se ordenan de forma ascendente por su costo

        for(l in ladosGNDC){                                                  //Iteramos sobre los lados ordenados
            if(CD.encontrarConjunto(l.x) != CD.encontrarConjunto(l.y)){       //Si ambos vértices no se encuentran en un mismo conjunto disjunto
                AMC.add(l)                                                    //Entonces el lado es un lado seguro, y por lo tanto puede añadirse al Árbol Mínimo Cobertor
                CD.union(l.x,l.y)                                             //Finalmente, se realiza la unión de los conjuntos para hacer saber que el lado fue ya agregado al árbol
            }
        }
    }

    /** Clase privada interna que dado un grafo costo no dirigido retorna un iterator de los lados del Árbol Mínimo Cobertor
     */

    private inner class obtenerLadosIterato(krustal: KruskalAMC): Iterator<Arista>{
        /** Entrada: un grafo no dirigido costo no vacio
         *  Salida: un iterador de aristas del árbol mínimo cobertor
         *  Precondicion: AMC.size > 0
         *  Tiempo de operacion: O(|E|)
         */

        private var temp = krustal.AMC
        private var i = 0
        private lateinit var result: AristaCosto

        override fun hasNext(): Boolean {
            return i<temp.size
        }

        override fun next(): Arista {
            result = temp[i]
            i++
            return Arista(result.x, result.y)
        }
    }

    /** Clase privada interna que dado un grafo costo no dirigido sobreescribe la funcián iterator
     *  y la iguala a obtenerLadosIterato
     */
    private inner class obtenerLadosIterable(val krustal: KruskalAMC): Iterable<Arista> {
        /** Entrada: un grafo no dirigido costo no vacio
         *  Salida: un iterable de aristas del arbol minimo cobertor
         *  Precondicion: AMC.size > 0
         *  Tiempo de operacion: O(|E|)
         */
        override fun iterator(): Iterator<Arista> = obtenerLadosIterato(krustal)
    }

    /** Método que retorna un iterable que contiene todas las aristas de Árbol Mínimo Cobertor generado
     */
    fun obtenerLados() : Iterable<Arista> {
        /** Salida: un iterable de aristas del arbol minimo cobertor
         *  Precondicion: AMC.size > 0
         *  Tiempo de operacion: O(|E|)
         */
        return obtenerLadosIterable(this)
    }

    /** Metodo que retorna un double que representa el costo total del Árbol Mínimo Cobertor
     */
    fun obtenerCosto() : Double {
        /** Salida: un iterable de aristas del arbol minimo cobertor
         *  Precondicion: AMC.size > 0
         *  Postcondicion: obtenerCosto() > 0.0
         *  Tiempo de operacion: O(|E|)
         */
        var sum = 0.0
        for(v in AMC){
            sum += v.costo
        }
        return sum
    }
}
