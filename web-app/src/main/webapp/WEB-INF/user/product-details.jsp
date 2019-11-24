<%@ page import="java.util.List" %>
<%@ page import="ru.rosbank.javaschool.web.constant.Constants" %>
<%@ page import="ru.rosbank.javaschool.web.model.OrderPositionModel" %>
<%@ page import="ru.rosbank.javaschool.web.dto.ProductDto" %>
<%@ page import="ru.rosbank.javaschool.web.dto.ProductDetailsDto" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- ! + Tab - emmet --%>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Product details</title>
    <%@include file="../bootstrap-css.jsp"%>
</head>
<body>
<iv class="container">
    <h1 align="center" style="margin: 30px;">Product Details</h1>
            <%ProductDetailsDto item =(ProductDetailsDto) request.getAttribute(Constants.ITEM); %>
            <div class="card mb-3">
                <div class="row no-gutters">
                    <div class="col-md-4">
                        <img src="<%= item.getImageUrl() %>" class="card-img">
                    </div>
                    <div class="col-md-8">
                        <div class="card-body">
                            <h5 class="card-title"><%= item.getName() %></h5>
                            <p class="card-text">Price: <%= item.getPrice() %></p>
                            <p class="card-text">Description: <%= item.getDescription() %></p>
                            <form method="post">
                                <input name="id" type="hidden" value="<%= item.getId() %>">
                                <input name="action" type="hidden" value="return">
                                <button class="btn btn-primary">Return</button>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </iv>


</body>
</html>
