
package workshop05;


import io.jsonwebtoken.Jwts;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.MediaType;


@WebServlet(urlPatterns = { "/login" })
public class LoginServlet extends HttpServlet{
    
    @Inject private KeyManager keyMgr;
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        
        try { 
            req.login(username, password);
        } catch (ServletException ex) {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
            resp.setContentType(MediaType.TEXT_PLAIN);
            try (PrintWriter pw = resp.getWriter()) {
                pw.println("incorrect login");
            }
            return;
            }
        System.out.println("LoginServlet");
        Map<String, Object> claims = new HashMap<>();
        claims.put("username",req.getRemoteUser());
        claims.put("role", "authenticate");
        claims.put("host", req.getRemoteHost());
        claims.put("port", req.getRemotePort());
        
        long exp = System.currentTimeMillis() + (1000 * 60 * 30);
        
        String token = Jwts.builder()
                .setAudience("Workshop05")
                .setExpiration(new Date(exp))
                .setIssuer("Workshop05")
                .addClaims(claims)
                .signWith(keyMgr.getkey())
                .compact();
        
           
        
        JsonObject result = Json.createObjectBuilder()
                .add("token_type","bearer")
                .add("token", token)
                .build();
        
         HttpSession sess = req.getSession();
            sess.invalidate();
            
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType(MediaType.APPLICATION_JSON);
                try (PrintWriter pw = resp.getWriter()) {
                pw.print(result.toString());
            } 
    }
}
    
    
    

