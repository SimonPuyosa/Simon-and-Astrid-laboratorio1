package ve.usb.libGrafo

class Vertice(val valor: Int?, var gradoInterior: Int = 0, var gradoExterior: Int = 0){
    var next: Vertice? = null
    var prev: Vertice? = null

    fun compareTo(other: Vertice): Int {
        return when {
            this.valor!! > other.valor!! -> {
                1
            }
            this.valor < other.valor -> {
                -1
            }
            else -> {
                0
            }
        }
    }
}

