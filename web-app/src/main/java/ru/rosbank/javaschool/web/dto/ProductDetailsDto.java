package ru.rosbank.javaschool.web.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.rosbank.javaschool.web.model.ProductModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailsDto {
    private int id;
    private String name;
    private int price;
    private String category;
    private String description;
    private String imageUrl;

    public static ProductDetailsDto from(ProductModel model)
    {
        return new ProductDetailsDto(
                model.getId(),
                model.getName(),
                model.getPrice(),
                model.getCategory(),
                model.getDescription(),
                model.getImageUrl()
        );
    }
}
