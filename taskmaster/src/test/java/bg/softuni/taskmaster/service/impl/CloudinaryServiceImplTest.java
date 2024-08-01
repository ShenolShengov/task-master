package bg.softuni.taskmaster.service.impl;

import bg.softuni.taskmaster.utils.PictureTestUtils;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CloudinaryServiceImplTest {


    public static final String URL = "TestUrl";
    public static final String PUBLIC_ID = "TestPublicId";
    public static final String FOLDER = "testFolder";
    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private Cloudinary cloudinary;

    private CloudinaryServiceImpl cloudinaryService;

    @BeforeEach
    void setUp() {
        this.cloudinaryService = new CloudinaryServiceImpl(cloudinary);
    }

    @Test
    public void test_GetUrl() {
        when(cloudinary.url().secure(true).generate(anyString()))
                .thenReturn(URL);
        String actualUrl = cloudinaryService.getUrl(PUBLIC_ID);
        assertEquals(URL, actualUrl);
    }

    @Test
    public void test_DeletePicture() throws IOException {
        when(cloudinary.uploader().destroy(PUBLIC_ID, ObjectUtils.emptyMap()))
                .thenReturn(ObjectUtils.emptyMap());
        cloudinaryService.deletePicture(PUBLIC_ID);
        verify(cloudinary.uploader()).destroy(PUBLIC_ID, ObjectUtils.emptyMap());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void test_UploadPicture() throws IOException {
        MultipartFile testMultipartPicture = PictureTestUtils.getMultipartPicture();
        Map<Object, Object> testOptions = (Map<Object, Object>) ObjectUtils.asMap("folder", FOLDER);
        when(cloudinary.uploader().upload(testMultipartPicture.getBytes(),
                testOptions).get("public_id"))
                .thenReturn(PUBLIC_ID);
        String actualPublicId = cloudinaryService.uploadPicture(testMultipartPicture, FOLDER);
        assertEquals(PUBLIC_ID, actualPublicId);
    }
}