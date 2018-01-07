package pl.fibinger.versionchecker;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import pl.fibinger.versionchecker.dto.VersionDTO;

import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = VersionCheckerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VersionCheckerApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void test_1_addSomeVersions() {
        restTemplate.postForEntity("/versions", "1.0", Void.class);
        restTemplate.postForEntity("/versions", "1.8", Void.class);
    }

    @Test
    public void test_2_getVersions() {
        ResponseEntity<Set<VersionDTO>> response = restTemplate.exchange(
                "/versions", HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<Set<VersionDTO>>() {
                });

        Set<VersionDTO> versions = response.getBody();
        Assert.assertThat(versions, Matchers.containsInAnyOrder(new VersionDTO().setName("1.0"), new VersionDTO().setName("1.8")));
    }

}
