import ve.usb.libGrafo.*

fun main(args: Array<String>) {
    val clase = GrafoNoDirigido(args[0])

    var tiempo: Double
    try{
        tiempo = -System.nanoTime().toDouble()
        val clase2 = ComponentesConexasDFS(clase)
        val comp = clase2.nCC()
        tiempo += System.nanoTime().toDouble()
        tiempo /= 1000000.0
        println("El tiempo que ha tomado ComponentesConexasDFS es: ${tiempo}ms")
        println("El numero de componentes conexas en ComponentesConexasDFS es: ${comp}")
    } catch (e: StackOverflowError){
        println("EL grafo es demasiado grande para manejarlo en ComponentesConexasDFS")
    }

    println()

    tiempo = -System.nanoTime().toDouble()
    val clase3 = ComponentesConexasCD(clase)
    val comp2 = clase3.nCC()
    tiempo += System.nanoTime().toDouble()
    tiempo /= 1000000.0
    println("El tiempo que ha tomado ComponentesConexasCD es: ${tiempo}ms")
    println("El numero de componentes conexas en ComponentesConexasCD es: ${comp2}")
}
