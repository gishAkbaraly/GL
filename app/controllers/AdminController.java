package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Tache;
import models.Utilisateur;
import play.Logger;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.adminClients;
import views.html.adminProjets;
import views.html.adminUtilisateur;


/**
 * Created by Gishan on 07/01/2016.
 */
public class AdminController extends Controller{

    public Result afficherAdminClients() {
        return ok(adminClients.render("Admin Clients"));
    }

    public Result afficherAdminProjets() {
        return ok(adminProjets.render("Admin Projets"));
    }

    public Result afficherAdminUtilisateur() {
        return ok(adminUtilisateur.render("Admin Utilisateur", Utilisateur.getAllNonArchives()));
    }

    public Result afficherUtilisateursArchives() {
        Logger.debug(Utilisateur.getAllArchives().toString());
        return ok(Json.toJson(Utilisateur.getAllArchives()));
    }
}
