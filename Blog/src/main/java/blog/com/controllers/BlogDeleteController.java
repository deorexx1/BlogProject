package blog.com.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import blog.com.models.entity.Admin;
import blog.com.service.BlogService;
import jakarta.servlet.http.HttpSession;

@Controller
public class BlogDeleteController {
	@Autowired
	private BlogService blogService;
	@Autowired
	private HttpSession session;

	@PostMapping("/blog/delete")
	public String blogDelete(Long blogId) {
		Admin admin = (Admin) session.getAttribute("loginAdminInfo");
		if (admin == null) {
			return "redirect:/admin/login";
		} else {
			if (blogService.deleteBlog(blogId)) {
				return "redirect:/blog/list";
			} else {
				return "redirect:/blog/edit" + blogId;
			}
		}
	}
}