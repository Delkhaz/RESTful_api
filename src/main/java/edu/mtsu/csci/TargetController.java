package edu.mtsu.csci;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TargetController {
    @Autowired
    private TargetRepository repository;
    @Autowired
    private WordRepository wordRepository;
    @Autowired
    private LanguageRepository languageRepository;

    /**
     *
     * Returns list of all targets in table, if target not passed in
     * otherwise, returns target object matching input
     * 127.0.0.1:8XXX/targets
     * or
     * 127.0.0.1:8XXX/targets?target=Hola
     */
    @RequestMapping(value="/targets", method= RequestMethod.GET)
    List<Targets> findAll(@RequestParam(required=false) String target){
        List<Targets> targets = new ArrayList<>();
        if(null==target){
            Iterable<Targets> results = this.repository.findAll();
            results.forEach(wordItem-> {targets.add(wordItem);});
        }else{
            Targets targetItem = this.repository.findByTarget(target);
            if(null!=targetItem) {
                targets.add(targetItem);
            }
        }
        return targets;
    }

    //This works!
    // 127.0.0.1:8XXX/targets/getById?id=1
    @RequestMapping(value="/targets/getById", method= RequestMethod.GET)
    String findTargetByID(@RequestParam(required=true) Long id){
        return this.repository.findById(id).orElseThrow(RuntimeException::new).getTarget();
    }

    // 127.0.0.1:8XXX/targets/getTranslationByLanguageId?word=Hello&languageId=1&targetLanguageId=2
    @RequestMapping(value="/targets/getTranslationByLanguageId", method= RequestMethod.GET)
    String findTranslationByLanguageId(@RequestParam(required=true) String word, Long languageId, Long targetLanguageId){
        return this.repository.findByWordIdAndLanguageId((this.wordRepository.findByWord(word)).getId(), targetLanguageId).getTarget();
        //return new String();
    }
    // 127.0.0.1:8XXX/targets/getTranslationByLanguage?word=Hello&language=English&targetLanguage=Spanish
    @RequestMapping(value="/targets/getTranslationByLanguage", method= RequestMethod.GET)
    String findTranslationByLanguage(@RequestParam(required=true) String word, String language, String targetLanguage){
        return this.repository.findByWordIdAndLanguageId((this.wordRepository.findByWord(word)).getId(), (this.languageRepository.findByLanguage(targetLanguage)).getId()).getTarget();
        //return new String();
    }

    //ADD OTHER STUFF HERE (POST/PUT/DELETE && MORE GETS??)

    @RequestMapping(value="/targets", method=RequestMethod.POST, consumes="application/json")
    @ResponseStatus(value = HttpStatus.OK)
    public void createTarget(@RequestBody(required=true) String target) {
        Targets temp = new Targets();
        try {
            temp = new ObjectMapper().readValue(target, Targets.class);
        } catch (IOException e) {
            throw new RuntimeException("Unable to save new target");
        }
        if(this.repository.findByTarget(temp.getTarget()) != null){
            throw new RuntimeException("Target already exists!");
        }
        //this.repository.save(temp);
        this.repository.insertTargetToTargets(temp.getTarget(), temp.getWordId(), temp.getLanguageId());
    }

}



