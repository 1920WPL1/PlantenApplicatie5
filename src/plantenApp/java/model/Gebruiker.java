package plantenApp.java.model;

import java.sql.Date;

public class Gebruiker {
    private int gebruiker_id;
    private String voornaam;
    private String achternaam;
    private String email;
    private String rol;
    private Date aanvraag_datum;
    private Boolean aanvraag_goedgekeurd;
    private String wachtwoord_hash;

    // aanmaak SHA-512 hash : https://www.baeldung.com/java-password-hashing

    public Gebruiker(int gebruiker_id, String voornaam, String achternaam, String email, String rol, Date aanvraag_datum, Boolean aanvraag_goedgekeurd, String wachtwoord_hash) {
        this.gebruiker_id = gebruiker_id;
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.email = email;
        this.rol = rol;
        this.aanvraag_datum = aanvraag_datum;
        this.aanvraag_goedgekeurd = aanvraag_goedgekeurd;
        this.wachtwoord_hash = wachtwoord_hash;
    }

    /* getters / setters */
    // id alleen getter

    public int getGebruiker_id() {
        return gebruiker_id;
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

    public Boolean getAanvraag_goedgekeurd() {
        return aanvraag_goedgekeurd;
    }

    public void setAanvraag_goedgekeurd(Boolean aanvraag_goedgekeurd) {
        this.aanvraag_goedgekeurd = aanvraag_goedgekeurd;
    }

    public String getWachtwoord_hash() {
        return wachtwoord_hash;
    }

    public void setWachtwoord_hash(String wachtwoord_hash) {
        this.wachtwoord_hash = wachtwoord_hash;
    }

}
