package Renter_Car.Service.Implement;

import Renter_Car.Models.Brand;
import Renter_Car.Repository.BrandRepository;
import Renter_Car.Service.BrandService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }


    @Override
    public List<Brand> getAllBrands() {
        return new ArrayList<>(brandRepository.findAll());
    }
}
