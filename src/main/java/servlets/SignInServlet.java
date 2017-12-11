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

@WebServlet("/sign-in")
public class SignInServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/view/sign-in.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        UserDao dao = new UserDao();
        User user = dao.getUserByLogin(login);
        if (user == null) {
            req.setAttribute("info", "Пользователь не найден");
            req.getRequestDispatcher("WEB-INF/view/sign-in.jsp").forward(req, resp);
        } else {
            if (user.getPass().equals(password)) {
                HttpSession session = req.getSession();
                session.setAttribute("login", login);
                resp.sendRedirect("/greetings");
            } else {
                req.setAttribute("info", "Введен неверный пароль");
                req.getRequestDispatcher("WEB-INF/view/sign-in.jsp").forward(req, resp);
            }
        }
    }
}
