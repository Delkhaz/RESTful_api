package edu.mtsu.csci;

import javax.persistence.*;

@Entity
@Table(name="LANGUAGES")
public class Languages {
    @Id
    @Column(name="LANGUAGE_ID")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    @Column(name="LANGUAGE")
    private String language;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLanguage() { return language; }

    public void setLanguage(String language) {
        this.language = language;
    }

}
