import ve.usb.libGrafo.*

fun main(args: Array<String>) {
    val clase = GrafoDirigido(args[0])

    val clase2 = ParticionNiveles(clase)
    println(clase2.gradoInterior.toString())
    println(clase2.hayCiclo)
    println(clase2.nivel)
    println(clase2.hayCiclo())
    println(clase2.nvert)
    println(clase2.particiones.toString())
    println(clase2.obtenerParticiones().toString())


}
