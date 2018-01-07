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
import pl.fibinger.versionchecker.dto.VersionDTO;
import pl.fibinger.versionchecker.representation.UserRepresentation;
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
        restTemplate.postForEntity("/versions", new VersionDTO("1.0", false, "xero", "billing", "account"), Void.class);
        restTemplate.postForEntity("/versions", new VersionDTO("1.8", true, "xero"), Void.class);

        restTemplate.put("/versions/1.0/features", new String[]{"xero", "account"});
        restTemplate.put("/versions/1.8/features", new String[]{"xero", "billing"});
    }

    @Test
    public void test_3_assertVersions() {
        List<String> versions = restTemplate.exchange(
                "/versions", HttpMethod.GET, HttpEntity.EMPTY, new ParameterizedTypeReference<List<String>>() {
                }).getBody();
        Assert.assertThat(versions, Matchers.containsInAnyOrder("1.0", "1.8"));

        VersionRepresentation version1_0 = restTemplate.getForEntity("/versions/1.0", VersionRepresentation.class).getBody();
        VersionRepresentation version1_8 = restTemplate.getForEntity("/versions/1.8", VersionRepresentation.class).getBody();
        Assert.assertEquals(new VersionRepresentation().setValid(false).setName("1.0").setActiveFeatures(ImmutableSet.of("xero", "account")), version1_0);
        Assert.assertEquals(new VersionRepresentation().setValid(true).setName("1.8").setActiveFeatures(ImmutableSet.of("xero", "billing")), version1_8);
    }

    @Test
    public void test_4_assertVersionsPerUser() {
        UserRepresentation franekUser = restTemplate.postForEntity("/users", "Franek", UserRepresentation.class).getBody();
        restTemplate.postForEntity("/users", "Mietek", UserRepresentation.class);

        VersionRepresentation franekVersion1_8 = restTemplate.getForEntity("/versions/1.8?userId=" + franekUser.getId(), VersionRepresentation.class).getBody();
        Assert.assertEquals(new VersionRepresentation().setValid(true).setName("1.8").setActiveFeatures(ImmutableSet.of("xero", "billing")), franekVersion1_8);

        restTemplate.put("/versions/1.8/features?userId=" + franekUser.getId(), new String[]{"credit", "billing"});

        franekVersion1_8 = restTemplate.getForEntity("/versions/1.8?userId=" + franekUser.getId(), VersionRepresentation.class).getBody();
        Assert.assertEquals(new VersionRepresentation().setValid(true).setName("1.8").setActiveFeatures(ImmutableSet.of("credit", "billing")), franekVersion1_8);

        VersionRepresentation version1_8 = restTemplate.getForEntity("/versions/1.8", VersionRepresentation.class).getBody();
        Assert.assertEquals(new VersionRepresentation().setValid(true).setName("1.8").setActiveFeatures(ImmutableSet.of("xero", "billing")), version1_8);
    }

}
