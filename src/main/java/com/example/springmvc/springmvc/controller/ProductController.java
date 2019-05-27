package com.example.springmvc.springmvc.controller;

import com.example.springmvc.springmvc.model.Product;
import com.example.springmvc.springmvc.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.Serializable;

@Controller /*表明这是一个Springmvc的控制器*/
public class ProductController {
    private ProductRepository productRepository;
    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Autowired
    private RedisTemplate<Serializable, Object> redisTemplate;


    @RequestMapping(path = "/")
    public String index(){
        return "index";
    }

    @RequestMapping(path = "/products/add", method = RequestMethod.GET)
    public String  creatProduct(Model model){
        Product product = new Product();
        model.addAttribute("product",product);
        return "edit";
    }

    @RequestMapping(path = "products", method = RequestMethod.POST)
    public String saveProduct(Product product){
        productRepository.save(product);
        redisTemplate.opsForValue().set("product2", product);
        System.out.println(product);
        return "redirect:/";
    }

    @RequestMapping(path = "/products",method = RequestMethod.GET)
    public String getAllProduct(Model model){
        model.addAttribute("products",productRepository.findAll());
        return "products";
    }

    @RequestMapping(path = "/products/edit/{id}",method = RequestMethod.GET)
    public String editProducts(Model model, @PathVariable(value = "id") String id){
        model.addAttribute("product",productRepository.findById(id));
        return "edit";
    }

    @RequestMapping(path = "/products/delete/{id}",method = RequestMethod.GET)
    public String deleteProducts(@PathVariable(value ="id") String id){
        productRepository.deleteById(id);
        return "redirect:/products";
    }

    /*
    @RequestMapping("/get")
    public Object getPOJO(){
        return redisTemplate.opsForValue().get("product2");
    }*/


}
