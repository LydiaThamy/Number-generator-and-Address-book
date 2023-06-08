package vttp.ssf.workshop_revision.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.SortedMap;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import vttp.ssf.workshop_revision.model.Contact;

@Service
public class Contacts {

    @Value("${dataDir}")
    private String dataDir;

    public File createFile(Contact regContact) throws IOException {
        File file = new File(/* App. */dataDir + File.separator + regContact.getId());
        file.createNewFile();
        return file;
    }

    public void writeFile(File file, Contact regContact) throws IOException {

        Writer fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(regContact.getName().substring(0, 1).toUpperCase() + regContact.getName().substring(1) + "\n");
        bw.write(regContact.getEmail() + "\n");
        bw.write(regContact.getPhone() + "\n");
        bw.write(regContact.getDob() + "");

        bw.flush();
        bw.close();
    }

    public Optional<Contact> readFile(String id) throws IOException {

        File file = new File(/* App. */dataDir + File.separator + id);

        if (!file.exists()) {
            return Optional.empty();
        }

        Contact reContact = new Contact();
        reContact.setId(id);

        Reader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);

        reContact.setName(br.readLine());
        reContact.setEmail(br.readLine());
        reContact.setPhone(br.readLine());
        reContact.setDob(LocalDate.parse(br.readLine()));

        br.close();

        return Optional.of(reContact);
    }

    public Map<String, String> findContacts() throws IOException {

        SortedMap<String, String> contactsList = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

        File dir = new File(/* App. */dataDir);

        for (String id : dir.list()) {
            String name = readFile(id).get().getName();
            contactsList.put(name, id);
        }

        return contactsList;
    }
}
