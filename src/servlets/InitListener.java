package servlets;

import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;

import dao.ConnectionManager;


public class InitListener implements ServletContextListener {

 
    public void contextDestroyed(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    }


    public void contextInitialized(ServletContextEvent e)  { 
         System.out.println("Initialization...");
         
         ServletContext context = e.getServletContext();
         
         context.setAttribute("usersSessions", new HashMap<String, HttpSession>());
         
         
         ConnectionManager.open();
         
         
         
         System.out.println("Finished...");
    }
	
}
