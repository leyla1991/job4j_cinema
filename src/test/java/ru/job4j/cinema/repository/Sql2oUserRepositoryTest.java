package ru.job4j.cinema.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.User;

import java.util.Properties;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Sql2oUserRepositoryTest {

    private static Sql2oUserRepository sql2oUserRepository;
    private static Sql2o sql2oCLean;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oUserRepositoryTest.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oCLean = sql2o;
        sql2oUserRepository = new Sql2oUserRepository(sql2o);
    }

    @AfterEach
    public void cleanTable() {
        try (var connection = sql2oCLean.open()) {
            connection.createQuery("DELETE FROM users", true).executeUpdate();
        }
    }

    @Test
    public void whenSaveThenGetSame() {
        var user = sql2oUserRepository.save(new User("pass0", "email", "name", 1));
        var savedUser = sql2oUserRepository.findByEmailAndPassword(user.get().getEmail(), user.get().getPassword());
        assertThat(savedUser).usingRecursiveComparison().isEqualTo(user);
    }

    @Test
    public void whenSaveSeveralThenGetAll() {
        var user1 = sql2oUserRepository.save(new User("pass0", "email1", "name1", 1));
        var user2 = sql2oUserRepository.save(new User("pass1", "email2", "name2", 2));
        var user3 = sql2oUserRepository.save(new User("pass2", "email3", "name3", 3));
        var result1 = sql2oUserRepository.findByEmailAndPassword(user1.get().getEmail(), user1.get().getPassword());
        var result2 = sql2oUserRepository.findByEmailAndPassword(user2.get().getEmail(), user2.get().getPassword());
        var result3 = sql2oUserRepository.findByEmailAndPassword(user3.get().getEmail(), user3.get().getPassword());
        assertThat(result1).usingRecursiveComparison().isEqualTo(user1);
        assertThat(result2).usingRecursiveComparison().isEqualTo(user2);
        assertThat(result3).usingRecursiveComparison().isEqualTo(user3);
    }

    @Test
    public void whenTwoEqualsEmail() {
        var user1 = sql2oUserRepository.save(new User("pass0", "email1", "name1", 1));
        var user2 = sql2oUserRepository.save(new User("pass1", "email1", "name2", 2));
        var savedUser = sql2oUserRepository.findByEmailAndPassword("email1", "pass1");
        assertThat(savedUser).usingRecursiveComparison().isEqualTo(user2);
        assertThat(user2).isEmpty();
    }
}