package com.Library_management_system.controller;

import com.Library_management_system.Enums.BookFilter;
import com.Library_management_system.Enums.BookType;
import com.Library_management_system.Enums.Operator;
import com.Library_management_system.dto.GenericReturnClass;
import com.Library_management_system.dto.request.BookCreationRequest;
import com.Library_management_system.dto.response.BookCreationResponse;
import com.Library_management_system.dto.response.BookFilterResponse;
import com.Library_management_system.service.impl.BookService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
@Validated
public class BookController
{
    @Autowired
    private BookService bookService;

    @PostMapping("/addBook")
    public ResponseEntity<GenericReturnClass> addBook(@RequestBody BookCreationRequest request)
    {
        BookCreationResponse response = bookService.addBook(request);
        GenericReturnClass returnObject = GenericReturnClass.builder().data(response).build();
        if(response != null){
            returnObject.setCode(0);
            returnObject.setMsg("Its successful");
        }else{
            returnObject.setCode(1);
            returnObject.setMsg("Its failed");
        }
        return new ResponseEntity<>(returnObject, HttpStatus.OK);
    }

    @GetMapping("/filterBook")
    public List<BookFilterResponse> filterBook(@NotNull(message = "filterBy must not be null") @RequestParam("filterBy") BookFilter filterBy,
                                               @NotNull(message = "operator must not be null") @RequestParam("operator") Operator operator,
                                               @NotBlank(message = "Value must not be Blank") @RequestParam("value") String value){
        return bookService.filterBook(filterBy, operator,value);
    }

}
