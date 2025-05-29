package blog.com.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.boot.test.mock.mockito.MockBean;
import blog.com.service.AdminService;

@SpringBootTest
@AutoConfigureMockMvc
public class AdminRegisterControllerTest {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private AdminService adminService;

	@BeforeEach
	public void prepareDate() {
		when(adminService.createAdmin(eq("gaku"), any(), any())).thenReturn(false);
		when(adminService.createAdmin("Akemi", "ake@test.com", "1234abcd")).thenReturn(true);
	}

	@Test
	public void testGetAdminRegisterPage_Succeed() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/admin/register");
		mockMvc.perform(request).andExpect(view().name("register.html"));
	}

	@Test
	public void testAdminRegisterProcess_NewAccount_Succeed() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.post("/admin/register/process").param("adminName", "Akemi")
				.param("adminEmail", "ake@test.com").param("password", "1234abcd");
		mockMvc.perform(request).andExpect(view().name("login.html"));
		verify(adminService, times(1)).createAdmin("Akemi", "ake@test.com","1234abcd");
	}

	@Test
	public void testAdminRegisterProcess_ExistingUsername_fail() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.post("/admin/register/process").param("adminName", "gaku")
				.param("adminEmail", "test@test.com").param("password", "123qwe");
		mockMvc.perform(request).andExpect(view().name("register.html"));
		verify(adminService, times(1)).createAdmin("gaku", "test@test.com","123qwe");

	}
}
