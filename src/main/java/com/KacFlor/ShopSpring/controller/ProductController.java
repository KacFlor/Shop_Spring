package com.KacFlor.ShopSpring.controller;

import java.util.List;
import java.util.Optional;

import com.KacFlor.ShopSpring.controllersRequests.NewOrderItem;
import com.KacFlor.ShopSpring.controllersRequests.NewProduct;
import com.KacFlor.ShopSpring.model.Product;
import com.KacFlor.ShopSpring.model.Role;
import com.KacFlor.ShopSpring.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        path = {"products"}
)
public class ProductController{

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @PreAuthorize("hasAnyAuthority('" + Role.Fields.USER + "', '" + Role.Fields.ADMIN + "')")
    @GetMapping
    public List<Product> getAll(){
        return this.productService.getAll();
    }

    @PreAuthorize("hasAnyAuthority('" + Role.Fields.USER + "', '" + Role.Fields.ADMIN + "')")
    @PostMapping("/order/{Oid}/order-item")
    public ResponseEntity<?> addOrderItem(@RequestBody NewOrderItem newOrderItem, @PathVariable("Oid") Integer id){
        productService.addOrderItem(newOrderItem, id);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasAnyAuthority('" + Role.Fields.USER + "', '" + Role.Fields.ADMIN + "')")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Product>> getById(@PathVariable("id") Integer id){
        Optional<Product> product = Optional.ofNullable(productService.getById(id));
        return ResponseEntity.ok(product);
    }

    @PreAuthorize("hasAuthority('" + Role.Fields.ADMIN + "')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") Integer id){
        productService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('" + Role.Fields.ADMIN + "')")
    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody NewProduct updatedProduct, @PathVariable("id") Integer id){
        productService.updateProduct(updatedProduct, id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('" + Role.Fields.ADMIN + "')")
    @PostMapping("/new")
    public ResponseEntity<?> create(@RequestBody NewProduct newProduct){
        productService.addNewProduct(newProduct);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasAuthority('" + Role.Fields.ADMIN + "')")
    @PostMapping("/{id}/promotion")
    public ResponseEntity<?> addPromotionById(@PathVariable("id") Integer id, @RequestParam("PNid") Integer PNid){
        productService.addPromotion(id, PNid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('" + Role.Fields.ADMIN + "')")
    @DeleteMapping("/{id}/promotion")
    public ResponseEntity<?> removePromotionById(@PathVariable("id") Integer id, @RequestParam("PNid") Integer PNid){
        productService.removePromotion(id, PNid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('" + Role.Fields.ADMIN + "')")
    @PostMapping("/{id}/category")
    public ResponseEntity<?> addCategoryById(@PathVariable("id") Integer id, @RequestParam("CYid") Integer CYid){
        productService.addCategory(id, CYid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('" + Role.Fields.ADMIN + "')")
    @DeleteMapping("/{id}/category")
    public ResponseEntity<?> removeCategoryById(@PathVariable("id") Integer id, @RequestParam("CYid") Integer CYid){
        productService.removeCategory(id, CYid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('" + Role.Fields.ADMIN + "')")
    @PostMapping("/{id}/supplier")
    public ResponseEntity<?> addSupplierById(@PathVariable("id") Integer id, @RequestParam("Sid") Integer Sid){
        productService.addSupplier(id, Sid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('" + Role.Fields.ADMIN + "')")
    @DeleteMapping("/{id}/supplier")
    public ResponseEntity<?> removeSupplierById(@PathVariable("id") Integer id, @RequestParam("Sid") Integer Sid){
        productService.removeSupplier(id, Sid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('" + Role.Fields.USER + "', '" + Role.Fields.ADMIN + "')")
    @PostMapping("/{id}/cart")
    public ResponseEntity<?> addToCartById(@PathVariable("id") Integer id, @RequestParam("Cid") Integer Cid){
        productService.addProductToCart(id, Cid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('" + Role.Fields.USER + "', '" + Role.Fields.ADMIN + "')")
    @DeleteMapping("/{id}/cart")
    public ResponseEntity<?> removeFromCartById(@PathVariable("id") Integer id, @RequestParam("Cid") Integer Cid){
        productService.removeProductFromCart(id, Cid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('" + Role.Fields.USER + "', '" + Role.Fields.ADMIN + "')")
    @PostMapping("/{id}/wishlist")
    public ResponseEntity<?> addToWishlistById(@PathVariable("id") Integer id, @RequestParam("Wid") Integer Wid){
        productService.addProductToWishlist(id, Wid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('" + Role.Fields.USER + "', '" + Role.Fields.ADMIN + "')")
    @DeleteMapping("/{id}/wishlist")
    public ResponseEntity<?> removeFromWishlistById(@PathVariable("id") Integer id, @RequestParam("Wid") Integer Wid) {
        productService.removeProductFromWishlist(id, Wid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
