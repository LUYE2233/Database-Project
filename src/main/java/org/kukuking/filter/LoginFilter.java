package org.kukuking.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.kukuking.entity.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebFilter("/Filter/*")
public class LoginFilter extends HttpFilter {
//    private final List<String> exclude = List.of("/Filter/Login","/Filter/Reset","/Filter/SignUp");

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
/*        for (String st : exclude){
            if (st.equals(req.getServletPath())){
                chain.doFilter(req,res);
            }
        }*/
        User user = (User) req.getSession().getAttribute("user");
        if(user != null){
            chain.doFilter(req,res);
        }else {
            res.sendRedirect(req.getContextPath()+"/Login");
        }
    }
}
