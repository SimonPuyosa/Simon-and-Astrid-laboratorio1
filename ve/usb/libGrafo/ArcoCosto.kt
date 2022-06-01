package ve.usb.libGrafo

public class ArcoCosto(val x: Int, val y: Int, val costo: Double) : Arco(x, y) {

    // Retorna el costo asociado del arco
    fun costo() : Double {
        return costo //?
    }

    // Representación del arco como String
    override fun toString() : String {
        return "$x -> $y"
    }
} 
