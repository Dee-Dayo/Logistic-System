package africa.semicolon.LogisticSystem.services;

import africa.semicolon.LogisticSystem.data.models.Order;
import africa.semicolon.LogisticSystem.data.models.Rider;
import africa.semicolon.LogisticSystem.data.models.User;
import africa.semicolon.LogisticSystem.data.repositories.RiderRepository;
import africa.semicolon.LogisticSystem.exceptions.RiderAlreadyExist;
import africa.semicolon.LogisticSystem.exceptions.RiderNotAvailableException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RiderServiceImpl implements RiderService{

    @Autowired
    RiderRepository riderRepository;

    @Override
    public Rider findRider() {
        List<Rider> riders = riderRepository.findByIsAvailableTrue();
        if(!riders.isEmpty()){
            return riders.get(0);
        }
        throw new RiderNotAvailableException("No rider available at the moment");
    }

    @Override
    public void assignOrder(Rider rider, Order order) {
        order.setIsAssignedTo(rider);
        order.setPending(true);
    }

    @Override
    public int findNoOfAvailableRiders() {
        List<Rider> riders = riderRepository.findByIsAvailableTrue();
        return riders.size();
    }

    @Override
    public void save(Rider rider) {
        if(riderRepository.existsById(rider.getId())) throw new RiderAlreadyExist("Rider already exist");
        riderRepository.save(rider);
    }
}
