package zombi.kampung.pisang22.firestoreuitm;

public class Model {

    String id, title, desc;

    public Model() {}

    // right click -> generate -> constructor
    public Model(String id, String title, String desc) {
        this.id = id;
        this.title = title;
        this.desc = desc;
    }

    // right click -> getter and setter

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
