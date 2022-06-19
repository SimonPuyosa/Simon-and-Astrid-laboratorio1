package ve.usb.libGrafo
import java.util.*

/**
 *  Funcion que dado un GrafoDirigido con una listaDeAdyacencia no vacia, retorna un segundo GrafoDirigido
 *  modificado, en el que se invierten los arcos de los lados, esto se hace cambiando las incidencias por
 *  adyacencias en la listaDeAdyacencia
 */
fun digrafoInverso(g: GrafoDirigido) : GrafoDirigido {
    /** Entrada: un digrafo en el cual se invertira el sentido de todos los Arcos pertenecientes a este
     *  Salida: el digrafo de entrada invertido
     *  Precondicion: listaDeAdyacencia != arrayOfNulls()
     *  Postcondicion: result is GrafoDirigido
     *  Tiempo: O(|V| + |E|)
     */
    val nuevaListaDeADyacencia: Array<LinkedList<Vertice>?> = Array(g.numDeVertices, { null } )     // se crea la nueva lista de adyacencia
    var it: MutableIterator<Vertice>                                                        // se crea una variable en la que iteraremos sobre los linkedlist
    var temp: Vertice                                                                       // se crea una variable temporal

    for (i in g.listaDeAdyacencia.indices){                                                 // se itera sobre la lista de adyacencia
        if (g.listaDeAdyacencia[i] != null){                                                // se verifica que el elemento de la lista no sea nulo
            it = g.listaDeAdyacencia[i]!!.iterator()                                        // si no es nulo entonces es un linkedlist
            while (it.hasNext()){                                                           // iteramos sobre el linked list
                temp = it.next()
                if (nuevaListaDeADyacencia[temp.valor] == null){                            // si la nueva lista de adyacencia tiene la casilla nula
                    nuevaListaDeADyacencia[temp.valor] = LinkedList<Vertice>()              // entonces se le asigna un linkedlist
                }
                nuevaListaDeADyacencia[temp.valor]!!.addFirst(g.listaDeVertices[i])         // se agrega la adyacencia de los mismos vertices pero en sentido contrario
            }
        }
    }
    // Se crea un GrafoDirigido y se le asigna las propiedades del digrafo g
    val result = GrafoDirigido(g.numDeVertices)
    result.numDeVertices = g.numDeVertices
    result.numDeLados = g.numDeLados
    result.listaDeVertices = g.listaDeVertices
    result.listaDeAdyacencia = nuevaListaDeADyacencia
    return result               // Se retorna el digrafo inverso
}