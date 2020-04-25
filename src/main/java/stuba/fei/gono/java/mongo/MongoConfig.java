package stuba.fei.gono.java.mongo;

import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import stuba.fei.gono.java.mongo.converters.OffsetDateTimeReadConverter;
import stuba.fei.gono.java.mongo.converters.OffsetDateTimeWriteConverter;

import java.util.ArrayList;
import java.util.List;

/***
 * Configuration class that modifies default configuration of MongoDB
 */
@Configuration
public class MongoConfig {


    /***
     * Adds custom converters for converting Java Classes that are unable to be serialized / de-serialized by MongoDB
     * to classes that are able to be serialized / de-serialized. Adding conversions from java.time.OffsetDateTime to
     * java.util.Date
     * @return Instance of class MongoCustomConversions instantiated by list of custom MongoDB converters
     * @see MongoCustomConversions
     * @see org.springframework.data.convert.WritingConverter
     * @see org.springframework.data.convert.ReadingConverter
     * @see java.util.Date
     * @see java.time.OffsetDateTime
     */
    @Bean
    public MongoCustomConversions customConversions()
    {
        List<Converter<?,?>> converters = new ArrayList<>();
        converters.add(new OffsetDateTimeReadConverter());
        converters.add(new OffsetDateTimeWriteConverter());
        return new MongoCustomConversions(converters);
    }
}
