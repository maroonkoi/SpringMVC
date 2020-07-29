package com.softserve.edu.controller;

import com.softserve.edu.model.Marathon;
import com.softserve.edu.model.User;
import com.softserve.edu.service.MarathonService;
import com.softserve.edu.service.UserService;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Set;


@Controller
@Data
public class StudentController {

    private final UserService userService;
    private final MarathonService marathonService;

    @GetMapping("/users")
    public String viewAllUsers(Model model) {
        model.addAttribute("users", userService.getAll());
        return "users";
    }

    @GetMapping("/users/{id}")
    public String viewAllUsersByMarathon (@PathVariable String id, Model model){
        Marathon marathon = marathonService.getMarathonById(Long.valueOf(id));
        Set<User> users = marathon.getUsers();
        model.addAttribute("users", users);
        return "users";
    }

    //TODO implement needed methods

}
