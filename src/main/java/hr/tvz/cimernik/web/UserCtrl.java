package hr.tvz.cimernik.web;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import hr.tvz.cimernik.db.UserRepository;
import hr.tvz.cimernik.model.User;

@Controller
public class UserCtrl {
	@Autowired
	UserRepository userRepository;

	@GetMapping("/user/details/{id}")
	String showUserDetails(Model model, Principal principal, @PathVariable Integer id) {
		User user = userRepository.findOne(id);
		User currentUser = userRepository.findOneByUsername(principal.getName());
		if (user == null) {
			return "redirect:/group/dashboard";
		}
		if (user.getRoomateGroup() == null) {
			return "redirect:/group/dashboard";
		}
		if (user.equals(currentUser)) {//bug kad je na ekranu cimera, ne promijeni se id
			model.addAttribute("user", currentUser);
		} else if (!user.equals(currentUser))
			model.addAttribute("user", user);

		return "userDetails";
	}
}
