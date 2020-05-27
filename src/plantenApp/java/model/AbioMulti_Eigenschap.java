package plantenApp.java.model;

/**@author Siebe*/
public class AbioMulti_Eigenschap {
    private int id;
    private String naam;
    private String value;

    public AbioMulti_Eigenschap(int id, String naam, String value) {
        this.id = id;
        this.naam = naam;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public String getValue() {
        return value;
    }
}
