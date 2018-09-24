package sample;

public class StdInstance {

    String RegId,Name,AnsString;
    int correct,incorrect,untouched,Id,Total;

    public int getTotal() {
        return Total;
    }

    public void setTotal(int total) {
        Total = total;
    }

    public StdInstance(String regId, String name) {
        RegId = regId;
        Name = name;
    }

    public StdInstance(String regId, String name, String ansString, int correct, int incorrect, int untouched, int total) {
        RegId = regId;
        Name = name;
        AnsString = ansString;
        this.correct = correct;
        this.incorrect = incorrect;
        this.untouched = untouched;
        Total = total;
    }


    public StdInstance(String regId, int correct, int incorrect, int untouched, int total) {
        RegId = regId;
        this.correct = correct;
        this.incorrect = incorrect;
        this.untouched = untouched;
        Total = total;
    }

    public String getRegId() {
        return RegId;
    }

    public void setRegId(String regId) {
        RegId = regId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getAnsString() {
        return AnsString;
    }

    public void setAnsString(String ansString) {
        AnsString = ansString;
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int correct) {
        this.correct = correct;
    }

    public int getIncorrect() {
        return incorrect;
    }

    public void setIncorrect(int incorrect) {
        this.incorrect = incorrect;
    }

    public int getUntouched() {
        return untouched;
    }

    public void setUntouched(int untouched) {
        this.untouched = untouched;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }
}
