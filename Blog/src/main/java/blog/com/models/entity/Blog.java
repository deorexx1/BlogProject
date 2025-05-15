package blog.com.models.entity;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Blog {
//設定
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long blogId;
	private String blogTitle;
	private String blogContents;
	private Timestamp createdAt;
	private String blogImg;
	private Long adminId;

	public Blog() {
	}

	public Blog(String blogTitle, String blogContents, Timestamp createdAt, String blogImg, Long adminId) {
		this.blogTitle = blogTitle;
		this.blogContents = blogContents;
		this.createdAt = createdAt;
		this.blogImg = blogImg;
		this.adminId = adminId;
	}

	public Long getBlogId() {
		return blogId;
	}

	public void setBlogId(Long blogId) {
		this.blogId = blogId;
	}

	public String getBlogTitle() {
		return blogTitle;
	}

	public void setBlogTitle(String blogTitle) {
		this.blogTitle = blogTitle;
	}

	public String getBlogContents() {
		return blogContents;
	}

	public void setBlogContents(String blogContents) {
		this.blogContents = blogContents;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public String getBlogImg() {
		return blogImg;
	}

	public void setBlogImg(String blogImg) {
		this.blogImg = blogImg;
	}

	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}

}
