package vttp.ssf.workshop_revision.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.validation.Valid;
import vttp.ssf.workshop_revision.model.Contact;
import vttp.ssf.workshop_revision.service.Contacts;
import vttp.ssf.workshop_revision.service.MainService;

@Controller
@RequestMapping
public class MainController {

    @Autowired
    MainService service;

    @Autowired
    Contacts contacts;

    // 1. landing page
    @GetMapping
    public String getHome() {
        return "home";
    }

    // 2. random number generator
    @GetMapping("/number-generator")
    public String getNumberGenerator() {
        return "number/random-generator";
    }

    @GetMapping("/result")
    public String getRandomImages(@RequestParam int n, Model model) {
        // spring will convert the parameter type string to int to match the above

        // add the number requested in the cache
        model.addAttribute("n", n);

        // run generator service
        Set<String> imageList = service.getRandomList(n);

        // add number list to the model
        model.addAttribute("imageList", imageList);

        return "number/random-images";
    }

    // 3. address book
    @GetMapping("/registration")
    public String registerContact(Model model) {

        model.addAttribute("contact", new Contact());

        return "contact/registration-form";
    }

    @PostMapping("/contact")
    public String submitContact(@ModelAttribute @Valid Contact regContact, BindingResult binding, Model model) {

        // 1. If there's error, return form with error message
        if (binding.hasErrors()) {
            return "contact/registration-form";
        }

        if (!service.validateDob(regContact.getDob())) {
            ObjectError err = new ObjectError("globalError", "You must be between 10 and 100 years old to register");
            binding.addError(err);
            return "contact/registration-form";
        }

        // 2. Generate ID
        regContact.setId(service.generateId());

        // 3. Create a file
        File file;

        try {
            file = contacts.createFile(regContact);
            // 4. Write in the file
            contacts.writeFile(file, regContact);

        } catch (IOException e) {
            e.printStackTrace();
        }

        model.addAttribute("httpStatus",
                "Status Code " + HttpStatus.CREATED + ": You have been successfully registered!");

        return "contact/registration-form";
    }

    @GetMapping("/contact/{id}")
    public String getContact(@PathVariable String id, Model model) throws IOException {

        // read file
        Optional<Contact> opt = contacts.readFile(id);

        if (opt.isEmpty()) {
            model.addAttribute("httpStatus", "Status Code " + HttpStatus.NOT_FOUND + ": The contact has been removed from the address book!");
            model.addAttribute("contactsList", contacts.findContacts());

            return "contact/contacts"; // address book
        }

        Contact reContact = opt.get();

        model.addAttribute("contact", reContact);

        return "contact/contact-details";
    }

    @GetMapping("/address-book")
    public String getAddressBook(Model model) throws IOException {

        // read files and add to model
        model.addAttribute("contactsList", contacts.findContacts());

        return "contact/contacts";
    }

}
