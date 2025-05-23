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
		// sessionからログインしている情報を取得
		Admin admin = (Admin) session.getAttribute("loginAdminInfo");
		// もしユーザーログイン情報がなければ、ログイン画面へ
		if (admin == null) {
			return "redirect:/admin/login";
		} else {
			// ブログ記事を取得
			List<Blog> blogList = blogService.selectAllBlog(admin.getAdminId());
			// データを渡す
			model.addAttribute("adminName", admin.getAdminName());
			model.addAttribute("blogList", blogList);
			return "blog_list.html";
		}
	}
}
