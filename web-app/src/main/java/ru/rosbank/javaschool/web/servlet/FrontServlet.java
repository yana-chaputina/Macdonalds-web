package ru.rosbank.javaschool.web.servlet;

import ru.rosbank.javaschool.util.SQLTemplate;
import ru.rosbank.javaschool.web.constant.Constants;
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
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;

// 1. Instantiation
// 2. init
// 3. service
// 4. destroy (уничтожение)

// Singleton'ы - Servlet*
public class FrontServlet extends HttpServlet {
  private ProductUserService productUserService;

  // Ctrl + O
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
        session.setAttribute("order-id", orderId);
      }

      int orderId = (Integer) session.getAttribute("order-id");
      req.setAttribute("ordered-items", productUserService.getAllOrderPosition(orderId));
      req.setAttribute(Constants.ITEMS, productUserService.getAll());
      req.getRequestDispatcher("/WEB-INF/user/frontpage.jsp").forward(req, resp);
      return;
    }
    if (url.startsWith("/fastfood/details")) {
      int id = Integer.parseInt(req.getParameter("id"));
      req.setAttribute(Constants.ITEM, productUserService.getById(id));
      req.getRequestDispatcher("/WEB-INF/user/product-details.jsp").forward(req, resp);
      return;
    }
   }

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String url = req.getRequestURI().substring(req.getContextPath().length());
    String action = req.getParameter("action");
    HttpSession session = req.getSession();

    if(action.equals("add")) {

      int orderId = (Integer) session.getAttribute("order-id");
      int id = Integer.parseInt(req.getParameter("id"));
      int quantity = Integer.parseInt(req.getParameter("quantity"));

      productUserService.order(orderId, id, quantity);
      resp.sendRedirect(url);
      return;
    }
    if(action.equals("remove")){
      int id = Integer.parseInt(req.getParameter("id"));
      productUserService.removeOrderById(id);
      resp.sendRedirect(url);
      return;
    }

    if(action.equals("update")){

      int orderId = (Integer) session.getAttribute("order-id");
      int id = Integer.parseInt(req.getParameter("id"));
      int quantity = Integer.parseInt(req.getParameter("quantity"));
      productUserService.updateOrderPositionModel(orderId,id,quantity);
      resp.sendRedirect(url);
      return;
    }

  }

//  @Override
//  protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//    // будем перенаправлять запрос
//    // resp.getWriter().write("...");
//    String rootUrl = req.getContextPath().isEmpty() ? "/" : req.getContextPath();
//    String url = req.getRequestURI().substring(req.getContextPath().length());
//    // routing
//    // в зависимости от url'а вызывать нужные обработчики
//    if (url.startsWith("/admin")) {
//      if (url.equals("/admin")) {
//        // TODO: work with admin panel
//        if (req.getMethod().equals("GET")) {
//          req.setAttribute(Constants.ITEMS, productAdminService.getAll());
//          req.getRequestDispatcher("/WEB-INF/admin/frontpage.jsp").forward(req, resp);
//          return;
//        }
//
//        if (req.getMethod().equals("POST")) {
//          // getParameter - POST (BODY FORM)
//          int id = Integer.parseInt(req.getParameter("id"));
//          String name = req.getParameter("name");
//          int price = Integer.parseInt(req.getParameter("price"));
//          int quantity = Integer.parseInt(req.getParameter("quantity"));
//          // TODO: validation
//         //productAdminService.save(new ProductModel(id, name, price, quantity, null));
//          resp.sendRedirect(url);
//          return;
//        }
//      }
//
//      if (url.startsWith("/admin/edit")) {
//        if (req.getMethod().equals("GET")) {
//          // ?id=value
//          int id = Integer.parseInt(req.getParameter("id"));
//          req.setAttribute(Constants.ITEM, productAdminService.getById(id));
//          req.setAttribute(Constants.ITEMS, productAdminService.getAll());
//          req.getRequestDispatcher("/WEB-INF/admin/frontpage.jsp").forward(req, resp);
//          return;
//        }
//      }
//
//      return;
//    }
//
//    if (url.equals("/")) {
//
//      if (req.getMethod().equals("GET")) {
//        HttpSession session = req.getSession();
//        if (session.isNew()) {
//          int orderId = burgerUserService.createOrder();
//          session.setAttribute("order-id", orderId);
//        }
//
//        int orderId = (Integer) session.getAttribute("order-id");
//        req.setAttribute("ordered-items", burgerUserService.getAllOrderPosition(orderId));
//        req.setAttribute(Constants.ITEMS, burgerUserService.getAll());
//        req.getRequestDispatcher("/WEB-INF/frontpage.jsp").forward(req, resp);
//        return;
//      }
//      if (req.getMethod().equals("POST")) {
//        HttpSession session = req.getSession();
//        if (session.isNew()) {
//          int orderId = burgerUserService.createOrder();
//          session.setAttribute("order-id", orderId);
//        }
//
//        int orderId = (Integer) session.getAttribute("order-id");
//        int id = Integer.parseInt(req.getParameter("id"));
//        int quantity = Integer.parseInt(req.getParameter("quantity"));
//
//        burgerUserService.order(orderId, id, quantity);
//        resp.sendRedirect(url);
//        return;
//      }
//    }
//  }





  @Override
  public void destroy() {
    log("destroy");
  }
}
