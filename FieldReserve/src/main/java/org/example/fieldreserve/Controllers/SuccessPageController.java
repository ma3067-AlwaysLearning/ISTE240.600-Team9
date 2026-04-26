package org.example.fieldreserve.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SuccessPageController {

    // Using Path Variable
    @GetMapping("/success/{entityName}") // Handles requests to access the success page
    public String showSuccessPage(@PathVariable String entityName, Model model) {
        model.addAttribute("entityName", entityName); // Gives the model a name for it to be displayed dynamically
        return "success"; // Used to display the page
    }
}