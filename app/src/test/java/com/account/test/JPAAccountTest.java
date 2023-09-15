package com.account.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import com.account.persistence.entity.Account;
import com.account.persistence.repository.AccountRepository;


@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class JPAAccountTest {


  @Autowired
  private AccountRepository accountRepository;

  @Test
  public void expectEmptyList() {
    List<Account> accounts = accountRepository.findAll();
    assertEquals(0, accounts.size());
  }

  @Test
  public void testInsertToDB() {
    Account account = new Account("testmail", "testusername", "testpassword", "teststatus");
    accountRepository.save(account);
    List<Account> accounts = accountRepository.findAll();
    assertEquals(1, accounts.size());
  }

  @Test
  public void testDataPersists() {
    Account account = new Account("testmail", "testusername", "testpassword", "teststatus");
    accountRepository.save(account);
    List<Account> accounts = accountRepository.findAll();
    Account accountFromDB = accounts.get(0);
    assertEquals(account.getId(), accountFromDB.getId());
    assertEquals(account.getUsername(), accountFromDB.getUsername());
    assertEquals(account.getPassword(), accountFromDB.getPassword());
    assertEquals(account.getStatus(), accountFromDB.getStatus());
    assertEquals(account.getEmail(), accountFromDB.getEmail());
    assertEquals(account.getCreatedAt(), accountFromDB.getCreatedAt());
  }

  @Test 
  public void testCreateNullAccount() {
    Exception thrown = assertThrows(
      DataIntegrityViolationException.class,
       () -> createNullAccount()
    );
  }


  public void createNullAccount() {
    Account account = new Account();
    accountRepository.save(account);
  }

}
