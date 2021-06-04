package fi.fmi.avi.model.bulletin.immutable;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;

import org.inferred.freebuilder.FreeBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import fi.fmi.avi.model.GenericAviationWeatherMessage;
import fi.fmi.avi.model.bulletin.BulletinHeading;
import fi.fmi.avi.model.bulletin.GenericMeteorologicalBulletin;
import fi.fmi.avi.model.bulletin.MeteorologicalBulletinBuilderHelper;
import fi.fmi.avi.model.immutable.GenericAviationWeatherMessageImpl;

@FreeBuilder
@JsonDeserialize(builder = GenericMeteorologicalBulletinImpl.Builder.class)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@JsonPropertyOrder({ "timeStamp", "timeStampFields", "heading", "messages" })
public abstract class GenericMeteorologicalBulletinImpl implements GenericMeteorologicalBulletin, Serializable {

    private static final long serialVersionUID = -4860727383244788466L;

    public static Builder builder() {
        return new Builder();
    }

    public static GenericMeteorologicalBulletinImpl immutableCopyOf(final GenericMeteorologicalBulletin bulletin) {
        Objects.requireNonNull(bulletin);
        if (bulletin instanceof GenericMeteorologicalBulletinImpl) {
            return (GenericMeteorologicalBulletinImpl) bulletin;
        } else {
            return Builder.from(bulletin).build();
        }
    }

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    public static Optional<GenericMeteorologicalBulletinImpl> immutableCopyOf(final Optional<GenericMeteorologicalBulletin> bulletin) {
        return bulletin.map(GenericMeteorologicalBulletinImpl::immutableCopyOf);
    }

    public abstract Builder toBuilder();

    public static class Builder extends GenericMeteorologicalBulletinImpl_Builder {
        @Deprecated
        public Builder() {
            this.setTransmissionSequenceNumber("");
        }
        public Optional<Integer> getTransmissionSequenceNumberAsInt() {
            try {
                return Optional.of(Integer.parseInt(getTransmissionSequenceNumber()));
            } catch(final NumberFormatException e){
                return Optional.empty();
            }
        }
        public Builder setTransmissionSequenceNumberAsInt(final int transmissionSequenceNumber) throws IllegalArgumentException {
            return setTransmissionSequenceNumberAsInt(transmissionSequenceNumber, 3);
        }

        public Builder setTransmissionSequenceNumberAsInt(final int transmissionSequenceNumber, final int numberOfDigits) throws IllegalArgumentException {
            if(String.valueOf(transmissionSequenceNumber).length() > numberOfDigits || transmissionSequenceNumber < 0) {
                throw new IllegalArgumentException("Transmission sequence number is out of range.");
            }
            return this.setTransmissionSequenceNumber(String.format("%0" + numberOfDigits + "d", transmissionSequenceNumber));
        }

        public static Builder from(final GenericMeteorologicalBulletin value) {
            if (value instanceof GenericMeteorologicalBulletinImpl) {
                return ((GenericMeteorologicalBulletinImpl) value).toBuilder();
            } else {
                final Builder builder = builder();
                MeteorologicalBulletinBuilderHelper.copyFrom(builder, value, //
                        Builder::setHeading, //
                        Builder::addAllMessages, //
                        GenericAviationWeatherMessageImpl::immutableCopyOf, //
                        Builder::setTimeStamp, //
                        Builder::addAllTimeStampFields);
                return builder;
            }
        }

        @Override
        @JsonDeserialize(as = BulletinHeadingImpl.class)
        public Builder setHeading(final BulletinHeading heading) {
            return super.setHeading(heading);
        }

        @Override
        @JsonDeserialize(contentAs = GenericAviationWeatherMessageImpl.class)
        @JsonProperty("messages")
        public Builder addMessages(final GenericAviationWeatherMessage... messages) {
            return super.addMessages(messages);
        }
    }
}
