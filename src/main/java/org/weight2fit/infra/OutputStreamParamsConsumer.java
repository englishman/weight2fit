package org.weight2fit.infra;

import com.garmin.fit.Manufacturer;
import org.weight2fit.domain.FitParams;
import org.weight2fit.domain.FitParamsConsumer;

import java.io.OutputStream;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @author Andriy Kryvtsun
 */
public class OutputStreamParamsConsumer implements FitParamsConsumer {

    private final OutputStream os;

    public OutputStreamParamsConsumer(OutputStream os) {
        this.os = checkNotNull(os, "OutputStream must be not null");
    }

    @Override
    public void accept(FitParams params) throws Exception {

        WeightScaleBuilder builder = new WeightScaleBuilder()
                .manufacturer(Manufacturer.TANITA);

        builder.timestamp(params.getTimestamp());
        builder.weight(params.getWeight());
        builder.percentFat(params.getBodyFat());
        builder.percentHydration(params.getBodyWater());
        builder.visceralFatRating(params.getVisceralFat());
        builder.muscleMass(params.getMuscleMass());
        builder.physiqueRating(params.getPhysiqueRating());
        builder.boneMass(params.getBoneMass());
        builder.activeMet(params.getDCI());
        builder.metabolicAge(params.getMetabolicAge());

        byte[] buffer = builder.build();

        try {
            os.write(buffer);
        }
        finally {
            os.close();
        }
    }
}
