import ve.usb.libGrafo.*
import java.lang.RuntimeException

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



    println(clase.obtenerNumeroDeLados())
    println(clase.obtenerNumeroDeVertices())
    //println(clase.agregarArco(Arco(4,4)))
    //println(clase.agregarArco(Arco(4,6)))
    //println(clase.agregarArco(Arco(4,6)))
    println(clase.grado(4))



    println(clase.toString())
    val a = clase.iterator()
    i = 0
    while(a.hasNext()){
        println(a.next())
        i++
    }
    println(i)
    println(clase.grado(4))

    val b = clase.adyacentes(4).iterator()
    i = 0
    while(b.hasNext()){
        println(b.next())
        i++
    }

}

