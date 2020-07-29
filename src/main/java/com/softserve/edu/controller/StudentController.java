package com.softserve.edu.controller;

import com.softserve.edu.model.Marathon;
import com.softserve.edu.model.User;
import com.softserve.edu.service.MarathonService;
import com.softserve.edu.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Controller
@AllArgsConstructor
@Data
public class StudentController {

    private UserService userService;
    private MarathonService marathonService;

    @GetMapping("/students")
    public String viewAllUsers(Model model) {
        model.addAttribute("students", getAllStudents(userService.getAll()));
        return "students";
    }


    @GetMapping("/students_marathon/{id}")
    public String viewAllUsersByMarathon (@PathVariable Long id, Model model){
        Marathon marathon = marathonService.getMarathonById(id);
        Set<User> users = marathon.getUsers();
        for (User user : users) {
            if (user.getRole().equals(User.Role.MENTOR)) {
                users.remove(user);
            }
        }
        model.addAttribute("students", users).addAttribute("marathon", marathon);
        return "students_marathon";

    }

    @GetMapping("/students/{marathon_id}/delete/{student_id}")
    public String removeStudentFromMarathon(@PathVariable(name = "marathon_id") Long marathonId,
                                            @PathVariable(name = "student_id") Long studentId,
                                            Model model) {
        Marathon marathon = marathonService.getMarathonById(marathonId);
        userService.removeUserFromMarathon(userService.getUserById(studentId), marathon);
        model.addAttribute("marathon", marathon);
        return "redirect:/students_marathon/" + marathonId.intValue();
    }

    @GetMapping("/student/{studentId}")
    public String showStudent (@PathVariable Long studentId, Model model){
        User user = userService.getUserById(studentId);
        model.addAttribute("student", user);
        return "student";
    }
    @GetMapping("/students/{marathonId}/add")
    public String addStudentToMarathon (@PathVariable Long marathonId, Model model){
        model.addAttribute("student", new User()).addAttribute("marathon", marathonService.getMarathonById(marathonId));
        return "create_student";
    }

    @PostMapping("/students/{marathonId}/add")
    public String addStudentToMarathon (@ModelAttribute(name="student") User user, @PathVariable("marathonId") Long id) {
        userService.addUserToMarathon(user, marathonService.getMarathonById(id));
        return "redirect:/students_marathon/"+id;
    }


    private List<User> getAllStudents (List<User> users) {
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
