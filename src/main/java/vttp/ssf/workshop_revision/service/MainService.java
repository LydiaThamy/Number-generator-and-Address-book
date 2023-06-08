package vttp.ssf.workshop_revision.service;

import java.io.File;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MainService {

    @Value("${dataDir}")
    private String dataDir;

    // String dir = App.dataDir;

    public boolean validateDob(LocalDate dob) {
        LocalDate tenYearsDate = LocalDate.now().minusYears(10);
        LocalDate hundredYearsDate = LocalDate.now().minusYears(100);
        if (dob.isAfter(tenYearsDate) || dob.isBefore(hundredYearsDate)) {
            return false;
        }
        return true;
    }

    public String generateId() {

        // 1. generate random ID
        Random random = new Random();
        StringBuilder sb = new StringBuilder(8);
        for (int i = 0; i < 8; i++) {
            sb.append(Integer.toHexString(random.nextInt(16)));
        }
        String newID = sb.toString();

        // 2. check if ID is unique
        File file = new File(/* App. */dataDir + File.separator + newID);
        if (file.exists()) {

            // if ID is not unique, regenerate the ID
            return generateId();
        }

        return newID;
    }

    public Set<String> getRandomList(int n) {
        Set<String> generatedList = new HashSet<>();

        Random random = new Random();
        generatedList.addAll(random
                .ints(1, 31)
                .distinct()
                .limit(n)
                .boxed() // converts primitive type to super type e.g. int to Integer
                .map(num -> num.toString().concat(".png"))
                .collect(Collectors.toSet()));

        return generatedList;
    }
}
