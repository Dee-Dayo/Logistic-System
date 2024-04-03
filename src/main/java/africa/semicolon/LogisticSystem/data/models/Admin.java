package africa.semicolon.LogisticSystem.data.models;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Admin {
    private String name = "Dee Logistics";
    private List<Order> orders = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private Rider[] riders;
    
    public Admin(){
        riders = new Rider[2];

        Rider rider1 = new Rider();
        rider1.setFirstName("Moh");
        rider1.setLastName("Baba");

        Rider rider2 = new Rider();
        rider2.setFirstName("Michael");
        rider2.setLastName("Softlife");

        riders[0] = rider1;
        riders[1] = rider2;
    }
}
