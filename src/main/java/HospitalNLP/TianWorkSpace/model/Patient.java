package HospitalNLP.TianWorkSpace.model;

/**
 * Created by tianjingwei on 16/4/3.
 */
public class Patient {
    private int id;
    private String isLungCanner;
    private String CannerDate;

    public String getCannerDate() {
        return CannerDate;
    }

    public void setCannerDate(String cannerDate) {
        CannerDate = cannerDate;
    }

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
