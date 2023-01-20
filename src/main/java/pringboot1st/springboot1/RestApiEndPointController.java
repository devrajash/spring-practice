package pringboot1st.springboot1;

import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pringboot1st.springboot1.DbInterface.MemeInterface;
import pringboot1st.springboot1.models.MemeModal;
import pringboot1st.springboot1.services.ListOfMames;

@RestController
public class RestApiEndPointController {

    @Autowired
    private MemeModal memeModal;

    @RequestMapping(value = "/", produces = { "application/json" }, method = RequestMethod.GET)
    public Object landingPage() {
        return (new JSONObject("{'msg':'OK'}")).toString();

    }

    @RequestMapping(value = "/list-memes", produces = { "application/json" }, method = RequestMethod.GET)
    public Object listOfMames() {
        return (new ListOfMames()).listOfMames();
    }

    @RequestMapping(value = "/set-my-fav-meme", produces = { "application/json" }, method = RequestMethod.POST)
    public Object setMyFavMeme(@RequestBody MemeInterface meme) {
        var res = memeModal.save(meme);
        return res;
    }

    @RequestMapping(value = "/get-all-fav-meme", produces = { "application/json" }, method = RequestMethod.GET)
    public Object getMyAllFavMeme() {
        var res = memeModal.findAll();
        return res;
    }

    @RequestMapping(value = "/get-fav-meme-byid", produces = { "application/json" }, method = RequestMethod.GET)
    public Object getMyFavMemeById(@RequestParam String id) {
        var res = memeModal.findById(id);
        if (res.equals(Optional.empty())) {
            return (new JSONObject("{'msg':'No Meme found'}")).toString();
        }
        return res;

    }

    @RequestMapping(value = "/delete-meme-byid/{idMeme}", produces = {
            "application/json" }, method = RequestMethod.DELETE)
    public Object deleteMemeById(@PathVariable String idMeme) {
        var res = memeModal.findById(idMeme);
        if (res.isPresent()) {
            memeModal.deleteById(idMeme);
            return (new JSONObject("{'msg':'Item deleted successfully'}")).toString();
        }
        return (new JSONObject("{'msg':'Item not found'}")).toString();

    }
}
