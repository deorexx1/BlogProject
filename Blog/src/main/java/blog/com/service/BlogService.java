package blog.com.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blog.com.models.dao.BlogDao;
import blog.com.models.entity.Blog;
import jakarta.transaction.Transactional;

@Service
public class BlogService {
	@Autowired
	private BlogDao blogDao;

	public List<Blog> selectAllBlog(Long adminId) {
		if (adminId == null) {
			return null;
		} else {
			return blogDao.findAll();
		}
	}

	// ブログ登録処理チェック
	public boolean creatBlog(String blogTitle, String blogContents, Timestamp createdAt, String blogImg, Long adminId) {
		if (blogDao.findByBlogTitle(blogTitle) == null) {
			blogDao.save(new Blog(blogTitle, blogContents, createdAt, blogImg, adminId));
			return true;
		} else {
			return false;
		}
	}

	// 編集画面を表示するときのチェック
	public Blog blogEditCheck(Long blogId) {
		if (blogId == null) {
			return null;
		} else {
			return blogDao.findByBlogId(blogId);
		}

	}

	// 更新処理のチェック
	public boolean blogUpdate(Long blogId, String blogTitle, String blogContents, Timestamp createdAt, String blogImg) {
		if (blogId == null) {
			return false;
		} else {
			Blog blog = blogDao.findByBlogId(blogId);
			blog.setBlogContents(blogContents);
			blog.setBlogImg(blogImg);
			blog.setBlogTitle(blogTitle);
			blog.setCreatedAt(createdAt);
			blogDao.save(blog);
			return true;
		}
	}

	// 削除処理
	@Transactional
	public boolean deleteBlog(Long blogId) {
		if (blogId == null) {
			return false;
		} else {
			blogDao.deleteByBlogId(blogId);
			return true;
		}
	}
}