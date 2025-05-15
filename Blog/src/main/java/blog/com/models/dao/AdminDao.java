package blog.com.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import blog.com.models.entity.Admin;

@Repository
public interface AdminDao extends JpaRepository<Admin, Long> {
    //保存と更新
	Admin save(Admin admin);

	// SELECT * FROM account WHERE admin_email = ?
	Admin findByAdminEmail(String adminEmail);

	// SELECT * FROM account WHERE admin_email = ? AND password = ?
	Admin findByAdminEmailAndPassword(String adminEmail, String password);
}
