package fi.fmi.avi.model.metar.immutable;

import static java.util.Objects.requireNonNull;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.inferred.freebuilder.FreeBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import fi.fmi.avi.model.Aerodrome;
import fi.fmi.avi.model.NumericMeasure;
import fi.fmi.avi.model.Weather;
import fi.fmi.avi.model.immutable.AerodromeImpl;
import fi.fmi.avi.model.immutable.NumericMeasureImpl;
import fi.fmi.avi.model.immutable.RunwayDirectionImpl;
import fi.fmi.avi.model.immutable.WeatherImpl;
import fi.fmi.avi.model.metar.HorizontalVisibility;
import fi.fmi.avi.model.metar.METAR;
import fi.fmi.avi.model.metar.MeteorologicalTerminalAirReport;
import fi.fmi.avi.model.metar.MeteorologicalTerminalAirReportBuilderHelper;
import fi.fmi.avi.model.metar.ObservedClouds;
import fi.fmi.avi.model.metar.ObservedSurfaceWind;
import fi.fmi.avi.model.metar.RunwayState;
import fi.fmi.avi.model.metar.RunwayVisualRange;
import fi.fmi.avi.model.metar.SPECI;
import fi.fmi.avi.model.metar.SeaState;
import fi.fmi.avi.model.metar.TrendForecast;
import fi.fmi.avi.model.metar.WindShear;

@FreeBuilder
@JsonDeserialize(builder = METARImpl.Builder.class)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonPropertyOrder({ "status", "aerodrome", "issueTime", "automatedStation", "surfaceWind", "visibility", "runwayVisualRanges", "presentWeather", "cloud",
        "airTemperature", "dewpointTemperature", "altimeterSettingQNH", "recentWeather", "windShear", "seaState", "runwayStates", "snowClosure",
        "noSignificantChanges", "trend", "remarks", "permissibleUsage", "permissibleUsageReason", "permissibleUsageSupplementary", "translated",
        "translatedBulletinID", "translatedBulletinReceptionTime", "translationCentreDesignator", "translationCentreName", "translationTime", "translatedTAC" })
public abstract class METARImpl implements METAR, Serializable {

    private static final long serialVersionUID = 5959988117998705772L;

    public static METARImpl immutableCopyOf(final METAR metar) {
        requireNonNull(metar);
        if (metar instanceof METARImpl) {
            return (METARImpl) metar;
        } else {
            return Builder.from(metar).build();
        }
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static Optional<METARImpl> immutableCopyOf(final Optional<METAR> metar) {
        requireNonNull(metar);
        return metar.map(METARImpl::immutableCopyOf);
    }

    @Override
    public abstract Builder toBuilder();

    public static class Builder extends METARImpl_Builder implements MeteorologicalTerminalAirReport.Builder<METARImpl, Builder> {
        public Builder() {
            setTranslated(false);
            setAutomatedStation(false);
            setCeilingAndVisibilityOk(false);
            setRoutineDelayed(false);
            setSnowClosure(false);
            setNoSignificantChanges(false);
        }

        public static Builder from(final METAR value) {
            if (value instanceof METARImpl) {
                return ((METARImpl) value).toBuilder();
            }
            return new METARImpl.Builder().copyFrom(value);
        }

        @Override
        public Builder copyFrom(final MeteorologicalTerminalAirReport value) {
            if (value instanceof METARImpl) {
                return clear().mergeFrom((METARImpl) value);
            }
            MeteorologicalTerminalAirReportBuilderHelper.copyFrom(this, value);
            if (value instanceof METAR) {
                final METAR fromMetar = (METAR) value;
                setRoutineDelayed(fromMetar.isRoutineDelayed());
            }
            return this;
        }

        public SPECI buildAsSPECI() {
            if (isRoutineDelayed()) {
                throw new IllegalStateException("Routine delayed (RTD) is true, cannot build as SPECI");
            }
            return (SPECI) Proxy.newProxyInstance(SPECI.class.getClassLoader(), new Class[] { SPECI.class }, new SPECIInvocationHandler(this.build()));
        }

        public SPECI buildPartialAsSPECI() {
            if (isRoutineDelayed()) {
                throw new IllegalStateException("Routine delayed (RTD) is true, cannot build as SPECI");
            }
            return (SPECI) Proxy.newProxyInstance(SPECI.class.getClassLoader(), new Class[] { SPECI.class }, new SPECIInvocationHandler(this.buildPartial()));
        }


        @Override
        @JsonDeserialize(as = AerodromeImpl.class)
        public Builder setAerodrome(final Aerodrome aerodrome) {
            final Builder retval = super.setAerodrome(aerodrome);
            if (getRunwayStates().isPresent()) {
                final List<RunwayState> oldStates = getRunwayStates().get();
                final List<RunwayState> newStates = new ArrayList<>(oldStates.size());
                for (final RunwayState state : oldStates) {
                    if (state.getRunwayDirection().isPresent()) {
                        if (state.getRunwayDirection().get().getAssociatedAirportHeliport().isPresent()) {
                            final RunwayStateImpl.Builder builder = RunwayStateImpl.immutableCopyOf(state).toBuilder();
                                builder.setRunwayDirection(RunwayDirectionImpl.immutableCopyOf(state.getRunwayDirection().get()).toBuilder()//
                                        .setAssociatedAirportHeliport(aerodrome)
                                        .build());

                                newStates.add(builder.build());
                        }
                    }
                }
                setRunwayStates(newStates);
            }
            if (getRunwayVisualRanges().isPresent()) {
                final List<RunwayVisualRange> oldRanges = getRunwayVisualRanges().get();
                final List<RunwayVisualRange> newRanges = new ArrayList<>(oldRanges.size());
                for (final RunwayVisualRange range : oldRanges) {
                    if (range.getRunwayDirection().getAssociatedAirportHeliport().isPresent()) {
                        final RunwayVisualRangeImpl.Builder builder = RunwayVisualRangeImpl.immutableCopyOf(range).toBuilder();
                        builder.setRunwayDirection(
                                RunwayDirectionImpl.immutableCopyOf(builder.getRunwayDirection()).toBuilder()//
                                        .setAssociatedAirportHeliport(aerodrome)//
                                        .build());
                        newRanges.add(builder.build());
                    }
                }
                setRunwayVisualRanges(newRanges);
            }
            return retval;
        }

        @Override
        @JsonDeserialize(as = NumericMeasureImpl.class)
        public Builder setAirTemperature(final NumericMeasure airTemperature) {
            return super.setAirTemperature(airTemperature);
        }

        @Override
        @JsonDeserialize(as = NumericMeasureImpl.class)
        public Builder setDewpointTemperature(final NumericMeasure dewpointTemperature) {
            return super.setDewpointTemperature(dewpointTemperature);
        }

        @Override
        @JsonDeserialize(as = NumericMeasureImpl.class)
        public Builder setAltimeterSettingQNH(final NumericMeasure altimeterSettingQNH) {
            return super.setAltimeterSettingQNH(altimeterSettingQNH);
        }

        @Override
        @JsonDeserialize(as = ObservedSurfaceWindImpl.class)
        public Builder setSurfaceWind(final ObservedSurfaceWind surfaceWind) {
            return super.setSurfaceWind(surfaceWind);
        }

        @Override
        @JsonDeserialize(as = HorizontalVisibilityImpl.class)
        public Builder setVisibility(final HorizontalVisibility visibility) {
            return super.setVisibility(visibility);
        }

        @Override
        @JsonDeserialize(contentAs = RunwayVisualRangeImpl.class)
        public Builder setRunwayVisualRanges(final List<RunwayVisualRange> runwayVisualRanges) {
            return super.setRunwayVisualRanges(runwayVisualRanges);
        }

        @Override
        @JsonDeserialize(contentAs = WeatherImpl.class)
        public Builder setPresentWeather(final List<Weather> weather) {
            return super.setPresentWeather(weather);
        }

        @Override
        @JsonDeserialize(as = ObservedCloudsImpl.class)
        public Builder setClouds(final ObservedClouds clouds) {
            return super.setClouds(clouds);
        }

        @Override
        @JsonDeserialize(contentAs = WeatherImpl.class)
        public Builder setRecentWeather(final List<Weather> weather) {
            return super.setRecentWeather(weather);
        }

        @Override
        @JsonDeserialize(as = WindShearImpl.class)
        public Builder setWindShear(final WindShear windShear) {
            return super.setWindShear(windShear);
        }

        @Override
        @JsonDeserialize(as = SeaStateImpl.class)
        public Builder setSeaState(final SeaState seaState) {
            return super.setSeaState(seaState);
        }

        @Override
        @JsonDeserialize(contentAs = RunwayStateImpl.class)
        public Builder setRunwayStates(final List<RunwayState> runwayStates) {
            return super.setRunwayStates(runwayStates);
        }

        @Override
        @JsonDeserialize(contentAs = TrendForecastImpl.class)
        public Builder setTrends(final List<TrendForecast> trends) {
            return super.setTrends(trends);
        }
    }

    static class SPECIInvocationHandler implements InvocationHandler {
        private final METARImpl delegate;

        SPECIInvocationHandler(final METARImpl delegate) {
            this.delegate = delegate;
        }

        @Override
        public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
            try {
                final Method delegateMethod = METARImpl.class.getMethod(method.getName(), method.getParameterTypes());
                return delegateMethod.invoke(delegate, args);
            } catch (final NoSuchMethodException nsme) {
                throw new RuntimeException("SPECI method " + method.getName() + "(" + Arrays.toString(method.getParameterTypes()) + ") not implemented by "
                        + METARImpl.class.getSimpleName() + ", cannot delegate. Make sure that " + METARImpl.class.getCanonicalName()
                        + " implements all SPECI methods");
            }
        }

        METARImpl getDelegate() {
            return delegate;
        }

    }

}
