package edu.mtsu.csci;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class LanguageController {
    @Autowired
    private LanguageRepository repository;

    @RequestMapping(value="/languages", method= RequestMethod.GET)
    List<Languages> findAll(@RequestParam(required=false) String language){
        List<Languages> languages = new ArrayList<>();
        if(language==null || language.isEmpty()){
            Iterable<Languages> results = this.repository.findAll();
            results.forEach(languageItem-> {languages.add(languageItem);});
        }else{
            Languages languageItem = this.repository.findByLanguage(language);
            if(null!=languageItem) {
                languages.add(languageItem);
            }
            else{
                throw new NoSuchElementException("Language (" + language + ") is not in system.");
            }
        }
        return languages;
    }

    @RequestMapping(value="/languages", method=RequestMethod.POST, consumes="application/json")
    @ResponseStatus(value = HttpStatus.OK)
    public void createLanguage(@RequestBody(required=true) String language)
    {
        if (!language.isEmpty()) {
            Languages temp = new Languages();
            try {
                temp = new ObjectMapper().readValue(language, Languages.class);
            } catch (IOException e) {
                throw new RuntimeException("Unable to save new language");
            }
            if(this.repository.findByLanguage(temp.getLanguage()) != null){
                throw new RuntimeException("Language already exists!");
            }
            this.repository.insertLanguageToLanguages(temp.getLanguage());
        }
        else
        {
            throw new RuntimeException("Language to add must be specified!");
        }
    }

    @RequestMapping(value="/languages", method=RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public void updateLanguage(@RequestParam(required=true) Long id, String language)
    {
        if (id > 0 && !language.isEmpty())
        {
            Languages existing = new Languages();
            existing.setId(id);
            existing.setLanguage(language);
            if(this.repository.findByLanguage(existing.getLanguage()) != null){
                throw new RuntimeException("Language already exists!");
            }
            this.repository.save(existing);
        }
        else {
            throw new NoSuchElementException("Unable to update language");
        }
    }

    @RequestMapping(value="/languages", method=RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteLanguage(@RequestParam(required=true) String language)
    {
        if (!language.isEmpty()) {
            try{
                Languages existing = this.repository.findByLanguage(language);
                this.repository.delete(existing);
            }
            catch(Exception e){
                throw new NoSuchElementException("Unable to locate language");
            }
        }
        else {
            throw new NoSuchElementException("Unable to locate empty language");
        }
    }
}
