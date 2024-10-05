package repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.File;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.repository.Sql2oFilmRepository;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

public class Sql2oFilmRepositoryTest {

    private static Sql2oFilmRepository sql2oFilmRepository;

    private static File file;

    @BeforeAll
    public static void init() throws Exception {
            var properties = new Properties();
            try (var inputStream = Sql2oFilmRepositoryTest.class
                    .getClassLoader().getResourceAsStream("connection.properties")) {
                properties.load(inputStream);
            }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oFilmRepository = new Sql2oFilmRepository(sql2o);
    }

    @Test
    public void whenShowAllFilms() {
        var film1 =  new Film(1, "Джава и ее друзья", "Базы данных встречают Джаву и Спринг",
                2023, 3, 5, 180, 3);
        var film2 = new Film(2, "Джавист встретил Хелоу ворлд", "Драматическая история",
                2023, 3, 18, 180, 3);
        var film3 = new Film(3, "Джавист и его первая работа", "Ужасы статиста",
                2023, 4, 18, 180, 2);
        var film4 = new Film(4, "Джавист на пути Мидла", "Ужасы статиста",
                2023, 4, 18, 180, 2);
        var result = sql2oFilmRepository.findAll();

        assertThat(result).isEqualTo(List.of(film1, film2, film3, film4));
    }

    @Test
    public void whenFindOneFilm() {
        var filmExpected =  new Film(1, "Джава и ее друзья", "Базы данных встречают Джаву и Спринг",
                2023, 3, 5, 180, 8);
        var actual = sql2oFilmRepository.findById(1);
        assertThat(actual).isEqualTo(Optional.of(filmExpected));
        }
}
