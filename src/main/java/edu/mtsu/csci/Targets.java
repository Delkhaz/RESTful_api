package edu.mtsu.csci;

import javax.persistence.*;

@Entity
@Table(name="TARGETS")
public class Targets {
    @Id
    @Column(name="TARGET_ID")
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;
    @Column(name="TARGET")
    private String target;
    @Column(name="WORD_ID")
    private long wordId;
    @Column(name="LANGUAGE_ID")
    private long languageId;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public long getWordId() {
        return wordId;
    }

    public void setWordId(long wordId) {
        this.wordId = wordId;
    }

    public long getLanguageId() {
        return languageId;
    }

    public void setLanguageId(long languageId) {
        this.languageId = languageId;
    }

}
