package bg.softuni.taskmaster.service.impl;

import bg.softuni.taskmaster.model.entity.Picture;
import bg.softuni.taskmaster.repository.PictureRepository;
import bg.softuni.taskmaster.service.CloudinaryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static bg.softuni.taskmaster.utils.PictureTestUtils.*;
import static bg.softuni.taskmaster.utils.PictureTestUtils.getMultipartPicture;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PictureServiceImplTest {


    public static final String FOLDER = "testFolder";
    public static final String PUBLIC_ID = "testPublicId";
    public static final String URL = FOLDER + "/" + PUBLIC_ID;
    public static final String ORIGINAL_NAME = "";
    @Mock
    private CloudinaryService mockCloudinaryService;

    @Mock
    private PictureRepository mockPictureRepository;

    private PictureServiceImpl pictureServiceToTest;

    private Picture testPicture;

    @Captor
    private ArgumentCaptor<Picture> pictureCaptor = ArgumentCaptor.forClass(Picture.class);

    @BeforeEach
    void setUp() {
        this.pictureServiceToTest = new PictureServiceImpl(mockPictureRepository, mockCloudinaryService);
        this.testPicture = getTestPicture();
    }

    @Test
    public void test_DeletePicture() throws IOException {
        doNothing().when(mockCloudinaryService).deletePicture(anyString());
        pictureServiceToTest.deletePicture(testPicture);
        verify(mockPictureRepository).delete(testPicture);
        verify(mockCloudinaryService).deletePicture(testPicture.getPublicId());
    }

    @Test
    public void test_CreatePictureOrGetDefault_ShouldReturn_Default_WithEmptyMultipartFile() throws IOException {
        when(mockPictureRepository.readById(1L)).thenReturn(testPicture);
        Picture actualPicture = pictureServiceToTest.createPictureOrGetDefault(getEmptyMultipartFile(), "testFolder");
        assertSame(testPicture, actualPicture);
    }

    @Test
    public void test_CreatePictureOrGetDefault_Should_Successfully_CreatePicture() throws IOException {
        when(mockCloudinaryService.uploadPicture(any(MultipartFile.class), eq(FOLDER)))
                .thenReturn(PUBLIC_ID);
        when(mockCloudinaryService.getUrl(PUBLIC_ID))
                .thenReturn(URL);

        pictureServiceToTest.createPictureOrGetDefault(getMultipartPicture(), FOLDER);
        verify(mockPictureRepository).save(pictureCaptor.capture());
        Picture actualSavedPicture = pictureCaptor.getValue();
        assertEquals(ORIGINAL_NAME, actualSavedPicture.getOriginalName());
        assertEquals(PUBLIC_ID, actualSavedPicture.getPublicId());
        assertEquals(URL, actualSavedPicture.getUrl());
    }

}