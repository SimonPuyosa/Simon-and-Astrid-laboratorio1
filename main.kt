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

    /*
    val clase = when (tipo) {
        'd' -> GrafoDirigido(ubicacion)
        'n' -> GrafoNoDirigido(ubicacion)
        'c' -> GrafoDirigidoCosto(ubicacion)
        else -> GrafoNoDirigidoCosto(ubicacion)
    }

     */
    val clase = GrafoDirigido(ubicacion)

    /*
    println(clase.grado(4))
    println(clase.gradoInterior(4))
    println(clase.gradoExterior(4))
    println(clase.obtenerNumeroDeLados())
    println(clase.obtenerNumeroDeVertices())

     */
    println(clase.toString())

}

