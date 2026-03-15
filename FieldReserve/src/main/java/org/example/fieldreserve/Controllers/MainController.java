package org.example.fieldreserve.Controllers;

import org.example.fieldreserve.Services.ReservationService;
import org.example.fieldreserve.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    private final ReservationService service;

    public MainController(ReservationService service) {
        this.service = service;
    }

    @GetMapping("/user")
    public String showUsers(Model model) {
        model.addAttribute("users", service.getAllUsers());
        return "userdetails-datatable";
    }

    @GetMapping("/user/add")
    public String showAddUserPage(Model model) {
        model.addAttribute("user", new User());
        return "userdetails_form";
    }
}
