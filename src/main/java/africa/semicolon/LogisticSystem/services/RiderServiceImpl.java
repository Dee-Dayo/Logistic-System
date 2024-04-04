package africa.semicolon.LogisticSystem.services;

import africa.semicolon.LogisticSystem.data.models.Order;
import africa.semicolon.LogisticSystem.data.models.Rider;
import africa.semicolon.LogisticSystem.data.repositories.RiderRepository;
import africa.semicolon.LogisticSystem.exceptions.RiderNotAvailableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RiderServiceImpl implements RiderService{

    @Autowired
    RiderRepository riderRepository;

    @Override
    public Rider findRider() {
        List<Rider> riders = riderRepository.findAll();
        for (Rider rider : riders) {
            if(rider.isAvailable()) {
                return rider;
            }
        }
        throw new RiderNotAvailableException("No rider available at the moment");
    }

    @Override
    public void assignOrder(Rider rider, Order order) {

    }
}
