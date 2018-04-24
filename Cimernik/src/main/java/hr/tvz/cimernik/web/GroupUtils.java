package hr.tvz.cimernik.web;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import hr.tvz.cimernik.repo.BillRepository;
import hr.tvz.cimernik.repo.CategoryRepository;
import hr.tvz.cimernik.repo.InviteRepository;
import hr.tvz.cimernik.repo.RoomateGroupRepository;
import hr.tvz.cimernik.repo.UserRepository;

public class GroupUtils {
	@Autowired
	static UserRepository userRepository;
	@Autowired
	static RoomateGroupRepository groupRepository;
	@Autowired
	static BillRepository billRepository;
	@Autowired
	static CategoryRepository categoryRepository;
	@Autowired
	static InviteRepository inviteRepository;

	public static String months[] = { "Siječanj", "Veljača", "Ožujak", "Travanj", "Svibanj", "Lipanj", "Srpanj",
			"Kolovoz", "Rujan", "Listopad", "Studeni", "Prosinac" };

	public static int currentMonth() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		return cal.get(Calendar.MONTH);

	}

}
