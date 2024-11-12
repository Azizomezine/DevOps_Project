package tn.esprit.devops_project;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.devops_project.entities.Product;
import tn.esprit.devops_project.repository.ProductRepository;
import tn.esprit.devops_project.service.ProductServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@TestMethodOrder(OrderAnnotation.class)
@ExtendWith(MockitoExtension.class)
class ProductServiceImplMockTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductServiceImpl productService;

    Product product = new Product(1L, "Product1", 100.0f, 10);
    List<Product> listProducts = new ArrayList<>() {
        {
            add(new Product(2L, "Product2", 200.0f, 20));
            add(new Product(3L, "Product3", 300.0f, 30));
        }
    };

    @Test
    @Order(1)
    void testRetrieveProduct() {
        Mockito.when(productRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(product));
        Product retrievedProduct = productService.retrieveProduct(1L);
        Assertions.assertNotNull(retrievedProduct);
        Assertions.assertEquals("Product1", retrievedProduct.getTitle());
    }

    @Test
    @Order(2)
    void testRetrieveAllProducts() {
        Mockito.when(productRepository.findAll()).thenReturn(listProducts);
        List<Product> retrievedProducts = productService.retrieveAllProducts();
        Assertions.assertEquals(2, retrievedProducts.size());
    }

    @Test
    @Order(3)
    void testAddProduct() {
        Product newProduct = new Product(null, "ProductTest", 150.0f, 15);

        Mockito.when(productRepository.save(Mockito.any(Product.class)))
                .thenAnswer(invocation -> {
                    Product savedProduct = invocation.getArgument(0);
                    savedProduct.setIdProduct(4L); // Mock setting an ID after saving
                    listProducts.add(savedProduct);
                    return savedProduct;
                });

        productService.addProduct(newProduct);
        Assertions.assertEquals(3, listProducts.size());
        Assertions.assertEquals("ProductTest", listProducts.get(2).getTitle());
    }
}
