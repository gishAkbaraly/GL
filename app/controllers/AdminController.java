package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.*;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.adminClients;
import views.html.adminProjets;
import views.html.adminUtilisateur;
import views.html.adminProjetsSelect;


/**
 * Created by Gishan on 07/01/2016.
 */
public class AdminController extends Controller{

    public Result afficherAdminClients() {
        return ok(adminClients.render("Admin Clients", Client.getAllNonArchives()));
    }

    public Result afficherClientsArchives() {
        return ok(Json.toJson(Client.getAllArchives()));
    }

    public Result afficherAdminProjets() {
        return ok(adminProjets.render("Admin Projets", models.Projet.getAllNonArchivesNonTermines()));
    }

    public Result afficherAdminUtilisateur() {
        return ok(adminUtilisateur.render("Admin Utilisateur", Utilisateur.getAllNonArchives()));
    }

    public Result afficherUtilisateursArchives() {
        //Logger.debug(Utilisateur.getAllArchives().toString());
        return ok(Json.toJson(Utilisateur.getAllArchives()));
    }

    public Result afficherModalUtilisateur(long idUtilisateur){
        return ok(Json.toJson(Utilisateur.find.byId(idUtilisateur)));
    }

    public Result afficherModalClient(long idUtilisateur) {
        return ok(Json.toJson(Client.find.byId(idUtilisateur)));
    }


    public Result afficherAdminProjetsSelect(Long idProjet) {
        models.Projet p = models.Projet.find.byId(idProjet);
        System.out.println(p.client.listeContacts.size());
        return ok(adminProjetsSelect.render("Projet",models.Projet.find.byId(idProjet)));
    }

}
