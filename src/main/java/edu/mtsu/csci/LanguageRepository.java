package edu.mtsu.csci;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface LanguageRepository extends CrudRepository<Languages, Long> {
    Languages findByLanguage(String language);

    @Modifying
    @Transactional
    @Query(value = "insert into LANGUAGES (LANGUAGE) values (?1)", nativeQuery = true)
    void insertLanguageToLanguages(String language);
}

