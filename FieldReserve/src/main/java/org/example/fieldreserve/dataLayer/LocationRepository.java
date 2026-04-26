package org.example.fieldreserve.dataLayer;


import org.example.fieldreserve.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {

    @Override
    Optional<Location> findById(Long id);

    @Override
    List<Location> findAll();

    @Query("SELECT 1 FROM Location l WHERE l.locationCity = :city")
    List<Location> findByCity(@Param("city") String city);

    void deleteById(Long id);

    @Modifying
    @Query("UPDATE Location l SET l.locationCity = :city WHERE l.locationID = :id")
    int updateLocationCityById(@Param("id") int id, @Param("city") String city);
}
