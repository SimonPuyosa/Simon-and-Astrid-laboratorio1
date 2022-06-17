import ve.usb.libGrafo.*

fun main(args: Array<String>) {
    var i = 0
    var tipo: Char = " "[0]
    var ubicacion = ""
    while (i <= args.lastIndex){
        if ("-g" in args[i]){
            tipo = args[i][2]
        }else {
            ubicacion = args[i]
        }
        i++
    }
    if (tipo == " "[0] || ubicacion == "") throw RuntimeException("los datos no fueron introducidos correctamente")

    val clase: Grafo = when (tipo) {
        'd' -> GrafoDirigido(ubicacion)
        'n' -> GrafoNoDirigido(ubicacion)
        'c' -> GrafoDirigidoCosto(ubicacion)
        'p' -> GrafoNoDirigidoCosto(ubicacion)
        else -> {
            GrafoDirigido(ubicacion)
            throw RuntimeException("el tipo de grafo debe ser uno de las 4 opciones especificadas")
        }
    }
    var tiempo: Double
    tiempo = -System.nanoTime().toDouble()
    val clase2 = ComponentesConexasDFS(clase as GrafoNoDirigido)
    val comp = clase2.nCC()
    tiempo += System.nanoTime().toDouble()
    tiempo /= 1000000.0
    println("El tiempo que ha tomado ComponentesConexasDFS es: ${tiempo}ms")
    println("El numero de componentes conexas en ComponentesConexasDFS es: ${comp}")
    println()

    tiempo = -System.nanoTime().toDouble()
    //val clase3 = ComponentesConexasCD(clase as GrafoNoDirigido)
    //val comp = ComponentesConexasCD.nCC()
    tiempo += System.nanoTime().toDouble()
    tiempo /= 1000000.0
    println("El tiempo que ha tomado ComponentesConexasCD es: ${tiempo}ms")
    println("El numero de componentes conexas en ComponentesConexasCD es: ${comp}")
}
