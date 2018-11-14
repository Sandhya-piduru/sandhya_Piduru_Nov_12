/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package workshop02.web;


import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import workshop02.business.CustomerBean;
import workshop02.model.Customer;
import java.util.Optional;
import workshop02.business.CustomerException;
import workshop02.model.DiscountCode;

@WebServlet(urlPatterns = {"/customer"})
public class CustomerServlet extends HttpServlet {

    @EJB private CustomerBean customerBean;
    
    private Customer createCustomer(HttpServletRequest req) {
        Customer customer = new Customer();
        customer.setCustomerId(Integer.parseInt(req.getParameter("customerId")));
        customer.setName(req.getParameter("name"));
        customer.setAddressline1(req.getParameter("addressline1"));
        customer.setAddressline2(req.getParameter("addressline2"));
        customer.setCity(req.getParameter("city"));
        customer.setState(req.getParameter("state"));
        customer.setZip(req.getParameter("zip"));
        customer.setPhone(req.getParameter("phone"));
        customer.setFax(req.getParameter("fax"));
        customer.setEmail(req.getParameter("email"));
        
        
        DiscountCode dc = new DiscountCode();
        dc.setDiscountCode(DiscountCode.Code.valueOf(req.getParameter("discountCode")));
        customer.setDiscountCode(dc);
        customer.setCreditlimit(Integer.parseInt(req.getParameter("creditLimit")));
        return (customer);
    }
    //Get /Workshop02/Customer
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        
        Integer customerId = Integer.parseInt(req.getParameter("customerId"));
        
//we get an optional object which indicates that the object may not exists

        Optional<Customer> opt = customerBean.findByCustomerId(customerId);
        
        if (!opt.isPresent()) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            resp.setContentType("text/plain");
            try (PrintWriter pw = resp.getWriter()) {
                pw.printf("customer is %d does not exists\n", customerId);
            }
            return;
        }
        
        Customer customer = opt.get();
        
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.setContentType("application/Json");
        try (PrintWriter pw = resp.getWriter()){
            pw.print(customer.toJson());
        }
        
        
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {

        Customer customer = createCustomer(req);
        Optional<Customer> opt = customerBean.findByCustomerId(customer.getCustomerId());
        if (opt.isPresent()) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setContentType("text/plain");
            try (PrintWriter pw = resp.getWriter()) {
                pw.printf("customer id %d does exists", customer.getCustomerId());
            }
            return;
        }
        try {
        customerBean.addNewCustomer(customer);
        } catch (CustomerException ex) {
            ex.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resp.setContentType("text/plain");
            try (PrintWriter pw = resp.getWriter()) {
                pw.printf(ex.getMessage());        
            }
            return;
        }
        resp.setStatus(HttpServletResponse.SC_ACCEPTED);
        resp.setContentType("text/plain");
        try (PrintWriter pw = resp.getWriter()) {
            pw.printf("customer created");
        }
    }

    
  }
