
package Workshop04.rest;

import Workshop04.business.CustomerBean;
import Workshop04.model.Customer;
import java.sql.SQLException;
import java.util.Optional;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.core.Response;



public class FindByCustomerIdRunnable implements Runnable{
    
    private CustomerBean customerBean;
    private Integer custId;
    private AsyncResponse asyncResp;
    
    public FindByCustomerIdRunnable(Integer cid, 
            CustomerBean cb, AsyncResponse ar) {
        custId = cid;
        customerBean = cb;
        asyncResp = ar;
        
    }

    @Override
    public void run() {
        Optional<Customer> opt = null;
        try {
            opt = customerBean.findByCustomerId(custId);      
        } catch (SQLException ex) {
            JsonObject error = Json.createObjectBuilder()
                    .add("error", ex.getMessage())
                    .build();
            //500 Server Error
            
            asyncResp.resume(Response.serverError().entity(error).build());
            return;
        }
        
        //Return 404 not found 
        
        if (!opt.isPresent()) {
            asyncResp.resume(Response.status(Response.Status.NOT_FOUND).build());
            return;
        }
        
        //Return the data as Json
        asyncResp.resume(Response.ok(opt.get().toJson()).build());   
        System.out.println(">> resuming request");
    }
    
    
}
