package ru.geekbrains;

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
import ru.geekbrains.model.Brand;

import javax.persistence.EntityManagerFactory;
import java.io.File;


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
                .split(s -> s.delimiters("\n"))
                .<String, Brand>transform(name -> {
                    Brand brand = new Brand();
                    brand.setName(name);
                    return brand;
                })
                //.handle(destDirectory())
                .handle(jpaPersistHandler(), ConsumerEndpointSpec::transactional)
                .get();
    }
}
