package plantenApp.java.model;

import java.util.ArrayList;

/**@author Siebe*/
public class AbiotischeFactoren {
    private int id;
    private int plant_id;
    private String bezonning;
    private String grondsoort;
    private String vochtbehoefte;
    private String voedingsbehoefte;
    private String reactieAntagonistischeOmgeving;
    private ArrayList<AbioMulti_Eigenschap> multiEigenschappen;

    public AbiotischeFactoren(int id, int plant_id, String bezonning, String grondsoort, String vochtbehoefte, String voedingsbehoefte, String reactieAntagonistischeOmgeving, ArrayList<AbioMulti_Eigenschap> multiEigenschappen) {
        this.id = id;
        this.plant_id = plant_id;
        this.bezonning = bezonning;
        this.grondsoort = grondsoort;
        this.vochtbehoefte = vochtbehoefte;
        this.voedingsbehoefte = voedingsbehoefte;
        this.reactieAntagonistischeOmgeving = reactieAntagonistischeOmgeving;
        this.multiEigenschappen = multiEigenschappen;
    }

    public int getId() {
        return id;
    }

    public int getPlant_id() {
        return plant_id;
    }

    public String getBezonning() {
        return bezonning;
    }

    public String getGrondsoort() {
        return grondsoort;
    }

    public String getVochtbehoefte() {
        return vochtbehoefte;
    }

    public String getVoedingsbehoefte() {
        return voedingsbehoefte;
    }

    public String getReactieAntagonistischeOmgeving() {
        return reactieAntagonistischeOmgeving;
    }

    public ArrayList<AbioMulti_Eigenschap> getMultiEigenschappen() {
        return multiEigenschappen;
    }
}
