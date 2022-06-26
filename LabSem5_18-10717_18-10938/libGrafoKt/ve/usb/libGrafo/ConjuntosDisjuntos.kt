package ve.usb.libGrafo
import kotlin.collections.ArrayList

/** Clase para representar la estructura de datos Conjuntos Disjuntos, implementados con áboles
 */
public class ConjuntosDisjuntos(val n: Int) {
    /** Entrada:
     *      n: número de elementos que van a conformar los conjuntos disjuntos
     *  Salida: Array donde se almacenarán los n conjuntos disjuntos iniciales
     *  Precondición: n>=0
     *  Postcondición: treeConjuntosDisjuntos.isNotEmpty()
     *  Tiempo: O(n)
     */
    private var treeConjuntosDisjuntos = Array(n){Vertice(it)}      //Array que representa la ejecución de make-set para n vértices
    var verticesCD = Array(n, {1})                                  //Array donde se almacena la cantidad de vértices que hay en cada conjunto
    var ConjuntosDisjuntos = ArrayList<Int>()                       //ArrayList donde se almacenan los representantes de cada conjunto

    init{
        for(i in 0 until n){
            ConjuntosDisjuntos.add(i)                               //Se inicializa la clase añadiendo los representantes de cada conjunto disjunto creado
        }
    }

    /** Método que dado dos enteros v, u, retornan un booleano que inidica que la unión entre sus conjuntos disjuntos se
     *  realizó o no
     */
    private fun link(v: Int, u: Int) : Boolean{
        /** Entrada:
         *      v: entero que representa al vértice v
         *      u: entero que representa al vértice u
         *  Salida: booleano que indica si la unión de los conjuntos que contienen a v y u se realizó o no
         *  Precondición: v<n && u<n
         *  Postcondición treeConjuntosDisjuntos[v].padre != treeConjuntosDisjuntos[u].padre
         *  Tiempo: O(1)
         */
        val x = treeConjuntosDisjuntos[v]
        val y = treeConjuntosDisjuntos[u]

        if (x.padre.valor != y.padre.valor) {                 //Para realizar la unión de conjuntos disjuntos, se verifica que los padres de ambos vértices sean distintos
            if (x.rank > y.rank) {                            //Se busca cual de los dos vértices tiene mayor altura
                val w = verticesCD[u]
                y.padre = x                                   //Para reasignar al padre del nodo con menor altura, realizando así la unión de los conjuntos
                verticesCD[u]-= w                             //Se actualiza la cantidad de vértices que posee el conjunto cuyo representante corresponde al de mayor altura
                verticesCD[v]+= w                             //Y se elimina la cantidad de vértices del conjunto cuyo representante corresponde al de menor altura
                ConjuntosDisjuntos.remove(u)                  //Además, se elimina dicho representante de la lista
            } else{
                val z = verticesCD[v]
                x.padre = y
                verticesCD[v]-= z
                verticesCD[u]+= z
                ConjuntosDisjuntos.remove(v)
                if(x.rank == y.rank){                         //En caso de que ambos árboles tienen la misma altura, se aumenta la misma
                    y.rank++
                }
            }
            return true                                       //Se retorna True una vez la unión fue realizada
        }
        return false                                          //Y False en caso contrario
    }
    /** Método que dado dos enteros v, u realiza la unión entre los dos conjuntos disjuntos y retorna un
     *  booleano indicando si dicha unión se realizó o no. Si el algunos de los dos elementos de
     *  entrada, no pertenece a algún conjunto, entonces se lanza un RuntineException
     */
    fun union(v: Int, u: Int) : Boolean {
        /** Entrada:
         *      v: entero que representa al vértice v
         *      u: entero que representa al vértice u
         *  Salida: booleano que indica si la unión de los conjuntos que contienen a v y u se realizó o no
         *  Precondición: v<n && u<n
         *  Postcondición treeConjuntosDisjuntos[v].padre != treeConjuntosDisjuntos[u].padre
         *  Tiempo: O(|V|)
         */
        if(0 > v || v >= n || 0 > u || u>=n) throw RuntimeException("Alguno de los dos enteros no pertenecen a ningún conjunto")
        return link(encontrarConjunto(v), encontrarConjunto(u))
    }

    /** Método que dado un entero v, retorna un entero que representa el elemento representativo de un conjunto
     *  disjunto. Si el identificador no pertenece a ningun elemento, entonces se lanza una RuntimeException.
     */
    fun encontrarConjunto(v: Int) : Int {
        /** Entrada:
         *      v: entero que representa al vértice v
         *  Salida: entero que indica el elemento representativo del conjunto disjunto de v
         *  Precondición: v<n
         *  Postcondición result = treeConjuntosDisjuntos[v].padre.valor
         *  Tiempo: O(|V|)
         */
        if(0 > v || v>=n) throw RuntimeException("El identificador no pertenece a ningun elemento")
        var temp = treeConjuntosDisjuntos[v]

        if(temp.valor != temp.padre.valor){
            temp = treeConjuntosDisjuntos[encontrarConjunto(temp.padre.valor)]
        }
        return temp.padre.valor
    }

    /** Método que retorna un entero que representa el número de conjuntos disjuntos actuales que posee la estructura
     *  de datos
     */
    fun numConjuntosDisjuntos() : Int {
        /** Salida: entero que indica el número de conjuntos disjuntos actuales de la estructura de datos
         *  Postcondición ConjuntosDisjuntos.size
         *  Tiempo: O(1)
         */
        return ConjuntosDisjuntos.size
    }
}
