package org.example.fieldreserve.Controllers;

import org.springframework.stereotype.Controller;

@Controller
public class MainController {
    private final DataService dataService;

    //Constructor injection
    public MainController(DataService dataService){
        this.dataService = dataService;
    }

    //For the landing page
    @GetMapping("/")
    public String home(){return "index";}

    //To display all fields as well as field table
    public String showLocationFields(Model model){
        model.addAttribute("fields", dataService.getFields());
        model.addAttribute("locations", dataService.getLocations());
        return "locationfield-datatable";
    }

    //Reservation table
    @GetMapping("/reservations")
    public String showReservations(Model model){
        model.addAttribute("reservations", dataService.getReservations());
        return "reservation-datatable";
    }

    //User table
    public String showUsers(Model model){
        model.addAttribute("users", dataService.getUsers());
        return "userdetails-datatable";
    }



}
