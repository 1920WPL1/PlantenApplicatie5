package plantenApp.java.model;

/**@author Siebe*/
public class FenoMulti_Eigenschap {
    private int id;
    private String naam;
    private String jan;
    private String feb;
    private String maa;
    private String apr;
    private String mei;
    private String jun;
    private String jul;
    private String aug;
    private String sep;
    private String okt;
    private String nov;
    private String dec;

    public FenoMulti_Eigenschap(int id, String naam, String jan, String feb, String maa, String apr, String mei, String jun, String jul, String aug, String sep, String okt, String nov, String dec) {
        this.id = id;
        this.naam = naam;
        this.jan = jan;
        this.feb = feb;
        this.maa = maa;
        this.apr = apr;
        this.mei = mei;
        this.jun = jun;
        this.jul = jul;
        this.aug = aug;
        this.sep = sep;
        this.okt = okt;
        this.nov = nov;
        this.dec = dec;
    }

    public int getId() {
        return id;
    }

    public String getNaam() {
        return naam;
    }

    public String getJan() {
        return jan;
    }

    public String getFeb() {
        return feb;
    }

    public String getMaa() {
        return maa;
    }

    public String getApr() {
        return apr;
    }

    public String getMei() {
        return mei;
    }

    public String getJun() {
        return jun;
    }

    public String getJul() {
        return jul;
    }

    public String getAug() {
        return aug;
    }

    public String getSep() {
        return sep;
    }

    public String getOkt() {
        return okt;
    }

    public String getNov() {
        return nov;
    }

    public String getDec() {
        return dec;
    }
}
