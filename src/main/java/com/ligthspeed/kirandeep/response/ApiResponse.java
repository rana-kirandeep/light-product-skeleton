package com.ligthspeed.kirandeep.response;


import com.ligthspeed.kirandeep.domain.Product;

import java.util.List;

public record ApiResponse(List<Product> productList) {
}
