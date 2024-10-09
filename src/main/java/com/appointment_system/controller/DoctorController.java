package com.appointment_system.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.appointment_system.entity.Doctor;
import com.appointment_system.entity.Medication;
import com.appointment_system.service.AppointmentService;
import com.appointment_system.service.DoctorService;
import com.appointment_system.service.MedicationService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class DoctorController {

	@Autowired
	private DoctorService doctorService;
	
	@Autowired
	private AppointmentService appointmentService;
	
	@Autowired
	private MedicationService medicationService;
	
	private String email;
	
	@GetMapping("/doctor/login")
	@Operation
	public String getLogin(Model model)
	{
		Doctor doctor=new Doctor();
		model.addAttribute("doctorLogin", doctor);
		return "DoctorLogin";
	}
	
	@PostMapping("/doctor/login")
	@Operation
	public String postLogin(@ModelAttribute("doctorLogin")Doctor doctor,Model model,HttpServletResponse response)
	{
		if(doctorService.verify(doctor, response))
		{
			return "redirect:/doctor/options";
		}
		model.addAttribute("error", "Invalid email or pasword");
		return "DoctorLogin";
	}
	
	@GetMapping("/doctor/options")
	@Operation
	public String optionPage()
	{
		return "DoctorOption";
	}
	
	@GetMapping("/doctor/appointments")
	@Operation
	public String getDoctorAppointment(Model model,Principal principal)
	{
		email=principal.getName();
		model.addAttribute("appointment", appointmentService.getAllAppointmentsByDdooctor(email));
		return "DoctorAppointment";
	}
	
	@GetMapping("/doctor/medication")
	@Operation
	public String medicationPage()
	{
		return "DoctorMedicationPage";
	}
	
	@GetMapping("/doctor/medication/add")
	@Operation
	public String getAddMedicine(Model model)
	{
		Medication medication=new Medication();
		model.addAttribute("medicines", medication);
		return "DoctorAddMedication";
	}
	
	@PostMapping("/doctor/medication/add")
	@Operation
	public String postAddMedicine(@ModelAttribute("medicines")Medication medication,Principal principal)
	{
		email=principal.getName();
		Doctor doctor=doctorService.findByEmail(email);
		medication.setDoctor(doctor);
		medicationService.save(medication);
		return "redirect:/doctor/medication";
		
	}
	
	@GetMapping("/doctor/medication/modify")
	@Operation
	public String modifyMedication(Model model,Principal principal)
	{
		email=principal.getName();
		model.addAttribute("medication", medicationService.getAllMedicationDoctor(email));
		return "DoctorModifyMedication";
	}
	
	@GetMapping("/doctor/medication/delete/{id}")
	@Operation
	public String medicineDelete(@PathVariable int id,Model model)
	{
		medicationService.deleteById(id);
		return "redirect:/doctor/medication/modify";
	}
	
	@GetMapping("/doctor/medication/update/{id}")
	@Operation
	public String medicineUpdate(Model model,@PathVariable int id)
	{
		model.addAttribute("medication", medicationService.getMedicationById(id));
		return "DoctorUpdateMedication";
	}
	
	@PostMapping("/doctor/medication/update/{id}")
	@Operation
	public String postMedicineUpdate(@ModelAttribute("medication")Medication medication,@PathVariable int id )
	{
		Medication existing_medicine=medicationService.getMedicationById(id);
		existing_medicine.setId(id);
		existing_medicine.setPatientEmail(medication.getPatientEmail());
		existing_medicine.setMedicine(medication.getMedicine());
		existing_medicine.setMorning(medication.getMorning());
		existing_medicine.setAfternoon(medication.getAfternoon());
		existing_medicine.setEvening(medication.getEvening());
		existing_medicine.setNight(medication.getNight());
		existing_medicine.setAfterOrBefore(medication.getAfterOrBefore());
		existing_medicine.setAppointmentId(medication.getAppointmentId());
		medicationService.save(existing_medicine);
		
		return "redirect:/doctor/medication/modify";
	}
}
