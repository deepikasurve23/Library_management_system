package com.Library_management_system.Controller;

import com.Library_management_system.controller.BookController;
import com.Library_management_system.service.impl.BookService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(BookController.class)
public class BookControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private BookService bookService;

    @Test
    public void testAddBook() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("bookTitle","title");
        jsonObject.put("securityAmount" , 100.0);
        jsonObject.put("bookNo" , "bookNo");
        jsonObject.put("bookType" , "EDUCATIONAL");
        jsonObject.put("authorName" , "authorName");
        jsonObject.put("authorEmail" , "authorEmail@gmail.com");
        mockMvc.perform(post("/book/addBook")
                .contentType(MediaType.APPLICATION_JSON_VALUE) // Pass data in json format
                .content(jsonObject.toString())) //Pass body in single json and enter key value in String format
                .andExpect(MockMvcResultMatchers.status().isOk()); // Expect result is ok
    }
}
