package models;

import com.avaje.ebean.Model;
import play.data.validation.Constraints;

import javax.persistence.*;
import javax.validation.Constraint;

/**
 * Created by Gishan on 04/01/2016.
 */
@MappedSuperclass
public abstract class Personne extends Model{
    @Id
    @GeneratedValue
    public Long id;
    @Column(name = "nom")
    public String nom;
    @Column(name = "prenom")
    public String prenom;

    @Constraints.Email
    @Constraints.Required
    @Column(name = "email")
    public String email;
    @Column(name = "telephone")
    public String telephone;

    public static Model.Finder<Long, Personne> find = new Model.Finder<>(Personne.class);

    public Personne(String nom, String prenom, String email, String telephone) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
    }

    public Personne() {
    }

    @Override
    public String toString() {
        return "[Personne : " + id + "] : " +
                nom + ", " + prenom + ", " +
                email + ", " + telephone;
    }
}
