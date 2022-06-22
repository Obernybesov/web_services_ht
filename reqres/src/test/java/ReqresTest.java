import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ReqresTest {

    public static final String BASE_URI = "https://reqres.in/";
    public static final String GET_USERS_URI = BASE_URI + "api/users?page=2";
    public static final String CREATE_USERS_URI = BASE_URI + "api/users";
    public static final String UPDATE_USERS_URI = BASE_URI + "/api/users/2";
    public static final String DELETE_USERS_URI = BASE_URI + "/api/users/2";

    @Test
    public void getListUsers() throws IOException {
        HttpUriRequest request = new HttpGet(GET_USERS_URI);
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        assertEquals(HttpStatus.SC_OK,
                response.getStatusLine().getStatusCode());

    }

    @Test
    public void createUser() throws IOException {
        HttpPost request = new HttpPost(CREATE_USERS_URI);
        List<NameValuePair> userParams = new ArrayList<NameValuePair>();
        userParams.add(new BasicNameValuePair("name", "Oleksii"));
        userParams.add(new BasicNameValuePair("job", "Manual QA"));

        request.setEntity(new UrlEncodedFormEntity(userParams));
        CloseableHttpResponse response = HttpClientBuilder.create().build().execute(request);

        ObjectMapper objectMapper = new ObjectMapper();
        UserCreate oleksiiCreate = objectMapper.readValue(response.getEntity().getContent(), UserCreate.class);

        assertEquals(HttpStatus.SC_CREATED, response.getStatusLine().getStatusCode());
        assertEquals(oleksiiCreate.getName(), "Oleksii");
        assertEquals(oleksiiCreate.getJob(), "Manual QA");
    }

    @Test
    public void updateUser() throws IOException {
        HttpPut request = new HttpPut(UPDATE_USERS_URI);
        List<NameValuePair> userParams = new ArrayList<NameValuePair>();
        userParams.add(new BasicNameValuePair("name", "Oleksii"));
        userParams.add(new BasicNameValuePair("job", "Automation QA"));

        request.setEntity(new UrlEncodedFormEntity(userParams));
        CloseableHttpResponse response = HttpClientBuilder.create().build().execute(request);

        ObjectMapper objectMapper = new ObjectMapper();
        UserUpdate oleksiiUpdaete = objectMapper.readValue(response.getEntity().getContent(), UserUpdate.class);
        assertEquals(HttpStatus.SC_OK, response.getStatusLine().getStatusCode());
        assertEquals(oleksiiUpdaete.getName(), "Oleksii");
        assertEquals(oleksiiUpdaete.getJob(), "Automation QA");
    }

    @Test
    public void userDelete() throws IOException {
        HttpDelete request = new HttpDelete(DELETE_USERS_URI);
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        assertEquals(HttpStatus.SC_NO_CONTENT,
                response.getStatusLine().getStatusCode());

    }


}
