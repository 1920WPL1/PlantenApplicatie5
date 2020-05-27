package plantenApp.java.model;

/**@author Siebe*/
public class Extra {
    private int id;
    private int plant_id;
    private int nectarwaarde;
    private int pollenwaarde;
    private String bijvriendelijk;
    private String eetbaar;
    private String kruidgebruik;
    private String geurend;
    private String vorstgevoelig;

    public Extra(int id, int plant_id, int nectarwaarde, int pollenwaarde, String bijvriendelijk, String eetbaar, String kruidgebruik, String geurend, String vorstgevoelig) {
        this.id = id;
        this.plant_id = plant_id;
        this.nectarwaarde = nectarwaarde;
        this.pollenwaarde = pollenwaarde;
        this.bijvriendelijk = bijvriendelijk;
        this.eetbaar = eetbaar;
        this.kruidgebruik = kruidgebruik;
        this.geurend = geurend;
        this.vorstgevoelig = vorstgevoelig;
    }

    public int getId() {
        return id;
    }

    public int getPlant_id() {
        return plant_id;
    }

    public int getNectarwaarde() {
        return nectarwaarde;
    }

    public int getPollenwaarde() {
        return pollenwaarde;
    }

    public String getBijvriendelijk() {
        return bijvriendelijk;
    }

    public String getEetbaar() {
        return eetbaar;
    }

    public String getKruidgebruik() {
        return kruidgebruik;
    }

    public String getGeurend() {
        return geurend;
    }

    public String getVorstgevoelig() {
        return vorstgevoelig;
    }
}
