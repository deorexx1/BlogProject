package blog.com.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import blog.com.models.entity.Admin;
import blog.com.models.entity.Blog;
import blog.com.service.BlogService;
import jakarta.servlet.http.HttpSession;

@Controller
public class BlogListController {
	@Autowired
	private HttpSession session;
	@Autowired
	private BlogService blogService;

	// ブログ一覧画面を表示
	@GetMapping("/blog/list")
	public String getBlogList(Model model) {
		Admin admin = (Admin) session.getAttribute("loginAdminInfo");
		if(admin == null) {
			return "redirect:/admin/login";
		}else {
			List<Blog>blogList = blogService.selectAllBlog(null);
			model.addAttribute("adminName",admin.getAdminName());
			return "blog_list.html";
		}
	}
}
