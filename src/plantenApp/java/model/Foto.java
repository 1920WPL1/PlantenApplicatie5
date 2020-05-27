package plantenApp.java.model;

import java.util.ArrayList;

/**@author Siebe*/
public class Foto {
    private int plant_id;
    private ArrayList<Foto_Eigenschap> fotos;

    public Foto(int plant_id, ArrayList<Foto_Eigenschap> fotos) {
        this.plant_id = plant_id;
        this.fotos = fotos;
    }

    public int getPlant_id() {
        return plant_id;
    }

    public ArrayList<Foto_Eigenschap> getFotos() {
        return fotos;
    }

}
