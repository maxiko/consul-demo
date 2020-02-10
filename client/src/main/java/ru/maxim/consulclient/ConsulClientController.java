package ru.maxim.consulclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.util.List;

@RestController
@RefreshScope
public class ConsulClientController {

    private final DiscoveryClient discoveryClient;
    private final LoadBalancerClient loadBalancer;

    public ConsulClientController(DiscoveryClient discoveryClient, LoadBalancerClient loadBalancer) {
        this.discoveryClient = discoveryClient;
        this.loadBalancer = loadBalancer;
    }

    public String doRequest(String serviceId, String uri){
        final CloseableHttpClient httpClient = HttpClients.createDefault();
        ServiceInstance instance = loadBalancer.choose(serviceId);
        URI requestUri = URI.create(String.format("http://%s:%s%s",instance.getHost(),instance.getPort(),uri));
        HttpGet request = new HttpGet(requestUri);
        try {
            CloseableHttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            if (entity != null) return EntityUtils.toString(entity);
        }catch (IOException e){
            return e.getMessage();
        }
        return null;
    }

    @GetMapping("/getHostname")
    public String getHostname(){
        return doRequest("MyBackend","/hostname");
    }

    @GetMapping("/getConfigFromValue")
    public String getConfigFromValue(){
        return doRequest("MyBackend","/getConfigFromValue");
    }

    @GetMapping(value = "/getAppAddresses", produces = "application/json")
    public String getAppAddresses() throws JsonProcessingException {
        List<ServiceInstance> list = discoveryClient.getInstances("MyBackend");
        if (list != null && list.size() > 0 ) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            return mapper.writeValueAsString(list);
        }
        return null;
    }
}
