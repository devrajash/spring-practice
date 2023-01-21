package pringboot1st.springboot1.DbInterface;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class MemeInterface {

    @Id
    private String id;

    @Indexed(unique = true)
    private String idName;

    private String name;
    private Number height;
    private Number width;
    private String url;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHeight(Number height) {
        this.height = height;
    }

    public void setWidth(Number width) {
        this.width = width;
    }

    public String getName() {
        return name;
    }

    public Number getHeight() {
        return height;
    }

    public Number getWidth() {
        return width;
    }

    public void setIdName(String idName) {
        this.idName = idName;
    }

    public String getIdName() {
        return idName;
    }

}
