package bg.softuni.taskmaster.service.impl;

import bg.softuni.taskmaster.testutils.PictureTestDataUtils;
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
    private Cloudinary mockCloudinary;

    private CloudinaryServiceImpl cloudinaryServiceToTest;

    @BeforeEach
    void setUp() {
        this.cloudinaryServiceToTest = new CloudinaryServiceImpl(mockCloudinary);
    }

    @Test
    public void test_GetUrl() {
        when(mockCloudinary.url().secure(true).generate(anyString()))
                .thenReturn(URL);
        String actualUrl = cloudinaryServiceToTest.getUrl(PUBLIC_ID);
        assertEquals(URL, actualUrl);
    }

    @Test
    public void test_DeletePicture() throws IOException {
        when(mockCloudinary.uploader().destroy(PUBLIC_ID, ObjectUtils.emptyMap()))
                .thenReturn(ObjectUtils.emptyMap());
        cloudinaryServiceToTest.deletePicture(PUBLIC_ID);
        verify(mockCloudinary.uploader()).destroy(PUBLIC_ID, ObjectUtils.emptyMap());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void test_UploadPicture() throws IOException {
        MultipartFile testMultipartPicture = PictureTestDataUtils.getMultipartPicture();
        Map<Object, Object> testOptions = (Map<Object, Object>) ObjectUtils.asMap("folder", FOLDER);
        when(mockCloudinary.uploader().upload(testMultipartPicture.getBytes(),
                testOptions).get("public_id"))
                .thenReturn(PUBLIC_ID);
        String actualPublicId = cloudinaryServiceToTest.uploadPicture(testMultipartPicture, FOLDER);
        assertEquals(PUBLIC_ID, actualPublicId);
    }
}