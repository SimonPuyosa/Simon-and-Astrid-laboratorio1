package ve.usb.libGrafo

class Vertice(val valor: Int, var Costo: Double = 0.0, var gradoInterior: Int = 0, var gradoExterior: Int = 0) {

    override fun equals(other: Any?): Boolean {
        val b: Vertice = other as Vertice
        return this.valor == b.valor
    }

    override fun hashCode(): Int = this.valor
}

