import ve.usb.libGrafo.*

fun main(args: Array<String>) {
    val clase = GrafoNoDirigido(args[0])

    var tiempo: Double
    tiempo = -System.nanoTime().toDouble()
    val clase2 = ComponentesConexasDFS(clase)
    var comp = clase2.nCC()
    tiempo += System.nanoTime().toDouble()
    tiempo /= 1000000.0
    println("El tiempo que ha tomado ComponentesConexasDFS es: ${tiempo}ms")
    println("El numero de componentes conexas en ComponentesConexasDFS es: ${comp}")
    println()

    tiempo = -System.nanoTime().toDouble()
    val clase3 = ComponentesConexasCD(clase)
    comp = clase3.nCC()
    tiempo += System.nanoTime().toDouble()
    tiempo /= 1000000.0
    println("El tiempo que ha tomado ComponentesConexasCD es: ${tiempo}ms")
    println("El numero de componentes conexas en ComponentesConexasCD es: ${comp}")
/*


    println()
    println("numeros de vertices en cada componente")
    for (i in 0 until clase2.nCC()) {
        println(clase2.numVerticesDeLaComponente(i))
    }

    println()
    println("posicion de cada vertice")
    for (i in 0 until clase.listaDeVertices.size) {
        println(clase2.obtenerComponente(i))
    }

    /*
    println()
    for (i in 0 until clase.listaDeVertices.size) {
        for (j in 0 until clase.listaDeVertices.size){
            print("estan en la misma comp $i & $j: ")
            println(clase2.estanMismaComponente(i, j))
        }
    }
    println()

     */
    println("ahora con clase 3")
    println()
    println("numeros de vertices en cada componente")
    for (i in 0 until clase3.nCC()) {
        println(clase3.numVerticesDeLaComponente(i))
    }

    println()
    println("posicion de cada vertice")
    for (i in 0 until clase.listaDeVertices.size) {
        println(clase3.obtenerComponente(i))
    }

    println()
    println(clase3.compConexas.ConjuntosDisjuntos.size)
    println("conjuntos disjuntos: ")
    for (i in clase3.compConexas.ConjuntosDisjuntos) println(i)
    println("vertices cd: ")
    var j = 0
    for (i in clase3.compConexas.verticesCD) {
        println(i)
        j += i
    }
    println()
    println(j)

    /*
    println()
    for (i in 0 until clase.listaDeVertices.size) {
        for (j in 0 until clase.listaDeVertices.size){
            print("estan en la misma comp $i & $j: ")
            println(clase3.mismaComponente(i, j))
        }
    }

     */


 */

}
