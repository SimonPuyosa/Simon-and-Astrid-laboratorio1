import libGrafoKt.ve.usb.libGrafo.*

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

    var clase: Grafo = when (tipo) {
        'd' -> GrafoDirigido(ubicacion)
        'n' -> GrafoNoDirigido(ubicacion)
        'c' -> GrafoDirigidoCosto(ubicacion)
        'p' -> GrafoNoDirigidoCosto(ubicacion)
        else -> {
            GrafoDirigido(ubicacion)
            throw RuntimeException("el tipo de grafo debe ser uno de las 4 opciones especificadas")
        }
    }

    clase = GrafoDirigido(ubicacion)
    val clase2 = CFC(clase)
    println("numeros de cfc: ${clase2.numeroDeCFC()}")
    println("estan en la misma cfc 2 y 3: ${clase2.estanEnLaMismaCFC(2, 2)}")
    println("estan en la misma cfc 2 y 3: ${clase2.estanEnLaMismaCFC(6, 5)}")
    println("indicador de 2 y 3: ${clase2.obtenerIdentificadorCFC(6)} y ${clase2.obtenerIdentificadorCFC(5)}")


    var e = clase2.obternerCFC().iterator()
    println("iterador 1:")
    while (e.hasNext()) println(e.next())
    println()

    val d = clase.iterator()
    println("iterador 2:")
    while (d.hasNext()) println(d.next())
    println()

}

