package edu.buffalo.itembench.generators.DataGenerator;

import edu.buffalo.itembench.generators.Generator;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * Created by abhinit on 11/15/15.
 */
public class AlphaNumericGenerator implements Generator{
    private int sampleLength = 10;

    @Override
    public String getNextString() {
            return RandomStringUtils.random(this.sampleLength);
    }

    public int getSampleLength() {
        return sampleLength;
    }

    public void setSampleLength(int sampleLength) {
        this.sampleLength = sampleLength;
    }
}
