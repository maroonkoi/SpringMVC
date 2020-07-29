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

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Controller
@Data
public class StudentController {

    private final UserService userService;
    private final MarathonService marathonService;

    @GetMapping("/students")
    public String viewAllUsers(Model model) {
        model.addAttribute("students", getAllStudents(userService.getAll()));
        return "students";
    }

    @GetMapping("/students/{id}")
    public String viewAllUsersByMarathon (@PathVariable(name = "id") Long id, Model model){
        Marathon marathon = marathonService.getMarathonById(id);
        Set<User> users = marathon.getUsers();
        model.addAttribute("students", users);
        model.addAttribute("marathon", marathon);
        return "students";
    }
//    @GetMapping("/marathons/delete/{id}")
//    public String delete(@PathVariable(name = "id") Long id, Model model) {
//        marathonService.deleteMarathonById(id);
//        return "redirect:/marathons";
//    }


    public List<User> getAllStudents (List<User> users) {
        List<User> students = new ArrayList<>();
        for (User user : users) {
            if (user.getRole().equals(User.Role.TRAINEE)) {
                students.add(user);
            }
        }
        return students;

    }

    //TODO implement needed methods





}
