package org.example.fieldreserve.service;

import jakarta.transaction.Transactional;
import org.example.fieldreserve.dataLayer.LocationRepository;
import org.example.fieldreserve.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class LocationService {

    @Autowired
    private LocationRepository locationRepository;

    public List<Location> getAllLocations(){
        return locationRepository.findAll();
    }

    public Optional<Location> getLocationById(long id){
        return locationRepository.findById(id);
    }

    public List<Location> findByCity(String city){
        return locationRepository.findByCity(city);
    }

    public Location saveLocation(Location location){
        return locationRepository.save(location);
    }

    public void deleteLocationById(Long id){
        locationRepository.deleteById(id);
    }
    public Location updateLocation(Long id, Location locationToUpdate){
        Location existingLocation = locationRepository.findById(id).orElseThrow(()-> new RuntimeException("Location not found"));
        existingLocation.setLocationName(locationToUpdate.getLocationName());
        existingLocation.setLocationArea(locationToUpdate.getLocationArea());
        existingLocation.setLocationCity(locationToUpdate.getLocationCity());
        return locationRepository.save(existingLocation);
    }

}
