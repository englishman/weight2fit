package org.weight2fit.domain;

/**
 * Interface for FIT parameters producers.
 *
 * @author Andriy Kryvtsun
 */
public interface FitParamsSupplier {

    /**
     * Returns FIT options set.
     *
     * @return  FIT options set
     * @throws Exception  if something was wrong during options set creation
     */
    public FitParams get() throws Exception;
}