package org.morehouse.app;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;


/**
 * App entry point, with Jetty.
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
    	ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        Server jettyServer = new Server(9090);
        jettyServer.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(
        		org.glassfish.jersey.servlet.ServletContainer.class, "/morehouse/restapp/*");

        jerseyServlet.setInitOrder(1);
        // Tells the Jersey Servlet to scan package where REST services/classes are to be loaded from.
        jerseyServlet.setInitParameter( 
        		"jersey.config.server.provider.packages",
        		"org.morehouse.app.resources");
        try {
            jettyServer.start();
            jettyServer.join();
        }finally {
            //jettyServer.destroy();
        }
    }
}
