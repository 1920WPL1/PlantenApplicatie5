package plantenApp

import javafx.scene.control.Button
import javafx.scene.control.ListView
import javafx.scene.input.MouseEvent

class ControllerBeheerRegistraties {
    var btnZoekScherm: Button? = null
    var lstAanvraagRegistraties: ListView<*>? = null
    var btnAanvraagGoedkeuren: Button? = null
    var btnAanvraagAfwijzen: Button? = null

    fun clicked_NaarZoekscherm(mouseEvent: MouseEvent) {}

    fun clicked_Goedkeuren(mouseEvent: MouseEvent) {}

    fun clicked_Afwijzen(mouseEvent: MouseEvent) {}
}
