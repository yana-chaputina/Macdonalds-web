package ru.rosbank.javaschool.web.servlet;

import ru.rosbank.javaschool.util.SQLTemplate;
import ru.rosbank.javaschool.web.constant.Constants;
import ru.rosbank.javaschool.web.model.ProductModel;
import ru.rosbank.javaschool.web.repository.*;
import ru.rosbank.javaschool.web.service.ProductUserService;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;

public class FrontServlet extends HttpServlet {
  private ProductUserService productUserService;

  @Override
  public void init() throws ServletException {
    log("Init");

    try {
      InitialContext initialContext = new InitialContext();
      DataSource dataSource = (DataSource) initialContext.lookup("java:/comp/env/jdbc/db");
      SQLTemplate sqlTemplate = new SQLTemplate();
      ProductRepository productRepository = new ProductRepositoryJdbcImpl(dataSource, sqlTemplate);
      OrderRepository orderRepository = new OrderRepositoryJdbcImpl(dataSource, sqlTemplate);
      OrderPositionRepository orderPositionRepository = new OrderPositionRepositoryJdbcImpl(dataSource, sqlTemplate);
      productUserService = new ProductUserService(productRepository, orderRepository, orderPositionRepository);
      insertInitialData(productRepository);
    } catch (NamingException e) {
      e.printStackTrace();
    }
  }

  private void insertInitialData(ProductRepository productRepository) {
    productRepository.save(new ProductModel(0, "Cheeseburger",70,"burger","This is a cheeseburger","/img/cheeseburger.jpg"));
    productRepository.save(new ProductModel(0, "Chickenburger",55,"burger","This is a chickenburger","/img/chickenburger.jpeg"));
    productRepository.save(new ProductModel(0, "BigBurger",150,"burger","This is a BigBurger","/img/bigburger.jpeg"));
    productRepository.save(new ProductModel(0, "FrenchFries",60,"fries","This is a french fries","/img/frenchfries.jpeg"));
    productRepository.save(new ProductModel(0, "MilkShake",75,"drink","This is a milkshake","/img/milkshake.jpeg"));
    productRepository.save(new ProductModel(0, "Cola",45,"drink","This is a coca-cola","/img/cola.jpeg"));
    productRepository.save(new ProductModel(0, "Juice",50,"drink","This is a juice","/img/juice.jpeg"));

  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    String url = req.getRequestURI().substring(req.getContextPath().length());

    if (url.equals("/")) {
      resp.sendRedirect("/fastfood");
      return;
    }

    if (url.equals("/fastfood")) {
      HttpSession session = req.getSession();
      if (session.isNew()) {
        int orderId = productUserService.createOrder();
        session.setAttribute(Constants.ORDER_ID, orderId);
      }

      int orderId = (Integer) session.getAttribute(Constants.ORDER_ID);
      req.setAttribute(Constants.ORDERED_ITEMS, productUserService.getAllOrderPosition(orderId));
      req.setAttribute(Constants.ITEMS, productUserService.getAll());
      req.getRequestDispatcher("/WEB-INF/user/frontpage.jsp").forward(req, resp);
      return;
    }
   }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String url = req.getRequestURI().substring(req.getContextPath().length());
    String action = req.getParameter(Constants.ACTION);
    HttpSession session = req.getSession();

    if (action.equals(Constants.ADD)) {

      int orderId = (Integer) session.getAttribute(Constants.ORDER_ID);
      int id = Integer.parseInt(req.getParameter(Constants.ID));
      int quantity = Integer.parseInt(req.getParameter(Constants.QUANTITY));

      productUserService.order(orderId, id, quantity);
      resp.sendRedirect(url);
      return;
    }
    if (action.equals(Constants.REMOVE)) {
      int id = Integer.parseInt(req.getParameter(Constants.ID));
      productUserService.removeOrderById(id);
      resp.sendRedirect(url);
      return;
    }

    if (action.equals(Constants.UPDATE)) {

      int orderId = (Integer) session.getAttribute(Constants.ORDER_ID);
      int id = Integer.parseInt(req.getParameter(Constants.ID));
      int quantity = Integer.parseInt(req.getParameter(Constants.QUANTITY));
      productUserService.updateOrderPositionModel(orderId,id,quantity);
      resp.sendRedirect(url);
      return;
    }

  }

  @Override
  public void destroy() {
    log("destroy");
  }
}
