package com.prankur.eCommerce.controllers.usersControllers;

import com.prankur.eCommerce.services.usersServices.AdminServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("admin")
public class AdminController
{
    @Autowired
    AdminServices adminServices;

    @GetMapping("/customers")
    MappingJacksonValue displayAllCustomers(@RequestParam(defaultValue = "10") String pagesize, @RequestParam(defaultValue = "0") String pageoff, @RequestParam(defaultValue = "id") String sortby, @RequestParam String email)
    {
        Integer pageSize = Integer.parseInt(pagesize);
        Integer pageOffSet = Integer.parseInt(pageoff);

        MappingJacksonValue mappingJacksonValue = adminServices.returnAllCustomersFiltered(pageOffSet,pageSize,sortby,email);
        return mappingJacksonValue;
    }

    @GetMapping("/sellers")
    MappingJacksonValue displayAllSellers(@RequestParam(defaultValue = "10") String pagesize, @RequestParam(defaultValue = "0") String pageoff, @RequestParam(defaultValue = "id") String sortby, @RequestParam String email)
    {
        Integer pageSize = Integer.parseInt(pagesize);
        Integer pageOffSet = Integer.parseInt(pageoff);

        MappingJacksonValue mappingJacksonValue = adminServices.returnAllSellersFiltered(pageOffSet,pageSize,sortby,email);
        return mappingJacksonValue;
    }

    @PostMapping("/activateCustomer/{id}")
    ResponseEntity<String> activateCustomer(@PathVariable Long id)
    {
        String response = null;
        response = adminServices.activateCustomer(id);
        ResponseEntity<String> responseEntity = ResponseEntity.status(HttpStatus.OK).body(response);
        return responseEntity;
    }

    @PostMapping("/activateSeller/{id}")
    ResponseEntity<String> activateSeller(@PathVariable Long id)
    {
        String response = null;
        response = adminServices.activateSeller(id);
        ResponseEntity<String> responseEntity = ResponseEntity.status(HttpStatus.OK).body(response);
        return responseEntity;
    }

    @PostMapping("/deactivateCustomer/{id}")
    ResponseEntity<String> deActivateCustomer(@PathVariable Long id)
    {
        String response = null;
        response = adminServices.deActivateCustomer(id);
        ResponseEntity<String> responseEntity = ResponseEntity.status(HttpStatus.OK).body(response);
        return responseEntity;
    }

    @PostMapping("/deactivateSeller/{id}")
    ResponseEntity<String> deActivateSeller(@PathVariable Long id)
    {
        String response = null;
        response = adminServices.deActivateSeller(id);
        ResponseEntity<String> responseEntity = ResponseEntity.status(HttpStatus.OK).body(response);
        return responseEntity;
    }

}
