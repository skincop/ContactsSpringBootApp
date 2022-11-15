package com.Contacts.db.Repository;

import com.Contacts.db.Contact;
import com.Contacts.db.User;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ContactRepo extends CrudRepository<Contact, Long> {
    Page<Contact> findAll(Pageable pageable);
    Page<Contact> findByAuthor(User author, Pageable pageable);
    Contact findFirstByFirstNameAndSecondNameAndEmailAndPhoneNumberAndAuthor(String firsName,
                                                                              String secondName,
                                                                              String Email,
                                                                              String PhoneNumber,
                                                                              User author);
    Page<Contact> findByFirstNameAndAuthor(String firstName,User author,Pageable pageable);
    Contact findFirstById(Long id);
}