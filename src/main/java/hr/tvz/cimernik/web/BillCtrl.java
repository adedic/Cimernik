package hr.tvz.cimernik.web;

import java.security.Principal;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import hr.tvz.cimernik.db.BillRepository;
import hr.tvz.cimernik.db.CategoryRepository;
import hr.tvz.cimernik.db.RoomateGroupRepository;
import hr.tvz.cimernik.db.UserRepository;
import hr.tvz.cimernik.model.Bill;
import hr.tvz.cimernik.model.Category;
import hr.tvz.cimernik.model.User;

@Controller
public class BillCtrl {
	@Autowired
	UserRepository userRepository;
	@Autowired
	RoomateGroupRepository groupRepository;
	@Autowired
	BillRepository billRepository;
	@Autowired
	CategoryRepository categoryRepository;

	@DeleteMapping("/deleteBill/{id}")
	ModelAndView deleteBill(@PathVariable Integer id, Principal principal) {
		billRepository.delete(billRepository.findOne(id));

		Integer userId = userRepository.findOneByUsername(principal.getName()).getId();
		return new ModelAndView("redirect:/group/bills/" + userId + "?deleteSuccess=true");
	}

	@GetMapping("/editBill/{id}") //
	String getEditBill(@PathVariable Integer id, Model model, Principal principal) {
		if (userRepository.findOneByUsername(principal.getName()).getRoomateGroup() == null) {
			return "redirect:/";
		}

		return "redirect:/dashboard?editBillSuccess=true";
	}

	@PostMapping("/group/editBill/{id}")
	String editBill(@PathVariable Integer id, Model model, Principal principal, @ModelAttribute("bill") Bill bill) {
		List<Category> categories = categoryRepository.findAll();
		categories.remove(0);
		model.addAttribute("categories", categories);

		User user = userRepository.findOneByUsername(principal.getName());
		model.addAttribute("user", user);
		Bill oldBill = billRepository.findOne(id);
		billRepository.delete(billRepository.findOne(id));
		bill = new Bill(user, user.getRoomateGroup(), bill.getTitle(), bill.getPrice(), oldBill.getDateCreated(),
				new Date(), bill.getDescription(), bill.getCategory());

		billRepository.save(bill);

		return "redirect:/group/dashboard?billEdit=true";
	}

	@GetMapping("/bill/new")
	String showFormAddBill(Model model, Principal principal) {

		if (userRepository.findOneByUsername(principal.getName()).getRoomateGroup() == null) {
			return "redirect:/";
		}
		User user = userRepository.findOneByUsername(principal.getName());
		model.addAttribute("user", user);
		model.addAttribute("bill", new Bill());
		List<Category> categories = categoryRepository.findAll();
		categories.remove(0);
		model.addAttribute("categories", categories);

		return "newBill";
	}

	@PostMapping("/bill/new")
	public String addNewBill(Model model, @Valid @ModelAttribute("bill") Bill bill, BindingResult bindingResult,
			Principal principal) {

		List<Category> categories = categoryRepository.findAll();
		categories.remove(0);
		model.addAttribute("categories", categories);
		boolean error = bindingResult.hasErrors();
		User user = userRepository.findOneByUsername(principal.getName());
		model.addAttribute("user", user);
		if (error) {
			return "newBill";
		}

		bill = new Bill(user, user.getRoomateGroup(), bill.getTitle(), bill.getPrice(), new Date(),
				bill.getDescription(), bill.getCategory());

		billRepository.save(bill);
		return "redirect:/group/dashboard";

	}

}
