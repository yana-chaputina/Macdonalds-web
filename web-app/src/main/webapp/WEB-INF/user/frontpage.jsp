<%@ page import="java.util.List" %>
<%@ page import="ru.rosbank.javaschool.web.constant.Constants" %>
<%@ page import="ru.rosbank.javaschool.web.model.OrderPositionModel" %>
<%@ page import="ru.rosbank.javaschool.web.dto.ProductDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!doctype html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport"
        content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <title>Fastfood</title>
  <%@include file="../bootstrap-css.jsp"%>
</head>
<body>
    <div class="container">
      <h1 align="center" style="margin: 30px;">Fastfood</h1>
      <div class="row">
        <div class="col-sm-8">
          <% for (ProductDto item : (List<ProductDto>) request.getAttribute(Constants.ITEMS)) { %>
          <div class="card mb-3" style="max-width: 540px;">
            <div class="row no-gutters">
              <div class="col-md-4">
                <img src="<%= item.getImageUrl() %>" class="card-img">
              </div>
              <div class="col-md-8">
                <div class="card-body">
                  <h5 class="card-title" align="center"><%= item.getName() %>
                  </h5>
                  <p class="card-text ">Price: <%= item.getPrice() %>
                  </p>
                  <form action="<%= request.getContextPath() %>" method="post">
                    <input name="id" type="hidden" value="<%= item.getId() %>">
                    <input name="action" type="hidden" value="add">
                    <div class="form group">
                      <label for="quantity">Product Quantity:</label>
                      <input type="number" min="1" max="<%= Integer.MAX_VALUE %>" id="quantity" name="quantity"
                             value="1" style="width: 170px;">
                    </div>
                    <button class="btn btn-primary" style="margin-top:10px">Add to card</button>
                  </form>
                </div>
              </div>
            </div>
          </div>
          <% } %>
        </div>
        <div class="col-sm-4">
          <h5>Cart:</h5>
          <% List<OrderPositionModel> positions = (List<OrderPositionModel>) request.getAttribute(Constants.ORDERED_ITEMS); %>
              <% for (OrderPositionModel model: positions) { %>
          <ul class="list-group">
            <li class="list-group-item"
                style="margin-bottom: 10px"><%= model.getProductName() + " Price: " + model.getProductPrice()%>
              <form action="<%= request.getContextPath() %>" method="post">
                <div class="form group">
                  <label>Quantity</label>
                  <input type="number" min="1" max="<%= Integer.MAX_VALUE %>" style="margin-bottom: 10px; width: 150px;"
                         name="quantity" value="<%=model.getProductQuantity()%>">
                  <br/>
                </div>
                <input name="id" type="hidden" value="<%= model.getId() %>">
                <input name="action" type="hidden" value="update">
                <button class="btn btn-primary" style="width: 100px">Update</button>
              </form>
              <form action="<%= request.getContextPath() %>" method="post">
                <input name="id" type="hidden" value="<%= model.getId() %>">
                <input name="action" type="hidden" value="remove">
                <button class="btn btn-primary" style="margin-top:10px; width: 100px">Remove</button>
              </form>
              <div class="form group">
                <br/>
                <label><%= "Cost: "+model.getProductPrice()*model.getProductQuantity()%></label>
              </div>
            </li>
          </ul>
          <% } %>
        </div>
      </div>
    </div>


</body>
</html>
