package plantenApp.java.model;

import java.sql.Blob;
import java.util.ArrayList;

/**
 * @author Siebe
 */
public class InfoTables {
    //Plant
    private ArrayList<String> types;
    private ArrayList<String> families;

    //Fenotype
    private ArrayList<String> kleuren;
    private ArrayList<String> bladgroottes;
    private ArrayList<String> bladvormen;
    private ArrayList<String> bloeiBladRatios;
    private ArrayList<String> spruitfenologieen;
    private ArrayList<String> bloeiwijzes;
    private ArrayList<Blob> bloeiwijzesFotos;
    private ArrayList<String> habitusMogelijkheden;
    private ArrayList<Blob> habitusFotos;
    private ArrayList<String> levensvormen;

    //Abiotische factoren
    private ArrayList<String> bezonningsMogelijkheden;
    private ArrayList<String> grondsoorten;
    private ArrayList<String> vochtbehoeftes;
    private ArrayList<String> voedingsbehoeftes;
    private ArrayList<String> antagonistischeOmgevingsReacties;
    private ArrayList<String> habitats;

    //Commensalisme
    private ArrayList<String> onstwikkelingssnelheden;
    private ArrayList<String> concurentiekrachten;
    private ArrayList<Integer> sociabiliteiten;
    private ArrayList<String> stratergieen;

    //Beheer
    private ArrayList<String> beheerdaden;

    //Extra
    private ArrayList<Integer> nectarwaardes;
    private ArrayList<Integer> pollenwaardes;

    public InfoTables(
            ArrayList<String> types
            , ArrayList<String> families, ArrayList<String> kleuren, ArrayList<String> bladgroottes, ArrayList<String> bladvormen,
             ArrayList<String> bloeiBladRatios, ArrayList<String> spruitfenologieen, ArrayList<String> bloeiwijze
            //,ArrayList<Blob> bloeiwijzesFotos
            ,ArrayList<String> habitusMogelijkheden, ArrayList<Blob> habitusFotos
            //, ArrayList<String> levensvormen
            , ArrayList<String> bezonningsMogelijkheden, ArrayList<String> grondsoorten, ArrayList<String> vochtbehoeftes, ArrayList<String>
               voedingsbehoeftes, ArrayList<String> antagonistischeOmgevingsReacties, ArrayList<String> habitats, ArrayList<String>
                onstwikkelingssnelheden, ArrayList<String> concurentiekrachten, ArrayList<Integer> sociabiliteiten, ArrayList<String>
                 stratergieen,ArrayList<String> beheerdaden, ArrayList<Integer> nectarwaardes, ArrayList<Integer> pollenwaardes) {


        this.types = types;
        this.families = families;
        this.kleuren = kleuren;
        this.bladgroottes = bladgroottes;
        this.bladvormen = bladvormen;
        this.bloeiBladRatios = bloeiBladRatios;
        this.spruitfenologieen = spruitfenologieen;
        this.bloeiwijzes = bloeiwijzes;
        //this.bloeiwijzesFotos = bloeiwijzesFotos;
        this.habitusMogelijkheden = habitusMogelijkheden;
        this.habitusFotos = habitusFotos;
        //this.levensvormen = levensvormen;
        this.bezonningsMogelijkheden = bezonningsMogelijkheden;
        this.grondsoorten = grondsoorten;
        this.vochtbehoeftes = vochtbehoeftes;
        this.voedingsbehoeftes = voedingsbehoeftes;
        this.antagonistischeOmgevingsReacties = antagonistischeOmgevingsReacties;
        this.habitats = habitats;
        this.onstwikkelingssnelheden = onstwikkelingssnelheden;
        this.concurentiekrachten = concurentiekrachten;
        this.sociabiliteiten = sociabiliteiten;
        this.stratergieen = stratergieen;
        this.beheerdaden = beheerdaden;
        this.nectarwaardes = nectarwaardes;
        this.pollenwaardes = pollenwaardes;
    }

    public ArrayList<String> getTypes() {
        return types;
    }

    public ArrayList<String> getFamilies() {
        return families;
    }

    public ArrayList<String> getKleuren() {
        return kleuren;
    }

    public ArrayList<String> getBladgroottes() {
        return bladgroottes;
    }

    public ArrayList<String> getBladvormen() {
        return bladvormen;
    }

    public ArrayList<String> getBloeiBladRatios() {
        return bloeiBladRatios;
    }

    public ArrayList<String> getSpruitfenologieen() {
        return spruitfenologieen;
    }

    public ArrayList<String> getBloeiwijzes() {
        return bloeiwijzes;
    }

    public ArrayList<Blob> getBloeiwijzesFotos() {
        return bloeiwijzesFotos;
    }

    public ArrayList<String> getHabitusMogelijkheden() {
        return habitusMogelijkheden;
    }

    public ArrayList<Blob> getHabitusFotos() {
        return habitusFotos;
    }

    public ArrayList<String> getLevensvormen() {
        return levensvormen;
    }

    public ArrayList<String> getBezonningsMogelijkheden() {
        return bezonningsMogelijkheden;
    }

    public ArrayList<String> getGrondsoorten() {
        return grondsoorten;
    }

    public ArrayList<String> getVochtbehoeftes() {
        return vochtbehoeftes;
    }

    public ArrayList<String> getVoedingsbehoeftes() {
        return voedingsbehoeftes;
    }

    public ArrayList<String> getAntagonistischeOmgevingsReacties() {
        return antagonistischeOmgevingsReacties;
    }

    public ArrayList<String> getHabitats() {
        return habitats;
    }

    public ArrayList<String> getOnstwikkelingssnelheden() {
        return onstwikkelingssnelheden;
    }

    public ArrayList<String> getConcurentiekrachten() {
        return concurentiekrachten;
    }

    public ArrayList<Integer> getSociabiliteiten() {
        return sociabiliteiten;
    }

    public ArrayList<String> getStratergieen() {
        return stratergieen;
    }

    public ArrayList<Integer> getNectarwaardes() {
        return nectarwaardes;
    }

    public ArrayList<Integer> getPollenwaardes() {
        return pollenwaardes;
    }
}
