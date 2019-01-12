package edu.mtsu.csci;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class WordController {
    @Autowired
    private WordRepository wordRepository;
   // private WordController repository;

    /**
     *
     * Returns list of all words in table, if word not passed in
     * otherwise, returns word object matching input
     * 127.0.0.1:8XXX/words
     * or
     * 127.0.0.1:8XXX/words?word=Hello
     */
    @RequestMapping(value="/words", method= RequestMethod.GET)
    List<Words> findAll(@RequestParam(required=false) String word){
        List<Words> words = new ArrayList<>();
        if(null==word){
            Iterable<Words> results = this.wordRepository.findAll();
            results.forEach(wordItem-> {words.add(wordItem);});
        }else{
            Words wordItem = this.wordRepository.findByWord(word);
            if(null!=wordItem) {
                words.add(wordItem);
            }
            else {
                throw new NoSuchElementException("Word (" + word + ") is not in system.");
            }
        }
        return words;
    }


    @RequestMapping(value="/words", method=RequestMethod.POST, consumes="application/json")
    @ResponseStatus(value = HttpStatus.OK)
    public void createWord(@RequestBody(required=true) String word)
    {
        if (!word.isEmpty()) {
            Words temp = new Words();
            try {
                temp = new ObjectMapper().readValue(word, Words.class);
            } catch (IOException e) {
                throw new RuntimeException("Unable to save new word");
            }
            if(this.wordRepository.findByWord(temp.getWord()) != null){
                throw new RuntimeException("Word already exists!");
            }
            this.wordRepository.insertWordToWords(temp.getWord());
        }
        else
        {
            throw new RuntimeException("Word to add must be specified!");
        }
    }

    @RequestMapping(value="/word", method=RequestMethod.PUT)
    @ResponseStatus(value = HttpStatus.OK)
    public void updateWord(@RequestParam(required=true) Long id, String word) {
        if (id > 0 && !word.isEmpty()) {
            Words existing = new Words();
            existing.setId(id);
            existing.setWord(word);
            if(this.wordRepository.findByWord(existing.getWord()) != null){
                throw new RuntimeException("Word already exists!");
            }
            this.wordRepository.save(existing);
        }
        else {
            throw new NoSuchElementException("Unable to update word");
        }
    }

    @RequestMapping(value="/word", method=RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteWord(@RequestParam(required=true) String word)
    {
        if (!word.isEmpty()) {
            try{
                Words existing = this.wordRepository.findByWord(word);
                this.wordRepository.delete(existing);
            }
            catch(Exception e){
                throw new NoSuchElementException("Unable to locate word");
            }
        }
        else {
            throw new NoSuchElementException("Unable to locate empty word");
        }
    }

    //ADD OTHER STUFF HERE (POST/PUT/DELETE && MORE GETS)

}


