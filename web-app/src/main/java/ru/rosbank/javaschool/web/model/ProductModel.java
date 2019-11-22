package ru.rosbank.javaschool.web.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.rosbank.javaschool.web.dto.ProductDetailsDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductModel {
    private int id;
    private String name;
    private int price;
    private String category;
    private String description;
    private String imageUrl;

    public static ProductModel from(ProductDetailsDto dto) {

        return new ProductModel(
                dto.getId(),
                dto.getName(),
                dto.getPrice(),
                dto.getCategory(),
                dto.getDescription(),
                dto.getImageUrl()
        );
    }
}
