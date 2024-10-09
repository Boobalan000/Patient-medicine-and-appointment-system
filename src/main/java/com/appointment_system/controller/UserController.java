package com.appointment_system.controller;

import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.appointment_system.entity.Appointment;
import com.appointment_system.entity.Doctor;
import com.appointment_system.entity.User;
import com.appointment_system.service.AppointmentService;
import com.appointment_system.service.DoctorService;
import com.appointment_system.service.MedicationService;
import com.appointment_system.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;


@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private DoctorService doctorService;
	
	@Autowired
	private MedicationService medicationService;
	
	@Autowired
	private AppointmentService appointmentService;
	
	private String email;
	
	@GetMapping("/")
	@Operation
	public String homePage(Model model,Principal principal)
	{
		if(principal!=null)
		{
			model.addAttribute("loggedIn",true);
		}
		else
		{
			model.addAttribute("loggedIn",false);
		}
		
		return "HomePage";
	}
	
	@GetMapping("/terms")
	@Operation
	public String termsAndCondition()
	{
		return "Terms";
	}
	
	@GetMapping("/privacy")
	@Operation
	public String privacyPolicy()
	{
		return "Privacy";
	}
	
	@GetMapping("/button/quick")
	@Operation
	public String quickAppointmentPage()
	{
		return "QuickAppointment";
	}
	
	@GetMapping("/button/available")
	@Operation
	public String availabilityPage()
	{
		return "Availability";
	}
	
	@GetMapping("/button/customer")
	@Operation
	public String customerSupportPage()
	{
		return "CustomerSupport";
	}
	
	@GetMapping("/user/register")
	@Operation
	public String getUserRegister(Model model)
	{
		User user=new User();
		model.addAttribute("userReg", user);
		return "Registration";
	}
	
	@PostMapping("/user/register")
	@Operation
	public String postUserRegister(@ModelAttribute("userReg")User user,Model model)
	{
		if(userService.findByUserEmail(user.getEmail()))
		{
			model.addAttribute("exist", "This "+user.getEmail()+" already exist");
			return "Registration";
		}
		else if(userService.save(user))
		{
			return "redirect:/user/login";
		}
		model.addAttribute("error", "error while creating your account try again :)");
		return "Registration";
	}
	
	@GetMapping("/user/login")
	@Operation
	public String getUserLogin(Model model)
	{
		User user=new User();
		model.addAttribute("userLog", user);
		return "Login";
	}
	
	@PostMapping("/user/login")
	@Operation
	public String postUserLogin(@ModelAttribute("userLog")User user,Model model,HttpServletResponse response)
	{
		if(userService.verify(user, response))
		{
			return "redirect:/user/options";
		}
		model.addAttribute("error", "Invalid email or password");
		return "Login";
	}
	
	@GetMapping("/user/options")
	@Operation
	public String userOption()
	{
		return "Option";
	}
	
	@GetMapping("/user/slot")
	@Operation
	public String viewTimeSlots(Model model)
	{
		model.addAttribute("dr", doctorService.getAllDoctor());
		return "TimeSlot";
	}
	
	@GetMapping("/user/medication")
	@Operation
	public String prescriptionPage(Model model,Principal principal)
	{
	    email=principal.getName();
		model.addAttribute("medication",medicationService.getAllMedicationUser(email));
		return "Prescription";
	}
	
	@GetMapping("/user/appointment/{id}")
	@Operation
	public String getUserAppointment(Model model,@PathVariable int id,Principal principal)
	{
		Appointment appointment=new Appointment();
		Optional<Doctor> doctorOp=doctorService.findById(id);
		email=principal.getName();
		User user=userService.findUser(email);
		
		if(doctorOp.isPresent())
		{
			Doctor doctor=doctorOp.get();
			model.addAttribute("appointmentUser", appointment);
			model.addAttribute("doctorInUser", doctor);
			model.addAttribute("userAppointment", user);
		}
		return "AppointmentPage";
	}
	
	@PostMapping("/user/appointment")
	@Operation
	public String postUserAppointment(@ModelAttribute("appointmentUser")Appointment appointment,Model model,RedirectAttributes redirect,Principal principal)
	{
		email=principal.getName();
		User user=userService.findUser(email);
		appointment.setUser(user);
		
		if(appointmentService.save(appointment))
		{
			redirect.addFlashAttribute("success", "Your appointment id "+appointment.getAppointmentId()+" is successfull");
			return "redirect:/user/appointment/submission";
		}
		model.addAttribute("error","Unable Book your Appointment due to Error" );
		return "AppointmentPage";
	}
	
	@GetMapping("/user/appointment/submission")
	@Operation
	public String appointmentSubmissionpage()
	{
		return "AppointmentSubmission";
	}
	
	@GetMapping("/user/appointment/details")
	@Operation
	public String appoincationDetails(Model model,Principal principal)
	{
		email=principal.getName();
		model.addAttribute("appointments", appointmentService.getAllAppointmentsByUser(email));
		return "AppointmentDetails";
	}
}
