package client.model;

public class DiscontCard {


    private Integer accumulationPercentage;
    private String id;


    public DiscontCard(String id) {

        this.accumulationPercentage = new Integer(0);
        this.id=id;
    }


    public DiscontCard() {

        this.accumulationPercentage = new Integer(0);
    }

    public void setAccumulationPercentage(Integer accumulationPercentage) {
        this.accumulationPercentage = accumulationPercentage;
    }

    public void icreaseAccumulationPercentage()
    {
        if(accumulationPercentage<40)
          accumulationPercentage++;
    }


    public void setMinustAccumulationPercentage()
    {
        accumulationPercentage=-3;
    }

    public void setId(String id) {
        this.id = id;
    }


    public Integer getAccumulationPercentage() {
        return accumulationPercentage;
    }

    public String getId() {
            return id;
    }

}
