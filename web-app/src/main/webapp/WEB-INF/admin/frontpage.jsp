<%@ page import="java.util.List" %>
<%@ page import="ru.rosbank.javaschool.web.constant.Constants" %>
<%@ page import="ru.rosbank.javaschool.web.model.ProductModel" %>
<%@ page import="ru.rosbank.javaschool.web.dto.ProductDetailsDto" %>
<%@ page import="ru.rosbank.javaschool.web.dto.ProductDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!doctype html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport"
        content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <title>Dashboard</title>
  <%@include file="../bootstrap-css.jsp" %>
</head>
<body>

<div class="container">
  <h1 align="center" style="margin: 30px;">Dashboard</h1>
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
              <p class="card-text">Price: <%= item.getPrice() %></p>
              <form action="<%= request.getContextPath() %>" method="post">
                <input name="id" type="hidden" value="<%= item.getId() %>">
                <input name="action" type="hidden" value="edit">
                <button class="btn btn-primary">Edit</button>
              </form>
            </div>
          </div>
        </div>
      </div>
      <% } %>
    </div>
      <div class="col-sm-4">
      <% if (request.getAttribute(Constants.ITEM) == null) { %>
      <form action="<%= request.getContextPath() %>/fastfood/admin" method="post">
        <input name="action" type="hidden" value="save">
        <input name="id" type="hidden" value="0">
        <div class="form group">
          <label for="name">Product Name</label>
            <br/>
            <input type="text" id="name" name="name" style="width: 200px;">
            <br/>
        </div>
        <div class="form group">
          <label>Product Price</label>
            <br/>
            <input type="number" min="0" max="<%= Integer.MAX_VALUE %>" name="price" style="width: 200px;">
            <br/>
        </div>
        <div class="form group">
          <label>Product Category</label>
            <br/>
            <input type="text" name="category" style="width: 200px;">
            <br/>
        </div>
        <div class="form group">
          <label>Product Description</label>
            <br/>
            <input type="text" name="description" style="width: 200px;">
            <br/>
        </div>
        <div class="form group">
          <label>Product ImageUrl</label>
            <br/>
            <input type="text" name="image" style="width: 200px;">
        </div>
          <br/>
        <button class="btn btn-primary">Add</button>
      </form>
      <% } %>

      <% if (request.getAttribute(Constants.ITEM) != null) { %>
      <% ProductDetailsDto item = (ProductDetailsDto) request.getAttribute(Constants.ITEM); %>
      <form method="post">
        <input name="action" type="hidden" value="save">
          <input name="id" type="hidden" value="<%= item.getId() %>" style="width: 200px;">
        <div class="form group">
          <label>Product Name</label>
            <br/>
            <input type="text" name="name" value="<%= item.getName() %>" style="width: 200px;">
            <br/>
        </div>
        <div class="form group">
          <label>Product Price</label>
            <br/>
            <input type="number" min="0" max="<%= Integer.MAX_VALUE %>" name="price" value="<%= item.getPrice() %>"
                   style="width: 200px;">
            <br/>
        </div>
        <div class="form group">
          <label>Product Category</label>
            <br/>
            <input type="text" name="category" value="<%= item.getCategory() %>" style="width: 200px;">
            <br/>
        </div>
        <div class="form group">
          <label>Product Description</label>
            <br/>
            <input type="text" name="description" value="<%= item.getDescription() %>" style="width: 200px;">
            <br/>
        </div>
        <div class="form group">
          <label>Product ImageUrl</label>
            <br/>
            <input type="text" name="image" value="<%= item.getImageUrl() %>" style="width: 200px;">
        </div>
          <br/>
        <button class="btn btn-primary">Save</button>
      </form>
      <% } %>
  </div>
</div>
</div>

</body>
</html>
