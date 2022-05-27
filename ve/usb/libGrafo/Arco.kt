package ve.usb.libGrafo

public open class Arco(val inicio: Int, val fin: Int) : Lado(inicio, fin) {

    // Retorna el vértice inicial del arco
    fun fuente() : Int {
    }

    // Retorna el vértice final del arco
    fun sumidero() : Int {
    }

    // Representación del arco
    override fun toString() : String {
     }
} 
