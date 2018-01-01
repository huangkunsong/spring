package com.hks.soap;

import com.hks.controller.HomeController;
import com.hks.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@Service
@WebService(name = "testWebServiceSOAP")
@SOAPBinding(style= Style.RPC)
public class TestSOAP {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestSOAP.class);

    @Autowired
    HomeController homeController;

    @WebMethod
    public void method(User user){
        LOGGER.info("User Info : ", user);
        homeController.home(user);
    }
}
