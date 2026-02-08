package com.lms.shoppingcart.image;

import java.util.List;

public interface IImageService {
    Image addImage(Image image);
    List<Image> GetAllImageOfProduct();
}
