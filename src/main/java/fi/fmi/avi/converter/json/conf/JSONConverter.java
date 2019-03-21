package fi.fmi.avi.converter.json.conf;

import fi.fmi.avi.converter.json.*;
import fi.fmi.avi.model.sigmet.AIRMET;
import fi.fmi.avi.model.sigmet.SIGMET;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import fi.fmi.avi.converter.AviMessageSpecificConverter;
import fi.fmi.avi.converter.ConversionSpecification;
import fi.fmi.avi.model.metar.METAR;
import fi.fmi.avi.model.taf.TAF;

/**
 * Spring configuration for Java POJO and JSON conversion.
 */
@Configuration
public class JSONConverter {

    /**
     * Pre-configured spec for {@link TAF} to IWXXM 2.1 XML format TAF document String.
     */
    public static final ConversionSpecification<TAF, String> TAF_POJO_TO_JSON_STRING = new ConversionSpecification<>(TAF.class, String.class,
            null, "TAF, fmi-avi-messageconverter JSON");


    /**
     * Pre-configured spec for {@link TAF} to IWXXM 2.1 XML format TAF document DOM Node.
     */
    public static final ConversionSpecification<METAR, String> METAR_POJO_TO_JSON_STRING = new ConversionSpecification<>(METAR.class, String.class,
            null, "METAR, fmi-avi-messageconverter JSON");

    /**
     * Pre-configured spec for {@link SIGMET} to IWXXM 2.1 XML format TAF document String.
     */
    public static final ConversionSpecification<SIGMET, String> SIGMET_POJO_TO_JSON_STRING = new ConversionSpecification<>(SIGMET.class, String.class,
            null, "SIGMET, fmi-avi-messageconverter JSON");

    /**
     * Pre-configured spec for {@link AIRMET} to IWXXM 2.1 XML format AIRMET document String.
     */
    public static final ConversionSpecification<AIRMET, String> AIRMET_POJO_TO_JSON_STRING = new ConversionSpecification<>(AIRMET.class, String.class,
            null, "SIGMET, fmi-avi-messageconverter JSON");

    /**
     * Pre-configured spec for IWXXM 2.1 XML format TAF document String to {@link TAF}.
     */
    public static final ConversionSpecification<String, TAF> JSON_STRING_TO_TAF_POJO = new ConversionSpecification<>(String.class,TAF.class,
            "TAF, fmi-avi-messageconverter JSON", null);


    /**
     * Pre-configured spec for IWXXM 2.1 XML format TAF document DOM Node to {@link TAF}.
     */
    public static final ConversionSpecification<String, METAR> JSON_STRING_TO_METAR_POJO = new ConversionSpecification<>(String.class, METAR.class,
            "METAR, fmi-avi-messageconverter JSON", null);

    /**
     * Pre-configured spec for IWXXM 2.1 XML format SIGMET document DOM Node to {@link TAF}.
     */
    public static final ConversionSpecification<String, SIGMET> JSON_STRING_TO_SIGMET_POJO = new ConversionSpecification<>(String.class, SIGMET.class,
            "SIGMET, fmi-avi-messageconverter JSON", null);

    /**
     * Pre-configured spec for IWXXM 2.1 XML format AIRMET document DOM Node to {@link AIRMET}
     */
    public static final ConversionSpecification<String, AIRMET> JSON_STRING_TO_AIRMET_POJO = new ConversionSpecification<>(String.class, AIRMET.class,
            "AIRMET, fmi-avi-messageconverter JSON", null);


    @Bean
    public AviMessageSpecificConverter<METAR, String> metarJSONSerializer() {
        return new METARJSONSerializer();
    }

    @Bean
    public AviMessageSpecificConverter<TAF, String> tafJSONSerializer() {
        return new TAFJSONSerializer();
    }

    @Bean
    public AviMessageSpecificConverter<String, TAF> tafJSONParser() {
        return new TAFJSONParser();
    }

    @Bean
    public AviMessageSpecificConverter<String, METAR> metarJSONParser() {
        return new METARJSONParser();
    }

    @Bean
    public AviMessageSpecificConverter<String, SIGMET> sigmetJSONParser() {return new SIGMETJSONParser();}

    @Bean
    public AviMessageSpecificConverter<String, AIRMET> airmetJSONParser() {return new AIRMETJSONParser();}

    @Bean
    public AviMessageSpecificConverter<SIGMET, String> sigmetJSONSerializer() {
        return new SIGMETJSONSerializer();
    }

    @Bean(name = "airmetJSONSerializer")
    public AviMessageSpecificConverter<AIRMET, String> airmetJSONSerializer() {
        return new AIRMETJSONSerializer();
    }

}
