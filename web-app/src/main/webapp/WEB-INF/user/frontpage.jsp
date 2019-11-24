<%@ page import="java.util.List" %>
<%@ page import="ru.rosbank.javaschool.web.constant.Constants" %>
<%@ page import="ru.rosbank.javaschool.web.model.OrderPositionModel" %>
<%@ page import="ru.rosbank.javaschool.web.dto.ProductDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- ! + Tab - emmet --%>
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
        <div class="col-sm-9">
          <% for (ProductDto item : (List<ProductDto>) request.getAttribute(Constants.ITEMS)) { %>
          <div class="card mb-3" style="max-width: 540px;">
            <div class="row no-gutters">
              <div class="col-md-4">
                <img src="<%= item.getImageUrl() %>" class="card-img">
              </div>
              <div class="col-md-8">
                <div class="card-body">
                  <h5 class="card-title"><%= item.getName() %></h5>
                  <p class="card-text">Price: <%= item.getPrice() %></p>
                  <form action="<%= request.getContextPath() %>" method="post">
                    <input name="id" type="hidden" value="<%= item.getId() %>">
                    <input name="action" type="hidden" value="add">
                    <div class="form group">
                      <label for="quantity">Product Quantity</label>
                      <input type="number" min="1" max="<%= Integer.MAX_VALUE %>" id="quantity" name="quantity" value="1">
                    </div>
                    <button class="btn btn-primary">Add to card</button>
                  </form>
                  <form action="<%= request.getContextPath() %>/fastfood/details?id=<%= item.getId() %>" method="get">
                  <button class="btn btn-primary">Details</button>
                  </form>
                </div>
              </div>
            </div>
          </div>
          <% } %>
        </div>
        <div class="col-sm-3">
          <h5>Cart:</h5>
              <% List<OrderPositionModel> positions = (List<OrderPositionModel>) request.getAttribute("ordered-items"); %>
              <% for (OrderPositionModel model: positions) { %>
          <ul class="list-group">
            <li class="list-group-item"><%= model.getProductName()+" Price: "+model.getProductPrice()%>
              <form action="<%= request.getContextPath() %>" method="post">
                <div class="form group">
                  <label>Quantity</label>
                  <input type="number" min="1" max="<%= Integer.MAX_VALUE %>" name="quantity" value="<%=model.getProductQuantity()%>">
                </div>
                <input name="id" type="hidden" value="<%= model.getId() %>">
                <input name="action" type="hidden" value="update">
              <button class="btn btn-primary">Update</button>
              </form>
              <form action="<%= request.getContextPath() %>" method="post">
                <input name="id" type="hidden" value="<%= model.getId() %>">
                <input name="action" type="hidden" value="remove">
                <button class="btn btn-primary">Remove</button>
              </form>
              <div class="form group">
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
