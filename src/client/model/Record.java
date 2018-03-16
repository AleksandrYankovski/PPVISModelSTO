package client.model;


import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by Александр on 15.09.2017.
 */
public class Record {


    private Car carModel;
    private LocalDate date;
    private LocalTime time;
    private Failure failure;
    private Client client;
    private Integer id;
    private String status;

    public Record() {

        carModel = new Car();
        failure = new Failure();

    }


    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public void setFailure(Failure failure) {
        this.failure = failure;
    }

    public void setCarModel(Car carModel) {
        this.carModel = carModel;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setStatus(Boolean bool) {
        if(bool.equals(true))
          this.status = "paid";
        else
            this.status="unpaid";
    }

    public void setStatus(String status)
    {
        this.status=status;
    }

    public Client getClient() {
        return client;
    }

    public Failure getFailure() {
        return failure;
    }


    public String getStatus() {
        return status;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }

    public Car getCarModel() {
        return carModel;
    }

    public Integer getId() {
        return id;
    }


}
