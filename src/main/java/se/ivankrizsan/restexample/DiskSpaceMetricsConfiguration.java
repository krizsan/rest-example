package se.ivankrizsan.restexample;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * Configuration of bean which expose disk-space metrics that can be scraped by Prometheus.
 * Enable or disable the disk-space metrics using the property "diskspace.metrics.enabled".
 * Configure the path for which disk-space metrics are to be calculated using the property
 * "diskspace.metrics.path".
 *
 * @author Ivan Krizsan
 */
@Configuration
@ConditionalOnProperty(name = "diskspace.metrics.enabled", havingValue = "true")
public class DiskSpaceMetricsConfiguration {
    /* Constant(s): */
    public final static String FREE_DISKSPACE_GAUGE_NAME = "free_disk_space";
    public final static String TOTAL_DISKSPACE_GAUGE_NAME = "total_disk_space";
    public final static String USED_DISKSPACE_PERCENTAGE_GAUGE_NAME = "used_disk_space";
    public final static String REMAINING_DISKSPACE_PERCENTAGE_GAUGE_NAME ="remaining_disk_space";

    /* Dependencies: */
    @Value("${diskspace.metrics.path}")
    protected String mDiskspaceMetricsPath;

    /**
     * Meter registry customizer bean that adds disk-space gauges to the set of metrics that
     * can be scraped by Prometheus.
     *
     * @return Meter registry customizer for disk space metrics.
     */
    @Bean
    MeterRegistryCustomizer<MeterRegistry> meterRegistryCustomizer() {
        final File theDiskSpaceMetricsFile = new File(mDiskspaceMetricsPath);

        return registry -> {
            Gauge
                .builder(FREE_DISKSPACE_GAUGE_NAME, theDiskSpaceMetricsFile::getFreeSpace)
                .description("Free disk-space in bytes")
                .baseUnit("bytes")
                .register(registry);

            Gauge
                .builder(TOTAL_DISKSPACE_GAUGE_NAME, theDiskSpaceMetricsFile::getTotalSpace)
                .description("Total disk-space in bytes")
                .baseUnit("bytes")
                .register(registry);

            Gauge
                .builder(USED_DISKSPACE_PERCENTAGE_GAUGE_NAME, () -> {
                    final double theFreeDiskSpace = theDiskSpaceMetricsFile.getFreeSpace();
                    final double theTotalDiskSpace = theDiskSpaceMetricsFile.getTotalSpace();
                    final double theUsedDiskSpace = theTotalDiskSpace - theFreeDiskSpace;
                    return (theUsedDiskSpace / theTotalDiskSpace) * 100.0;
                })
                .description("Used disk-space in percent")
                .baseUnit("percent")
                .register(registry);

            Gauge
                .builder(REMAINING_DISKSPACE_PERCENTAGE_GAUGE_NAME, () -> {
                    final double theFreeDiskSpace = theDiskSpaceMetricsFile.getFreeSpace();
                    final double theTotalDiskSpace = theDiskSpaceMetricsFile.getTotalSpace();
                    return (theFreeDiskSpace / theTotalDiskSpace) * 100.0;
                })
                .description("Remaining disk-space in percent")
                .baseUnit("percent")
                .register(registry);
        };
    }
}
