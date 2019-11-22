package ru.rosbank.javaschool.web.repository;

import ru.rosbank.javaschool.web.dto.ProductDetailsDto;
import ru.rosbank.javaschool.web.model.ProductModel;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    List<ProductModel> getAll();
    Optional<ProductModel> getById(int id);
    ProductModel save(ProductModel model);
    boolean removeById(int id);
}
