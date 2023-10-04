package practicespringboot.notesapplicationonline.note;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import practicespringboot.notesapplicationonline.user.Users;

import java.io.Serializable;
import java.util.Date;
@Entity
public class Note implements Serializable {
    @Id
    private String id;
    private String title;
    private String description;
    private Date date;
    @ManyToOne
    private Users owner;

    public Note() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Users getOwner() {
        return owner;
    }

    public void setOwner(Users owner) {
        this.owner = owner;
    }
}
