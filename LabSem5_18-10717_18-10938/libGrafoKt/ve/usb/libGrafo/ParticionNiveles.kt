package ve.usb.libGrafo

/** Clase que realiza un partición por niveles de los vértices de un Grafo Acíclico Directo (DAG),
 *  retornando para cada nivel un conjunto de vértices, además de retornar un booleano indicando que
 *  no hay ciclos. En caso de que el grafo de entrada no sea un DAG, entonces se indica que hay ciclo.
 */
public class ParticionNiveles(val g: GrafoDirigido){
    /** Entrada: g: un Grafo Dirigido sin ciclos
     *  Salida: un arreglo de conjuntos mutables que contienen los vértices del digrafo particionados por nivel
     *  Precondición: OrdenamientoTopológico(g).esDAG()
     *  Postcondición: particiones.isNotEmpty
     *  Tiempo: O(máx(|V|,|E|))
     */

    //Inicialización de variables
    var nvert: Int = 0
    var nivel: Int = 0
    var gradoInterior = ArrayList<Int>()                      //Array donde se almacenan el grado interior de cada vértice
    var particiones = ArrayList<MutableSet<Int>>()            //Array de conjuntos donde se almacenará la partición

    init{
        for (i in g.listaDeVertices) {
            gradoInterior.add(i.gradoInterior)         //se almacenan los grados interiores en cada grafo
            particiones.add(mutableSetOf<Int>())                            //se inicializa el arreglo de particiones con conjuntos vacíos
        }

        for (u in g.listaDeVertices) {                                      //se itera por la lista de vértices del digrafo y se almacenan en el arreglo particiones
            if (gradoInterior[u.valor] == 0) {                            //aquellos vértices cuyo grado interior es 0
                particiones[nivel].add(u.valor)
                nvert += 1                                                  //y aumentamos el número de vértices
            }
        }
        while (nvert < g.numDeVertices && particiones[nivel].isNotEmpty()) {
            for (u in particiones[nivel]){                                  //se vuelve a iterar sombre la lista de vértices del digrafo
                if (g.listaDeAdyacencia[u] != null) {
                    val it = g.listaDeAdyacencia[u]!!.iterator()                //y vamos consultando sus vértices adyacentes
                    while (it.hasNext()) {
                        val v = it.next()
                        gradoInterior[v.valor] -= 1                             //si al disminuir el grado interior de vértice actual este se hace 0,
                        if (gradoInterior[v.valor] == 0) {                      //añadimos al arreglo el vértice en su nivel correspondiente
                            particiones[nivel + 1].add(v.valor)
                            nvert += 1                                          //y aumentamos el número de vértices
                        }
                    }
                }
            }
            nivel += 1                                                      //se aumenta el nivel de la partición
        }
    }

    /** Método que retorna un arreglo de conjuntos mutables que representa las particiones de niveles de
     * los vértices pertenecientes al grafo de entrada. Si el grafo ingresado no es DAG, entonces se lanza
     * una RuntimeException
     */
    fun obtenerParticiones(): Array<MutableSet<Int>> {
        /** Salida: Un arreglo de conjuntos mutables que representa la partición por nivel del grafo
         *  Precondición: OrdenamientoTopológico(g).esDAG()
         *  Postcondición: particiones.toTypedArray()
         *  Tiempo: O(1)
         */
        if(!OrdenamientoTopologico(g).esDAG()) throw RuntimeException("El grafo ingresado no es un Grafo Acíclico Directo")
        return particiones.distinct().toTypedArray()
    }

    /** Clase que retorna un booleano que indica si existe o no en el digrafo un ciclo
     */
    fun hayCiclo() : Boolean {
        /** Salida: Un booleano que indica si hay ciclo o no en el grafo
         *  Precondición: nvert>=0
         *  Postcondición: nvert < g.numDeVertices
         *  Tiempo: O(1)
         */
        return nvert < g.numDeVertices
    }
}
