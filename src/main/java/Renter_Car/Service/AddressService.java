package Renter_Car.Service;
import java.io.InputStream;
import java.util.List;

public interface AddressService {
    List<String> readAddressesFromExcel(InputStream inputStream);
    List<String> getCityFromExcel(String filePath);
    List<String> getDistrictFromExcel(String filePath, String city);
    List<String> readWardFromExcel(String filePath, String district);

}
