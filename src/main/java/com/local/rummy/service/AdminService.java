package com.local.rummy.service;

import com.local.rummy.entity.Admin;
import com.local.rummy.request.IsAdminRequest;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "/api")
public interface AdminService {

    @RequestMapping(path = "/add-admin-user", method = RequestMethod.POST)
    void addAdminUser(Admin admin);

    @RequestMapping(path = "/get-admin-users", method = RequestMethod.POST)
    List<Admin> getAdminUser();

    @RequestMapping(path = "/is-admin", method = RequestMethod.POST)
    boolean isAdmin(IsAdminRequest request);


}
