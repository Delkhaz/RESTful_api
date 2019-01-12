package edu.mtsu.csci;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface WordRepository extends CrudRepository<Words, Long> {
    Words findByWord(String word);

    @Modifying
    @Transactional
    @Query(value = "insert into WORDS (WORD) values (?1)", nativeQuery = true)
    void insertWordToWords(String word);
}
