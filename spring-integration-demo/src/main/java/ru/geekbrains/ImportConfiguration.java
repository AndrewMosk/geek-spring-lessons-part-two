package ru.geekbrains;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.ColumnPositionMappingStrategy;
import au.com.bytecode.opencsv.bean.CsvToBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.core.MessageSource;
import org.springframework.integration.dsl.ConsumerEndpointSpec;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.file.FileReadingMessageSource;
import org.springframework.integration.file.transformer.FileToStringTransformer;
//import org.springframework.integration.jdbc.JdbcMessageHandler;
import org.springframework.integration.jpa.dsl.Jpa;
import org.springframework.integration.jpa.dsl.JpaUpdatingOutboundEndpointSpec;
import org.springframework.integration.jpa.support.PersistMode;
import org.springframework.integration.transformer.GenericTransformer;
import ru.geekbrains.model.Brand;

import javax.persistence.EntityManagerFactory;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;


@Configuration
public class ImportConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(ImportConfiguration.class);

    @Autowired
    private final EntityManagerFactory entityManagerFactory;

    @Value("${source.dir.path}")
    private String sourceDirectoryPath;

    @Value("${dest.dir.path}")
    private String destDirectoryPath;

    public ImportConfiguration(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Bean
    public MessageSource<File> sourceDirectory() {
        FileReadingMessageSource messageSource = new FileReadingMessageSource();
        messageSource.setDirectory(new File(sourceDirectoryPath));
        messageSource.setAutoCreateDirectory(true);

        return messageSource;
    }

    @Bean
    public JpaUpdatingOutboundEndpointSpec jpaPersistHandler() {
        return Jpa.outboundAdapter(this.entityManagerFactory)
                .entityClass(Brand.class)
                .persistMode(PersistMode.PERSIST);
    }

    @Bean
    public IntegrationFlow fileMoveFlow() {
        return IntegrationFlows.from(sourceDirectory(), conf -> conf.poller(Pollers.fixedDelay(2000)))
                .filter(msg -> ((File) msg).getName().endsWith(".txt"))
                .transform(new FileToStringTransformer())
//                .split(s -> s.delimiters("\n"))

                .<String, Brand>transform(new GenericTransformer<String, List<Brand>>() {
                    @Override
                    public List<Brand> transform(String name) {
//                        Brand brand = new Brand();
//                        brand.setName(name);
//                        return brand;
                        return parseCsv();

                    }
                })
                .handle(jpaPersistHandler(), ConsumerEndpointSpec::transactional)
                .get();
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private List<Brand>  parseCsv()  {
        CsvToBean csv = new CsvToBean();
        String csvFilename = "F:/User/Andrew/Downloads/data.csv";
        CSVReader csvReader = null;
        try {
            csvReader = new CSVReader(new FileReader(csvFilename));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        List list = csv.parse(setColumnMapping(), csvReader);
        List<Brand> brands = new ArrayList<>();
        for (Object object : list) {
            brands.add((Brand) object);
        }
        return brands;

    }

    private Brand castToBrand(Object object) {
        return (Brand) object;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    private static ColumnPositionMappingStrategy setColumnMapping() {
        ColumnPositionMappingStrategy strategy = new ColumnPositionMappingStrategy();
        strategy.setType(Brand.class);
        String[] columns = new String[] {"name"};
        strategy.setColumnMapping(columns);
        return strategy;
    }
}
