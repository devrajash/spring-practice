package pringboot1st.springboot1.services;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Service
public class GoogleApiAuth {
    public <k, v> HashMap<k,v> verifyToken(String token) {
        var httpServiceCaller = new RestTemplate();
        String url = "https://www.googleapis.com/oauth2/v3/userinfo?access_token="+token;
        // System.out.println(((Object) jsonRes).getClass().getSimpleName());
        // System.out.println(jsonRes.has("data"));
        // System.out.println(jsonRes.get("data"));
        return httpServiceCaller.getForObject(url, HashMap.class);
    }
}
