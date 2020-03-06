package fi.fmi.avi.model.SWX.immutable;

import static org.junit.Assert.assertEquals;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import fi.fmi.avi.model.PartialDateTime;
import fi.fmi.avi.model.PartialOrCompleteTimeInstant;
import fi.fmi.avi.model.PhenomenonGeometryWithHeight;
import fi.fmi.avi.model.SWX.NextAdvisory;
import fi.fmi.avi.model.SWX.SWX;
import fi.fmi.avi.model.SWX.SWXAnalysis;
import fi.fmi.avi.model.immutable.CircleByCenterPointImpl;
import fi.fmi.avi.model.immutable.NumericMeasureImpl;
import fi.fmi.avi.model.immutable.PhenomenonGeometryWithHeightImpl;
import fi.fmi.avi.model.immutable.PolygonsGeometryImpl;
import fi.fmi.avi.model.immutable.TacOrGeoGeometryImpl;

public class SWXTest {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @BeforeClass
    public static void setup() {
        OBJECT_MAPPER.registerModule(new Jdk8Module()).registerModule(new JavaTimeModule());
    }

    private AdvisoryNumberImpl getAdvisoryNumber() {
        AdvisoryNumberImpl.Builder advisory = AdvisoryNumberImpl.builder().setYear(2020).setSerialNumber(1);

        return advisory.build();
    }

    private NextAdvisory getNextAdvisory(boolean hasNext) {
        NextAdvisoryImpl.Builder next = NextAdvisoryImpl.builder();

        if (hasNext) {
            PartialOrCompleteTimeInstant nextAdvisoryTime = PartialOrCompleteTimeInstant.of(ZonedDateTime.parse("2020-02-27T01:00Z[UTC]"));
            next.setTime(nextAdvisoryTime);
            next.setTimeSpecifier(NextAdvisory.Type.NEXT_ADVISORY_AT);
        } else {
            next.setTimeSpecifier(NextAdvisory.Type.NO_FURTHER_ADVISORIES);
        }

        return next.build();
    }

    private List<String> getRemarks() {
        List<String> remarks = new ArrayList<>();
        remarks.add("RADIATION LVL EXCEEDED 100 PCT OF BACKGROUND LVL AT FL350 AND ABV. THE CURRENT EVENT HAS PEAKED AND LVL SLW RTN TO BACKGROUND LVL."
                + " SEE WWW.SPACEWEATHERPROVIDER.WEB");

        return remarks;
    }

    private List<SWXAnalysis> getAnalyses(boolean hasObservation) {
        List<SWXAnalysis> analyses = new ArrayList<>();

        int day = 27;
        int hour = 1;

        for (int i = 0; i < 5; i++) {
            SWXAnalysisImpl.Builder analysis = SWXAnalysisImpl.builder();

            if (i == 0 && hasObservation) {
                analysis.setAnalysisType(SWXAnalysis.Type.OBSERVATION);
            } else {
                analysis.setAnalysisType(SWXAnalysis.Type.FORECAST);
            }

            String partialTime = "--" + day + "T" + hour + ":00Z";
            analysis.setAnalysis(getPhenomenon(partialTime));
            analyses.add(analysis.build());

            hour += 6;
            if (hour >= 24) {
                day += 1;
                hour = hour % 24;
            }

        }

        return analyses;
    }

    private PhenomenonGeometryWithHeight getPhenomenon(String partialTime) {
        PolygonsGeometryImpl.Builder polygon = PolygonsGeometryImpl.builder();
        polygon.addAllPolygons(
                Arrays.asList(Arrays.asList(-180.0, -90.0), Arrays.asList(-180.0, -60.0), Arrays.asList(180.0, -60.0), Arrays.asList(180.0, -90.0),
                        Arrays.asList(-180.0, -90.0)));

        PhenomenonGeometryWithHeightImpl.Builder phenomenon = new PhenomenonGeometryWithHeightImpl.Builder().setTime(
                PartialOrCompleteTimeInstant.of(PartialDateTime.parse(partialTime))).setGeometry(TacOrGeoGeometryImpl.of(polygon.build()));

        return phenomenon.build();
    }

    @Test
    public void buildSWXWithCircleByCenterPoint() throws Exception {
        NextAdvisoryImpl.Builder nextAdvisory = NextAdvisoryImpl.builder()
                .setTimeSpecifier(NextAdvisory.Type.NEXT_ADVISORY_BY)
                .setTime(PartialOrCompleteTimeInstant.of(ZonedDateTime.parse("2020-02-27T01:00Z[UTC]")));

        int day = 27;
        int hour = 1;
        String partialTime = "--" + day + "T" + hour + ":00Z";

        NumericMeasureImpl.Builder measure = NumericMeasureImpl.builder().setValue(5409.75).setUom("[nmi_i]");

        CircleByCenterPointImpl.Builder cbcp = CircleByCenterPointImpl.builder()
                .addAllCoordinates(Arrays.asList(-16.6392, 160.9368))
                .setRadius(measure.build());

        PhenomenonGeometryWithHeightImpl.Builder phenomenon = new PhenomenonGeometryWithHeightImpl.Builder().setTime(
                PartialOrCompleteTimeInstant.of(PartialDateTime.parse(partialTime)))
                .setApproximateLocation(false)
                .setGeometry(TacOrGeoGeometryImpl.of(cbcp.build()));

        SWXAnalysisImpl.Builder analysis = SWXAnalysisImpl.builder().setAnalysisType(SWXAnalysis.Type.FORECAST).setAnalysis(phenomenon.build());

        List<SWXAnalysis> analyses = new ArrayList<>();
        analyses.add(analysis.build());
        analyses.add(analysis.build());
        analyses.add(analysis.build());
        analyses.add(analysis.build());
        analyses.add(analysis.build());

        SWXImpl SWXObject = SWXImpl.builder()
                .setTranslated(false)
                .setIssuingCenterName("DONLON")
                .setIssueTime(PartialOrCompleteTimeInstant.builder().setCompleteTime(ZonedDateTime.parse("2020-02-27T01:00Z[UTC]")).build())
                .setStatus(SWX.STATUS.TEST)
                .addAllPhenomena(Arrays.asList("HF COM MOD", "GNSS MOD"))
                .setAdvisoryNumber(getAdvisoryNumber())
                .setReplacementAdvisoryNumber(Optional.empty())
                .addAllAnalyses(analyses)
                .setRemarks(getRemarks())
                .setNextAdvisory(nextAdvisory.build())
                .build();

        Assert.assertEquals(1, SWXObject.getAdvisoryNumber().getSerialNumber());
        Assert.assertEquals(2020, SWXObject.getAdvisoryNumber().getYear());
        Assert.assertEquals(SWXAnalysis.Type.FORECAST, SWXObject.getAnalyses().get(0).getAnalysisType());
        Assert.assertEquals(NextAdvisory.Type.NEXT_ADVISORY_BY, SWXObject.getNextAdvisory().getTimeSpecifier());
        Assert.assertTrue(SWXObject.getNextAdvisory().getTime().isPresent());

        final String serialized = OBJECT_MAPPER.writeValueAsString(SWXObject);
        final SWXImpl deserialized = OBJECT_MAPPER.readValue(serialized, SWXImpl.class);

        assertEquals(SWXObject, deserialized);
    }

    @Test
    public void buildSWXWithoutNextAdvisory() throws Exception {
        SWXImpl SWXObject = SWXImpl.builder()
                .setTranslated(false)
                .setIssuingCenterName("DONLON")
                .setIssueTime(PartialOrCompleteTimeInstant.builder().setCompleteTime(ZonedDateTime.parse("2020-02-27T01:00Z[UTC]")).build())
                .setStatus(SWX.STATUS.TEST)
                .addAllPhenomena(Arrays.asList("HF COM MOD", "GNSS MOD"))
                .setAdvisoryNumber(getAdvisoryNumber())
                .setReplacementAdvisoryNumber(Optional.empty())
                .addAllAnalyses(getAnalyses(true))
                .setRemarks(getRemarks())
                .setNextAdvisory(getNextAdvisory(false))
                .build();

        Assert.assertEquals(1, SWXObject.getAdvisoryNumber().getSerialNumber());
        Assert.assertEquals(2020, SWXObject.getAdvisoryNumber().getYear());
        Assert.assertEquals(SWXAnalysis.Type.OBSERVATION, SWXObject.getAnalyses().get(0).getAnalysisType());
        Assert.assertEquals(5, SWXObject.getAnalyses().size());
        Assert.assertFalse(SWXObject.getNextAdvisory().getTime().isPresent());
        Assert.assertEquals(NextAdvisory.Type.NO_FURTHER_ADVISORIES, SWXObject.getNextAdvisory().getTimeSpecifier());

        final String serialized = OBJECT_MAPPER.writeValueAsString(SWXObject);
        final SWXImpl deserialized = OBJECT_MAPPER.readValue(serialized, SWXImpl.class);

        assertEquals(SWXObject, deserialized);
    }

    @Test
    public void buildSWXWithoutObservation() throws Exception {
        SWXImpl SWXObject = SWXImpl.builder()
                .setTranslated(false)
                .setIssuingCenterName("DONLON")
                .setIssueTime(PartialOrCompleteTimeInstant.builder().setCompleteTime(ZonedDateTime.parse("2020-02-27T01:00Z[UTC]")).build())
                .setStatus(SWX.STATUS.TEST)
                .addAllAnalyses(getAnalyses(false))
                .addAllPhenomena(Arrays.asList("HF COM MOD", "GNSS MOD"))
                .setAdvisoryNumber(getAdvisoryNumber())
                .setReplacementAdvisoryNumber(Optional.empty())
                .setRemarks(getRemarks())
                .setNextAdvisory(getNextAdvisory(false))
                .build();

        Assert.assertEquals(1, SWXObject.getAdvisoryNumber().getSerialNumber());
        Assert.assertEquals(2020, SWXObject.getAdvisoryNumber().getYear());
        Assert.assertEquals(5, SWXObject.getAnalyses().size());

        Assert.assertFalse(SWXObject.getNextAdvisory().getTime().isPresent());
        Assert.assertEquals(NextAdvisory.Type.NO_FURTHER_ADVISORIES, SWXObject.getNextAdvisory().getTimeSpecifier());

        final String serialized = OBJECT_MAPPER.writeValueAsString(SWXObject);
        final SWXImpl deserialized = OBJECT_MAPPER.readValue(serialized, SWXImpl.class);

        assertEquals(SWXObject, deserialized);
    }

    @Test
    public void swxSerializationTest() throws Exception {
        SWXImpl SWXObject = SWXImpl.builder()
                .setIssuingCenterName("DONLON")
                .setTranslated(false)
                .setIssueTime(PartialOrCompleteTimeInstant.builder().setCompleteTime(ZonedDateTime.parse("2020-02-27T01:00Z[UTC]")).build())
                .setStatus(SWX.STATUS.TEST)
                .setReplacementAdvisoryNumber(getAdvisoryNumber())
                .addAllPhenomena(Arrays.asList("HF COM MOD", "GNSS MOD"))
                .setAdvisoryNumber(getAdvisoryNumber())
                .setReplacementAdvisoryNumber(Optional.empty())
                .addAllAnalyses(getAnalyses(true))
                .setRemarks(getRemarks())
                .setNextAdvisory(getNextAdvisory(true))
                .build();

        final String serialized = OBJECT_MAPPER.writeValueAsString(SWXObject);
        final SWXImpl deserialized = OBJECT_MAPPER.readValue(serialized, SWXImpl.class);

        assertEquals(SWXObject, deserialized);
    }
}
