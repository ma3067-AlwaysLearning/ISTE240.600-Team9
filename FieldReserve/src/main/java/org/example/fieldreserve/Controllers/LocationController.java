package org.example.fieldreserve.Controllers;

import org.example.fieldreserve.Services.LocationService;
import org.example.fieldreserve.model.Location;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @GetMapping
    public List<Location> getAllLocations() {
        return locationService.getAllLocations();
    }

    @GetMapping("/{id}")
    public Optional<Location> getLocationById(@PathVariable int id) {
        return locationService.getLocationById(id);
    }

    @GetMapping("/citySearch")
    public List<Location> searchByCity(@RequestParam String city){
        return locationService.findByCity(city);
    }

    @PostMapping
    public Location createLocation(@RequestBody Location location){
        return locationService.saveLocation(location);
    }

    @PutMapping("/{id}")
    public Location updateLocation(@PathVariable Long id, @RequestBody Location location){
        return locationService.updateLocation(id, location);
    }

    @DeleteMapping("/{id}")
    public void deleteLocationById(@PathVariable long id){
        locationService.deleteLocationById(id);
    }
}