package com.example.PersonalAccounting.controllers;

import com.example.PersonalAccounting.dto.UserDTO;
import com.example.PersonalAccounting.entity.User;
import com.example.PersonalAccounting.services.entity_service_impl.UserService;
import com.example.PersonalAccounting.util.ErrorResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static com.example.PersonalAccounting.util.ErrorsUtil.generateErrorMessage;

@RestController
@RequestMapping("/auth")
public class AuthController {

    //TODO: add validator that will check user email unique

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public AuthController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public String registerPage() {
        return "register";
    }

    @PostMapping("/registration")
    public ResponseEntity<HttpStatus> registration(@RequestBody @Valid UserDTO userDTO, BindingResult bindingResult) {

        if(bindingResult.hasErrors()) {
            throw new IllegalArgumentException(generateErrorMessage(bindingResult.getFieldErrors()));
        }

        User user = convertToUser(userDTO);
        userService.create(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> handleException(IllegalArgumentException e) {
        ErrorResponse response = new ErrorResponse(
                e.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private User convertToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }


}
