package blog.com.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import blog.com.models.entity.Admin;
import blog.com.service.AdminService;
import jakarta.servlet.http.HttpSession;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminLoginControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private AdminService adminService;
	private Admin admin;

	@BeforeEach
	public void prepareDate() {
		  admin = new Admin();
	        admin.setAdminEmail("ake@test.com");
	        admin.setAdminName("Akemi");
	        admin.setPassword("1234abcd");
		when(adminService.loginCheck("ake@test.com", "1234abcd")).thenReturn(admin);
		when(adminService.loginCheck(any(), any())).thenReturn(null);
	}

//表示テスト
	@Test
	public void testGetAdminLoginPage_Succeed() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/admin/login");
		mockMvc.perform(request).andExpect(view().name("login.html"));
	}

// email入力値テスト
	@Test
	public void testGetAdminLPageProcess_Succeed() throws Exception {
		// テスト用のMockHttpSessionを作成
		MockHttpSession session = new MockHttpSession();
		
		// POSTリクエストを作成
		RequestBuilder request = MockMvcRequestBuilders.post("/admin/login/process").session(session)
				.param("adminEmail", "ake@test.com").param("password", "1234abcd");

		// リクエストを実行してレスポンスを取得
		MvcResult result = mockMvc.perform(request)		
				.andExpect(view().name("welcome.html")).andReturn();
		// セッションから "admin" を取得して null ではないことを確認

		HttpSession sessionAfterRequest = result.getRequest().getSession();
		Object adminInSession = sessionAfterRequest.getAttribute("admin");
		assertNotNull(adminInSession, "sessionはNULLではない");
	}

	@Test
	public void testAdminLoginProcess_InvalidEmail_False() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.post("/admin/login/process")
				.param("adminEmail", "test@test.com").param("password", "1234abcd");
		mockMvc.perform(request).andExpect(view().name("login.html"));
	}

	@Test
	public void testAdminLoginProcess_InvalidPassword_False() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.post("/admin/login/process").param("adminEmail", "ake@test.com")
				.param("password", "1234");
		mockMvc.perform(request).andExpect(view().name("login.html"));

	}

	@Test
	public void testadminLoginProcess_InvalidCredentials_False() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.post("/admin/login/process")
				.param("adminEmail", "test@test.com").param("password", "1234");
		mockMvc.perform(request).andExpect(view().name("login.html"));

	}

	@Test
	public void testUserEntityIsNullInSession() throws Exception {
		// GETリクエストを送信してセッションを取得
		HttpSession session = mockMvc.perform(get("/admin/login")).andExpect(status().isOk()) // ステータスが200 OKであることを期待
				.andReturn() // リクエストとレスポンスの結果を返す
				.getRequest() // リクエストを取得
				.getSession(); // リクエストからセッションを取得

		// セッションからUserEntityを取得してnullであることを検証
		Object userInSession = session.getAttribute("admin");
		assertNull(userInSession, "sessionはNULLである");
	}
}
