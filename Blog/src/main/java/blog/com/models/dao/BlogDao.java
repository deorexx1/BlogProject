package blog.com.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import blog.com.models.entity.Blog;

@Repository
public interface BlogDao extends JpaRepository<Blog, Long> {
	// 保存と更新
	Blog save(Blog blog);

	// SELECT * FROM blog
	List<Blog>findAll();
	
	// SELECT * FROM blog WHERE blog_title = ?
	Blog findByBlogTitle(String blogTitle);
	
	// SELECT * FROM blog WHERE blog_id = ?
	Blog findByBlogId(Long blogId);
	
	//Delete FROM blog WHERE blog_id = ?
	void deleteByBlogId(Long blogId);
}
