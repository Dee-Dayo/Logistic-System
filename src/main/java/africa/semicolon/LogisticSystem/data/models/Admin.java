package africa.semicolon.LogisticSystem.data.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Data
public class Admin {
    private String name;
    private List<Order> orders = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private List<Rider> riders = new ArrayList<>();
}
