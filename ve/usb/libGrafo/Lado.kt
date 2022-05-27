package ve.usb.libGrafo

import RuntimeExpception

abstract class Lado(val a: Int, val b: Int) {

    // Retorna cualquiera de los dos vértices del grafo
    fun cualquieraDeLosVertices() : Int {
        return a
    }

    // Dado un vertice w, si w == a entonces retorna b, de lo contrario si w == b  entonces retorna a,  y si w no es igual a a ni a b, entonces se lanza una RuntimeExpception 
    fun elOtroVertice(w: Int) : Int {
        when (w) {
            a -> {
                return b
            }
            b -> {
                return a
            }
            else -> {
                RuntimeExpception("la integral debe ser uno de los dos vertices")
            }
        }
        return a
    }
}
