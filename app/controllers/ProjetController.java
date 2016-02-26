package controllers;

import models.*;
import models.Error;
import play.Logger;
import play.libs.Json;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.creerClient;
import views.html.creerProjet;
import views.html.projet;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import static play.data.Form.form;

public class ProjetController extends Controller{

    public Result afficherProjets() {
        return ok(projet.render("Projets"));
    }

    public Result afficherCreerProjet(){
        return ok(creerProjet.render("Créer Projet", Utilisateur.getAllNonArchives(), Client.getAllNonArchives()));
    }

    public Result afficherProjetsClient(long idClient) {
        Client client = Client.find.byId(idClient);
        Logger.debug("Projet Client ID : "+idClient);
        return ok(Json.toJson(models.Projet.find.where().eq("client",client).findList()));
    }

    public Result creerProjet() {
        //TODO : description non obligatoire ?
        Map<String, String[]> map = request().body().asFormUrlEncoded();
        Error error = new Error();
        String nom = map.get("nom")[0];
        if(nom.isEmpty())
        {
            error.nomProjetVide = true;
        }
        else if(nom.length()>30)
        {
            error.nomProjetVide = true;
        }
        Utilisateur responsableProjet = Utilisateur.find.byId(Long.valueOf(map.get("responsableProjet")[0]));
        Client client = Client.find.byId(Long.valueOf(map.get("client")[0]));

        String description = map.get("description")[0];
        System.out.println("DESCRIPTION   ========="+description);

        int priorite = Integer.parseInt(map.get("priorite")[0]);
        System.out.println("PRIOTITY ========= "+priorite);
        UniteProjetEnum unite;
        System.out.println("UNITE ==========="+map.get("unite")[0]);
        if(map.get("unite")[0] == "JOUR"){
            unite = UniteProjetEnum.JOUR;
        }else{
            unite = UniteProjetEnum.SEMAINE;
        }

        String dateDeb = map.get("dateDebutTheorique")[0];
        String dateFin = map.get("dateFinTheorique")[0];
        //Date
        if(dateDeb.isEmpty()){
            error.dateThDebutProjetVide = true;
        }
        if(dateFin.isEmpty()){
            error.dateThFinProjetVide = true;
        }

        if(description.length()>65536){
            error.descriptionTropLong = true;
        }
        if(error.hasErrorProjet()){
            return badRequest(Json.toJson(error));
        }
        //TODO : AU NIVEAU DES DATE IL Y A UN PROBLEME

        //transform string to date
        //TODO : check format date en fonction de la langue ou on impose un seul format de date

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date dateDebutTheorique = null;
        Date dateFinTheorique = null;
        try {
            dateDebutTheorique = formatter.parse(dateDeb);
            dateFinTheorique = formatter.parse(dateFin);
            if(dateFinTheorique.after(dateDebutTheorique)){
                Projet p = new Projet(nom,description,responsableProjet,dateDebutTheorique,dateFinTheorique,unite,client,priorite);
                p.save();

            }
            //TODO : RENVOYER ERROR
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ok();
    }
}
