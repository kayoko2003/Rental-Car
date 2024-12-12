package Renter_Car.Controllers;

import Renter_Car.Service.AddressService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.InputStream;
import java.text.Normalizer;
import java.util.List;
import java.util.regex.Pattern;

@Controller
public class AddressController {
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @GetMapping("/addresses")
    @ResponseBody
    public List<String> suggestAddresses(String input) {
        String filePath = "/list of values/Address value list.xls";

        InputStream inputStream = getClass().getResourceAsStream(filePath);

        if (inputStream == null) {
            throw new IllegalArgumentException("File not found: " + filePath);
        }

        List<String> allAddresses = addressService.readAddressesFromExcel(inputStream);

        // Loại bỏ dấu và chuẩn hóa chuỗi nhập
        String normalizedInput = removeAccents(input).toLowerCase();
        // Tách chuỗi nhập thành các từ khóa
        String[] keywords = normalizedInput.split("\\s+");

        return allAddresses.stream()
                .filter(address -> {
                    // Loại bỏ dấu và chuẩn hóa địa chỉ
                    String normalizedAddress = removeAccents(address).toLowerCase();

                    // Kiểm tra xem tất cả từ khóa đều xuất hiện và theo đúng thứ tự
                    int lastIndex = -1; // Chỉ số của từ khóa trước đó đã tìm thấy
                    for (String keyword : keywords) {
                        lastIndex = normalizedAddress.indexOf(keyword, lastIndex + 1); // Tìm từ khóa bắt đầu sau chỉ số lastIndex
                        if (lastIndex == -1) {
                            return false; // Nếu từ khóa không tìm thấy, trả về false
                        }
                    }
                    return true; // Tất cả từ khóa đều xuất hiện theo thứ tự
                })
                .toList();

    }

    @GetMapping("/addresses/city")
    @ResponseBody
    public List<String> suggestCity() {
        String filePath = "public/list of values/Address value list.xls";
        List<String> allAddresses = addressService.getCityFromExcel(filePath);

        List<String> suggestions = allAddresses.stream()
                .toList();

        return suggestions;
    }

    @GetMapping("/addresses/district")
    @ResponseBody
    public List<String> suggestDistrict(@RequestParam String city) {
        String filePath = "public/list of values/Address value list.xls";
        List<String> allAddresses = addressService.getDistrictFromExcel(filePath, city);
        return allAddresses;
    }

    @GetMapping("/addresses/ward")
    @ResponseBody
    public List<String> suggestWard(@RequestParam String district) {
        String filePath = "public/list of values/Address value list.xls";
        List<String> allAddresses = addressService.readWardFromExcel(filePath, district);

        return allAddresses;
    }

    public static String removeAccents(String input) {
        if (input == null) return null;
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        return Pattern.compile("\\p{M}").matcher(normalized).replaceAll("");
    }

}
