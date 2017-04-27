package com.pingrong.web.start;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class InterfaceUrlInitServlet extends HttpServlet {

    private static final long serialVersionUID = 2590783578755346877L;

    @Override
    public void init(ServletConfig config) throws ServletException {
        InterfaceUrlInit.init();
    }
}
