package pringboot1st.springboot1;

import java.util.HashMap;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// import com.fasterxml.jackson.core.JsonProcessingException;
// import com.fasterxml.jackson.databind.ObjectMapper;

import pringboot1st.springboot1.DbInterface.MemeInterface;
import pringboot1st.springboot1.config.JwtTokenData;
import pringboot1st.springboot1.models.MemeModal;
import pringboot1st.springboot1.services.ListOfMames;
import pringboot1st.springboot1.user.User;

@RestController
@RequiredArgsConstructor
public class RestApiEndPointController {

    @Autowired
    private MemeModal memeModal;

    private final JwtTokenData jwtTokenData;

    @RequestMapping(value = "/", produces = { "application/json" }, method = RequestMethod.GET)
    public Object landingPage() {
        return (new JSONObject("{'msg':'OK'}")).toString();

    }

    @RequestMapping(value = "/list-memes", produces = { "application/json" }, method = RequestMethod.GET)
    public Object listOfMames() {
        try {
            return (new ListOfMames()).listOfMames();
        } catch (Exception exception) {
            var sendMap = new HashMap<>();
            sendMap.put("msg", exception.getMessage());
            return sendMap;
        }
    }

    @RequestMapping(value = "/set-my-fav-meme", produces = { "application/json" }, method = RequestMethod.POST)
    public Object setMyFavMeme(@RequestBody MemeInterface meme) {

        try {
            var alreadyExistData = memeModal.findByIdName(meme.getIdName());

            // var objMapper = new ObjectMapper();
            // try {
            // var x = objMapper.writeValueAsString(alreadyExistData.get());
            // System.out.println((new JSONObject(x)).get("url"));
            // System.out.println((new JSONObject(alreadyExistData.get())));
            // } catch (JsonProcessingException e) {
            // e.printStackTrace();
            // }

            if (alreadyExistData.isPresent()) {
                return (new JSONObject("{'msg':'Meme already exist'}")).toString();
            }
            meme.setUserId(jwtTokenData.getTokenAuthData().getId());
            var res = memeModal.save(meme);

            // System.out.println(res.getId());
            // System.out.println((new JSONObject(res)).toString());

            return res;

        } catch (Exception exception) {
            var sendMap = new HashMap<>();
            sendMap.put("msg", exception.getMessage());
            return sendMap;
        }
    }

    @RequestMapping(value = "/get-all-fav-meme", produces = { "application/json" }, method = RequestMethod.GET)
    public Object getMyAllFavMeme() {
        try {
            User tokenAuth = jwtTokenData.getTokenAuthData();
            var res = memeModal.findAllByUserId(tokenAuth.getId());
            return res;
        } catch (Exception exception) {
            var sendMap = new HashMap<>();
            sendMap.put("msg", exception.getMessage());
            return sendMap;
        }
    }

    @RequestMapping(value = "/get-fav-meme-byid", produces = { "application/json" }, method = RequestMethod.GET)
    public Object getMyFavMemeById(@RequestParam String idName) {
        try {
            var res = memeModal.findByIdName(idName);
            // var res = memeModal.findById("63cbc17eb61bd94061c641b4");
            // // 63cbc17eb61bd94061c641b4
            if (res.equals(Optional.empty())) {
                return (new JSONObject("{'msg':'No Meme found'}")).toString();
            }

            // System.out.println("ggggggggggg " + (new JSONObject(res.get())));
            return res;
        } catch (Exception exception) {
            var sendMap = new HashMap<>();
            sendMap.put("msg", exception.getMessage());
            return sendMap;
        }

    }

    @RequestMapping(value = "/delete-meme-byid/{idName}", produces = {
            "application/json" }, method = RequestMethod.DELETE)
    public Object deleteMemeById(@PathVariable String idName) {

        try {
            var res = memeModal.findByIdName(idName);
            if (res.isPresent()) {
                memeModal.deleteByIdName(idName);
                return (new JSONObject("{'msg':'Item deleted successfully'}")).toString();
            }
            var resById = memeModal.findById(idName);
            if (resById.isPresent()) {
                memeModal.deleteById(idName);
                return (new JSONObject("{'msg':'Item deleted successfully'}")).toString();
            }
            return (new JSONObject("{'msg':'Item not found'}")).toString();
        } catch (Exception exception) {
            var sendMap = new HashMap<>();
            sendMap.put("msg", exception.getMessage());
            return sendMap;
        }

    }
}
