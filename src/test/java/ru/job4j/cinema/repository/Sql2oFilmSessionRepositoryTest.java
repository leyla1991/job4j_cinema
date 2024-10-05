package repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.FilmSession;
import ru.job4j.cinema.repository.Sql2oFilmSessionRepository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class Sql2oFilmSessionRepositoryTest {

    private static Sql2oFilmSessionRepository sql2oFilmSessionRepository;

    @BeforeAll
    public static void init() throws  Exception {
        var properties = new Properties();
        try (var inputSteam = Sql2oFilmSessionRepositoryTest.class.getClassLoader().getResourceAsStream("connection.properties")) {
            properties.load(inputSteam);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oFilmSessionRepository = new Sql2oFilmSessionRepository(sql2o);
    }

    @Test
    public void whenFindAll() {
        var filmSession1 = new FilmSession(1, 4, 1,
                LocalDateTime.of(2024, 9,9,9, 30, 0),
                LocalDateTime.of(2024,9,10, 11, 0, 0),
                100);
        var filmSession2 = new FilmSession(2, 4, 2,
                LocalDateTime.of(2024, 9, 9, 8, 25,0),
                LocalDateTime.of(2024, 9, 10, 10, 0, 0),
                75);
        var filmSession3 = new FilmSession(3, 4, 1,
                LocalDateTime.of(2024, 9, 9, 9,25,33),
                LocalDateTime.of(2024, 9, 10, 12, 0, 0),
                100);
        var filmSession4 = new FilmSession(4, 4, 2,
                LocalDateTime.of(2024, 9, 9, 18,25,33),
                LocalDateTime.of(2024, 9, 10, 20, 0, 0),
                75);
        var actual = sql2oFilmSessionRepository.findAll();
        var expectedFilmSession = List.of(filmSession1, filmSession2, filmSession3, filmSession4);

        assertThat(actual).isEqualTo(expectedFilmSession);
    }

    @Test
    public void whenFindByOneFilm() {
        var filmSession1 = new FilmSession(2, 4, 2,
                LocalDateTime.of(2024, 9, 9, 8, 25,0),
                LocalDateTime.of(2024, 9, 10, 10, 0, 0),
                75);
        var actualFilmSession = sql2oFilmSessionRepository.findById(2);

        assertThat(actualFilmSession).isEqualTo(Optional.of(filmSession1));
    }

}
