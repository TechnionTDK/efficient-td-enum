package tdk_enum.ml.solvers.execution;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

/**
 * @author ABSEHER Michael (abseher@dbai.tuwien.ac.at)
 */
public class BenchmarkOperations {

    public static long getCpuTime() {
        ThreadMXBean bean =
                ManagementFactory.getThreadMXBean();

        return bean.isCurrentThreadCpuTimeSupported() ?
                (bean.getCurrentThreadCpuTime()) / 1000000 : getWallClockTime();
    }

    public static long getUserTime() {
        ThreadMXBean bean =
                ManagementFactory.getThreadMXBean();

        return bean.isCurrentThreadCpuTimeSupported() ?
                (bean.getCurrentThreadUserTime()) / 1000000 : getWallClockTime();
    }

    public static long getSystemTime() {
        ThreadMXBean bean =
                ManagementFactory.getThreadMXBean();

        return bean.isCurrentThreadCpuTimeSupported() ?
                (bean.getCurrentThreadCpuTime() - bean.getCurrentThreadUserTime()) / 1000000 : getWallClockTime();
    }

    public static long getWallClockTime() {
        return System.currentTimeMillis();
    }

}
