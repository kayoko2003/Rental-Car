package Renter_Car.Security;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class CloudinaryConfig {



    @Bean
    public Cloudinary cloudinary() {
        final Map<String, String> options = new HashMap<>();
        options.put("cloud_name", CLOUD_NAME);
        options.put("api_key", API_KEY);
        options.put("api_secret", API_SECRET);
        return new Cloudinary(options);
    }
}
