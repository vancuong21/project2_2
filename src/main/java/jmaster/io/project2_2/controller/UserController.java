package jmaster.io.project2_2.controller;

import jakarta.servlet.http.HttpServletResponse;
import jmaster.io.project2_2.dto.PageDTO;
import jmaster.io.project2_2.dto.UserDTO;
import jmaster.io.project2_2.repo.UserRepo;
import jmaster.io.project2_2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;

/**
 * @ModelAttribute User u : sẽ map tất cả những thằng trùng thuộc tính vào User
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    private UserRepo userRepo;

    @GetMapping("/new")
    public String add() {
        return "user/add.html";
    }

    @PostMapping("/new")
    public String add(@ModelAttribute UserDTO u) throws IOException {
        if (u.getFile() != null && !u.getFile().isEmpty()) {
            final String UPLOAD_FOLDER = "C:\\Users\\cuong\\OneDrive\\Desktop\\KhoaHocSpring_JMaster\\spring_2\\project2_2\\file\\";
            String filename = u.getFile().getOriginalFilename(); // trả lại tên gốc của tệp để lưu vào db
            File newFile = new File(UPLOAD_FOLDER + filename);
            u.getFile().transferTo(newFile); // lưu lại file
            u.setAvatar(filename); // lưu lại tên file vào db
        }
        userService.create(u); // gọi hàm save thay đổi db
        return "redirect:/user/search";
    }

    // download file: /user/download?filename=abc.jpg
    @GetMapping("/download")
    public void download(@RequestParam("filename") String filename,
                         HttpServletResponse response) throws IOException {
        final String UPLOAD_FOLDER = "C:\\Users\\cuong\\OneDrive\\Desktop\\KhoaHocSpring_JMaster\\spring_2\\project2_2\\file\\";
        File file = new File(UPLOAD_FOLDER + filename);
        Files.copy(file.toPath(), response.getOutputStream());
    }

    @GetMapping("/get/{id}") // get/10
    public String get(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.getById(id));
        return "user/detail.html";
    }

    @GetMapping("/delete") // ?id=1
    public String delete(@RequestParam("id") int id) {
        userService.delete(id);
        return "redirect:/user/search";
    }

    @GetMapping("/edit") // ?id=1
    public String edit(@RequestParam("id") int id, Model model) {
        model.addAttribute("user", userService.getById(id));
        return "user/edit.html";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute UserDTO userDTO) throws IllegalStateException, IOException {

        if (userDTO != null) {
            if (!userDTO.getFile().isEmpty()) {
                final String UPLOAD_FOLDER = "D:/file/";

                String filename = userDTO.getFile().getOriginalFilename();
                File newFile = new File(UPLOAD_FOLDER + filename);

                userDTO.getFile().transferTo(newFile);

                userDTO.setAvatar(filename);// save to db
            }

            // lay du lieu can update tu edit qua current, de tranh mat du lieu cu
            userService.update(userDTO);
        }

        return "redirect:/user/search";
    }

    @GetMapping("/search")
    public String search(@RequestParam(name = "id", required = false) Integer id,
                         @RequestParam(name = "name", required = false) String name,

                         @RequestParam(name = "start", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") Date start,
                         @RequestParam(name = "end", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy HH:mm") Date end,

                         @RequestParam(name = "size", required = false) Integer size,
                         @RequestParam(name = "page", required = false) Integer page, Model model) {

        size = size == null ? 10 : size;
        page = page == null ? 0 : page;
        name = name == null ? "" : name;

        PageDTO<UserDTO> pageRS = userService.searchByName("%" + name + "%", page, size);

        model.addAttribute("totalPage", pageRS.getTotalPages());
        model.addAttribute("count", pageRS.getTotalElements());
        model.addAttribute("userList", pageRS.getContents());

        // luu lai du lieu set sang view lai
        model.addAttribute("id", id);
        model.addAttribute("name", name);
        model.addAttribute("start", start);
        model.addAttribute("end", end);

        model.addAttribute("page", page);
        model.addAttribute("size", size);

        return "user/search.html";
    }
}
