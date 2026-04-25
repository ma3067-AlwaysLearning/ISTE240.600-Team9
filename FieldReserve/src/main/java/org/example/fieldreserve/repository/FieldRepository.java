package org.example.fieldreserve.repository;

import org.example.fieldreserve.model.Field;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FieldRepository extends JpaRepository<Field, Integer> {

    //Add findByFieldName(String fieldName)
    List<Field> findByFieldName(String fieldName);

    //Add one custom JPQL query, for example findAvailableFields()
    @Query("SELECT f FROM Field f WHERE f.availabilityStatus = 'AVAILABLE'")
    List<Field> findAvailableFields();

    //Add updateAvailabilityStatusById(Integer id, String availabilityStatus)
    @Modifying
    @Transactional
    @Query("UPDATE Field f SET f.availabilityStatus = :availabilityStatus WHERE f.fieldID = :id")
    void updateAvailabilityStatusById(@Param("id") Integer id, @Param("availabilityStatus") String availabilityStatus);

}