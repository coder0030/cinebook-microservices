package com.movie.user_service.ServiceImpl;

import com.movie.user_service.DTO.UserDTO;
import com.movie.user_service.Entity.User;
import com.movie.user_service.ExceptionHandler.InvalidCredentialsException;
import com.movie.user_service.ExceptionHandler.UserAlreadyExistException;
import com.movie.user_service.ExceptionHandler.UserNotFoundException;
import com.movie.user_service.Helper.Role;
import com.movie.user_service.Mapper.UserMapper;
import com.movie.user_service.Redis.RedisService;
import com.movie.user_service.Repository.UserRepository;
import com.movie.user_service.RequestDTO.UpdateUserRequestDTO;
import com.movie.user_service.RequestDTO.UserRequestDTO;
import com.movie.user_service.Service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RedisService redisService;

    private static final String USER_ID_CACHE_KEY = "user:id:";
    private static final String USER_EMAIL_CACHE_KEY = "user:email:";
    private static final String USER_NAME_CACHE_KEY = "user:name:";
    private static final String USER_TOPIC = "user-events";

    private static final int CACHE_TTL_MINUTES = 60;

    private void validateRequest(String email, Long id) {
        if (email != null && userRepository.existsByEmailAndIsActiveTrueAndIdNot(email, id)) {
            throw new UserAlreadyExistException("User already exists with email: " + email);
        }
    }

    private void cacheUser(User user) {
        if (user == null || user.getId() == null) {
            log.warn("Attempted to cache null user or user without ID");
            return;
        }

        String idKey = USER_ID_CACHE_KEY + user.getId();
        String emailKey = USER_EMAIL_CACHE_KEY + user.getEmail();
        String nameKey = USER_NAME_CACHE_KEY + user.getName();

        redisService.setUserWithExpiry(idKey, user, CACHE_TTL_MINUTES, TimeUnit.MINUTES);
        redisService.setUserWithExpiry(emailKey, user, CACHE_TTL_MINUTES, TimeUnit.MINUTES);
        redisService.setUserWithExpiry(nameKey, user, CACHE_TTL_MINUTES, TimeUnit.MINUTES);

        log.debug("Cached user with ID: {}, Email: {}, Name: {}", user.getId(), user.getEmail(), user.getName());
    }

    private void evictUserCache(User user) {
        if (user == null || user.getId() == null) {
            log.warn("Attempted to evict null user or user without ID");
            return;
        }

        String idKey = USER_ID_CACHE_KEY + user.getId();
        String emailKey = USER_EMAIL_CACHE_KEY + user.getEmail();
        String nameKey = USER_NAME_CACHE_KEY + user.getName();

        redisService.delete(idKey);
        redisService.delete(emailKey);
        redisService.delete(nameKey);

        redisService.removeFromSet("users:all", user.getId().toString());

        log.debug("Evicted user caches for ID: {}", user.getId());
    }


    private void updateUserCache(User oldUser, User updatedUser) {
        evictUserCache(oldUser);
        cacheUser(updatedUser);

        log.debug("Updated user caches for ID: {}", updatedUser.getId());
    }

    @Cacheable(value = "users", key = "#userId")
    public User findById(Long userId) {
        log.debug("Finding user by ID: {} from database", userId);

        String key = USER_ID_CACHE_KEY + userId;
        User cachedUser = redisService.getUserById(key);

        if (cachedUser != null) {
            log.debug("User found in Redis cache with ID: {}", userId);
            return cachedUser;
        }

        User user = userRepository.findByIdAndIsActiveTrue(userId);
        if (user == null) {
            throw new UserNotFoundException("User id: " + userId + " not exists");
        }

        cacheUser(user);
        return user;
    }


    public User findByEmail(String email) {
        log.debug("Finding user by email: {}", email);

        String key = USER_EMAIL_CACHE_KEY + email;
        User cachedUser = redisService.getUserByEmail(key);

        if (cachedUser != null) {
            log.debug("User found in Redis cache with email: {}", email);
            return cachedUser;
        }

        User user = userRepository.findByEmailAndIsActiveTrue(email);
        if (user == null) {
            throw new UserNotFoundException("User not found with email: " + email);
        }

        cacheUser(user);
        return user;
    }


    public User findByName(String name) {
        log.debug("Finding user by name: {}", name);

        String key = USER_NAME_CACHE_KEY + name;
        User cachedUser = (User) redisService.getUserByName(key);

        if (cachedUser != null) {
            log.debug("User found in Redis cache with name: {}", name);
            return cachedUser;
        }

        User user = userRepository.findByNameAndIsActiveTrue(name);
        if (user == null) {
            throw new UserNotFoundException("User not found with name: " + name);
        }

        cacheUser(user);
        return user;
    }

    @Transactional
    public UserDTO registerUser(UserRequestDTO userRequestDTO) {
        log.info("Registering new user with email: {}", userRequestDTO.getEmail());

        if (existsByEmail(userRequestDTO.getEmail())) {
            throw new UserAlreadyExistException("User already exists with email: " + userRequestDTO.getEmail());
        }

        User user = userMapper.convertRequestDTOToEntity(userRequestDTO, new User());
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        user.setIsActive(true);

        if (user.getRole() == null) {
            user.setRole(Role.USER);
        }

        User savedUser = userRepository.save(user);
        cacheUser(savedUser);

        log.info("User registered successfully with ID: {}", savedUser.getId());
        return userMapper.convertEntityToDTO(savedUser);
    }

    @Override
    public UserDTO getUserById(Long userId) {
        log.debug("Getting user by ID: {}", userId);

        String cachedUserById = USER_ID_CACHE_KEY + userId;
        User cachedUser =  redisService.getUserById(cachedUserById);
        if (cachedUser != null) {
            return userMapper.convertEntityToDTO(cachedUser);
        }

        User user = findById(userId);
        return userMapper.convertEntityToDTO(user);
    }

    public UserDTO getUserByEmailDTO(String email) {
        log.debug("Getting user by email: {}", email);

        String cachedUserByEmail = USER_EMAIL_CACHE_KEY + email;
        User cachedUser =  redisService.getUserByEmail(cachedUserByEmail);
        if (cachedUser != null) {
            return userMapper.convertEntityToDTO(cachedUser);
        }

        User user = findByEmail(email);
        return userMapper.convertEntityToDTO(user);
    }

    @Transactional
    @Override
    public UserDTO updateUser(Long userId, UpdateUserRequestDTO userDTO) {
        log.info("Updating user with ID: {}", userId);

        User existingUser = findById(userId);
        User oldUser = existingUser;

        boolean isUpdated = false;

        if (userDTO.getEmail() != null && !userDTO.getEmail().equals(existingUser.getEmail())) {
            validateRequest(userDTO.getEmail(), userId);
            existingUser.setEmail(userDTO.getEmail());
            isUpdated = true;
        }

        if (userDTO.getPassword() != null && !userDTO.getPassword().isBlank()) {
            existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            isUpdated = true;
        }

        if (userDTO.getPhoneNumber() != null && !userDTO.getPhoneNumber().isBlank()
                && !userDTO.getPhoneNumber().equals(existingUser.getPhoneNumber())) {
            existingUser.setPhoneNumber(userDTO.getPhoneNumber());
            isUpdated = true;
        }

        if (userDTO.getName() != null && !userDTO.getName().equals(existingUser.getName())) {
            existingUser.setName(userDTO.getName());
            isUpdated = true;
        }

        if (!isUpdated) {
            log.debug("No changes detected for user ID: {}", userId);
            return userMapper.convertEntityToDTO(existingUser);
        }

        User updatedUser = userRepository.save(existingUser);
        updateUserCache(oldUser, updatedUser);

        log.info("User updated successfully with ID: {}", userId);
        return userMapper.convertEntityToDTO(updatedUser);
    }

    @Transactional
    @Override
    public void deleteUser(Long userId) {
        log.info("Soft deleting user with ID: {}", userId);

        User user = findById(userId);

        user.setIsActive(false);

        userRepository.save(user);
        evictUserCache(user);

        log.info("User soft deleted successfully with ID: {}", userId);
    }



    @Override
    public Boolean checkUserExists(Long userId) {
        log.debug("Checking if user exists with ID: {}", userId);

        String key = USER_ID_CACHE_KEY + userId;
        if (redisService.hasKey(key)) {
            return true;
        }

        return userRepository.existsByIdAndIsActiveTrue(userId);
    }

    @Override
    public User getUserByEmail(String email) {
        log.debug("Getting user by email: {}", email);

        String cachedUserByEmail = USER_EMAIL_CACHE_KEY + email;
        User cachedUser =  redisService.getUserByEmail(cachedUserByEmail);
        if (cachedUser != null) {
            return cachedUser;
        }

        return findByEmail(email);
    }

    @Override
    public boolean existsByEmailAndIdNot(String email, Long id) {
        log.debug("Checking if email exists for other users: {}", email);
        return userRepository.existsByEmailAndIsActiveTrueAndIdNot(email, id);
    }

    @Override
    public boolean existsByEmail(String email) {
        log.debug("Checking if email exists: {}", email);
        return userRepository.existsByEmailAndIsActiveTrue(email);
    }


    @Override
    public User saveUser(User user) {
        log.debug("Saving user: {}", user.getEmail());
        User savedUser = userRepository.save(user);
        cacheUser(savedUser);
        return savedUser;
    }

    @Override
    public User saveExistingUser(User user) {
        if (user == null || user.getEmail() == null) {
            log.error("Attempted to save null user or user with null email");
            throw new InvalidCredentialsException("User profile update failed. Service is unavailable.");
        }

        log.debug("Saving existing user with ID: {}", user.getId());
        User savedUser = userRepository.save(user);
        updateUserCache(user, savedUser);
        return savedUser;
    }


    public void clearAllUserCaches() {
        log.warn("Clearing all user caches");
        redisService.invalidateByPattern("user:*");
        log.info("All user caches cleared");
    }
}