package ru.rosbank.javaschool.web.servlet;

import ru.rosbank.javaschool.util.SQLTemplate;
import ru.rosbank.javaschool.web.constant.Constants;
import ru.rosbank.javaschool.web.dto.ProductDetailsDto;
import ru.rosbank.javaschool.web.model.ProductModel;
import ru.rosbank.javaschool.web.repository.*;
import ru.rosbank.javaschool.web.service.ProductAdminService;
import ru.rosbank.javaschool.web.service.ProductUserService;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

public class AdminServlet extends HttpServlet {

    private ProductAdminService productAdminService;

    @Override
    public void init() throws ServletException {
        log("init");
        try {
            InitialContext initialContext = new InitialContext();
            DataSource dataSource = (DataSource) initialContext.lookup("java:/comp/env/jdbc/db");
            SQLTemplate sqlTemplate = new SQLTemplate();
            ProductRepository productRepository = new ProductRepositoryJdbcImpl(dataSource, sqlTemplate);
            OrderRepository orderRepository = new OrderRepositoryJdbcImpl(dataSource, sqlTemplate);
            OrderPositionRepository orderPositionRepository = new OrderPositionRepositoryJdbcImpl(dataSource, sqlTemplate);
            productAdminService = new ProductAdminService(productRepository, orderRepository, orderPositionRepository);
            //insertInitialData(productRepository);
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    String url = req.getRequestURI().substring(req.getContextPath().length());

        if (url.equals("/fastfood/admin")) {
            req.setAttribute(Constants.ITEMS, productAdminService.getAll());
          req.getRequestDispatcher("/WEB-INF/admin/frontpage.jsp").forward(req, resp);
          return;
        }

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI().substring(req.getContextPath().length());

        String action=req.getParameter("action");

        if(action.equals("save")) {
            int id = Integer.parseInt(req.getParameter("id"));
          String name = req.getParameter("name");
          int price = Integer.parseInt(req.getParameter("price"));
          String category=req.getParameter("category");
          String desc=req.getParameter("description");
          String imageURL=req.getParameter("image");
          productAdminService.save(new ProductDetailsDto(id, name, price, category,desc,imageURL));
          resp.sendRedirect(url);
          return;
        }

        if (action.equals("edit")) {
            int id = Integer.parseInt(req.getParameter("id"));
            req.setAttribute(Constants.ITEM, productAdminService.getById(id));
            req.setAttribute(Constants.ITEMS, productAdminService.getAll());
            req.getRequestDispatcher("/WEB-INF/admin/frontpage.jsp").forward(req, resp);
            return;
        }
    }

    @Override
    public void destroy() {
        log("destroy");
    }

}
