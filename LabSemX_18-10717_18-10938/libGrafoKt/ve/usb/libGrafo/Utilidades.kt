package libGrafoKt.ve.usb.libGrafo
import java.util.*

// Retorna el digrafo inverso de un grafo
fun digrafoInverso(g: GrafoDirigido) : GrafoDirigido {
    val nuevaListaDeADyacencia: Array<LinkedList<Vertice>?> = Array(g.numDeVertices, { null } )
    var it: MutableIterator<Vertice>
    var temp: Vertice
    for (i in g.listaDeAdyacencia.indices){
        if (g.listaDeAdyacencia[i] != null){
            it = g.listaDeAdyacencia[i]!!.iterator()
            while (it.hasNext()){
                temp = it.next()
                if (nuevaListaDeADyacencia[temp.valor] == null){
                    nuevaListaDeADyacencia[temp.valor] = LinkedList<Vertice>()
                }
                nuevaListaDeADyacencia[temp.valor]!!.addFirst(g.listaDeVertices[i])
            }
        }
    }
    val result = GrafoDirigido(g.numDeVertices)
    result.numDeVertices = g.numDeVertices
    result.numDeLados = g.numDeLados
    result.listaDeVertices = g.listaDeVertices
    result.listaDeAdyacencia = nuevaListaDeADyacencia
    return result
}