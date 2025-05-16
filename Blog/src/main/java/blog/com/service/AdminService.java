package blog.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import blog.com.models.dao.AdminDao;
import blog.com.models.entity.Admin;

@Service
public class AdminService {
	@Autowired
	private AdminDao adminDao;

	public boolean createAdmin( String adminName,String adminEmail, String password) {
		if (adminDao.findByAdminEmail(adminEmail) == null) {
			adminDao.save(new Admin(adminEmail, adminName, password));
			return true;
		} else {
			return false;
		}
	}

	
	// ログイン処理
	// メールとパスワードなければ
	public Admin loginCheck(String adminEmail, String password) {
		Admin admin = adminDao.findByAdminEmailAndPassword(adminEmail, password);
		if (admin == null) {
			return null;
		} else {
			return admin;
		}
	}

}
