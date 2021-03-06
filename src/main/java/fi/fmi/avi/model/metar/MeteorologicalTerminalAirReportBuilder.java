package fi.fmi.avi.model.metar;

import static java.util.Objects.requireNonNull;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.UnaryOperator;

import javax.annotation.Nullable;

import fi.fmi.avi.model.Aerodrome;
import fi.fmi.avi.model.AviationCodeListUser;
import fi.fmi.avi.model.AviationWeatherMessage;
import fi.fmi.avi.model.NumericMeasure;
import fi.fmi.avi.model.PartialOrCompleteTimeInstant;
import fi.fmi.avi.model.Weather;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public interface MeteorologicalTerminalAirReportBuilder<T extends MeteorologicalTerminalAirReport, B extends MeteorologicalTerminalAirReportBuilder<T, B>> {

    /**
     * Returns a newly-created {@link MeteorologicalTerminalAirReport} based on the contents of the {@code Builder}.
     *
     * @throws IllegalStateException
     *         if any field has not been set
     */
    T build();

    /**
     * Sets all property values using the given {@code MeteorologicalTerminalAirReport} as a template.
     */
    B mergeFrom(T value);

    /**
     * Copies values from the given {@code Builder}. Does not affect any properties not set on the
     * input.
     */
    B mergeFrom(B template);

    /**
     * Copies all property values from the given {@code MeteorologicalTerminalAirReport}.
     * Properties specific to {@link METAR} are copied when {@code value} is an instance of {@code METAR}.
     *
     * @param value
     *         copy source
     *
     * @return this builder
     */
    B copyFrom(MeteorologicalTerminalAirReport value);

    /**
     * Resets the state of this builder.
     */
    B clear();

    default B withCompleteIssueTime(final YearMonth yearMonth) {
        return mapIssueTime((input) -> input.toBuilder().completePartialAt(yearMonth).build());
    }

    default B withCompleteIssueTimeNear(final ZonedDateTime reference) {
        return mapIssueTime((input) -> input.toBuilder().completePartialNear(reference).build());
    }

    default B withCompleteForecastTimes(final YearMonth issueYearMonth, final int issueDay, final int issueHour, final ZoneId tz)
            throws IllegalArgumentException {
        requireNonNull(issueYearMonth, "issueYearMonth");
        requireNonNull(tz, "tz");
        return withCompleteForecastTimes(ZonedDateTime.of(LocalDateTime.of(issueYearMonth.getYear(), issueYearMonth.getMonth(), issueDay, issueHour, 0), tz));
    }

    B withCompleteForecastTimes(ZonedDateTime reference);

    default B withAllTimesComplete(final ZonedDateTime reference) {
        requireNonNull(reference, "reference");
        ZonedDateTime forecastReference = reference;
        if (getIssueTime().isPresent() && getIssueTime().get().getCompleteTime().isPresent()) {
            forecastReference = getIssueTime().get().getCompleteTime().get();
        }
        return withCompleteIssueTimeNear(reference)//
                .withCompleteForecastTimes(forecastReference);
    }

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getRemarks()}.
     *
     * @return this {@code Builder} object
     */
    default B setNullableRemarks(@Nullable final List<String> remarks) {
        if (remarks != null) {
            return setRemarks(remarks);
        } else {
            return clearRemarks();
        }
    }

    /**
     * If the value to be returned by {@link MeteorologicalTerminalAirReport#getRemarks()} is present, replaces it by
     * applying {@code mapper} to it and using the result.
     *
     * <p>If the result is null, clears the value.
     *
     * @return this {@code Builder} object
     *
     * @throws NullPointerException
     *         if {@code mapper} is null
     */
    default B mapRemarks(final UnaryOperator<List<String>> mapper) {
        return setRemarks(getRemarks().map(mapper));
    }

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getRemarks()} to {@link Optional#empty()
     * Optional.empty()}.
     *
     * @return this {@code Builder} object
     */
    B clearRemarks();

    /**
     * Returns the value that will be returned by {@link MeteorologicalTerminalAirReport#getRemarks()}.
     */
    Optional<List<String>> getRemarks();

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getRemarks()}.
     *
     * @return this {@code Builder} object
     *
     * @throws NullPointerException
     *         if {@code remarks} is null
     */
    B setRemarks(List<String> remarks);

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getRemarks()}.
     *
     * @return this {@code Builder} object
     */
    B setRemarks(Optional<? extends List<String>> remarks);

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getPermissibleUsage()}.
     *
     * @return this {@code Builder} object
     */
    default B setNullablePermissibleUsage(@Nullable final AviationCodeListUser.PermissibleUsage permissibleUsage) {
        if (permissibleUsage != null) {
            return setPermissibleUsage(permissibleUsage);
        } else {
            return clearPermissibleUsage();
        }
    }

    /**
     * If the value to be returned by {@link MeteorologicalTerminalAirReport#getPermissibleUsage()} is present, replaces
     * it by applying {@code mapper} to it and using the result.
     *
     * <p>If the result is null, clears the value.
     *
     * @return this {@code Builder} object
     *
     * @throws NullPointerException
     *         if {@code mapper} is null
     */
    default B mapPermissibleUsage(final UnaryOperator<AviationCodeListUser.PermissibleUsage> mapper) {
        return setPermissibleUsage(getPermissibleUsage().map(mapper));
    }

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getPermissibleUsage()} to {@link
     * Optional#empty() Optional.empty()}.
     *
     * @return this {@code Builder} object
     */
    B clearPermissibleUsage();

    /**
     * Returns the value that will be returned by {@link MeteorologicalTerminalAirReport#getPermissibleUsage()}.
     */
    Optional<AviationCodeListUser.PermissibleUsage> getPermissibleUsage();

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getPermissibleUsage()}.
     *
     * @return this {@code Builder} object
     *
     * @throws NullPointerException
     *         if {@code permissibleUsage} is null
     */
    B setPermissibleUsage(AviationCodeListUser.PermissibleUsage permissibleUsage);

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getPermissibleUsage()}.
     *
     * @return this {@code Builder} object
     */
    B setPermissibleUsage(Optional<? extends AviationCodeListUser.PermissibleUsage> permissibleUsage);

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getPermissibleUsageReason()}.
     *
     * @return this {@code Builder} object
     */
    default B setNullablePermissibleUsageReason(@Nullable final AviationCodeListUser.PermissibleUsageReason permissibleUsageReason) {
        if (permissibleUsageReason != null) {
            return setPermissibleUsageReason(permissibleUsageReason);
        } else {
            return clearPermissibleUsageReason();
        }
    }

    /**
     * If the value to be returned by {@link MeteorologicalTerminalAirReport#getPermissibleUsageReason()} is present,
     * replaces it by applying {@code mapper} to it and using the result.
     *
     * <p>If the result is null, clears the value.
     *
     * @return this {@code Builder} object
     *
     * @throws NullPointerException
     *         if {@code mapper} is null
     */
    default B mapPermissibleUsageReason(final UnaryOperator<AviationCodeListUser.PermissibleUsageReason> mapper) {
        return setPermissibleUsageReason(getPermissibleUsageReason().map(mapper));
    }

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getPermissibleUsageReason()} to {@link
     * Optional#empty() Optional.empty()}.
     *
     * @return this {@code Builder} object
     */
    B clearPermissibleUsageReason();

    /**
     * Returns the value that will be returned by {@link MeteorologicalTerminalAirReport#getPermissibleUsageReason()}.
     */
    Optional<AviationCodeListUser.PermissibleUsageReason> getPermissibleUsageReason();

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getPermissibleUsageReason()}.
     *
     * @return this {@code Builder} object
     *
     * @throws NullPointerException
     *         if {@code permissibleUsageReason} is null
     */
    B setPermissibleUsageReason(AviationCodeListUser.PermissibleUsageReason permissibleUsageReason);

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getPermissibleUsageReason()}.
     *
     * @return this {@code Builder} object
     */
    B setPermissibleUsageReason(Optional<? extends AviationCodeListUser.PermissibleUsageReason> permissibleUsageReason);

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getPermissibleUsageSupplementary()}.
     *
     * @return this {@code Builder} object
     */
    default B setNullablePermissibleUsageSupplementary(@Nullable final String permissibleUsageSupplementary) {
        if (permissibleUsageSupplementary != null) {
            return setPermissibleUsageSupplementary(permissibleUsageSupplementary);
        } else {
            return clearPermissibleUsageSupplementary();
        }
    }

    /**
     * If the value to be returned by {@link MeteorologicalTerminalAirReport#getPermissibleUsageSupplementary()} is
     * present, replaces it by applying {@code mapper} to it and using the result.
     *
     * <p>If the result is null, clears the value.
     *
     * @return this {@code Builder} object
     *
     * @throws NullPointerException
     *         if {@code mapper} is null
     */
    default B mapPermissibleUsageSupplementary(final UnaryOperator<String> mapper) {
        return setPermissibleUsageSupplementary(getPermissibleUsageSupplementary().map(mapper));
    }

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getPermissibleUsageSupplementary()} to
     * {@link Optional#empty() Optional.empty()}.
     *
     * @return this {@code Builder} object
     */
    B clearPermissibleUsageSupplementary();

    /**
     * Returns the value that will be returned by {@link
     * MeteorologicalTerminalAirReport#getPermissibleUsageSupplementary()}.
     */
    Optional<String> getPermissibleUsageSupplementary();

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getPermissibleUsageSupplementary()}.
     *
     * @return this {@code Builder} object
     *
     * @throws NullPointerException
     *         if {@code permissibleUsageSupplementary} is null
     */
    B setPermissibleUsageSupplementary(String permissibleUsageSupplementary);

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getPermissibleUsageSupplementary()}.
     *
     * @return this {@code Builder} object
     */
    B setPermissibleUsageSupplementary(Optional<? extends String> permissibleUsageSupplementary);

    /**
     * Replaces the value to be returned by {@link MeteorologicalTerminalAirReport#isTranslated()} by applying {@code
     * mapper} to it and using the result.
     *
     * @return this {@code Builder} object
     *
     * @throws NullPointerException
     *         if {@code mapper} is null or returns null
     * @throws IllegalStateException
     *         if the field has not been set
     */
    default B mapTranslated(final UnaryOperator<Boolean> mapper) {
        Objects.requireNonNull(mapper);
        return setTranslated(mapper.apply(isTranslated()));
    }

    /**
     * Returns the value that will be returned by {@link MeteorologicalTerminalAirReport#isTranslated()}.
     *
     * @throws IllegalStateException
     *         if the field has not been set
     */
    boolean isTranslated();

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#isTranslated()}.
     *
     * @return this {@code Builder} object
     */
    B setTranslated(boolean translated);

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getTranslatedBulletinID()}.
     *
     * @return this {@code Builder} object
     */
    default B setNullableTranslatedBulletinID(@Nullable final String translatedBulletinID) {
        if (translatedBulletinID != null) {
            return setTranslatedBulletinID(translatedBulletinID);
        } else {
            return clearTranslatedBulletinID();
        }
    }

    /**
     * If the value to be returned by {@link MeteorologicalTerminalAirReport#getTranslatedBulletinID()} is present,
     * replaces it by applying {@code mapper} to it and using the result.
     *
     * <p>If the result is null, clears the value.
     *
     * @return this {@code Builder} object
     *
     * @throws NullPointerException
     *         if {@code mapper} is null
     */
    default B mapTranslatedBulletinID(final UnaryOperator<String> mapper) {
        return setTranslatedBulletinID(getTranslatedBulletinID().map(mapper));
    }

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getTranslatedBulletinID()} to {@link
     * Optional#empty() Optional.empty()}.
     *
     * @return this {@code Builder} object
     */
    B clearTranslatedBulletinID();

    /**
     * Returns the value that will be returned by {@link MeteorologicalTerminalAirReport#getTranslatedBulletinID()}.
     */
    Optional<String> getTranslatedBulletinID();

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getTranslatedBulletinID()}.
     *
     * @return this {@code Builder} object
     *
     * @throws NullPointerException
     *         if {@code translatedBulletinID} is null
     */
    B setTranslatedBulletinID(String translatedBulletinID);

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getTranslatedBulletinID()}.
     *
     * @return this {@code Builder} object
     */
    B setTranslatedBulletinID(Optional<? extends String> translatedBulletinID);

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getTranslatedBulletinReceptionTime()}.
     *
     * @return this {@code Builder} object
     */
    default B setNullableTranslatedBulletinReceptionTime(@Nullable final ZonedDateTime translatedBulletinReceptionTime) {
        if (translatedBulletinReceptionTime != null) {
            return setTranslatedBulletinReceptionTime(translatedBulletinReceptionTime);
        } else {
            return clearTranslatedBulletinReceptionTime();
        }
    }

    /**
     * If the value to be returned by {@link MeteorologicalTerminalAirReport#getTranslatedBulletinReceptionTime()} is
     * present, replaces it by applying {@code mapper} to it and using the result.
     *
     * <p>If the result is null, clears the value.
     *
     * @return this {@code Builder} object
     *
     * @throws NullPointerException
     *         if {@code mapper} is null
     */
    default B mapTranslatedBulletinReceptionTime(final UnaryOperator<ZonedDateTime> mapper) {
        return setTranslatedBulletinReceptionTime(getTranslatedBulletinReceptionTime().map(mapper));
    }

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getTranslatedBulletinReceptionTime()} to
     * {@link Optional#empty() Optional.empty()}.
     *
     * @return this {@code Builder} object
     */
    B clearTranslatedBulletinReceptionTime();

    /**
     * Returns the value that will be returned by {@link
     * MeteorologicalTerminalAirReport#getTranslatedBulletinReceptionTime()}.
     */
    Optional<ZonedDateTime> getTranslatedBulletinReceptionTime();

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getTranslatedBulletinReceptionTime()}.
     *
     * @return this {@code Builder} object
     *
     * @throws NullPointerException
     *         if {@code translatedBulletinReceptionTime} is null
     */
    B setTranslatedBulletinReceptionTime(ZonedDateTime translatedBulletinReceptionTime);

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getTranslatedBulletinReceptionTime()}.
     *
     * @return this {@code Builder} object
     */
    B setTranslatedBulletinReceptionTime(Optional<? extends ZonedDateTime> translatedBulletinReceptionTime);

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getTranslationCentreDesignator()}.
     *
     * @return this {@code Builder} object
     */
    default B setNullableTranslationCentreDesignator(@Nullable final String translationCentreDesignator) {
        if (translationCentreDesignator != null) {
            return setTranslationCentreDesignator(translationCentreDesignator);
        } else {
            return clearTranslationCentreDesignator();
        }
    }

    /**
     * If the value to be returned by {@link MeteorologicalTerminalAirReport#getTranslationCentreDesignator()} is
     * present, replaces it by applying {@code mapper} to it and using the result.
     *
     * <p>If the result is null, clears the value.
     *
     * @return this {@code Builder} object
     *
     * @throws NullPointerException
     *         if {@code mapper} is null
     */
    default B mapTranslationCentreDesignator(final UnaryOperator<String> mapper) {
        return setTranslationCentreDesignator(getTranslationCentreDesignator().map(mapper));
    }

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getTranslationCentreDesignator()} to
     * {@link Optional#empty() Optional.empty()}.
     *
     * @return this {@code Builder} object
     */
    B clearTranslationCentreDesignator();

    /**
     * Returns the value that will be returned by {@link
     * MeteorologicalTerminalAirReport#getTranslationCentreDesignator()}.
     */
    Optional<String> getTranslationCentreDesignator();

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getTranslationCentreDesignator()}.
     *
     * @return this {@code Builder} object
     *
     * @throws NullPointerException
     *         if {@code translationCentreDesignator} is null
     */
    B setTranslationCentreDesignator(String translationCentreDesignator);

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getTranslationCentreDesignator()}.
     *
     * @return this {@code Builder} object
     */
    B setTranslationCentreDesignator(Optional<? extends String> translationCentreDesignator);

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getTranslationCentreName()}.
     *
     * @return this {@code Builder} object
     */
    default B setNullableTranslationCentreName(@Nullable final String translationCentreName) {
        if (translationCentreName != null) {
            return setTranslationCentreName(translationCentreName);
        } else {
            return clearTranslationCentreName();
        }
    }

    /**
     * If the value to be returned by {@link MeteorologicalTerminalAirReport#getTranslationCentreName()} is present,
     * replaces it by applying {@code mapper} to it and using the result.
     *
     * <p>If the result is null, clears the value.
     *
     * @return this {@code Builder} object
     *
     * @throws NullPointerException
     *         if {@code mapper} is null
     */
    default B mapTranslationCentreName(final UnaryOperator<String> mapper) {
        return setTranslationCentreName(getTranslationCentreName().map(mapper));
    }

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getTranslationCentreName()} to {@link
     * Optional#empty() Optional.empty()}.
     *
     * @return this {@code Builder} object
     */
    B clearTranslationCentreName();

    /**
     * Returns the value that will be returned by {@link MeteorologicalTerminalAirReport#getTranslationCentreName()}.
     */
    Optional<String> getTranslationCentreName();

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getTranslationCentreName()}.
     *
     * @return this {@code Builder} object
     *
     * @throws NullPointerException
     *         if {@code translationCentreName} is null
     */
    B setTranslationCentreName(String translationCentreName);

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getTranslationCentreName()}.
     *
     * @return this {@code Builder} object
     */
    B setTranslationCentreName(Optional<? extends String> translationCentreName);

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getTranslationTime()}.
     *
     * @return this {@code Builder} object
     */
    default B setNullableTranslationTime(@Nullable final ZonedDateTime translationTime) {
        if (translationTime != null) {
            return setTranslationTime(translationTime);
        } else {
            return clearTranslationTime();
        }
    }

    /**
     * If the value to be returned by {@link MeteorologicalTerminalAirReport#getTranslationTime()} is present, replaces
     * it by applying {@code mapper} to it and using the result.
     *
     * <p>If the result is null, clears the value.
     *
     * @return this {@code Builder} object
     *
     * @throws NullPointerException
     *         if {@code mapper} is null
     */
    default B mapTranslationTime(final UnaryOperator<ZonedDateTime> mapper) {
        return setTranslationTime(getTranslationTime().map(mapper));
    }

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getTranslationTime()} to {@link
     * Optional#empty() Optional.empty()}.
     *
     * @return this {@code Builder} object
     */
    B clearTranslationTime();

    /**
     * Returns the value that will be returned by {@link MeteorologicalTerminalAirReport#getTranslationTime()}.
     */
    Optional<ZonedDateTime> getTranslationTime();

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getTranslationTime()}.
     *
     * @return this {@code Builder} object
     *
     * @throws NullPointerException
     *         if {@code translationTime} is null
     */
    B setTranslationTime(ZonedDateTime translationTime);

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getTranslationTime()}.
     *
     * @return this {@code Builder} object
     */
    B setTranslationTime(Optional<? extends ZonedDateTime> translationTime);

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getTranslatedTAC()}.
     *
     * @return this {@code Builder} object
     */
    default B setNullableTranslatedTAC(@Nullable final String translatedTAC) {
        if (translatedTAC != null) {
            return setTranslatedTAC(translatedTAC);
        } else {
            return clearTranslatedTAC();
        }
    }

    /**
     * If the value to be returned by {@link MeteorologicalTerminalAirReport#getTranslatedTAC()} is present, replaces it
     * by applying {@code mapper} to it and using the result.
     *
     * <p>If the result is null, clears the value.
     *
     * @return this {@code Builder} object
     *
     * @throws NullPointerException
     *         if {@code mapper} is null
     */
    default B mapTranslatedTAC(final UnaryOperator<String> mapper) {
        return setTranslatedTAC(getTranslatedTAC().map(mapper));
    }

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getTranslatedTAC()} to {@link
     * Optional#empty() Optional.empty()}.
     *
     * @return this {@code Builder} object
     */
    B clearTranslatedTAC();

    /**
     * Returns the value that will be returned by {@link MeteorologicalTerminalAirReport#getTranslatedTAC()}.
     */
    Optional<String> getTranslatedTAC();

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getTranslatedTAC()}.
     *
     * @return this {@code Builder} object
     *
     * @throws NullPointerException
     *         if {@code translatedTAC} is null
     */
    B setTranslatedTAC(String translatedTAC);

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getTranslatedTAC()}.
     *
     * @return this {@code Builder} object
     */
    B setTranslatedTAC(Optional<? extends String> translatedTAC);

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getIssueTime()}.
     *
     * @return this {@code Builder} object
     */
    default B setNullableIssueTime(@Nullable final PartialOrCompleteTimeInstant issueTime) {
        if (issueTime != null) {
            return setIssueTime(issueTime);
        } else {
            return clearIssueTime();
        }
    }

    /**
     * If the value to be returned by {@link MeteorologicalTerminalAirReport#getIssueTime()} is present, replaces it by
     * applying {@code mapper} to it and using the result.
     *
     * <p>If the result is null, clears the value.
     *
     * @return this {@code Builder} object
     *
     * @throws NullPointerException
     *         if {@code mapper} is null
     */
    default B mapIssueTime(final UnaryOperator<PartialOrCompleteTimeInstant> mapper) {
        Objects.requireNonNull(mapper);
        return setIssueTime(getIssueTime().map(mapper));
    }

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getIssueTime()} to {@link Optional#empty()
     * Optional.empty()}.
     *
     * @return this {@code Builder} object
     */
    B clearIssueTime();

    /**
     * Returns the value that will be returned by {@link MeteorologicalTerminalAirReport#getIssueTime()}.
     */
    Optional<PartialOrCompleteTimeInstant> getIssueTime();

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getIssueTime()}.
     *
     * @return this {@code Builder} object
     *
     * @throws NullPointerException
     *         if {@code issueTime} is null
     */
    B setIssueTime(PartialOrCompleteTimeInstant issueTime);

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getIssueTime()}.
     *
     * @return this {@code Builder} object
     */
    B setIssueTime(Optional<? extends PartialOrCompleteTimeInstant> issueTime);

    /**
     * Replaces the value to be returned by {@link MeteorologicalTerminalAirReport#getReportStatus()} by applying {@code
     * mapper} to it and using the result.
     *
     * @return this {@code Builder} object
     *
     * @throws NullPointerException
     *         if {@code mapper} is null or returns null
     */
    default B mapReportStatus(final UnaryOperator<AviationWeatherMessage.ReportStatus> mapper) {
        return setReportStatus(mapper.apply(getReportStatus()));
    }

    /**
     * Returns the value that will be returned by {@link MeteorologicalTerminalAirReport#getReportStatus()}.
     */
    AviationWeatherMessage.ReportStatus getReportStatus();

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getReportStatus()}.
     *
     * @return this {@code Builder} object
     *
     * @throws NullPointerException
     *         if {@code reportStatus} is null
     */
    B setReportStatus(AviationWeatherMessage.ReportStatus reportStatus);

    /**
     * Replaces the value to be returned by {@link MeteorologicalTerminalAirReport#getAerodrome()} by applying {@code
     * mapper} to it and using the result.
     *
     * @return this {@code Builder} object
     *
     * @throws NullPointerException
     *         if {@code mapper} is null or returns null
     * @throws IllegalStateException
     *         if the field has not been set
     */
    default B mapAerodrome(final UnaryOperator<Aerodrome> mapper) {
        Objects.requireNonNull(mapper);
        return setAerodrome(mapper.apply(getAerodrome()));
    }

    /**
     * Returns the value that will be returned by {@link MeteorologicalTerminalAirReport#getAerodrome()}.
     *
     * @throws IllegalStateException
     *         if the field has not been set
     */
    Aerodrome getAerodrome();

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getAerodrome()}.
     *
     * @return this {@code Builder} object
     *
     * @throws NullPointerException
     *         if {@code aerodrome} is null
     */
    B setAerodrome(Aerodrome aerodrome);

    /**
     * Replaces the value to be returned by {@link MeteorologicalTerminalAirReport#isAutomatedStation()} by applying
     * {@code mapper} to it and using the result.
     *
     * @return this {@code Builder} object
     *
     * @throws NullPointerException
     *         if {@code mapper} is null or returns null
     * @throws IllegalStateException
     *         if the field has not been set
     */
    default B mapAutomatedStation(final UnaryOperator<Boolean> mapper) {
        Objects.requireNonNull(mapper);
        return setAutomatedStation(mapper.apply(isAutomatedStation()));
    }

    /**
     * Returns the value that will be returned by {@link MeteorologicalTerminalAirReport#isAutomatedStation()}.
     *
     * @throws IllegalStateException
     *         if the field has not been set
     */
    boolean isAutomatedStation();

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#isAutomatedStation()}.
     *
     * @return this {@code Builder} object
     */
    B setAutomatedStation(boolean automatedStation);

    /**
     * Replaces the value to be returned by {@link MeteorologicalTerminalAirReport#getStatus()} by applying {@code
     * mapper} to it and using the result.
     *
     * @return this {@code Builder} object
     *
     * @throws NullPointerException
     *         if {@code mapper} is null or returns null
     * @throws IllegalStateException
     *         if the field has not been set
     */
    @Deprecated
    default B mapStatus(final UnaryOperator<AviationCodeListUser.MetarStatus> mapper) {
        Objects.requireNonNull(mapper);
        return setStatus(mapper.apply(getStatus()));
    }

    /**
     * Provides the current builder value of the status property.
     *
     * Note, this method is provided for backward compatibility with previous versions of the API. The <code>status</code> is no longer
     * explicitly stored. This implementation uses {@link AviationCodeListUser.MetarStatus#fromReportStatus(AviationWeatherMessage.ReportStatus, boolean)}
     * instead to determine the returned value on-the-fly.
     *
     * @return the message status
     *
     * @deprecated migrate to using a combination of {@link #getReportStatus()} and {@link #isMissingMessage()} instead
     */
    @Deprecated
    default AviationCodeListUser.MetarStatus getStatus() {
        return AviationCodeListUser.MetarStatus.fromReportStatus(getReportStatus(), isMissingMessage());
    }

    /**
     * Sets the METAR-specific message status.
     *
     * Note, this method is provided for backward compatibility with previous versions of the API. The <code>status</code> is no longer
     * explicitly stored. Instead, this method sets other property values with the following logic:
     * <dl>
     *     <dt>{@link fi.fmi.avi.model.AviationCodeListUser.MetarStatus#MISSING MISSING}</dt>
     *     <dd>
     *         <code>reportStatus = {@link fi.fmi.avi.model.AviationWeatherMessage.ReportStatus#NORMAL NORMAL}</code><br>
     *         <code>missingMessage = {@code true}</code>
     *     </dd>
     *
     *     <dt>{@link fi.fmi.avi.model.AviationCodeListUser.MetarStatus#NORMAL NORMAL}</dt>
     *     <dd>
     *         <code>reportStatus = {@link fi.fmi.avi.model.AviationWeatherMessage.ReportStatus#NORMAL NORMAL}</code><br>
     *         <code>missingMessage = {@code false}</code>
     *     </dd>
     *
     *     <dt>{@link fi.fmi.avi.model.AviationCodeListUser.MetarStatus#CORRECTION CORRECTION}</dt>
     *     <dd>
     *         <code>reportStatus = {@link fi.fmi.avi.model.AviationWeatherMessage.ReportStatus#CORRECTION CORRECTION}</code><br>
     *         <code>missingMessage = {@code false}</code>
     *     </dd>
     * </dl>
     *
     * @param status
     *         the status to set
     *
     * @return builder
     *
     * @deprecated migrate to using a combination of {@link #setReportStatus(AviationWeatherMessage.ReportStatus)} and {@link #setMissingMessage(boolean)}.
     */
    @Deprecated
    default B setStatus(final AviationCodeListUser.MetarStatus status) {
        requireNonNull(status);
        return setMissingMessage(status.isMissingMessage())//
                .setReportStatus(status.getReportStatus());
    }

    /**
     * Replaces the value to be returned by {@link MeteorologicalTerminalAirReport#isMissingMessage()} by applying
     * {@code mapper} to it and using the result.
     *
     * @return this {@code Builder} object
     *
     * @throws NullPointerException
     *         if {@code mapper} is null or returns null
     * @throws IllegalStateException
     *         if the field has not been set
     */
    default B mapMissingMessage(final UnaryOperator<Boolean> mapper) {
        Objects.requireNonNull(mapper);
        return setMissingMessage(mapper.apply(isMissingMessage()));
    }

    /**
     * Returns the value that will be returned by {@link MeteorologicalTerminalAirReport#isMissingMessage()}.
     *
     * @throws IllegalStateException
     *         if the field has not been set
     */
    boolean isMissingMessage();

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#isMissingMessage()}.
     *
     * @return this {@code Builder} object
     */
    B setMissingMessage(boolean missingMessage);

    /**
     * Replaces the value to be returned by {@link MeteorologicalTerminalAirReport#isCeilingAndVisibilityOk()} by
     * applying {@code mapper} to it and using the result.
     *
     * @return this {@code Builder} object
     *
     * @throws NullPointerException
     *         if {@code mapper} is null or returns null
     * @throws IllegalStateException
     *         if the field has not been set
     */
    default B mapCeilingAndVisibilityOk(final UnaryOperator<Boolean> mapper) {
        Objects.requireNonNull(mapper);
        return setCeilingAndVisibilityOk(mapper.apply(isCeilingAndVisibilityOk()));
    }

    /**
     * Returns the value that will be returned by {@link MeteorologicalTerminalAirReport#isCeilingAndVisibilityOk()}.
     *
     * @throws IllegalStateException
     *         if the field has not been set
     */
    boolean isCeilingAndVisibilityOk();

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#isCeilingAndVisibilityOk()}.
     *
     * @return this {@code Builder} object
     */
    B setCeilingAndVisibilityOk(boolean ceilingAndVisibilityOk);

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getAirTemperature()}.
     *
     * @return this {@code Builder} object
     */
    default B setNullableAirTemperature(@Nullable final NumericMeasure airTemperature) {
        if (airTemperature != null) {
            return setAirTemperature(airTemperature);
        } else {
            return clearAirTemperature();
        }
    }

    /**
     * If the value to be returned by {@link MeteorologicalTerminalAirReport#getAirTemperature()} is present, replaces it
     * by applying {@code mapper} to it and using the result.
     *
     * <p>If the result is null, clears the value.
     *
     * @return this {@code Builder} object
     *
     * @throws NullPointerException
     *         if {@code mapper} is null
     */
    default B mapAirTemperature(final UnaryOperator<NumericMeasure> mapper) {
        return setAirTemperature(getAirTemperature().map(mapper));
    }

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getAirTemperature()} to {@link
     * Optional#empty() Optional.empty()}.
     *
     * @return this {@code Builder} object
     */
    B clearAirTemperature();

    /**
     * Returns the value that will be returned by {@link MeteorologicalTerminalAirReport#getAirTemperature()}.
     */
    Optional<NumericMeasure> getAirTemperature();

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getAirTemperature()}.
     *
     * @return this {@code Builder} object
     *
     * @throws NullPointerException
     *         if {@code airTemperature} is null
     */
    B setAirTemperature(NumericMeasure airTemperature);

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getAirTemperature()}.
     *
     * @return this {@code Builder} object
     */
    B setAirTemperature(Optional<? extends NumericMeasure> airTemperature);

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getDewpointTemperature()}.
     *
     * @return this {@code Builder} object
     */
    default B setNullableDewpointTemperature(@Nullable final NumericMeasure dewpointTemperature) {
        if (dewpointTemperature != null) {
            return setDewpointTemperature(dewpointTemperature);
        } else {
            return clearDewpointTemperature();
        }
    }

    /**
     * If the value to be returned by {@link MeteorologicalTerminalAirReport#getDewpointTemperature()} is present,
     * replaces it by applying {@code mapper} to it and using the result.
     *
     * <p>If the result is null, clears the value.
     *
     * @return this {@code Builder} object
     *
     * @throws NullPointerException
     *         if {@code mapper} is null
     */
    default B mapDewpointTemperature(final UnaryOperator<NumericMeasure> mapper) {
        return setDewpointTemperature(getDewpointTemperature().map(mapper));
    }

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getDewpointTemperature()} to {@link
     * Optional#empty() Optional.empty()}.
     *
     * @return this {@code Builder} object
     */
    B clearDewpointTemperature();

    /**
     * Returns the value that will be returned by {@link MeteorologicalTerminalAirReport#getDewpointTemperature()}.
     */
    Optional<NumericMeasure> getDewpointTemperature();

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getDewpointTemperature()}.
     *
     * @return this {@code Builder} object
     *
     * @throws NullPointerException
     *         if {@code dewpointTemperature} is null
     */
    B setDewpointTemperature(NumericMeasure dewpointTemperature);

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getDewpointTemperature()}.
     *
     * @return this {@code Builder} object
     */
    B setDewpointTemperature(Optional<? extends NumericMeasure> dewpointTemperature);

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getAltimeterSettingQNH()}.
     *
     * @return this {@code Builder} object
     */
    default B setNullableAltimeterSettingQNH(@Nullable final NumericMeasure altimeterSettingQNH) {
        if (altimeterSettingQNH != null) {
            return setAltimeterSettingQNH(altimeterSettingQNH);
        } else {
            return clearAltimeterSettingQNH();
        }
    }

    /**
     * If the value to be returned by {@link MeteorologicalTerminalAirReport#getAltimeterSettingQNH()} is present,
     * replaces it by applying {@code mapper} to it and using the result.
     *
     * <p>If the result is null, clears the value.
     *
     * @return this {@code Builder} object
     *
     * @throws NullPointerException
     *         if {@code mapper} is null
     */
    default B mapAltimeterSettingQNH(final UnaryOperator<NumericMeasure> mapper) {
        return setAltimeterSettingQNH(getAltimeterSettingQNH().map(mapper));
    }

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getAltimeterSettingQNH()} to {@link
     * Optional#empty() Optional.empty()}.
     *
     * @return this {@code Builder} object
     */
    B clearAltimeterSettingQNH();

    /**
     * Returns the value that will be returned by {@link MeteorologicalTerminalAirReport#getAltimeterSettingQNH()}.
     */
    Optional<NumericMeasure> getAltimeterSettingQNH();

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getAltimeterSettingQNH()}.
     *
     * @return this {@code Builder} object
     *
     * @throws NullPointerException
     *         if {@code altimeteSettingQNH} is null
     */
    B setAltimeterSettingQNH(NumericMeasure altimeterSettingQNH);

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getAltimeterSettingQNH()}.
     *
     * @return this {@code Builder} object
     */
    B setAltimeterSettingQNH(Optional<? extends NumericMeasure> altimeterSettingQNH);

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getSurfaceWind()}.
     *
     * @return this {@code Builder} object
     */
    default B setNullableSurfaceWind(@Nullable final ObservedSurfaceWind surfaceWind) {
        if (surfaceWind != null) {
            return setSurfaceWind(surfaceWind);
        } else {
            return clearSurfaceWind();
        }
    }

    /**
     * If the value to be returned by {@link MeteorologicalTerminalAirReport#getSurfaceWind()} is present, replaces it by
     * applying {@code mapper} to it and using the result.
     *
     * <p>If the result is null, clears the value.
     *
     * @return this {@code Builder} object
     *
     * @throws NullPointerException
     *         if {@code mapper} is null
     */
    default B mapSurfaceWind(final UnaryOperator<ObservedSurfaceWind> mapper) {
        return setSurfaceWind(getSurfaceWind().map(mapper));
    }

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getSurfaceWind()} to {@link
     * Optional#empty() Optional.empty()}.
     *
     * @return this {@code Builder} object
     */
    B clearSurfaceWind();

    /**
     * Returns the value that will be returned by {@link MeteorologicalTerminalAirReport#getSurfaceWind()}.
     */
    Optional<ObservedSurfaceWind> getSurfaceWind();

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getSurfaceWind()}.
     *
     * @return this {@code Builder} object
     *
     * @throws NullPointerException
     *         if {@code surfaceWind} is null
     */
    B setSurfaceWind(ObservedSurfaceWind surfaceWind);

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getSurfaceWind()}.
     *
     * @return this {@code Builder} object
     */
    B setSurfaceWind(Optional<? extends ObservedSurfaceWind> surfaceWind);

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getVisibility()}.
     *
     * @return this {@code Builder} object
     */
    default B setNullableVisibility(@Nullable final HorizontalVisibility visibility) {
        if (visibility != null) {
            return setVisibility(visibility);
        } else {
            return clearVisibility();
        }
    }

    /**
     * If the value to be returned by {@link MeteorologicalTerminalAirReport#getVisibility()} is present, replaces it by
     * applying {@code mapper} to it and using the result.
     *
     * <p>If the result is null, clears the value.
     *
     * @return this {@code Builder} object
     *
     * @throws NullPointerException
     *         if {@code mapper} is null
     */
    default B mapVisibility(final UnaryOperator<HorizontalVisibility> mapper) {
        return setVisibility(getVisibility().map(mapper));
    }

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getVisibility()} to {@link
     * Optional#empty() Optional.empty()}.
     *
     * @return this {@code Builder} object
     */
    B clearVisibility();

    /**
     * Returns the value that will be returned by {@link MeteorologicalTerminalAirReport#getVisibility()}.
     */
    Optional<HorizontalVisibility> getVisibility();

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getVisibility()}.
     *
     * @return this {@code Builder} object
     *
     * @throws NullPointerException
     *         if {@code visibility} is null
     */
    B setVisibility(HorizontalVisibility visibility);

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getVisibility()}.
     *
     * @return this {@code Builder} object
     */
    B setVisibility(Optional<? extends HorizontalVisibility> visibility);

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getRunwayVisualRanges()}.
     *
     * @return this {@code Builder} object
     */
    default B setNullableRunwayVisualRanges(@Nullable final List<RunwayVisualRange> runwayVisualRanges) {
        if (runwayVisualRanges != null) {
            return setRunwayVisualRanges(runwayVisualRanges);
        } else {
            return clearRunwayVisualRanges();
        }
    }

    /**
     * If the value to be returned by {@link MeteorologicalTerminalAirReport#getRunwayVisualRanges()} is present,
     * replaces it by applying {@code mapper} to it and using the result.
     *
     * <p>If the result is null, clears the value.
     *
     * @return this {@code Builder} object
     *
     * @throws NullPointerException
     *         if {@code mapper} is null
     */
    default B mapRunwayVisualRanges(final UnaryOperator<List<RunwayVisualRange>> mapper) {
        return setRunwayVisualRanges(getRunwayVisualRanges().map(mapper));
    }

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getRunwayVisualRanges()} to {@link
     * Optional#empty() Optional.empty()}.
     *
     * @return this {@code Builder} object
     */
    B clearRunwayVisualRanges();

    /**
     * Returns the value that will be returned by {@link MeteorologicalTerminalAirReport#getRunwayVisualRanges()}.
     */
    Optional<List<RunwayVisualRange>> getRunwayVisualRanges();

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getRunwayVisualRanges()}.
     *
     * @return this {@code Builder} object
     *
     * @throws NullPointerException
     *         if {@code runwayVisualRanges} is null
     */
    B setRunwayVisualRanges(List<RunwayVisualRange> runwayVisualRanges);

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getRunwayVisualRanges()}.
     *
     * @return this {@code Builder} object
     */
    B setRunwayVisualRanges(Optional<? extends List<RunwayVisualRange>> runwayVisualRanges);

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getPresentWeather()}.
     *
     * @return this {@code Builder} object
     */
    default B setNullablePresentWeather(@Nullable final List<Weather> presentWeather) {
        if (presentWeather != null) {
            return setPresentWeather(presentWeather);
        } else {
            return clearPresentWeather();
        }
    }

    /**
     * If the value to be returned by {@link MeteorologicalTerminalAirReport#getPresentWeather()} is present, replaces it
     * by applying {@code mapper} to it and using the result.
     *
     * <p>If the result is null, clears the value.
     *
     * @return this {@code Builder} object
     *
     * @throws NullPointerException
     *         if {@code mapper} is null
     */
    default B mapPresentWeather(final UnaryOperator<List<Weather>> mapper) {
        return setPresentWeather(getPresentWeather().map(mapper));
    }

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getPresentWeather()} to {@link
     * Optional#empty() Optional.empty()}.
     *
     * @return this {@code Builder} object
     */
    B clearPresentWeather();

    /**
     * Returns the value that will be returned by {@link MeteorologicalTerminalAirReport#getPresentWeather()}.
     */
    Optional<List<Weather>> getPresentWeather();

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getPresentWeather()}.
     *
     * @return this {@code Builder} object
     *
     * @throws NullPointerException
     *         if {@code presentWeather} is null
     */
    B setPresentWeather(List<Weather> presentWeather);

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getPresentWeather()}.
     *
     * @return this {@code Builder} object
     */
    B setPresentWeather(Optional<? extends List<Weather>> presentWeather);

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getClouds()}.
     *
     * @return this {@code Builder} object
     */
    default B setNullableClouds(@Nullable final ObservedClouds clouds) {
        if (clouds != null) {
            return setClouds(clouds);
        } else {
            return clearClouds();
        }
    }

    /**
     * If the value to be returned by {@link MeteorologicalTerminalAirReport#getClouds()} is present, replaces it by
     * applying {@code mapper} to it and using the result.
     *
     * <p>If the result is null, clears the value.
     *
     * @return this {@code Builder} object
     *
     * @throws NullPointerException
     *         if {@code mapper} is null
     */
    default B mapClouds(final UnaryOperator<ObservedClouds> mapper) {
        return setClouds(getClouds().map(mapper));
    }

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getClouds()} to {@link Optional#empty()
     * Optional.empty()}.
     *
     * @return this {@code Builder} object
     */
    B clearClouds();

    /**
     * Returns the value that will be returned by {@link MeteorologicalTerminalAirReport#getClouds()}.
     */
    Optional<ObservedClouds> getClouds();

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getClouds()}.
     *
     * @return this {@code Builder} object
     *
     * @throws NullPointerException
     *         if {@code clouds} is null
     */
    B setClouds(ObservedClouds clouds);

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getClouds()}.
     *
     * @return this {@code Builder} object
     */
    B setClouds(Optional<? extends ObservedClouds> clouds);

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getRecentWeather()}.
     *
     * @return this {@code Builder} object
     */
    default B setNullableRecentWeather(@Nullable final List<Weather> recentWeather) {
        if (recentWeather != null) {
            return setRecentWeather(recentWeather);
        } else {
            return clearRecentWeather();
        }
    }

    /**
     * If the value to be returned by {@link MeteorologicalTerminalAirReport#getRecentWeather()} is present, replaces it
     * by applying {@code mapper} to it and using the result.
     *
     * <p>If the result is null, clears the value.
     *
     * @return this {@code Builder} object
     *
     * @throws NullPointerException
     *         if {@code mapper} is null
     */
    default B mapRecentWeather(final UnaryOperator<List<Weather>> mapper) {
        return setRecentWeather(getRecentWeather().map(mapper));
    }

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getRecentWeather()} to {@link
     * Optional#empty() Optional.empty()}.
     *
     * @return this {@code Builder} object
     */
    B clearRecentWeather();

    /**
     * Returns the value that will be returned by {@link MeteorologicalTerminalAirReport#getRecentWeather()}.
     */
    Optional<List<Weather>> getRecentWeather();

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getRecentWeather()}.
     *
     * @return this {@code Builder} object
     *
     * @throws NullPointerException
     *         if {@code recentWeather} is null
     */
    B setRecentWeather(List<Weather> recentWeather);

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getRecentWeather()}.
     *
     * @return this {@code Builder} object
     */
    B setRecentWeather(Optional<? extends List<Weather>> recentWeather);

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getWindShear()}.
     *
     * @return this {@code Builder} object
     */
    default B setNullableWindShear(@Nullable final WindShear windShear) {
        if (windShear != null) {
            return setWindShear(windShear);
        } else {
            return clearWindShear();
        }
    }

    /**
     * If the value to be returned by {@link MeteorologicalTerminalAirReport#getWindShear()} is present, replaces it by
     * applying {@code mapper} to it and using the result.
     *
     * <p>If the result is null, clears the value.
     *
     * @return this {@code Builder} object
     *
     * @throws NullPointerException
     *         if {@code mapper} is null
     */
    default B mapWindShear(final UnaryOperator<WindShear> mapper) {
        return setWindShear(getWindShear().map(mapper));
    }

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getWindShear()} to {@link Optional#empty()
     * Optional.empty()}.
     *
     * @return this {@code Builder} object
     */
    B clearWindShear();

    /**
     * Returns the value that will be returned by {@link MeteorologicalTerminalAirReport#getWindShear()}.
     */
    Optional<WindShear> getWindShear();

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getWindShear()}.
     *
     * @return this {@code Builder} object
     *
     * @throws NullPointerException
     *         if {@code windShear} is null
     */
    B setWindShear(WindShear windShear);

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getWindShear()}.
     *
     * @return this {@code Builder} object
     */
    B setWindShear(Optional<? extends WindShear> windShear);

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getSeaState()}.
     *
     * @return this {@code Builder} object
     */
    default B setNullableSeaState(@Nullable final SeaState seaState) {
        if (seaState != null) {
            return setSeaState(seaState);
        } else {
            return clearSeaState();
        }
    }

    /**
     * If the value to be returned by {@link MeteorologicalTerminalAirReport#getSeaState()} is present, replaces it by
     * applying {@code mapper} to it and using the result.
     *
     * <p>If the result is null, clears the value.
     *
     * @return this {@code Builder} object
     *
     * @throws NullPointerException
     *         if {@code mapper} is null
     */
    default B mapSeaState(final UnaryOperator<SeaState> mapper) {
        return setSeaState(getSeaState().map(mapper));
    }

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getSeaState()} to {@link Optional#empty()
     * Optional.empty()}.
     *
     * @return this {@code Builder} object
     */
    B clearSeaState();

    /**
     * Returns the value that will be returned by {@link MeteorologicalTerminalAirReport#getSeaState()}.
     */
    Optional<SeaState> getSeaState();

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getSeaState()}.
     *
     * @return this {@code Builder} object
     *
     * @throws NullPointerException
     *         if {@code seaState} is null
     */
    B setSeaState(SeaState seaState);

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getSeaState()}.
     *
     * @return this {@code Builder} object
     */
    B setSeaState(Optional<? extends SeaState> seaState);

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getRunwayStates()}.
     *
     * @return this {@code Builder} object
     */
    default B setNullableRunwayStates(@Nullable final List<RunwayState> runwayStates) {
        if (runwayStates != null) {
            return setRunwayStates(runwayStates);
        } else {
            return clearRunwayStates();
        }
    }

    /**
     * If the value to be returned by {@link MeteorologicalTerminalAirReport#getRunwayStates()} is present, replaces it
     * by applying {@code mapper} to it and using the result.
     *
     * <p>If the result is null, clears the value.
     *
     * @return this {@code Builder} object
     *
     * @throws NullPointerException
     *         if {@code mapper} is null
     */
    default B mapRunwayStates(final UnaryOperator<List<RunwayState>> mapper) {
        return setRunwayStates(getRunwayStates().map(mapper));
    }

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getRunwayStates()} to {@link
     * Optional#empty() Optional.empty()}.
     *
     * @return this {@code Builder} object
     */
    B clearRunwayStates();

    /**
     * Returns the value that will be returned by {@link MeteorologicalTerminalAirReport#getRunwayStates()}.
     */
    Optional<List<RunwayState>> getRunwayStates();

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getRunwayStates()}.
     *
     * @return this {@code Builder} object
     *
     * @throws NullPointerException
     *         if {@code runwayStates} is null
     */
    B setRunwayStates(List<RunwayState> runwayStates);

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getRunwayStates()}.
     *
     * @return this {@code Builder} object
     */
    B setRunwayStates(Optional<? extends List<RunwayState>> runwayStates);

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getTrends()}.
     *
     * @return this {@code Builder} object
     */
    default B setNullableTrends(@Nullable final List<TrendForecast> trends) {
        if (trends != null) {
            return setTrends(trends);
        } else {
            return clearTrends();
        }
    }

    /**
     * If the value to be returned by {@link MeteorologicalTerminalAirReport#getTrends()} is present, replaces it by
     * applying {@code mapper} to it and using the result.
     *
     * <p>If the result is null, clears the value.
     *
     * @return this {@code Builder} object
     *
     * @throws NullPointerException
     *         if {@code mapper} is null
     */
    default B mapTrends(final UnaryOperator<List<TrendForecast>> mapper) {
        return setTrends(getTrends().map(mapper));
    }

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getTrends()} to {@link Optional#empty()
     * Optional.empty()}.
     *
     * @return this {@code Builder} object
     */
    B clearTrends();

    /**
     * Returns the value that will be returned by {@link MeteorologicalTerminalAirReport#getTrends()}.
     */
    Optional<List<TrendForecast>> getTrends();

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getTrends()}.
     *
     * @return this {@code Builder} object
     *
     * @throws NullPointerException
     *         if {@code trends} is null
     */
    B setTrends(List<TrendForecast> trends);

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getTrends()}.
     *
     * @return this {@code Builder} object
     */
    B setTrends(Optional<? extends List<TrendForecast>> trends);

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getColorState()}.
     *
     * @return this {@code Builder} object
     */
    default B setNullableColorState(@Nullable final AviationCodeListUser.ColorState colorState) {
        if (colorState != null) {
            return setColorState(colorState);
        } else {
            return clearColorState();
        }
    }

    /**
     * If the value to be returned by {@link MeteorologicalTerminalAirReport#getColorState()} is present, replaces it by
     * applying {@code mapper} to it and using the result.
     *
     * <p>If the result is null, clears the value.
     *
     * @return this {@code Builder} object
     *
     * @throws NullPointerException
     *         if {@code mapper} is null
     */
    default B mapColorState(final UnaryOperator<AviationCodeListUser.ColorState> mapper) {
        return setColorState(getColorState().map(mapper));
    }

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getColorState()} to {@link
     * Optional#empty() Optional.empty()}.
     *
     * @return this {@code Builder} object
     */
    B clearColorState();

    /**
     * Returns the value that will be returned by {@link MeteorologicalTerminalAirReport#getColorState()}.
     */
    Optional<AviationCodeListUser.ColorState> getColorState();

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getColorState()}.
     *
     * @return this {@code Builder} object
     *
     * @throws NullPointerException
     *         if {@code colorState} is null
     */
    B setColorState(AviationCodeListUser.ColorState colorState);

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#getColorState()}.
     *
     * @return this {@code Builder} object
     */
    B setColorState(Optional<? extends AviationCodeListUser.ColorState> colorState);

    /**
     * Replaces the value to be returned by {@link MeteorologicalTerminalAirReport#isSnowClosure()} by applying {@code
     * mapper} to it and using the result.
     *
     * @return this {@code Builder} object
     *
     * @throws NullPointerException
     *         if {@code mapper} is null or returns null
     * @throws IllegalStateException
     *         if the field has not been set
     */
    default B mapSnowClosure(final UnaryOperator<Boolean> mapper) {
        Objects.requireNonNull(mapper);
        return setSnowClosure(mapper.apply(isSnowClosure()));
    }

    /**
     * Returns the value that will be returned by {@link MeteorologicalTerminalAirReport#isSnowClosure()}.
     *
     * @throws IllegalStateException
     *         if the field has not been set
     */
    boolean isSnowClosure();

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#isSnowClosure()}.
     *
     * @return this {@code Builder} object
     */
    B setSnowClosure(boolean snowClosure);

    /**
     * Replaces the value to be returned by {@link MeteorologicalTerminalAirReport#isNoSignificantChanges()} by applying
     * {@code mapper} to it and using the result.
     *
     * @return this {@code Builder} object
     *
     * @throws NullPointerException
     *         if {@code mapper} is null or returns null
     * @throws IllegalStateException
     *         if the field has not been set
     */
    default B mapNoSignificantChanges(final UnaryOperator<Boolean> mapper) {
        Objects.requireNonNull(mapper);
        return setNoSignificantChanges(mapper.apply(isNoSignificantChanges()));
    }

    /**
     * Returns the value that will be returned by {@link MeteorologicalTerminalAirReport#isNoSignificantChanges()}.
     *
     * @throws IllegalStateException
     *         if the field has not been set
     */
    boolean isNoSignificantChanges();

    /**
     * Sets the value to be returned by {@link MeteorologicalTerminalAirReport#isNoSignificantChanges()}.
     *
     * @return this {@code Builder} object
     */
    B setNoSignificantChanges(boolean noSignificantChanges);

}
