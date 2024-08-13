package io.ovida.test;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static io.ovida.test.constant.TestConstants.ADMIN_PASSWORD;
import static io.ovida.test.constant.TestConstants.ADMIN_USER_NAME;
import static io.ovida.test.constant.TestConstants.MEMBER_PASSWORD;
import static io.ovida.test.constant.TestConstants.MEMBER_USER_NAME;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void unauthenticatedGetMeRequestShouldFail() throws Exception {
        final ResponseEntity<String> response = this.restTemplate.getForEntity("http://localhost:" + port + "/me", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void authenticatedAdminGetMeRequestShouldReturnAdminUser() throws Exception {
        final ResponseEntity<String> response = this.restTemplate
                .withBasicAuth(ADMIN_USER_NAME, ADMIN_PASSWORD)
                .getForEntity("http://localhost:" + port + "/me", String.class);
        final JSONObject me = new JSONObject(response.getBody());
        assertThat(me.get("email")).isEqualTo(ADMIN_USER_NAME);
    }

    @Test
    void unauthenticatedFetchUsersRequestShouldFail() throws Exception {
        final ResponseEntity<String> response = this.restTemplate.getForEntity("http://localhost:" + port + "/users", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void authenticatedMemberFetchUsersRequestShouldFail() throws Exception {
        final ResponseEntity<String> response = this.restTemplate
                .withBasicAuth(MEMBER_USER_NAME, MEMBER_PASSWORD)
                .getForEntity("http://localhost:" + port + "/users", String.class);
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void authenticatedAdminFetchUsersRequestShouldReturnUsersList() throws Exception {
        final ResponseEntity<String> rawResponse = this.restTemplate
                .withBasicAuth(ADMIN_USER_NAME, ADMIN_PASSWORD)
                .getForEntity("http://localhost:" + port + "/users", String.class);
        final JSONObject fetchUsersResponse = new JSONObject(rawResponse.getBody());
        assertThat(fetchUsersResponse.getJSONObject("page").getInt("totalElements")).isEqualTo(2);
    }

    @Test
    void unauthenticatedGrantPermissionRequestShouldFail() throws Exception {
        final ResponseEntity responseEntity = this.restTemplate.postForEntity(
                "http://localhost:" + port + "/admin/grant-permission?roleId=2&permissionId=2",
                null,
                ResponseEntity.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void authenticatedMemberGrantPermissionRequestShouldFail() throws Exception {
        final ResponseEntity responseEntity = this.restTemplate
                .withBasicAuth(MEMBER_USER_NAME, MEMBER_PASSWORD)
                .postForEntity("http://localhost:" + port + "/admin/grant-permission?roleId=2&permissionId=2",
                        null,
                        ResponseEntity.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void authenticatedAdminGrantPermissionRequestShouldFail() throws Exception {
        final ResponseEntity responseEntity = this.restTemplate
                .withBasicAuth(ADMIN_USER_NAME, ADMIN_PASSWORD)
                .postForEntity("http://localhost:" + port + "/admin/grant-permission?roleId=2&permissionId=2",
                        null,
                        ResponseEntity.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void unauthenticatedRevokePermissionRequestShouldFail() throws Exception {
        final ResponseEntity responseEntity = this.restTemplate.exchange(
                "http://localhost:" + port + "/admin/revoke-permission?roleId=2&permissionId=2",
                HttpMethod.DELETE,
                null,
                ResponseEntity.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
    }

    @Test
    void authenticatedMemberRevokePermissionRequestShouldFail() throws Exception {
        final ResponseEntity responseEntity = this.restTemplate
                .withBasicAuth(MEMBER_USER_NAME, MEMBER_PASSWORD)
                .exchange("http://localhost:" + port + "/admin/revoke-permission?roleId=2&permissionId=2",
                        HttpMethod.DELETE,
                        null,
                        ResponseEntity.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    void authenticatedAdminRevokePermissionRequestShouldFail() throws Exception {
        final ResponseEntity responseEntity = this.restTemplate
                .withBasicAuth(ADMIN_USER_NAME, ADMIN_PASSWORD)
                .exchange("http://localhost:" + port + "/admin/revoke-permission?roleId=2&permissionId=2",
                        HttpMethod.DELETE,
                        null,
                        ResponseEntity.class);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
