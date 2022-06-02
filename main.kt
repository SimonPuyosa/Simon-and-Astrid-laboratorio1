import ve.usb.libGrafo.*

fun main(args: Array<String>) {
    var i = 0
    var tipo: Char
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
        'd' -> GrafoDirigido.kt(ubicacion)
        'n' -> GrafoNoDirigido(ubicacion)
        'c' -> GrafoDirigidoCosto(ubicacion)
        else -> GrafoNoDirigidoCosto(ubicacion)
    }

     */
    val clase = GrafoDirigido(ubicacion)

    clase.agregarArco(Arco(4,4))
    clase.agregarArco(Arco(4,6))

    println(clase.toString())
    val a = clase.ladosAdyacentes(Arco(4,6)).iterator()
    while(a.hasNext()){
        println(a.next())
    }
    println(clase.grado(4))
    println(clase.gradoExterior(4))
    println(clase.gradoInterior(4))





}

