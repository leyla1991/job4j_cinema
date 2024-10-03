package ru.job4j.cinema.service;

import ru.job4j.cinema.dto.FileDTO;
import ru.job4j.cinema.model.File;

import java.util.Optional;

public interface FileService {

    File save(FileDTO fileDTO);

    Optional<FileDTO> getFileById(int id);

    void deleteById(int id);
}
