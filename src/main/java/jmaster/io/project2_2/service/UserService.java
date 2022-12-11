package jmaster.io.project2_2.service;

import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;
import jmaster.io.project2_2.dto.PageDTO;
import jmaster.io.project2_2.dto.UserDTO;
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
public class UserService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    UserRoleRepo userRoleRepo;

    // thêm mới dữ liệu thì map giao diện với dto
    @Transactional // thêm, sủa, xoá, thì thêm @Tran.. có thể rollback nếu lỗi
    public void create(UserDTO userDTO) {
        User user = new User();
        // convert dto -> entity
        user.setName(userDTO.getName());
        user.setUsername(userDTO.getUsername());
        user.setBirthdate(userDTO.getBirthdate());
        user.setPassword(userDTO.getPassword());
        user.setAvatar(userDTO.getAvatar());

        userRepo.save(user);

        List<UserRoleDTO> userRoleDTOs = userDTO.getUserRoles();
        for (UserRoleDTO userRoleDTO : userRoleDTOs) {
            if (userRoleDTO.getRole() != null) {
                // save to db
                UserRole userRole = new UserRole();
                userRole.setUser(user);
                userRole.setRole(userRole.getRole());

                userRoleRepo.save(userRole);
            }
        }
    }

    @Transactional
    public void update(UserDTO userDTO) {
        User user = userRepo.findById(userDTO.getId()).orElseThrow(NoResultException::new);
        user.setName(userDTO.getName());
        user.setUsername(userDTO.getUsername());
        user.setBirthdate(userDTO.getBirthdate());
        //   user.setPassword(userDTO.getPassword());
        user.setAvatar(userDTO.getAvatar());

        userRepo.save(user);
    }

    @Transactional
    public void updatePassword(UserDTO userDTO) {
        User user = userRepo.findById(userDTO.getId()).
                orElseThrow(NoResultException::new);
        user.setPassword(userDTO.getPassword());

        userRepo.save(user);
    }

    @Transactional
    public void delete(int id) { // delete by user role id
        userRoleRepo.deleteById(id);
    }

    @Transactional
    public void deleteAll(List<Integer> ids) { // delete nhieu
        userRoleRepo.deleteAllById(ids);
    }

    public PageDTO<UserDTO> searchByName(String name, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Page<User> pageRS = userRepo.searchByName("%" + name + "%", pageable);

        PageDTO<UserDTO> pageDTO = new PageDTO<>();
        pageDTO.setTotalPages(pageRS.getTotalPages());
        pageDTO.setTotalElements(pageRS.getTotalElements());
        List<UserDTO> userDTOs = new ArrayList<>();
        for (User user : pageRS.getContent()) {
            userDTOs.add(new ModelMapper().map(user, UserDTO.class));
        }

        pageDTO.setContents(userDTOs); // set vao pageDTO
        return pageDTO;
    }

    // lấy ngược ra thì không cần @Transactionl
    public UserDTO getById(int id) {
        User user = userRepo.findById(id)
                .orElseThrow(NoResultException::new);
        // convert entity -> dto
        // có thể sử dụng thư viện modelmapper, thay vì set từng thằng
        UserDTO userDTO = new ModelMapper().map(user, UserDTO.class);

//        UserDTO userDTO = new UserDTO();
//
//        userDTO.setId(user.getId());
//        userDTO.setName(user.getName());
//        userDTO.setUsername(user.getUsername());
//        userDTO.setBirthdate(user.getBirthdate());
//  //      userDTO.setPassword(user.getPassword());
//        userDTO.setAvatar(user.getAvatar());
//        userDTO.setCreatedAt(user.getCreatedAt());

        return userDTO;
    }
}
