package plantenApp.java.model;

import java.util.ArrayList;

/**@author Siebe*/
public class Beheer {
    private int plant_id;
    private ArrayList<Beheerdaad_Eigenschap> multiEigenschappen;

    public Beheer(int plant_id, ArrayList<Beheerdaad_Eigenschap> multiEigenschappen) {
        this.plant_id = plant_id;
        this.multiEigenschappen = multiEigenschappen;
    }

    public int getPlant_id() {
        return plant_id;
    }

    public ArrayList<Beheerdaad_Eigenschap> getMultiEigenschappen() {
        return multiEigenschappen;
    }
}
