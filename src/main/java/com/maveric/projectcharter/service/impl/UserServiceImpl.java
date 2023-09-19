package com.maveric.projectcharter.service.impl;

import com.maveric.projectcharter.dto.DeleteArchiveResponse;
import com.maveric.projectcharter.dto.UserDTO;
import com.maveric.projectcharter.entity.User;
import com.maveric.projectcharter.exception.ServiceException;
import com.maveric.projectcharter.repository.UserRepository;
import com.maveric.projectcharter.service.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private DeleteArchiveResponse response;

    @Autowired
    private ModelMapper modelMapper;

    public DeleteArchiveResponse createUser(UserDTO userDTO) {
        try {
            User user = modelMapper.map(userDTO, User.class);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            response.setMessage("User Created");
            response.setHttpStatus(HttpStatus.CREATED);
            return response;
        }
        catch (DataAccessException | TransactionException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public boolean checkUserName(String username){
        Optional<User> user = userRepository.findByUsername(username);
        return user.isPresent();
    }

}
