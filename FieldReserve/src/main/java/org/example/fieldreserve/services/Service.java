package org.example.fieldreserve.services;

import org.example.fieldreserve.model.Location;

import java.util.ArrayList;
import java.util.List;

@org.springframework.stereotype.Service
public class Service {
    public final List<Location> location;


    public Service() {
        this.location = new ArrayList<>();
    }

    private void seedData() {
        location.add(new Location("Qusais Fields","Al Qusais","Dubai", 10));
        location.add(new Location("Barsha Courts","Al Barsha","Dubai", 3));
    }

}
