package phammenungdungj2ee.bai4_qlsp.model;

import lombok.*;
import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    private int id;

    @NotBlank(message = "Tên danh mục không được để trống")
    private String name;
}