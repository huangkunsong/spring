package com.hks.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hks.dao.ITestDao;
import com.hks.entity.User;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Huangkunsong
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@RestController
@RequestMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
public class HomeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    RestTemplate rest;

    @Autowired
    ITestDao iTestDao;

    @RequestMapping(value = {"/home","/"}, method = RequestMethod.GET)
    public List<User> home(User user){
        return iTestDao.findAllUser(user);
    }

    @RequestMapping(value = "/listUsers", method = RequestMethod.GET)
    public List<User> getListUser() throws Throwable {
        return getUsersByRest();
    }

    private List<User> getUsersByRest() {
        Map<String, String> header = new TreeMap<String, String>();
        header.put("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<Object> requestEntity = new HttpEntity<>(header);
        ResponseEntity<List<User>> response = rest.exchange(
            "http://localhost:8080/",
            HttpMethod.GET,
            requestEntity,
            new ParameterizedTypeReference<List<User>>() {
            }
        );
        return response.getBody();
    }

    private List<User> getUsers() throws Throwable{
        HttpClient client = HttpClients.createDefault();
        HttpGet request = new HttpGet("http://localhost:8080/");
        request.setHeader("Accept", MediaType.APPLICATION_JSON_VALUE);
        HttpResponse response = client.execute(request);
        org.apache.http.HttpEntity entity = response.getEntity();
        ObjectMapper mapper = new ObjectMapper();
        List<User> users = mapper.readValue(entity.getContent(), new TypeReference<List<User>>(){});
        return users;
    }
}
