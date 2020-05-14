package rest.client;

import domain.Proba;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.Callable;


public class UsersClient {
    public static final String URL = "http://localhost:8080/probe";

    private RestTemplate restTemplate = new RestTemplate();

    private <T> T execute(Callable<T> callable) throws Exception {
        try {
            return callable.call();
        } catch (ResourceAccessException | HttpClientErrorException e) { // server down, resource exception
            throw new Exception(e);
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public Proba[] getAll() throws Exception {
        return execute(() -> restTemplate.getForObject(URL, Proba[].class));
    }

    public Proba getById(String id) throws Exception {
        return execute(() -> restTemplate.getForObject(String.format("%s/%s", URL, id), Proba.class));
    }
    public Proba update(Proba proba) throws Exception {
        return execute(() -> restTemplate.postForObject(URL, proba, Proba.class));
    }

    public void delete(String id) throws Exception {
        execute(() -> {
            restTemplate.delete(String.format("%s/%s", URL, id));
            return null;
        });
    }

}
