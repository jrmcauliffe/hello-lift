import org.eclipse.jetty.server.Server
import org.eclipse.jetty.servlet.{DefaultServlet, ServletContextHandler}
import net.liftweb.http.LiftFilter
import org.eclipse.jetty.server.nio.SelectChannelConnector

object JettyLauncher {
  def main(args: Array[String]) {
    val port = if(System.getenv("PORT") != null) System.getenv("PORT").toInt else 8080
    val server = new Server
    val scc = new SelectChannelConnector
    scc.setPort(port)
    server.setConnectors(Array(scc))
      
    val context = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS)
    context.addServlet(classOf[DefaultServlet], "/");
    context.addFilter(classOf[LiftFilter], "/*", 0)
    context.setResourceBase("src/main/webapp")

    server.start
    server.join
  }
}
