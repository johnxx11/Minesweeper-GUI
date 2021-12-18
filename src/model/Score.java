package model;

import java.io.Serializable;

/**
 * This class is use to store score history.
 */
public class Score implements Serializable {
    private String username;
    private Integer spentTime;

    public Score() {
    }

    public Score(String username, Integer spentTime) {
        this.username = username;
        this.spentTime = spentTime;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getSpentTime() {
        return spentTime;
    }

    public void setSpentTime(Integer spentTime) {
        this.spentTime = spentTime;
    }

}
