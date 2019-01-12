package edu.mtsu.csci;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TargetRepository extends CrudRepository<Targets, Long> {
    Targets findByTarget(String target);
    Targets findByWordIdAndLanguageId(Long id, Long languageId);


    @Modifying
    @Transactional
    @Query(value = "insert into TARGETS (TARGET, WORD_ID, LANGUAGE_ID) values (?1, ?2, ?3)", nativeQuery = true)
    void insertTargetToTargets(String word, Long word_id, Long lang_id);

}

