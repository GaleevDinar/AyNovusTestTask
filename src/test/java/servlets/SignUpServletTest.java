package servlets;

import dbservice.dao.UserDao;
import dbservice.model.User;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SignUpServletTest {
    private SignUpServlet signUpServlet = new SignUpServlet();
    UserDao dao = new UserDao();

    @Test
    public void whenLoginNotFreeThenAssertFalse() throws Exception {
        dao.createTable();
        dao.addUser(new User("admin", "admin"));
        dao.addUser(new User("test", "test"));
        assertFalse(signUpServlet.isLoginFree("admin"));
        assertFalse(signUpServlet.isLoginFree("test"));
    }

    @Test
    public void whenLoginFreeThenAssertTrue() throws Exception {
        dao.createTable();
        assertTrue(signUpServlet.isLoginFree("admin"));
        assertTrue(signUpServlet.isLoginFree("test"));
    }

    @Test
    public void whenLoginIsNotValidThenAssertFalse() throws Exception {
        String[] invalidLogins = {"zxc", "B@TM@AN", "John_Snow", "Rob()"};
        for (String login : invalidLogins) {
            assertFalse(signUpServlet.isLoginValid(login));
        }
    }

    @Test
    public void whenLoginIsValidThenAssertTrue() throws Exception {
        String[] validLogins = {"qwerty", "Batman", "John.Snow", "Rob..."};
        for (String login : validLogins) {
            assertTrue(signUpServlet.isLoginValid(login));
        }
    }

    @Test
    public void whenPasswordsNotEqualsAssertFalse() throws Exception {
        String[] pass1 = {"12345", "111", "zxc", "qwe"};
        String[] pass2 = {"12333", "121", "zxc11", "qWe"};
        for (int i = 0; i < pass1.length; i++) {
            assertFalse(signUpServlet.checkPasswordForEquals(pass1[i], pass2[i]));
        }
    }

    @Test
    public void whenPasswordsAreEqualsAssertTrue() throws Exception {
        String[] pass1 = {"12345", "111", "zxc", "qwe", "12345678"};
        String[] pass2 = {"12345", "111", "zxc", "qwe", "12345678"};
        for (int i = 0; i < pass1.length; i++) {
            assertTrue(signUpServlet.checkPasswordForEquals(pass1[i], pass2[i]));
        }
    }

    @Test
    public void whenPasswordValidAssertTrue() throws Exception {
        String[] validPasswords = {"12345678Qz", "111222Zz", "QWEzxc123.", "123/456Pp"};
        for (String pass : validPasswords) {
            assertTrue(signUpServlet.isPasswordValid(pass));
        }
    }

    @Test
    public void whenPasswordIsNotValidAssertFalse() throws Exception {
        String[] invalidPasswords = {"1234Qz", "111222Z", "QWEzxcRRsd.", "123/456ppp"};
        for (String pass : invalidPasswords) {
            assertFalse(signUpServlet.isPasswordValid(pass));
        }
    }
}