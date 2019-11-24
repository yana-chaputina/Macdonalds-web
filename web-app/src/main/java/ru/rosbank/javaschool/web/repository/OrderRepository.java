package ru.rosbank.javaschool.web.repository;

import ru.rosbank.javaschool.web.model.OrderModel;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    List<OrderModel> getAll();

    Optional<OrderModel> getById(int id);

    OrderModel save(OrderModel model);

    boolean removeById(int id);
}
