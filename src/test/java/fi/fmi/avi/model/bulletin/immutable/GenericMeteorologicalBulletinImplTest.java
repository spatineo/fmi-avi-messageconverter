package fi.fmi.avi.model.bulletin.immutable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Optional;

import org.junit.Test;

public class GenericMeteorologicalBulletinImplTest {
    @Test
    public void transmissionSequenceNumberTest() {
        GenericMeteorologicalBulletinImpl.Builder builder = GenericMeteorologicalBulletinImpl.builder();
        //Check transmisson sequence number default value
        assertEquals("", builder.getTransmissionSequenceNumber());
        assertEquals(Optional.empty(), builder.getTransmissionSequenceNumberAsInt());

        //Check transmisson sequence number set as string
        builder.setTransmissionSequenceNumber("355n");
        assertEquals("355n", builder.getTransmissionSequenceNumber());
        assertEquals(Optional.empty(), builder.getTransmissionSequenceNumberAsInt());

        //Check transmisson sequence number set as number with default length
        builder.setTransmissionSequenceNumberAsInt(2);
        assertEquals("002", builder.getTransmissionSequenceNumber());
        assertEquals(Optional.of(2), builder.getTransmissionSequenceNumberAsInt());

        //Check transmisson sequence number set as number with default length
        builder.setTransmissionSequenceNumberAsInt(156);
        assertEquals("156", builder.getTransmissionSequenceNumber());
        assertEquals(Optional.of(156), builder.getTransmissionSequenceNumberAsInt());

        //Check transmisson sequence number set as number with custom length length
        builder.setTransmissionSequenceNumberAsInt(46855, 5);
        assertEquals("46855", builder.getTransmissionSequenceNumber());
        assertEquals(Optional.of(46855), builder.getTransmissionSequenceNumberAsInt());

        //Check transmisson sequence number set as number with custom length length
        builder.setTransmissionSequenceNumberAsInt(5468, 8);
        assertEquals("00005468", builder.getTransmissionSequenceNumber());
        assertEquals(Optional.of(5468), builder.getTransmissionSequenceNumberAsInt());

        try {
            builder.setTransmissionSequenceNumberAsInt(9785);
            fail("An illegal argument exception should have been thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("Transmission sequence number is out of range.", e.getMessage());
        }

        try {
            builder.setTransmissionSequenceNumberAsInt(54685, 4);
            fail("An illegal argument exception should have been thrown");
        } catch (IllegalArgumentException e) {
            assertEquals("Transmission sequence number is out of range.", e.getMessage());
        }
    }
}
