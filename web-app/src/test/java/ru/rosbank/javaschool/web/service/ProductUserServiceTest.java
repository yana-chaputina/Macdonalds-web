package ru.rosbank.javaschool.web.service;

import org.junit.jupiter.api.Test;
import ru.rosbank.javaschool.web.dto.ProductDetailsDto;
import ru.rosbank.javaschool.web.dto.ProductDto;
import ru.rosbank.javaschool.web.exception.NotFoundException;
import ru.rosbank.javaschool.web.model.OrderModel;
import ru.rosbank.javaschool.web.model.OrderPositionModel;
import ru.rosbank.javaschool.web.model.ProductModel;
import ru.rosbank.javaschool.web.repository.OrderPositionRepository;
import ru.rosbank.javaschool.web.repository.OrderRepository;
import ru.rosbank.javaschool.web.repository.ProductRepository;
import ru.rosbank.javaschool.web.service.ProductUserService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductUserServiceTest {

    @Test
    void getAll() {
        ProductRepository productRepoMock=mock(ProductRepository.class);
        OrderPositionRepository orderPositionRepoMock=mock(OrderPositionRepository.class);
        OrderRepository orderRepoMock=mock(OrderRepository.class);
        ProductModel model=new ProductModel(1,"burger",100,"burger","burger",null);
        List<ProductModel> listModel=new ArrayList<>();
        listModel.add(model);
        ProductDto dto=new ProductDto(1,"burger",100,null);
        List<ProductDto> listDto=new ArrayList<>();
        listDto.add(dto);
        ProductUserService productUserService=new ProductUserService(productRepoMock,orderRepoMock,orderPositionRepoMock);

        when(productRepoMock.getAll()).thenReturn(listModel);

        List<ProductDto> actual=productUserService.getAll();
        assertIterableEquals(actual,listDto);
    }

    @Test
    void getById() {
        ProductRepository productRepoMock=mock(ProductRepository.class);
        OrderPositionRepository orderPositionRepoMock=mock(OrderPositionRepository.class);
        OrderRepository orderRepoMock=mock(OrderRepository.class);
        ProductModel model=new ProductModel(1,"burger",100,"burger","burger",null);
        ProductDetailsDto dto=new ProductDetailsDto(1,"burger",100,"burger","burger",null);
        ProductUserService productUserService=new ProductUserService(productRepoMock,orderRepoMock,orderPositionRepoMock);

        when(productRepoMock.getById(1)).thenReturn(Optional.of(model));

        ProductDetailsDto actual=productUserService.getById(1);
        assertEquals(actual,dto);
    }

    @Test
    void getByIdThrowEx() {
        ProductRepository productRepoMock=mock(ProductRepository.class);
        OrderPositionRepository orderPositionRepoMock=mock(OrderPositionRepository.class);
        OrderRepository orderRepoMock=mock(OrderRepository.class);
        ProductUserService productUserService=new ProductUserService(productRepoMock,orderRepoMock,orderPositionRepoMock);

        when(productRepoMock.getById(1)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,()->productUserService.getById(1));
    }

    @Test
    void createOrder() {
        ProductRepository productRepoMock=mock(ProductRepository.class);
        OrderPositionRepository orderPositionRepoMock=mock(OrderPositionRepository.class);
        OrderRepository orderRepoMock=mock(OrderRepository.class);
        ProductUserService productUserService=new ProductUserService(productRepoMock,orderRepoMock,orderPositionRepoMock);
        OrderModel model = new OrderModel(0);
        when(orderRepoMock.save(model)).thenReturn(model);

        int actual=productUserService.createOrder();
        assertEquals(actual,model.getId());

    }

    @Test
    void orderSaveUpdateOrderPositionModel() {
        ProductRepository productRepoMock=mock(ProductRepository.class);
        OrderPositionRepository orderPositionRepoMock=mock(OrderPositionRepository.class);
        OrderRepository orderRepoMock=mock(OrderRepository.class);
        ProductUserService productUserService=new ProductUserService(productRepoMock,orderRepoMock,orderPositionRepoMock);
        OrderPositionModel orderPositionModel=new OrderPositionModel(1,1,1,"burger",100,10);
        List<OrderPositionModel> listOrdPosModel=new ArrayList<>();
        listOrdPosModel.add(orderPositionModel);
        when(orderPositionRepoMock.getAllByOrderId(1)).thenReturn(listOrdPosModel);

        OrderPositionModel expected=new OrderPositionModel(1,1,1,"burger",100,20);
        when(orderPositionRepoMock.save(expected)).thenReturn(expected);

        OrderPositionModel actual=productUserService.order(1,1,10);
        assertEquals(actual,expected);
    }

    @Test
    void orderSaveNewOrderPositionModel() {
        ProductRepository productRepoMock=mock(ProductRepository.class);
        OrderPositionRepository orderPositionRepoMock=mock(OrderPositionRepository.class);
        OrderRepository orderRepoMock=mock(OrderRepository.class);
        ProductUserService productUserService=new ProductUserService(productRepoMock,orderRepoMock,orderPositionRepoMock);
        ProductModel model=new ProductModel(1,"burger",100,"burger","burger",null);

        when(orderPositionRepoMock.getAllByOrderId(1)).thenReturn(Collections.emptyList());
        when(productRepoMock.getById(1)).thenReturn(Optional.of(model));

        OrderPositionModel expected=new OrderPositionModel(0,1,1,"burger",100,10);
        when(orderPositionRepoMock.save(expected)).thenReturn(expected);

        OrderPositionModel actual=productUserService.order(1,1,10);
        assertEquals(actual,expected);
    }

    @Test
    void orderThrowEx() {
        ProductRepository productRepoMock=mock(ProductRepository.class);
        OrderPositionRepository orderPositionRepoMock=mock(OrderPositionRepository.class);
        OrderRepository orderRepoMock=mock(OrderRepository.class);
        ProductUserService productUserService=new ProductUserService(productRepoMock,orderRepoMock,orderPositionRepoMock);

        when(orderPositionRepoMock.getAllByOrderId(1)).thenReturn(Collections.emptyList());
        when(productRepoMock.getById(1)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,()->productUserService.order(1,1,10));

    }

    @Test
    void getAllOrderPosition() {

        ProductRepository productRepoMock=mock(ProductRepository.class);
        OrderPositionRepository orderPositionRepoMock=mock(OrderPositionRepository.class);
        OrderRepository orderRepoMock=mock(OrderRepository.class);
        ProductUserService productUserService=new ProductUserService(productRepoMock,orderRepoMock,orderPositionRepoMock);
        OrderPositionModel orderPositionModel=new OrderPositionModel(1,1,1,"burger",100,10);
        List<OrderPositionModel> listOrdPosModel=new ArrayList<>();
        listOrdPosModel.add(orderPositionModel);

        when(orderPositionRepoMock.getAllByOrderId(1)).thenReturn(listOrdPosModel);

        List<OrderPositionModel> actual=productUserService.getAllOrderPosition(1);
        assertIterableEquals(listOrdPosModel,actual);
    }

    @Test
    void updateOrderPositionModel() {
        ProductRepository productRepoMock=mock(ProductRepository.class);
        OrderPositionRepository orderPositionRepoMock=mock(OrderPositionRepository.class);
        OrderRepository orderRepoMock=mock(OrderRepository.class);
        ProductUserService productUserService=new ProductUserService(productRepoMock,orderRepoMock,orderPositionRepoMock);
        OrderPositionModel orderPositionModel=new OrderPositionModel(1,1,1,"burger",100,10);
        List<OrderPositionModel> listOrdPosModel=new ArrayList<>();
        listOrdPosModel.add(orderPositionModel);
        when(orderPositionRepoMock.getAllByOrderId(1)).thenReturn(listOrdPosModel);

        OrderPositionModel expected=new OrderPositionModel(1,1,1,"burger",100,10);
        when(orderPositionRepoMock.save(expected)).thenReturn(expected);

        OrderPositionModel actual=productUserService.updateOrderPositionModel(1,1,10);
        assertEquals(actual,expected);
    }

    @Test
    void updateOrderPositionModelThrowEx() {

        ProductRepository productRepoMock=mock(ProductRepository.class);
        OrderPositionRepository orderPositionRepoMock=mock(OrderPositionRepository.class);
        OrderRepository orderRepoMock=mock(OrderRepository.class);
        ProductUserService productUserService=new ProductUserService(productRepoMock,orderRepoMock,orderPositionRepoMock);

        when(orderPositionRepoMock.getAllByOrderId(1)).thenReturn(Collections.emptyList());
        when(productRepoMock.getById(1)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,()->productUserService.updateOrderPositionModel(1,1,10));
    }

    @Test
    void removeOrderById() {

        ProductRepository productRepoMock=mock(ProductRepository.class);
        OrderPositionRepository orderPositionRepoMock=mock(OrderPositionRepository.class);
        OrderRepository orderRepoMock=mock(OrderRepository.class);
        ProductUserService productUserService=new ProductUserService(productRepoMock,orderRepoMock,orderPositionRepoMock);

        when(orderPositionRepoMock.removeById(1)).thenReturn(true);

        boolean actual=productUserService.removeOrderById(1);
        assertTrue(actual);
    }
}