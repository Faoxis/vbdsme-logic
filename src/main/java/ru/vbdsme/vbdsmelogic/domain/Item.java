package ru.vbdsme.vbdsmelogic.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @ApiModelProperty(name = "Id товара", example = "1")
    private long id;

    @Size(min = 5, max = 50)
    @NotNull
    @ApiModelProperty(name = "Название товара", example = "Dildo 2000")
    private String name;

    @NotNull
    @Min(100)
    @Max(1_000_000_00)
    @ApiModelProperty(name = "Стоимость товара", example = "300000")
    private long price;

    @NotNull
    @Min(1)
    @ApiModelProperty(name = "Количество товаров", example = "100")
    private long quantity;

    @NotNull
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "item_category",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories;

}
