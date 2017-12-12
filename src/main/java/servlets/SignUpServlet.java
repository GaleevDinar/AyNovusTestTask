package servlets;

import dbservice.dao.UserDao;
import dbservice.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@WebServlet("/sign-up")
public class SignUpServlet extends HttpServlet {
    UserDao dao = new UserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/view/sign-up.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String login = req.getParameter("login").trim();
        String password = req.getParameter("password");
        String repeatPassword = req.getParameter("repeatPassword");

        if (checkPasswordForEquals(password, repeatPassword)) {
            if (!isPasswordValid(password)) {
                req.setAttribute("info", "Пароль недостаточно сложен: должны быть цифры, заглавные и строчные буквы и длина минимум 8 символов");
            }
        } else {
            req.setAttribute("info", "Пароли не совпадают");
        }
        if (isLoginFree(login)) {
            if (!isLoginValid(login)) {
                req.setAttribute("info2", "Имя пользователя должно быть длиннее 4 символов и состоять из цифр, букв английского алфавита и точек");
            }
        } else {
            req.setAttribute("info2", "Логин занят");
        }
        if (isLoginFree(login) && isLoginValid(login) && checkPasswordForEquals(password, repeatPassword) && isPasswordValid(password)) {
            dao.addUser(new User(login, password));
            HttpSession session = req.getSession();
            session.setAttribute("login", login);
            resp.sendRedirect("/greetings");
        }
        doGet(req, resp);
    }

    protected boolean isLoginFree(String login) {
        User user = dao.getUserByLogin(login);
        return user == null;
    }

    protected boolean isLoginValid(String login) {
        if (login.length() > 4) {
            String check = login.replaceAll("[^a-zA-Z0-9.]", "");
            if (check.equals(login)) {
                return true;
            }
        }
        return false;
    }

    protected boolean checkPasswordForEquals(String pass1, String pass2) {
        return pass1.equals(pass2);
    }

    protected boolean isPasswordValid(String pass) {
        String passwordPattern = "(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,14}";
        Pattern pattern = Pattern.compile(passwordPattern);
        Matcher matcher = pattern.matcher(pass);
        return matcher.matches();
    }
}
