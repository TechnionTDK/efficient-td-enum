package tdk_enum.ml.solvers;

import tdk_enum.common.configuration.config_types.MLProblemType;
import tdk_enum.ml.solvers.execution.CommandResult;
import tdk_enum.ml.solvers.execution.SolverRunner;

import java.io.File;




public class DflatSolver extends AbstractSolver {



    String threeColorsPath = "./dflat/problems/3col_dynamic.lp";

    String command = "./dflat/dflat -p $PROBLEM_PATH --graphml-in $TD_PATH -f $FILE_PATH --depth 0 --output quiet";

    public DflatSolver()
    {
        permitedErrorCodes.add(10);
        permitedErrorCodes.add(20);
        permitedErrorCodes.add(30);
    }

    @Override
    public CommandResult solve(File graphFile, File tdFile) {
        String solverCommand = command;
        solverCommand = solverCommand.replace("$TD_PATH", tdFile.getPath());
        solverCommand = solverCommand.replace("$FILE_PATH", graphFile.getPath());
        switch (mlProblemType){
            case DFLAT_3COL:
            {
                solverCommand = solverCommand.replace("$PROBLEM_PATH", threeColorsPath );
            }
            case DFLAT_CONNECTED:
            {

            }

        }
        return SolverRunner.RunSolverCommand(solverCommand, memeoryLimit, timeLimit, permitedErrorCodes, graphFile);

    }
}
