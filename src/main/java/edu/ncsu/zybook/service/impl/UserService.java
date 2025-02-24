package edu.ncsu.zybook.service.impl;

import edu.ncsu.zybook.domain.model.Chapter;
import edu.ncsu.zybook.domain.model.Textbook;
import edu.ncsu.zybook.domain.model.User;
import edu.ncsu.zybook.persistence.repository.IUserRepository;
import edu.ncsu.zybook.persistence.repository.UserRepository;
import edu.ncsu.zybook.service.IUserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;
@Service
public class UserService implements IUserService {
    private IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public User create(User user) {
        Optional<User> result= userRepository.findById(user.getUserId());
        if(result.isEmpty()) {
            return userRepository.create(user);
        }
        else {
            throw new RuntimeException("User already exists");
        }
    }

    @Override
    public Optional<User> findById(int id) {
        Optional<User> result = userRepository.findById(id);
        if(result.isPresent()) {
            User user = result.get();
            return Optional.of(user);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> update(User user) {
        if(userRepository.findById(user.getUserId()).isPresent())
            return userRepository.update(user);
        else
            throw new RuntimeException("There is no User with this id");
    }

    @Override
    public boolean delete(int id) {
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            userRepository.delete(id);
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }
}
