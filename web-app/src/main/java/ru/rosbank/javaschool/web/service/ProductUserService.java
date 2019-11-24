package ru.rosbank.javaschool.web.service;

import lombok.RequiredArgsConstructor;
import ru.rosbank.javaschool.web.dto.ProductDetailsDto;
import ru.rosbank.javaschool.web.dto.ProductDto;
import ru.rosbank.javaschool.web.exception.NotFoundException;
import ru.rosbank.javaschool.web.model.OrderModel;
import ru.rosbank.javaschool.web.model.OrderPositionModel;
import ru.rosbank.javaschool.web.model.ProductModel;
import ru.rosbank.javaschool.web.repository.OrderPositionRepository;
import ru.rosbank.javaschool.web.repository.OrderRepository;
import ru.rosbank.javaschool.web.repository.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class ProductUserService {
  private final ProductRepository productRepository;
  private final OrderRepository orderRepository;
  private final OrderPositionRepository orderPositionRepository;

  public List<ProductDto> getAll() {
    return productRepository.getAll().stream()
            .map(ProductDto::from)
            .collect(Collectors.toList())
            ;
  }

  public ProductDetailsDto getById(int id) {
    return productRepository.getById(id)
            .map(o->ProductDetailsDto.from(o))
            .orElseThrow(NotFoundException::new)
            ;
  }

  public int createOrder() {
    OrderModel model = new OrderModel(0);
    orderRepository.save(model);
    return model.getId();
  }

  public OrderPositionModel order(int orderId, int id, int quantity) {
    OrderPositionModel forUpdate=null;

    for (OrderPositionModel orderPositionModel : orderPositionRepository.getAllByOrderId(orderId))
    {
      if(orderPositionModel.getProductId()==id) {
        orderPositionModel.setProductQuantity(orderPositionModel.getProductQuantity()+quantity);
        forUpdate=orderPositionModel;
      }
    }
    if(forUpdate!=null) {
      return orderPositionRepository.save(forUpdate);
    }

    ProductModel productModel = productRepository.getById(id).orElseThrow(NotFoundException::new);
    OrderPositionModel orderPositionModel = new OrderPositionModel(
        0,
        orderId,
        productModel.getId(),
        productModel.getName(),
        productModel.getPrice(),
        quantity
    );
    return orderPositionRepository.save(orderPositionModel);
  }

  public List<OrderPositionModel> getAllOrderPosition(int orderId) {
      return orderPositionRepository.getAllByOrderId(orderId);
  }

  public OrderPositionModel updateOrderPositionModel(int orderId,int id,int quantity)
  {
    OrderPositionModel forUpdate=null;

    for (OrderPositionModel orderPositionModel : orderPositionRepository.getAllByOrderId(orderId))
    {
      if(orderPositionModel.getProductId()==id) {
        orderPositionModel.setProductQuantity(quantity);
        forUpdate=orderPositionModel;
      }
    }
    if(forUpdate!=null) {
      return orderPositionRepository.save(forUpdate);
    }
    throw new NotFoundException();
  }

  public boolean removeOrderById(int id) {

      return orderPositionRepository.removeById(id);
  }
}
