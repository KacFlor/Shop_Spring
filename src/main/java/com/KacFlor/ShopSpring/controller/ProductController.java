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
    public ResponseEntity<?> addOrderItem(@RequestBody NewOrderItem updatedOrderItem, @PathVariable("Oid") Integer id){
        productService.addOrderItem(updatedOrderItem, id);
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
    public ResponseEntity<?> create(@RequestBody NewProduct updatedProduct){
        productService.addNewProduct(updatedProduct);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasAuthority('" + Role.Fields.ADMIN + "')")
    @PostMapping("/{PTid}/promotion/{PNid}")
    public ResponseEntity<?> addPromotionById(@PathVariable("PTid") Integer PTid, @PathVariable("PNid") Integer PNid){
        productService.addPromotion(PTid, PNid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('" + Role.Fields.ADMIN + "')")
    @DeleteMapping("/{PTid}/promotion/{PNid}")
    public ResponseEntity<?> removePromotionById(@PathVariable("PTid") Integer PTid, @PathVariable("PNid") Integer PNid){
        productService.removePromotion(PTid, PNid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('" + Role.Fields.ADMIN + "')")
    @PostMapping("/{PTid}/category/{Cid}")
    public ResponseEntity<?> addCategoryById(@PathVariable("PTid") Integer PTid, @PathVariable("Cid") Integer Cid){
        productService.addCategory(PTid, Cid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('" + Role.Fields.ADMIN + "')")
    @DeleteMapping("/{PTid}/category/{Cid}")
    public ResponseEntity<?> removeCategoryById(@PathVariable("PTid") Integer PTid, @PathVariable("Cid") Integer Cid){
        productService.removeCategory(PTid, Cid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('" + Role.Fields.ADMIN + "')")
    @PostMapping("/{PTid}/supplier/{Sid}")
    public ResponseEntity<?> addSupplierById(@PathVariable("PTid") Integer PTid, @PathVariable("Sid") Integer Sid){
        productService.addSupplier(PTid, Sid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('" + Role.Fields.ADMIN + "')")
    @DeleteMapping("/{PTid}/supplier/{Sid}")
    public ResponseEntity<?> removeSupplierById(@PathVariable("PTid") Integer PTid, @PathVariable("Sid") Integer Sid){
        productService.removeSupplier(PTid, Sid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('" + Role.Fields.USER + "', '" + Role.Fields.ADMIN + "')")
    @PostMapping("/{PTid}/cart/{Cid}")
    public ResponseEntity<?> addToCartById(@PathVariable("PTid") Integer PTid, @PathVariable("Cid") Integer Cid){
        productService.addProductToCart(PTid, Cid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('" + Role.Fields.USER + "', '" + Role.Fields.ADMIN + "')")
    @DeleteMapping("/{PTid}/cart/{Cid}")
    public ResponseEntity<?> removeFromCartById(@PathVariable("PTid") Integer PTid, @PathVariable("Cid") Integer Cid){
        productService.removeProductFromCart(PTid, Cid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('" + Role.Fields.USER + "', '" + Role.Fields.ADMIN + "')")
    @PostMapping("/{PTid}/wishlist/{Wid}")
    public ResponseEntity<?> addToWishlistById(@PathVariable("PTid") Integer PTid, @PathVariable("Wid") Integer Wid){
        productService.addProductToWishlist(PTid, Wid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('" + Role.Fields.USER + "', '" + Role.Fields.ADMIN + "')")
    @DeleteMapping("/{PTid}/wishlist/{Wid}")
    public ResponseEntity<?> removeFromWishlistById(@PathVariable("PTid") Integer PTid, @PathVariable("Wid") Integer Wid){
        productService.removeProductFromWishlist(PTid, Wid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
