package com.infosystem.springmvc.service;

import java.util.List;
import java.util.stream.Collectors;

import com.infosystem.springmvc.dto.EditUserDto;
import com.infosystem.springmvc.dto.SearchByNumber;
import com.infosystem.springmvc.dto.SetNewStatusDto;
import com.infosystem.springmvc.exception.DatabaseException;
import com.infosystem.springmvc.exception.LogicException;
import com.infosystem.springmvc.model.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.infosystem.springmvc.dao.UserDao;
import com.infosystem.springmvc.model.User;
import sun.rmi.runtime.Log;

import static java.util.Arrays.stream;


@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao dao;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ContractService contractService;

    public User findById(int id) {
        return dao.findById(id);
    }

    public User findByLogin(String login) {
        return dao.findByLogin(login);
    }

    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        dao.save(user);
    }

    /*
     * Since the method is running with Transaction, No need to call hibernate update explicitly.
     * Just fetch the entity from db and update it with proper values within transaction.
     * It will be updated in db once transaction ends.
     */
    public void updateUser(User user) {
        User entity = dao.findById(user.getId());
        if (entity != null) {
            entity.setFirstName(user.getFirstName());
            entity.setLastName(user.getLastName());
            entity.setAddress(user.getAddress());
            entity.setBirthDate(user.getBirthDate());
            entity.setPassport(user.getPassport());
            entity.setLogin(user.getLogin());
            entity.setMail(user.getMail());
            entity.setPassword(user.getPassword());
            entity.setRole(user.getRole());
        }
    }


    public List<User> findAllUsers() {
        return dao.findAllUsers();
    }

    @Override
    public List<User> findFirstUsers() {
        return dao.findAllUsers().stream().limit(5).collect(Collectors.toList());
    }


    /**
     * Deletes user if he has no contracts
     * @param id
     * @throws LogicException if user still has contracts
     */
    @Override
    public void deleteUserById(int id) throws LogicException, DatabaseException {
        if (!dao.findById(id).getUserContracts().isEmpty()) {
            throw new LogicException("User still have contracts!");
        }
        dao.deleteById(id);
    }

    @Override
    public void setStatus(SetNewStatusDto setNewStatusDto) {
        dao.findById(setNewStatusDto.getEntityId()).setStatus(setNewStatusDto.getEntityStatus());
    }

    @Override
    public void updateUser(EditUserDto editUserDto) {
        User user = dao.findById(editUserDto.getUserId());
        switch (editUserDto.getDataInstance()) {
            case "address":
                user.setAddress(editUserDto.getValue());
                break;
            case "passport":
                user.setPassport(Integer.valueOf(editUserDto.getValue()));
                break;
            case "mail":
                user.setMail(editUserDto.getValue());
                break;
            default:
                break;
        }
    }

    /**
     * @param searchByNumber
     * @return user (related with contract that has phoneNumber)
     * @throws LogicException if there is no contract (user) with such phone number
     */
    @Override
    public User findByPhoneNumber(SearchByNumber searchByNumber) throws LogicException {
        Contract contract = contractService.findByPhoneNumber(searchByNumber.getPhoneNumber());
        if(contract == null){
            throw new LogicException("No such number.");
        }
        return contractService.findByPhoneNumber(searchByNumber.getPhoneNumber()).getUser();
    }

}
