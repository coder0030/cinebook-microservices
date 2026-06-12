package com.movie.user_service.ServiceImpl;

import com.movie.user_service.DTO.BookingDTO;
import com.movie.user_service.DTO.UserDTO;
import com.movie.user_service.Entity.User;
import com.movie.user_service.ExceptionHandler.InvalidCredentialsException;
import com.movie.user_service.ExceptionHandler.UserAlreadyExistException;
import com.movie.user_service.ExceptionHandler.UserNotFoundException;
import com.movie.user_service.Mapper.UserMapper;
import com.movie.user_service.Repository.UserRepository;
import com.movie.user_service.RequestDTO.LoginRequestDTO;
import com.movie.user_service.RequestDTO.UpdateUserRequestDTO;
import com.movie.user_service.RequestDTO.UserRequestDTO;
import com.movie.user_service.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    private void validateRequest(String email, Long id) {
        if(email != null && userRepository.existsByEmailAndIsActiveTrueAndIdNot(email, id)) {
            throw new UserAlreadyExistException("User already exists.");
        }
    }

    private User checkUserForLogin(String email) {
        User user = userRepository.findByEmailAndIsActiveTrue(email);
        if(user == null) {
            throw new UserNotFoundException("User email: " + email + " not exists");
        }
        return user;
    }

    private User findById(Long userId) {
        User user = userRepository.findByIdAndIsActiveTrue(userId);
        if(user == null) {
            throw new UserNotFoundException("User id: " + userId + " not exists");
        }
        return user;
    }

    @Transactional
    @Override
    public UserDTO registerUser(UserRequestDTO requestDTO) {
        validateRequest(requestDTO.getEmail(), null);
        User user = userMapper.convertRequestDTOToEntity(requestDTO, new User());
        user.setPassword(passwordEncoder.encode(requestDTO.getPassword()));
        return userMapper.convertEntityToDTO(userRepository.save(user));
    }

    @Override
    public String loginUser(LoginRequestDTO loginRequest) {
        User user = checkUserForLogin(loginRequest.getEmail());
        String encodedPassword = user.getPassword();
        boolean isMatch = passwordEncoder.matches(loginRequest.getPassword(), encodedPassword);

        if(!isMatch) {
            throw new InvalidCredentialsException("User credentials are not correct, Please try again.");
        }
        return "Login successful! Welcome " + user.getName();
    }

    @Override
    public UserDTO getUserById(Long userId) {
        User user = findById(userId);
        return userMapper.convertEntityToDTO(user);
    }

    @Transactional
    @Override
    public UserDTO updateUser(Long userId, UpdateUserRequestDTO userDTO) {
        User user = findById(userId);

        if(userDTO.getEmail() != null && !userDTO.getEmail().equals(user.getEmail())) {
            validateRequest(userDTO.getEmail(), userId);
            user.setEmail(userDTO.getEmail());
        }

        if(userDTO.getPassword() != null && !userDTO.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        }

        if(userDTO.getPhoneNumber() != null && !userDTO.getPhoneNumber().isBlank() && !userDTO.getPhoneNumber().equals(user.getPhoneNumber())) {
            user.setPhoneNumber(userDTO.getPhoneNumber());
        }

        if(userDTO.getName() != null && !userDTO.getName().equals(user.getName())) {
            user.setName(userDTO.getName());
        }
        return userMapper.convertEntityToDTO(userRepository.save(user));
    }

    @Transactional
    @Override
    public void deleteUser(Long userId) {
        User user = findById(userId);
        user.setIsActive(false);
        userRepository.save(user);
    }
}