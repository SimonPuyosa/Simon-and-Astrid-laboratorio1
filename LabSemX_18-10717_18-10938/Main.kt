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
    print("Numero de lados: ")
    println(clase.obtenerNumeroDeLados())
    print("Numero de vertices: ")
    println(clase.obtenerNumeroDeVertices())
    print("Grado del vertice 4: ")
    println(clase.grado(4))
    println()
    print("Se imprime el grafo (lista de adyacencia): ")
    println(clase.toString())


    println("probamos iteradores 1")
    val a = clase.iterator()
    while (a.hasNext()) println(a.next())

    println()
    println("probamos iteradores 2")
    //val b = clase.ladosAdyacentes(Arista(4,7)).iterator()
    //while (b.hasNext()) println(b.next())

    println()
    println("probamos iteradores 3")
    val c = clase.adyacentes(4).iterator()
    while (c.hasNext()) println(c.next())
    println()

    val clase2 = DFS(clase)
    println(clase2.obtenerPredecesor(2))
    println(clase2.obtenerTiempos(5))
    println(clase2.obtenerTiempos(4))

    println("camino de 4 a 5 iterador")
    for (i in 0 until 7){
        var j = i
        while (j < 8){
            print("Hay camino desde $i hasta $j: ")
            println(clase2.hayCamino(i,j))
            j++
        }
    }

    for (i in 0 until 7){
        var j = i
        while (j < 7){
            if (clase2.hayCamino(i,j)){
                println("Se imprime el camino desde $i hasta $j")
                val d = clase2.caminoDesdeHasta(i, j).iterator()
                while (d.hasNext()) println(d.next())
                println()
            }
            j++
        }
    }

    for (i in 0 until 7){
        println()
        println("Vamos con $i")
        print("Tiempo inicial: ")
        println(clase2.g.listaDeVertices[i]!!.tiempoInicial)
        print("Tiempo final: ")
        println(clase2.g.listaDeVertices[i]!!.tiempoFinal)
    }

    println("hayLadosCruzados: ${clase2.hayLadosCruzados()}")

    val d = clase2.caminoDesdeHasta(4, 5).iterator()
    while (d.hasNext()) println(d.next())
    println()

    println("lados de bosque iterador")
    var e = clase2.ladosDeBosque()
    while (e.hasNext()) println(e.next())
    println()


    println(clase2.hayLadosDeIda())
    println("lados de ida iterador")
    e = clase2.ladosDeIda()
    while (e.hasNext()) println(e.next())
    println()


    println(" lados de vuelta iterador")
    e = clase2.ladosDeVuelta()
    while (e.hasNext()) println(e.next())
    println()

    println("lados cruzados")
    e = clase2.ladosCruzados()
    while (e.hasNext()) println(e.next())
    println()

    println()
    clase2.mostrarBosqueDFS()


}

