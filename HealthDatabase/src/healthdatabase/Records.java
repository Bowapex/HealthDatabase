
package healthdatabase;

import javafx.beans.property.SimpleStringProperty;


public class Records {
    private final SimpleStringProperty date;
    private final SimpleStringProperty time;
    private final SimpleStringProperty glucose;
    private final SimpleStringProperty mealType;
    private final SimpleStringProperty bloodPress;
    private final SimpleStringProperty pulse;
    private final SimpleStringProperty weight;
    private final SimpleStringProperty note;
    private final SimpleStringProperty id;
    
    Records(){
    this.date = new SimpleStringProperty(null);
    this.time = new SimpleStringProperty(null);
    this.glucose = new SimpleStringProperty(null);
    this.mealType = new SimpleStringProperty(null);
    this.bloodPress = new SimpleStringProperty(null);
    this.pulse = new SimpleStringProperty(null);
    this.weight = new SimpleStringProperty(null);
    this.note = new SimpleStringProperty(null);
    this.id = new SimpleStringProperty(null);
    }
    
    Records(String date, String time, String glucose, String mealType, String bloodPress,
            String pulse, String weight){
    this.date = new SimpleStringProperty(date);
    this.time = new SimpleStringProperty(time);
    this.glucose = new SimpleStringProperty(glucose);
    this.mealType = new SimpleStringProperty(mealType);
    this.bloodPress = new SimpleStringProperty(bloodPress);
    this.pulse = new SimpleStringProperty(pulse);
    this.weight = new SimpleStringProperty(weight);
    this.note = new SimpleStringProperty("");
    this.id = new SimpleStringProperty("");
    }
    
    Records(Integer id, String date, String time, String glucose, String mealType, String bloodPress,
            String pulse, String weight){
    this.date = new SimpleStringProperty(date);
    this.time = new SimpleStringProperty(time);
    this.glucose = new SimpleStringProperty(glucose);
    this.mealType = new SimpleStringProperty(mealType);
    this.bloodPress = new SimpleStringProperty(bloodPress);
    this.pulse = new SimpleStringProperty(pulse);
    this.weight = new SimpleStringProperty(weight);
    this.note = new SimpleStringProperty("");
    this.id = new SimpleStringProperty(String.valueOf(id));
    }

    
    
    public String getDate(){
        return date.get();
    }
    
    public void setDate(String setDate){
        this.date.set(setDate);
    }
    
    public String getTime(){
        return time.get();
    }
    
    public void setTime(String setTime){
        this.time.set(setTime);
    }
    
    public String getGlucose(){
        return glucose.get();
    }
    
    public void setGlucose(String setGlucose){
        this.glucose.set(setGlucose);
    }
    
    public String getMealType(){
        return mealType.get();
    }
    
    public void setMealType(String setMealType){
        this.mealType.set(setMealType);
    }
    
    public String getBloodPress(){
        return bloodPress.get();
    }
    
    public void setBloodPress(String setBloodPress){
        this.bloodPress.set(setBloodPress);
    }
    
    public String getPulse(){
        return pulse.get();
    }
    
    public void setPulse(String setPulse){
        this.pulse.set(setPulse);
    }
    
    public String getWeight(){
        return weight.get();
    }
    
    public void setWeight(String setWeight){
        this.weight.set(setWeight);
    }
    
    public String getNote(){
        return note.get();
    }
    
    public void setNote(String setNote){
        this.note.set(setNote);
    }
    
    public String getId(){
        return id.get();
    }
    
    public void setId(String setId){
        this.id.set(setId);
    }
}

