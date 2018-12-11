package tdk_enum.factories.ml.solver_factory;

import tdk_enum.common.configuration.TDKMLConfiguration;
import tdk_enum.factories.TDKEnumFactory;
import tdk_enum.ml.solvers.DflatSolver;
import tdk_enum.ml.solvers.ISolver;

public class SolverFactory implements ISolverFactory {
    @Override
    public ISolver produce() {

        TDKMLConfiguration configuration = (TDKMLConfiguration) TDKEnumFactory.getConfiguration();
        ISolver solver ;
        switch (configuration.getMlProblemType())
        {
            case DFLAT_CONNECTED: case DFLAT_3COL:
                solver = new DflatSolver();
                break;
                default:
                    solver = new DflatSolver();

        }
        return inject(solver);


    }

    ISolver inject(ISolver solver)
    {
        TDKMLConfiguration configuration = (TDKMLConfiguration) TDKEnumFactory.getConfiguration();
        solver.setTimeLimit(configuration.getSolverTimeLimit());
        solver.setMemoryLimit(configuration.getSolverMemoryLimit());
        solver.setMlProblemType(configuration.getMlProblemType());
        return solver;

    }
}
