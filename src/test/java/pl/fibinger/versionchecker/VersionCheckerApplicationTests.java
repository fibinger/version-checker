package pl.fibinger.versionchecker;

import com.google.common.collect.ImmutableSet;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import pl.fibinger.versionchecker.representation.VersionRepresentation;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = VersionCheckerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class VersionCheckerApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void test_1_addSomeFeatures() {
        restTemplate.postForEntity("/features", "xero", Void.class);
        restTemplate.postForEntity("/features", "credit", Void.class);
        restTemplate.postForEntity("/features", "account", Void.class);
        restTemplate.postForEntity("/features", "billing", Void.class);
    }

    @Test
    public void test_2_addSomeVersions() {
        restTemplate.postForEntity("/versions", "1.0", Void.class);
        restTemplate.postForEntity("/versions", "1.8", Void.class);

        restTemplate.postForEntity("/versions/1.0/features", new String[]{"xero", "billing", "account"}, Void.class);
        restTemplate.postForEntity("/versions/1.8/features", new String[]{"xero", "billing"}, Void.class);
    }

    @Test
    public void test_3_assertVersions() {
        List<String> versions = restTemplate.exchange(
                "/versions", HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<List<String>>() {
                }).getBody();
        Assert.assertThat(versions, Matchers.containsInAnyOrder("1.0", "1.8"));

        VersionRepresentation version1_0 = restTemplate.getForEntity("/versions/1.0/validity", VersionRepresentation.class).getBody();
        VersionRepresentation version1_8 = restTemplate.getForEntity("/versions/1.8/validity", VersionRepresentation.class).getBody();
        Assert.assertEquals(new VersionRepresentation().setName("1.0").setActiveFeatures(ImmutableSet.of("xero", "billing", "account")), version1_0);
        Assert.assertEquals(new VersionRepresentation().setName("1.8").setActiveFeatures(ImmutableSet.of("xero", "billing")), version1_8);
    }

}
