package fi.fmi.avi.model.sigmet;

import java.util.List;
import java.util.Optional;

import fi.fmi.avi.model.NumericMeasure;
import fi.fmi.avi.model.PhenomenonGeometry;
import fi.fmi.avi.model.PhenomenonGeometryWithHeight;
import fi.fmi.avi.model.SIGMETAIRMET;

public interface SIGMET extends SIGMETAIRMET {
    AeronauticalSignificantWeatherPhenomenon getSigmetPhenomenon();

    Optional<SigmetReference> getCancelledReference();

    SigmetAnalysisType getAnalysisType();

    Optional<List<PhenomenonGeometryWithHeight>> getAnalysisGeometries();

    Optional<NumericMeasure> getMovingSpeed();

    Optional<NumericMeasure> getMovingDirection();

    Optional<SigmetIntensityChange> getIntensityChange();

    Optional<List<PhenomenonGeometry>> getForecastGeometries();

    Optional<Boolean> getNoVaExpected(); //Only applicable to ForecastPositionAnalysis

    Optional<VAInfo> getVAInfo(); //If this is present this is a VASigmet
}
