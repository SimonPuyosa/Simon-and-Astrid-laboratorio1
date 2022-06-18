package ve.usb.libGrafo

/** Clase para representar la estructura de datos Conjuntos Disjuntos, implementados con áboles
 *
 Implementación de las estructuras de datos para conjuntos disjuntos.
 Los conjuntos disjuntos son representado como árboles.
 El constructor recibe como entrada en número elementos que van a conformar los cojuntos disjuntos.
 Los elementos de los conjuntos disjuntos están identificados en el intervalo [0 .. n-1].
 Cuando se ejecuta el constructor, se crean n conjuntos disjuntos iniciales, es decir,
 se debe ejecutar make-set(i) para todo i en el intervalo [0 .. n-1].
*/
public class ConjuntosDisjuntos(val n: Int) {
    /** Entrada:
     *      n: número de elementos que van a conformar los conjuntos disjuntos
     *  Salida: Array donde se almacenarán los n conjuntos disjuntos iniciales
     *  Precondición: n>=0
     *  Postcondición: treeConjuntosDisjuntos.isNotEmpty()
     *  Tiempo: O(n)
     */
    var treeConjuntosDisjuntos = Array(n){Vertice(it)}
    var verticesCD = ArrayList<Int>()
    var ConjuntosDisjuntos = ArrayList<Int>()

    init{
        for(i in 0 until n){
            treeConjuntosDisjuntos[i] = Vertice(i)
            ConjuntosDisjuntos.add(i)
            verticesCD.add(i, 1)
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

        if (x.padre != y.padre) {
            if (x.rank > y.rank) {
                y.padre = x
                verticesCD[u]--
                verticesCD[v]++
                ConjuntosDisjuntos.remove(u)
            } else{
                x.padre = y
                verticesCD[v]--
                verticesCD[u]++
                ConjuntosDisjuntos.remove(v)
                if(x.rank == y.rank){
                    y.rank++
                }
            }
            return true
        }
        return false
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
         *  Tiempo: O(rank)
         */
        if(v>=n || u>=n) throw RuntimeException("Alguno de los dos enteros no pertenecen a ningún conjunto")
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
         *  Tiempo: O(rank)
         */
        if(v>=n) throw RuntimeException("El identificador no pertenece a ningun elemento")
        var temp = treeConjuntosDisjuntos[v]

        if(temp != temp.padre){
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
