package plantenApp.java.model;

import java.sql.Connection;
import java.util.Date;

/**
 * @author Bart
 */
public class Gebruiker {
    private int gebruiker_id;
    private String voornaam;
    private String achternaam;
    private String email;
    private String rol;
    private Date aanvraag_datum;
    private int geregistreerd;
    private int aanvraag_status; // 0 : geen aanvraag (want vooraf geregistreerd) 1 : aanvraag ingediend 2: aanvraag goedgekeurd
    private byte[] wachtwoord_hash;
    private byte[] salt;

    public Gebruiker(int gebruiker_id, String voornaam, String achternaam,
                     String email, String rol, Date aanvraag_datum,
                     int aanvraag_status, int geregistreerd, byte[] wachtwoord_hash, byte[] salt) {
        this.gebruiker_id = gebruiker_id;
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.email = email;
        this.rol = rol;
        this.aanvraag_datum = aanvraag_datum;
        this.aanvraag_status = aanvraag_status;
        this.geregistreerd = geregistreerd;
        this.wachtwoord_hash = wachtwoord_hash;
        this.salt = salt;
    }

    public Gebruiker(int gebruiker_id, String voornaam, String achternaam, String email) {
        this.gebruiker_id = gebruiker_id;
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.email = email;
    }


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

    public int isAanvraag_status() {
        return aanvraag_status;
    }

    public void setAanvraag_status(int aanvraag_status) {
        this.aanvraag_status = aanvraag_status;
    }

    public int isGeregistreerd() {
        return geregistreerd;
    }

    public void setGeregistreerd(int geregistreerd) {
        this.geregistreerd = geregistreerd;
    }

    public byte[] getWachtwoord_hash() {
        return wachtwoord_hash;
    }

    public void setWachtwoord_hash(byte[] wachtwoord_hash) {
        this.wachtwoord_hash = wachtwoord_hash;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }
}