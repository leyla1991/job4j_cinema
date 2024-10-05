package ru.job4j.cinema.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.cinema.dto.FileDTO;
import ru.job4j.cinema.service.FileService;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FileControllerTest {
    private FileService fileService;
    private FileController fileController;
    private MultipartFile testFile;

    @BeforeEach
    public void init() {
        fileService = mock(FileService.class);
        fileController = new FileController(fileService);
        testFile = new MockMultipartFile("image", new byte[] {1, 2, 3});
    }

    @Test
    public void whenGetId() throws Exception {
        var fileDto = new FileDTO(testFile.getOriginalFilename(), testFile.getBytes());
        when(fileService.getFileById(any(Integer.class))).thenReturn(Optional.of(fileDto));
        var fileResponse = fileController.getById(any(Integer.class));
        assertThat(fileResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void whenGetNotId() {
        when(fileService.getFileById(any(Integer.class))).thenReturn(Optional.empty());
        var fileResponse = fileController.getById(any(Integer.class));
        assertThat(fileResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}