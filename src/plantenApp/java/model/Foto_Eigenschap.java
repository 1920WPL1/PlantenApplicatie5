package plantenApp.java.model;

import java.sql.Blob;

/**@author Siebe*/
public class Foto_Eigenschap {
    private int id;
    private String eigenschap;
    private String url;
    private Blob image;

    public Foto_Eigenschap(int id, String eigenschap, String url,Blob image) {
        this.id = id;
        this.eigenschap = eigenschap;
        this.url = url;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getEigenschap() {
        return eigenschap;
    }

    public String getUrl() {
        return url;
    }

    public Blob getImage() {
        return image;
    }
}
