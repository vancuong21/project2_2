package jmaster.io.project2_2.service;

import jakarta.transaction.Transactional;
import jmaster.io.project2_2.dto.PageDTO;
import jmaster.io.project2_2.dto.UserRoleDTO;
import jmaster.io.project2_2.entity.User;
import jmaster.io.project2_2.entity.UserRole;
import jmaster.io.project2_2.repo.UserRepo;
import jmaster.io.project2_2.repo.UserRoleRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserRoleService {
    @Autowired
    UserRoleRepo userRoleRepo;
    @Autowired
    UserRepo userRepo;

    @Transactional
    public void create(UserRoleDTO userRoleDTO) {
        UserRole userRole = new UserRole();
        userRole.setRole(userRoleDTO.getRole());

        User user = userRepo.findById(userRoleDTO.getUserId()).
                orElseThrow(NoClassDefFoundError::new);
        userRole.setUser(user);

        userRoleRepo.save(userRole);
    }

    @Transactional
    public void update(UserRoleDTO userRoleDTO) {
        UserRole userRole = userRoleRepo.findById(userRoleDTO.getId()).
                orElseThrow(NoClassDefFoundError::new);
        userRole.setRole(userRoleDTO.getRole());

        User user = userRepo.findById(userRoleDTO.getUserId()).
                orElseThrow(NoClassDefFoundError::new);
        userRole.setUser(user);

        userRoleRepo.save(userRole);
    }

    @Transactional
    public void delete(int id) { // delete by user role id
        userRoleRepo.deleteById(id);
    }

    @Transactional
    public void deleteAll(List<Integer> ids) { // delete nhieu
        userRoleRepo.deleteAllById(ids);
    }

    @Transactional
    public void deleteByUserId(int userId) { // delete by user role id
        userRoleRepo.deleteById(userId);
    }

    // search du lieu,/ tra du lieu ra
    public UserRoleDTO getById(int id) {
        UserRole userRole = userRoleRepo.findById(id).
                orElseThrow(NoClassDefFoundError::new);
        return new ModelMapper().map(userRole, UserRoleDTO.class);
    }

    public PageDTO<UserRoleDTO> searchByUserId(int userId, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<UserRole> pageRS = userRoleRepo.searchByUserId(userId, pageable);

        PageDTO<UserRoleDTO> pageDTO = new PageDTO<>();
        pageDTO.setTotalPages(pageRS.getTotalPages());
        pageDTO.setTotalElements(pageRS.getTotalElements());

        List<UserRoleDTO> userRoleDTOs = new ArrayList<>();
        for (UserRole userRole : pageRS.getContent()) {
            userRoleDTOs.add(new ModelMapper().map(userRole, UserRoleDTO.class));
        }

        pageDTO.setContents(userRoleDTOs); // set vao pageDTO
        return pageDTO;
    }

    public PageDTO<UserRoleDTO> searchByRole(String role, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<UserRole> pageRS = userRoleRepo.searchByRole(role, pageable);

        PageDTO<UserRoleDTO> pageDTO = new PageDTO<>();
        pageDTO.setTotalPages(pageRS.getTotalPages());
        pageDTO.setTotalElements(pageRS.getTotalElements());

        List<UserRoleDTO> userRoleDTOs = new ArrayList<>();
        for (UserRole userRole : pageRS.getContent()) {
            userRoleDTOs.add(new ModelMapper().map(userRole, UserRoleDTO.class));
        }

        pageDTO.setContents(userRoleDTOs); // set vao pageDTO
        return pageDTO;
    }
}
