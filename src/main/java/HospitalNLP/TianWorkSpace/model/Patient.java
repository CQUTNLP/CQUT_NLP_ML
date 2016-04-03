package HospitalNLP.TianWorkSpace.model;

/**
 * Created by tianjingwei on 16/4/3.
 */
public class Patient {
    private int id;
    private String isLungCanner;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getIsLungCanner() {
        return isLungCanner;
    }

    public void setIsLungCanner(String isLungCanner) {
        this.isLungCanner = isLungCanner;
    }
}
