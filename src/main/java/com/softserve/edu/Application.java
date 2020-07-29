package com.softserve.edu;


import com.softserve.edu.model.*;
import com.softserve.edu.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;


@SpringBootApplication
public class Application implements CommandLineRunner {

	UserService userService;
	MarathonService marathonService;

	public Application(UserService userService, MarathonService marathonService, SprintService sprintService, TaskService taskService, ProgressService progressService) {
		this.userService = userService;
		this.marathonService = marathonService;
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Running Spring Boot Application");
		try{
			for (int i=0; i < 100; i++) {
				User user = new User();
				user.setPassword("q1w2e3r4t5y6");
				user.setRole(User.Role.TRAINEE);
				user.setFirstName("User № " + i);
				user.setLastName("Mister " + i);
				user.setEmail("userMail" + i + "@email.com");
				userService.createOrUpdateUser(user);
			}
		}
		catch (ConstraintViolationException e){
			System.out.println(e.getMessage());
		}

		try{
			for (int i=0; i < 5; i++) {
				Marathon marathon = new Marathon();
				marathon.setTitle("Marathon № " + i);
				marathonService.createOrUpdate(marathon);
			}
		}
		catch (ConstraintViolationException e){
			System.out.println(e.getMessage());
		}
		addUsersToMarathon();
	}

	private void addUsersToMarathon() {
		int userNumber = userService.getAll().size();
		int marathonNumber = marathonService.getAll().size();
		Random randomUserId = new Random();
		Random randomMarathonId = new Random();
		for (int i = 1; i <= userNumber; i++) {
			User user = userService.getUserById((long) randomUserId.nextInt(userNumber) + 1);
			Marathon marathon = marathonService.getMarathonById((long) randomMarathonId.nextInt(marathonNumber) + 1);
			userService.addUserToMarathon(user, marathon);
		}
	}
}