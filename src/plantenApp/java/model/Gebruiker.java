package plantenApp.java.model;
        import java.util.Date;
/**
 * @author Bart
 * @author Jasper : datatypes
 */
public class Gebruiker {
    private int gebruiker_id;
    private String voornaam;
    private String achternaam;
    private String email;
    private String rol;
    private Date aanvraag_datum;
    private int aanvraag_goedgekeurd; // 0 : geen aanvraag (want vooraf geregistreerd) 1 : aanvraag ingediend 2: aanvraag goedgekeurd
    private boolean geregistreerd; // false : niet geregistreerd true : geregistreerd
    private byte[] wachtwoord_hash;
    public Gebruiker(int gebruiker_id, String voornaam, String achternaam,
                     String email, String rol, Date aanvraag_datum,
                     int aanvraag_goedgekeurd, boolean geregistreerd, byte[] wachtwoord_hash) {
        this.gebruiker_id = gebruiker_id;
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.email = email;
        this.rol = rol;
        this.aanvraag_datum = aanvraag_datum;
        this.aanvraag_goedgekeurd = aanvraag_goedgekeurd;
        this.geregistreerd = geregistreerd;
        this.wachtwoord_hash = wachtwoord_hash;
    }
    public int getGebruiker_id() {
        return gebruiker_id;
    }
    public void setGebruiker_id(int gebruiker_id) {
        this.gebruiker_id = gebruiker_id;
    }
    public String getVoornaam() {
        return voornaam;
    }
    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }
    public String getAchternaam() {
        return achternaam;
    }
    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getRol() {
        return rol;
    }
    public void setRol(String rol) {
        this.rol = rol;
    }
    public Date getAanvraag_datum() {
        return aanvraag_datum;
    }
    public void setAanvraag_datum(Date aanvraag_datum) {
        this.aanvraag_datum = aanvraag_datum;
    }

    public int isAanvraag_goedgekeurd() {
        return aanvraag_goedgekeurd;
    }
    public void setAanvraag_goedgekeurd(int aanvraag_goedgekeurd) {
        this.aanvraag_goedgekeurd = aanvraag_goedgekeurd;
    }

    public boolean isGeregistreerd() {
        return geregistreerd;
    }
    public void setGeregistreerd(boolean geregistreerd) {
        this.geregistreerd = geregistreerd;
    }

    public byte[] getWachtwoord_hash() {
        return wachtwoord_hash;
    }
    public void setWachtwoord_hash(byte[] wachtwoord_hash) {
        this.wachtwoord_hash = wachtwoord_hash;
    }
}