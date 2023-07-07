package ensf607.propertysearchengine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


import ensf607.propertysearchengine.user.*;
import ensf607.propertysearchengine.property.*;

@Controller
public class AppController {
 
    @Autowired
    private UserRepository userRepo;
     
    @GetMapping("")
    public String viewHomePage() {
        return "landing";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "signup_form";
    }

    @PostMapping("/process_register")
    public String processRegister(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        
        userRepo.save(user);
        
        return "register_success";
    }

    @GetMapping("/testingoptions")
    public String testingOptions() {
        return "optionslist";
    }

    @GetMapping("/landing")
    public String landing() {
        return "landing";
    }

    @GetMapping("/printalllistings")
    public String alllistings() {
        return "all_listings";
    }

    @GetMapping("/listing_data")
    public String listing() {
        return "listing_data";
    }

    @GetMapping("api/v1/property/add")
    public String addNewProperty(Model model) {
        model.addAttribute("property", new Property());
        return "add_property_form";
    }

    @GetMapping("/favorites")
    public String favorites() {
        return "my_favorites";
    }

    @GetMapping("my_listings")
    public String myListings() {
        return "my_listings";
    }
}