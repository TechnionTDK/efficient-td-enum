package tdk_enum.ml;

import org.joda.time.DateTime;
import tdk_enum.common.IO.GraphMLPrinter;
import tdk_enum.common.IO.InputFile;
import tdk_enum.common.configuration.config_types.MLModelInput;
import tdk_enum.common.configuration.config_types.MLSortTD;
import tdk_enum.enumerators.common.IEnumerator;
import tdk_enum.enumerators.tree_decomposition.ITreeDecompositionEnumerator;
import tdk_enum.enumerators.triangulation.parallel.StoringParallelMinimalTriangulationsEnumerator;
import tdk_enum.factories.TDKEnumFactory;
import tdk_enum.factories.enumeration.minimal_triangulations_enumerator_factory.MinimalTriangulationsEnumeratorFactory;
import tdk_enum.factories.ml.feature_extractor_factory.FeatureExtractorFactory;
import tdk_enum.factories.ml.solver_factory.SolverFactory;
import tdk_enum.graph.converters.Converter;
import tdk_enum.graph.graphs.chordal_graph.IChordalGraph;
import tdk_enum.graph.graphs.tree_decomposition.ITreeDecomposition;
import tdk_enum.ml.classifiers.IClassifier;
import tdk_enum.ml.feature_extractor.IFeatureExtractor;
import tdk_enum.ml.feature_extractor.abseher.feature.BenchmarkRun;
import tdk_enum.ml.feature_extractor.abseher.feature.FeatureExtractionResult;
import tdk_enum.ml.solvers.ISolver;
import tdk_enum.ml.solvers.execution.CommandResult;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class TDMLRunner {

    ITreeDecompositionEnumerator enumerator;
    ISolver solver;
    IFeatureExtractor featureExtractor;
    IClassifier classifier;
    int tdLimit;

    MLModelInput mlModelInput;


    String inputPath = "";

    String modelStorePath = "";

    MLSortTD mlSortTD;

    public MLSortTD getMlSortTD() {
        return mlSortTD;
    }

    public void setMlSortTD(MLSortTD mlSortTD) {
        this.mlSortTD = mlSortTD;
    }

    public String getInputPath() {
        return inputPath;
    }

    public void setInputPath(String inputPath) {
        this.inputPath = inputPath;
    }

    public String getModelStorePath() {
        return modelStorePath;
    }

    public void setModelStorePath(String modelStorePath) {
        this.modelStorePath = modelStorePath;
    }

    public String getModelLoadPath() {
        return modelLoadPath;
    }

    public void setModelLoadPath(String modelLoadPath) {
        this.modelLoadPath = modelLoadPath;
    }

    String modelLoadPath = "";

    public MLModelInput getMlModelInput() {
        return mlModelInput;
    }

    public void setMlModelInput(MLModelInput mlModelInput) {
        this.mlModelInput = mlModelInput;
    }



    public ITreeDecompositionEnumerator getEnumerator() {
        return enumerator;
    }

    public void setEnumerator(ITreeDecompositionEnumerator enumerator) {
        this.enumerator = enumerator;
    }

    public ISolver getSolver() {
        return solver;
    }

    public void setSolver(ISolver solver) {
        this.solver = solver;
    }

    public IFeatureExtractor getFeatureExtractor() {
        return featureExtractor;
    }

    public void setFeatureExtractor(IFeatureExtractor featureExtractor) {
        this.featureExtractor = featureExtractor;
    }

    public IClassifier getClassifier() {
        return classifier;
    }

    public void setClassifier(IClassifier classifier) {
        this.classifier = classifier;
    }

    public int getTdLimit() {
        return tdLimit;
    }

    public void setTdLimit(int tdLimit) {
        this.tdLimit = tdLimit;
    }


    class TreeDecompositionMeta
    {
        ITreeDecomposition treeDecomposition;
        int id;
        String graphName;
        CommandResult commandResult;
        File file;
        BenchmarkRun benchmarkRun;
        File graph;
        FeatureExtractionResult featureExtractionResult;

        public TreeDecompositionMeta(IChordalGraph chordalGraph, int id, String graphName, File graph)
        {
            this.treeDecomposition = Converter.chordalGraphToNiceTreeDecomposition(chordalGraph);
            this.graphName = graphName;
            this.id = id;
            this.file = new File("./"+graphName + "_" + id + ".gml");
            this.graph = graph;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public CommandResult getCommandResult() {
            return commandResult;
        }

        public void setCommandResult(CommandResult commandResult) {
            this.commandResult = commandResult;
            this.benchmarkRun = BenchmarkRun.getInstance(id, graph ,commandResult);
        }

        public File getFile()
        {


            GraphMLPrinter.treeDecompositionToGraphMLFile(treeDecomposition, file.getAbsolutePath());
            return file;
        }

        public void deleteFile()
        {
            file.delete();
        }

        public ITreeDecomposition getTreeDecomposition() {
            return treeDecomposition;
        }

        public void setTreeDecomposition(ITreeDecomposition treeDecomposition) {
            this.treeDecomposition = treeDecomposition;
        }

        public String getGraphName() {
            return graphName;
        }

        public void setGraphName(String graphName) {
            this.graphName = graphName;
        }

        public void setFile(File file) {
            this.file = file;
        }

        public BenchmarkRun getBenchmarkRun() {
            return benchmarkRun;
        }

        public void setBenchmarkRun(BenchmarkRun benchmarkRun) {
            this.benchmarkRun = benchmarkRun;
        }

        public File getGraph() {
            return graph;
        }

        public void setGraph(File graph) {
            this.graph = graph;
        }

        public FeatureExtractionResult getFeatureExtractionResult() {
            return featureExtractionResult;
        }

        public void setFeatureExtractionResult(FeatureExtractionResult featureExtractionResult) {
            this.featureExtractionResult = featureExtractionResult;
        }
    }
    public void trainByDataSet(List<String> inputs)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy:HH:mm:SS");
        Date date = new Date();

        File csv = new File("./csv_files/datasetFeatures_"+ dateFormat.format(date) + ".csv");
        System.out.println("Starting enumeration and features extraction for input dataset");
        for (String filePath : inputs)
        {
            InputFile inputFile = new InputFile(filePath);
            System.out.println("Starting enumeration and features extraction for " + inputFile.getPath());
            TDKEnumFactory.init(inputFile);
            StoringParallelMinimalTriangulationsEnumerator enumerator = (StoringParallelMinimalTriangulationsEnumerator) new MinimalTriangulationsEnumeratorFactory().produce();
            runTimeLimited(enumerator);
            Set<IChordalGraph> chordalGraphs = enumerator.getTriangulations();
            List<IChordalGraph> results;
            switch (mlSortTD)
            {
                case FILL:
                {
                    System.out.println("Sorting TDs by fill");
                    results = chordalGraphs.stream().
                            sorted((iChordalGraph, t1) -> {return  iChordalGraph.getFillIn(TDKEnumFactory.getGraph()) -
                                    t1.getFillIn(TDKEnumFactory.getGraph());}).
                            limit(tdLimit).
                            collect(Collectors.toCollection(ArrayList::new));
                    break;
                }
                case WIDTH:
                {
                    System.out.println("Sorting TDs by width");
                    results = chordalGraphs.stream().
                            sorted((iChordalGraph, t1) -> {return  iChordalGraph.getTreeWidth() -
                                    t1.getTreeWidth();}).
                            limit(tdLimit).
                            collect(Collectors.toCollection(ArrayList::new));

                    break;
                }
                case NO:
                default:
                    results = chordalGraphs.stream().limit(tdLimit).collect(Collectors.toCollection(ArrayList::new));
                    break;
            }
            Set<TreeDecompositionMeta> decompositionMetas = new HashSet<>();

            for(int i =0 ; i < results.size(); i++)
            {
                decompositionMetas.add( new TreeDecompositionMeta(results.get(i), i, TDKEnumFactory.getInputFile().getName(), TDKEnumFactory.getInputFile().getFile()));
            }
            ISolver solver = new SolverFactory().produce();
            IFeatureExtractor featureExtractor = new FeatureExtractorFactory().produce();
            decompositionMetas.parallelStream().forEach(treeDecompositionMeta -> {
                treeDecompositionMeta.setCommandResult(solver.solve(TDKEnumFactory.getInputFile().getFile(), treeDecompositionMeta.getFile()));
                treeDecompositionMeta.deleteFile();
                treeDecompositionMeta.setFeatureExtractionResult( featureExtractor.getFeatures(
                        treeDecompositionMeta.id,
                        treeDecompositionMeta.treeDecomposition ,
                        TDKEnumFactory.getGraph(),
                        treeDecompositionMeta.benchmarkRun ));
            });
            decompositionMetas.stream().forEach(treeDecompositionMeta -> featureExtractor.toCSV(csv, treeDecompositionMeta.featureExtractionResult));

        }

        System.out.println("dataset raw features were written on " + csv.getAbsolutePath());
        File refinedOutput = new File("./csv_files/datasetFeatures_"+ dateFormat.format(date) + "_prepared.csv");
        featureExtractor.prepareCSV(csv.getAbsolutePath(), refinedOutput.getAbsolutePath());
        System.out.println("dataset prepared features were written on " + csv.getAbsolutePath());

        trainByCSV(csv.getAbsolutePath());

    }

    void runTimeLimited(IEnumerator enumerator)
    {

        ExecutorService executorService = Executors.newFixedThreadPool(1);

        List<Future<Object>> futures = new ArrayList<>();

        List<Callable<Object>> callables = Arrays.asList(enumerator);
        try {
            int timelimit = TDKEnumFactory.getConfiguration().getTime_limit();
            futures = executorService.invokeAll(callables, timelimit, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        for(Future f : futures)
        {
            if(!f.isDone())
            {
                f.cancel(true);
            }
        }
        executorService.shutdownNow();



    }

    public void trainByInput(InputFile inputFile, Set<IChordalGraph> chordalGraphs)
    {

    }

    public void trainByCSV(String csvFile)
    {

    }

    public void loadTrainedModels()
    {

    }

    void train()
    {

    }

//    public Map<String, Double> predict(ITreeDecomposition td)
//    {
//
//    }

}
