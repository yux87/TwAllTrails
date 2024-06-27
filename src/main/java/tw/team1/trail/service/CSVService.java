package tw.team1.trail.service;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import tw.team1.trail.dao.TrailRepository;
import tw.team1.trail.model.Trail;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Service
public class CSVService {

    @Autowired
    private TrailRepository trailRepository;

    @Autowired
    private ResourceLoader resourceLoader;

    public void uploadCSVFile() {
        try {
            Reader reader = new BufferedReader(new InputStreamReader(
                    resourceLoader.getResource("classpath:/static/file/Trails_final.csv").getInputStream(),
                    StandardCharsets.UTF_8
            ));

            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .withIgnoreHeaderCase()
                    .withTrim());

            Map<String, Integer> headerMap = csvParser.getHeaderMap();
            // Check for BOM in 'tid' header and clean if necessary
            String correctTidHeader = headerMap.keySet().stream()
                    .filter(key -> key.startsWith("\uFEFFtid") || key.equals("tid"))
                    .findFirst()
                    .orElse("tid"); // Use "tid" as default if not found

            for (CSVRecord csvRecord : csvParser) {
                Trail trail = new Trail();
                // Use the correct header name to access the value
                trail.setTid(Long.parseLong(csvRecord.get(correctTidHeader)));
                trail.setTname(csvRecord.get("tname"));
                trail.setTclass(csvRecord.get("tclass"));
                trail.setTmain(csvRecord.get("tmain"));
                trail.setTlength(csvRecord.get("tlength"));
                trail.setTpave(csvRecord.get("tpave"));
                trail.setTlevel(csvRecord.get("tlevel"));
                trail.setTtour(csvRecord.get("ttour"));
                trail.setTseason(csvRecord.get("tseason"));
                trail.setTspecial(csvRecord.get("tspecial"));

                try {
                    trail.setTlat(Double.parseDouble(csvRecord.get("tlat")));
                    trail.setTlng(Double.parseDouble(csvRecord.get("tlng")));
                } catch (NumberFormatException e) {
                    // Handle the exception, e.g., log the error, assign a default value, or skip the record
                    System.err.println("Error parsing latitude value for record: " + csvRecord);
                    // You can assign a default value or skip the record here
                }

                // Continue with other fields

                trailRepository.save(trail);
            }

            reader.close();
        } catch (Exception e) {
            throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
        }

    }
}
