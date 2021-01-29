package fr.istic.mob.start2tb

interface MyListenner {
    fun busSelected(direction: Int, id_route: String, date: String, time: String);
    fun arretSelected(id_arret: String, id_bus: String, dir: String, date: String, time: String)
    fun horaireSelected(idTrip : String, time: String)
}