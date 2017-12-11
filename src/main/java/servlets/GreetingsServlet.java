package servlets;

import com.sun.istack.internal.Nullable;
import dbservice.dao.UserDao;
import dbservice.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/greetings")
public class GreetingsServlet extends HttpServlet {
    private volatile int viewCount;
    private UserDao dao;

    @Override
    public void init() throws ServletException {
        viewCount = 0;
        dao = new UserDao();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String login = (String) session.getAttribute("login");
        increaseView();

        if (lastTime(login) != 0) {
            Date dateLastTimeWas = new Date(lastTime(login));
            SimpleDateFormat dateFormatLastTimeWas = new SimpleDateFormat("HH:mm dd/MM/yyyy ");
            req.setAttribute("dateLastTimeWas", dateFormatLastTimeWas.format(dateLastTimeWas));
        } else {
            req.setAttribute("dateLastTimeWas", "Вы у нас впервые");
        }

        req.setAttribute("viewCount", viewCount / 2);
        req.setAttribute("name", login);

        Date date = new Date();
        SimpleDateFormat hour = new SimpleDateFormat("HH");
        if (Integer.valueOf(hour.format(date)) < 6) {
            req.setAttribute("greeting", "Доброй ночи");
        } else if (Integer.valueOf(hour.format(date)) <= 10) {
            req.setAttribute("greeting", "Доброе утро");
        } else if ((Integer.valueOf(hour.format(date)) <= 18)) {
            req.setAttribute("greeting", "Добрый день");
        } else if ((Integer.valueOf(hour.format(date)) <= 22)) {
            req.setAttribute("greeting", "Добрый вечер");
        } else {
            req.setAttribute("greeting", "Доброй ночи");
        }

        setTimeLastTimeWas(session.getLastAccessedTime(), login);
        req.getRequestDispatcher("WEB-INF/view/greetings.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        session.invalidate();
        resp.sendRedirect("/sign-in");
    }

    private synchronized int increaseView() {
        return ++viewCount;
    }

    @Nullable
    private long lastTime(String login) {
        User user = dao.getUserByLogin(login);
        return user.getLastTime();
    }

    private void setTimeLastTimeWas(long time, String login) {
        dao.changeLastTime(time, login);
    }
}
