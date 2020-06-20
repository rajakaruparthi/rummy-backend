package com.local.rummy.serviccImpl;

import com.local.rummy.entity.Admin;
import com.local.rummy.repository.AdminRepository;
import com.local.rummy.request.IsAdminRequest;
import com.local.rummy.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepository adminRepository;


    @Override
    public void addAdminUser(Admin admin) {
        adminRepository.save(admin);
    }

    @Override
    public List<Admin> getAdminUser() {
        List<Admin> admins = adminRepository.findAll();
        return admins;
    }

    @Override
    public boolean isAdmin(IsAdminRequest request) {
        List<Admin> admins = adminRepository.findAll();
        for (Admin each: admins) {
            if(each.getName().equalsIgnoreCase(request.getName())
            && each.getPassword().equalsIgnoreCase(request.getPassword())){
                return true;
            }
        }
        return false;
    }
}
