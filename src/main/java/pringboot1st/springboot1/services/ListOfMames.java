package pringboot1st.springboot1.services;

import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;

public class ListOfMames {
    public String listOfMames() {
        var httpServiceCaller = new RestTemplate();
        String url = "https://api.imgflip.com/get_memes";
        var jsonRes = new JSONObject(httpServiceCaller.getForObject(url, String.class));
        // System.out.println(((Object) jsonRes).getClass().getSimpleName());
        // System.out.println(jsonRes.has("data"));
        // System.out.println(jsonRes.get("data"));
        return jsonRes.get("data").toString();
    }

}
