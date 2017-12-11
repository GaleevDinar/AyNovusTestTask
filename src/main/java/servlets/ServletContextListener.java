package servlets;

import dbservice.dao.UserDao;
import dbservice.model.User;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;
import java.io.File;

@WebListener
public class ServletContextListener implements javax.servlet.ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String homeDir = sce.getServletContext().getRealPath("/");
        File propertiesFile = new File(homeDir, "log4j.properties");
        PropertyConfigurator.configure(propertiesFile.toString());
        Logger logger = LogManager.getLogger(ServletContextListener.class);
        logger.info("Логгер инициализировался");
        UserDao dao = new UserDao();
        dao.createTable();
        dao.addUser(new User("admin", "admin"));
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }


}
