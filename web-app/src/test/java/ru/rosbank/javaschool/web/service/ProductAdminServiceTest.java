package ru.rosbank.javaschool.web.service;

import org.junit.jupiter.api.Test;
import ru.rosbank.javaschool.web.dto.ProductDetailsDto;
import ru.rosbank.javaschool.web.dto.ProductDto;
import ru.rosbank.javaschool.web.exception.NotFoundException;
import ru.rosbank.javaschool.web.model.ProductModel;
import ru.rosbank.javaschool.web.repository.OrderPositionRepository;
import ru.rosbank.javaschool.web.repository.OrderRepository;
import ru.rosbank.javaschool.web.repository.ProductRepository;
import ru.rosbank.javaschool.web.service.ProductAdminService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductAdminServiceTest {

    @Test
    void save() {

        ProductRepository productRepoMock=mock(ProductRepository.class);
        OrderPositionRepository orderPositionRepoMock=mock(OrderPositionRepository.class);
        OrderRepository orderRepoMock=mock(OrderRepository.class);
        ProductModel model=new ProductModel(0,"burger",100,"burger","burger",null);
        ProductDetailsDto dto=new ProductDetailsDto(0,"burger",100,"burger","burger",null);
        ProductAdminService productAdminService=new ProductAdminService(productRepoMock,orderRepoMock,orderPositionRepoMock);

        when(productRepoMock.save(model)).thenReturn(model);

        ProductDetailsDto actual=productAdminService.save(dto);
        assertEquals(actual,dto);

    }

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
        ProductAdminService productAdminService=new ProductAdminService(productRepoMock,orderRepoMock,orderPositionRepoMock);

        when(productRepoMock.getAll()).thenReturn(listModel);

        List<ProductDto> actual=productAdminService.getAll();
        assertIterableEquals(actual,listDto);

    }

    @Test
    void getById() {

        ProductRepository productRepoMock=mock(ProductRepository.class);
        OrderPositionRepository orderPositionRepoMock=mock(OrderPositionRepository.class);
        OrderRepository orderRepoMock=mock(OrderRepository.class);
        ProductModel model=new ProductModel(1,"burger",100,"burger","burger",null);
        ProductDetailsDto dto=new ProductDetailsDto(1,"burger",100,"burger","burger",null);
        ProductAdminService productAdminService=new ProductAdminService(productRepoMock,orderRepoMock,orderPositionRepoMock);

        when(productRepoMock.getById(1)).thenReturn(Optional.of(model));

        ProductDetailsDto actual=productAdminService.getById(1);
        assertEquals(actual,dto);
    }

    @Test
    void getByIdThrowEx() {

        ProductRepository productRepoMock=mock(ProductRepository.class);
        OrderPositionRepository orderPositionRepoMock=mock(OrderPositionRepository.class);
        OrderRepository orderRepoMock=mock(OrderRepository.class);
        ProductAdminService productAdminService=new ProductAdminService(productRepoMock,orderRepoMock,orderPositionRepoMock);

        when(productRepoMock.getById(1)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,()->productAdminService.getById(1));
    }

    @Test
    void removeProductById() {

        ProductRepository productRepoMock=mock(ProductRepository.class);
        OrderPositionRepository orderPositionRepoMock=mock(OrderPositionRepository.class);
        OrderRepository orderRepoMock=mock(OrderRepository.class);
        ProductAdminService productAdminService=new ProductAdminService(productRepoMock,orderRepoMock,orderPositionRepoMock);

        when(productRepoMock.removeById(1)).thenReturn(true);

        boolean actual=productAdminService.removeProductById(1);
        assertTrue(actual);
    }
}