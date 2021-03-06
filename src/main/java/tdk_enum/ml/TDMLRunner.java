package tdk_enum.ml;

import tdk_enum.common.IO.CSVOperations;
import tdk_enum.common.IO.GraphMLPrinter;
import tdk_enum.common.IO.InputFile;
import tdk_enum.common.configuration.TDKEnumConfiguration;
import tdk_enum.common.configuration.TDKMLConfiguration;
import tdk_enum.common.configuration.TDKTreeDecompositionEnumConfiguration;
import tdk_enum.common.configuration.config_types.*;
import tdk_enum.enumerators.common.IEnumerator;
import tdk_enum.enumerators.tree_decomposition.ITreeDecompositionEnumerator;
import tdk_enum.factories.TDKEnumFactory;
import tdk_enum.factories.enumeration.tree_decomposition_enumerator_factory.NiceTreeDecompositionEnumeratorFactory;
import tdk_enum.factories.enumeration.tree_decomposition_enumerator_factory.TreeDecompositionEnumeratorFactory;
import tdk_enum.factories.ml.classifier_factory.ClassifierFactory;
import tdk_enum.factories.ml.feature_extractor_factory.FeatureExtractorFactory;
import tdk_enum.factories.ml.solver_factory.SolverFactory;
import tdk_enum.graph.graphs.tree_decomposition.ITreeDecomposition;
import tdk_enum.ml.classifiers.IClassifier;
import tdk_enum.ml.classifiers.common.*;
import tdk_enum.ml.feature_extractor.IFeatureExtractor;
import tdk_enum.ml.feature_extractor.abseher.feature.BenchmarkRun;
import tdk_enum.ml.feature_extractor.abseher.feature.FeatureExtractionResult;
import tdk_enum.ml.feature_extractor.abseher.feature.StringOperations;
import tdk_enum.ml.solvers.ISolver;
import tdk_enum.ml.solvers.execution.CommandResult;
import tdk_enum.ml.solvers.execution.MemoryFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
        DecompositionDetails details;
        FeatureExtractionResult featureExtractionResult;

        public TreeDecompositionMeta(ITreeDecomposition treeDecomposition, int id, String graphName, File graph)
        {
//            this.treeDecomposition = Converter.chordalGraphToNiceTreeDecomposition(chordalGraph);
            this.treeDecomposition = treeDecomposition;
            this.graphName = graphName;
            this.id = id;
            this.file = new File("./"+graphName + "_" + id + ".gml");
            this.graph = graph;
        }

        public TreeDecompositionMeta(ITreeDecomposition treeDecomposition, int id, String graphName)
        {
            this.treeDecomposition = treeDecomposition;
            this.id = id;
            this.graphName = graphName;
            this.file = new File("./"+graphName + "_" + id + ".gml");
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



        public void setFeatureExtractionResult(FeatureExtractionResult featureExtractionResult) {
            this.details = DecompositionDetails.getInstance(featureExtractionResult);
//            this.featureExtractionResult = featureExtractionResult;
        }
        public DecompositionDetails getDecompositionDetails()
        {
            return this.details;
        }


    }
    public void trainByDataSet(List<String> inputs)
    {
        TDKMLConfiguration configuration = (TDKMLConfiguration) TDKEnumFactory.getConfiguration();
//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy:HH:mm:ss");
//        Date date = new Date();

        File csv = new File(configuration.getDatasetPath() + "/csv_files/datasetFeatures.csv");
        System.out.println("Starting enumeration and features extraction for input dataset");
        for (String filePath : inputs)
        {
            List<ITreeDecomposition> results = getTreeDecompositions(filePath);


            Set<TreeDecompositionMeta> decompositionMetas = new HashSet<>();

            for(int i =0 ; i < results.size(); i++)
            {
                decompositionMetas.add( new TreeDecompositionMeta(
                      results.get(i), i, TDKEnumFactory.getInputFile().getName(), TDKEnumFactory.getInputFile().getFile()));
            }
            results.clear();
            ISolver solver = new SolverFactory().produce();

            IFeatureExtractor featureExtractor = new FeatureExtractorFactory().produce();
            decompositionMetas.parallelStream().forEach(treeDecompositionMeta -> {
                treeDecompositionMeta.setCommandResult(solver.solve(TDKEnumFactory.getInputFile().getFile(), treeDecompositionMeta.getFile()));
                treeDecompositionMeta.deleteFile();
                FeatureExtractionResult result = featureExtractor.getFeatures(
                        treeDecompositionMeta.id,
                        treeDecompositionMeta.treeDecomposition ,
                        TDKEnumFactory.getMlGraph(),
                        treeDecompositionMeta.benchmarkRun );
                treeDecompositionMeta.setFeatureExtractionResult(result);
            });
            decompositionMetas.stream().forEach(treeDecompositionMeta -> featureExtractor.toCSV(csv, treeDecompositionMeta.getDecompositionDetails()));
            decompositionMetas.clear();
        }

        System.out.println("dataset raw features were written on " + csv.getAbsolutePath());
//        File refinedOutput = new File(csv.getAbsolutePath());
//        String csvFile = featureExtractor.prepareCSV(csv.getAbsolutePath(), refinedOutput.getAbsolutePath());


        trainByCSV(csv.getAbsolutePath());

    }

    private List<ITreeDecomposition> getTreeDecompositions(String filePath) {
        InputFile inputFile = new InputFile(filePath);
        System.out.println("Starting enumeration and features extraction for " + inputFile.getPath());
        TDKEnumFactory.init(inputFile);

        ITreeDecompositionEnumerator enumerator = createSingleThreadEnumerator(true);
        List<ITreeDecomposition> results = new ArrayList<>();
        while(enumerator.hasNext() && results.size()<tdLimit){
            results.add(enumerator.next());
        }
        System.out.println("done ");


//        switch (mlSortTD)
//        {
//            case FILL:
//            {
//                System.out.println("Sorting TDs by fill");
//                results = chordalGraphs.stream().
//                        sorted((iChordalGraph, t1) -> {return  iChordalGraph.getFillIn(TDKEnumFactory.getGraph()) -
//                                t1.getFillIn(TDKEnumFactory.getGraph());}).
//                        limit(tdLimit).
//                        collect(Collectors.toCollection(ArrayList::new));
//                break;
//            }
//            case WIDTH:
//            {
//                System.out.println("Sorting TDs by width");
//                results = chordalGraphs.stream().
//                        sorted((iChordalGraph, t1) -> {return  iChordalGraph.getTreeWidth() -
//                                t1.getTreeWidth();}).
//                        limit(tdLimit).
//                        collect(Collectors.toCollection(ArrayList::new));
//
//                break;
//            }
//            case NO:
//            default:
//                results = chordalGraphs.stream().limit(tdLimit).collect(Collectors.toCollection(ArrayList::new));
//                break;
//        }
        return results;
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


    public void trainByCSV(String csvFile)
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy:HH:mm:ss");
        Date date = new Date();

        TDKMLConfiguration configuration = (TDKMLConfiguration)TDKEnumFactory.getConfiguration();
        IClassifier classifier = new ClassifierFactory().produce();
        String outputPath = configuration.getDatasetPath()+"/models";

        classifier.trainModel(csvFile, outputPath, configuration.getMlModelType() );


    }

    public void classify(List<String> inputs)
    {



        for (String filePath : inputs)
        {

            InputFile inputFile = new InputFile(filePath);
            System.out.println("Starting prediction for " + inputFile.getPath());
            TDKEnumFactory.init(inputFile);

            if(predictVanilla(inputFile))
            {
                predictFullRandom(inputFile);
                predictRandom(inputFile);
                predictReal(inputFile);
            }

//            ITreeDecompositionEnumerator singleThreadEnumerator = creatSingleThreadEnumerator();
//            ITreeDecompositionEnumerator randomEnumerator = createSingleThreadRandomEnumerator();


//            List<ITreeDecomposition> first40 = getFirst40(singleThreadEnumerator);
//            List<ITreeDecomposition> first40Random = getFirst40(randomEnumerator);

//           List<EvaluationDetails> detFirst40 = predict(first40, inputFile);
//            List<EvaluationDetails> randomized40 =  predict(first40Random, inputFile);



//            printPredictionsToCSV(detFirst40, "deterministic first40", filePath);
//            printPredictionsToCSV(randomized40, "randomized40", filePath);





        }





    }

    private void predictFullRandom(InputFile inputFile) {
        ITreeDecompositionEnumerator randomEnumerator = createGeigerRandomEnumerator();
        List<ITreeDecomposition> randomTrees = new ArrayList<>();
        long timeLimit = TDKEnumFactory.getConfiguration().getTime_limit();
        long start =   System.currentTimeMillis();
        while (randomEnumerator.hasNext() && System.currentTimeMillis() - start < timeLimit*1000)

        {
            randomTrees.add(randomEnumerator.next());
        }

        System.out.println("Fully Random trees: " + randomTrees.size());
        List<EvaluationDetails> rendomDetails = predict(randomTrees, inputFile);
        printPredictionsToCSV(rendomDetails, "full random (geiger)", inputFile.getPath(),randomTrees.size());
        List<ITreeDecomposition> first70 = new ArrayList<>();
        int max = randomTrees.size()>=70? 70 : randomTrees.size();
        for (int i = 0; i <max; i++)
        {

            first70.add(randomTrees.get(i));
        }
        List<EvaluationDetails> random70Details = predict(first70, inputFile);
        printPredictionsToCSV(random70Details, "full random (geiger) 70", inputFile.getPath(),first70.size());
    }

    private ITreeDecompositionEnumerator createGeigerRandomEnumerator() {
        TDKEnumConfiguration configuration = TDKEnumFactory.getConfiguration();
        TDKTreeDecompositionEnumConfiguration  decompositionEnumConfiguration = new TDKTreeDecompositionEnumConfiguration();
        decompositionEnumConfiguration.setEnumerationType(EnumerationType.NICE_TD);
        decompositionEnumConfiguration.setMinimalTriangulatorType(MinimalTriangulatorType.RANDOM);
        decompositionEnumConfiguration.setRunningMode(RunningMode.SINGLE_THREAD);
        decompositionEnumConfiguration.setSeparatorsGraphType(SeparatorsGraphType.CACHED);
        decompositionEnumConfiguration.setSingleThreadMISEnumeratorType(SingleThreadMISEnumeratorType.RANDOM);
        TDKMLConfiguration tdkmlConfiguration = (TDKMLConfiguration) configuration;
        decompositionEnumConfiguration.setTriangulationAlgorithm(tdkmlConfiguration.getTriangulationAlgorithm());
        TDKEnumFactory.setConfiguration(decompositionEnumConfiguration);
        ITreeDecompositionEnumerator enumerator = new NiceTreeDecompositionEnumeratorFactory().produce();
        TDKEnumFactory.setConfiguration(configuration);
        return enumerator;
    }

    private void predictRandom(InputFile inputFile) {
        ITreeDecompositionEnumerator randomEnumerator = createSingleThreadRandomEnumerator();
        List<ITreeDecomposition> randomTrees = new ArrayList<>();
        long timeLimit = TDKEnumFactory.getConfiguration().getTime_limit();
        long start =   System.currentTimeMillis();
        while (randomEnumerator.hasNext() && System.currentTimeMillis() - start < timeLimit*1000)

        {
            randomTrees.add(randomEnumerator.next());
        }

        System.out.println("Random trees: " + randomTrees.size());
        List<EvaluationDetails> vanillaDetails = predict(randomTrees, inputFile);
        printPredictionsToCSV(vanillaDetails, "random", inputFile.getPath(),randomTrees.size());
        List<ITreeDecomposition> first70 = new ArrayList<>();
        int max = randomTrees.size()>=70? 70 : randomTrees.size();
        for (int i = 0; i <max; i++)
        {

            first70.add(randomTrees.get(i));
        }
        List<EvaluationDetails> random70Details = predict(first70, inputFile);
        printPredictionsToCSV(random70Details, "random 70", inputFile.getPath(),first70.size());
//        List<ITreeDecomposition> first70 = new ArrayList<>();
//        for (int i = 0; i < 70; i++)
//        {
//            first70.add(randomTrees.get(i));
//        }
//        List<EvaluationDetails> vanilla70Details = predict(first70, inputFile);
//        printPredictionsToCSV(vanillaDetails, "random70", inputFile.getPath(),first70.size());
    }

    private boolean predictVanilla(InputFile inputFile)
    {
        ITreeDecompositionEnumerator vanillaEnumerator = createVanillaEnumerator();
        List<ITreeDecomposition> vanillaTrees = new ArrayList<>();
        TDKMLConfiguration configuration = (TDKMLConfiguration) TDKEnumFactory.getConfiguration();
        long timeLimit = configuration.getTime_limit();
        long start =   System.currentTimeMillis();
        while (vanillaEnumerator.hasNext() && System.currentTimeMillis() - start < timeLimit*1000)

        {
            vanillaTrees.add(vanillaEnumerator.next());
        }

        TreeDecompositionMeta meta = new TreeDecompositionMeta(
                vanillaTrees.get(0), 0, TDKEnumFactory.getInputFile().getName(), TDKEnumFactory.getInputFile().getFile());
        solver.setTimeLimit(1000*configuration.getPredictionSolverMinTime()+5);
        CommandResult result = solver.solve(TDKEnumFactory.getInputFile().getFile(), meta.getFile());
        solver.setTimeLimit(configuration.getSolverTimeLimit());
        meta.deleteFile();

        if(result.getTotalDuration_UserTime()<1000*configuration.getPredictionSolverMinTime())
        {
            System.out.println("First vanilla TD ran less then "+ configuration.getPredictionSolverMinTime()+" sec");
            return false;
        }

        System.out.println("Vanilla trees: " + vanillaTrees.size());
        List<EvaluationDetails> vanillaDetails = predict(vanillaTrees, inputFile);
        printPredictionsToCSV(vanillaDetails, "vanilla", inputFile.getPath(),vanillaTrees.size());

//        List<ITreeDecomposition> first70 = new ArrayList<>();
//        for (int i = 0; i < 70; i++)
//        {
//            first70.add(vanillaTrees.get(i));
//        }
//        List<EvaluationDetails> vanilla70Details = predict(first70, inputFile);
//        printPredictionsToCSV(vanillaDetails, "vanilla70", inputFile.getPath(),first70.size());

        return true;
    }

    private void predictReal(InputFile inputFile)
    {
        ITreeDecompositionEnumerator enumerator = new TreeDecompositionEnumeratorFactory().produce();
        runTimeLimited(enumerator);
        List<ITreeDecomposition> treeDecompositions = new ArrayList<>(enumerator.getDecompositions());

//        StoringParallelMinimalTriangulationsEnumerator enumerator = (StoringParallelMinimalTriangulationsEnumerator) new MinimalTriangulationsEnumeratorFactory().produce();
//        runTimeLimited(enumerator);
//        Set<IChordalGraph> chordalGraphs = enumerator.getTriangulations();
//
//        List<ITreeDecomposition> treeDecompositions = chordalGraphs.stream().map(chordalGraph ->{ chordalGraphs.remove(chordalGraph); return Converter.chordalGraphToNiceTreeDecomposition(chordalGraph); } ).collect(Collectors.toCollection(ArrayList::new));
        System.out.println(treeDecompositions.size() + " TD where produced");
        List<EvaluationDetails> real = predict(treeDecompositions, inputFile);
        printPredictionsToCSV(real, "real", inputFile.getPath(),treeDecompositions.size());

    }

    private void printPredictionsToCSV(List<EvaluationDetails> evaluations, String mode, String filePath, int results) {

        TDKMLConfiguration configuration = (TDKMLConfiguration) TDKEnumFactory.getConfiguration();
        File csvFile = new File(configuration.getDatasetPath() + "/models/models evaluation.csv");
        if(!csvFile.exists())
        {
            csvFile.getParentFile().mkdirs();
            try (PrintWriter writer = new PrintWriter(csvFile)) {
                writer.println(CSVOperations.dataToCSV("file path", "mode",  "enumeration_timeout","results", "features extraction overhead","model", "model_overhead",
                        "first TD prediction", "first TD actual runtime",
                        "optimal TD prediction", "optimal TD actual runtime"
                      ));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        try ( PrintWriter writer = new PrintWriter(new FileOutputStream(csvFile, true))){

            for (EvaluationDetails evaluationDetails : evaluations)
            {
                writer.println(CSVOperations.dataToCSV(filePath,mode, TDKEnumFactory.getConfiguration().getTime_limit(), results, evaluationDetails.toCSV()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    class EvaluationDetails
    {

        EvaluationInput firstDecomposition = null;
        EvaluationInput optimalDecomposition = null;
        EvaluationInput preferredDecomposition = null;
        EvaluationInput beneficialDecomposition = null;
        MLModelType mlModelType;
        double modelOverhead;
        double extractionOverhead;


        public EvaluationInput getFirstDecomposition() {
            return firstDecomposition;
        }

        public void setFirstDecomposition(EvaluationInput firstDecomposition) {
            this.firstDecomposition = firstDecomposition;
        }

        public EvaluationInput getOptimalDecomposition() {
            return optimalDecomposition;
        }

        public void setOptimalDecomposition(EvaluationInput optimalDecomposition) {
            this.optimalDecomposition = optimalDecomposition;
        }

        public EvaluationInput getPreferredDecomposition() {
            return preferredDecomposition;
        }

        public void setPreferredDecomposition(EvaluationInput preferredDecomposition) {
            this.preferredDecomposition = preferredDecomposition;
        }

        public EvaluationInput getBeneficialDecomposition() {
            return beneficialDecomposition;
        }

        public void setBeneficialDecomposition(EvaluationInput beneficialDecomposition) {
            this.beneficialDecomposition = beneficialDecomposition;
        }

        public MLModelType getMlModelType() {
            return mlModelType;
        }

        public void setMlModelType(MLModelType mlModelType) {
            this.mlModelType = mlModelType;
        }

        public double getModelOverhead() {
            return modelOverhead;
        }

        public void setModelOverhead(double overhead) {
            this.modelOverhead = overhead;
        }

        public double getExtractionOverhead() {
            return extractionOverhead;
        }

        public void setExtractionOverhead(double extractionOverhead) {
            this.extractionOverhead = extractionOverhead;
        }

        public String toCSV()
        {
            return CSVOperations.dataToCSV(extractionOverhead, mlModelType, modelOverhead,

                    firstDecomposition.getPredictedValue(), firstDecomposition.getActualValue(),
                    optimalDecomposition.getPredictedValue(), optimalDecomposition.getActualValue());
//                    preferredDecomposition.getPredictedValue(), preferredDecomposition.getActualValue(),
//                    beneficialDecomposition.getPredictedValue(), beneficialDecomposition.getActualValue());
        }
    }

    private List<EvaluationDetails> predict(List<ITreeDecomposition> treeDecompositions, InputFile inputFile) {
//        DecimalFormat format =
//                new DecimalFormat("0.000");
//
//        StringBuilder resultHeaderBuilder = new StringBuilder();
//        StringBuilder resultStringBuilder = new StringBuilder();
//
//        long start =
//                System.currentTimeMillis();
//        List<DecompositionDetails> allDecompositionDetails = new ArrayList<>();
//        IClassifier classifier = new ClassifierFactory().produce();
//        TDKMLConfiguration configuration = (TDKMLConfiguration)TDKEnumFactory.getConfiguration();
//        classifier.loadModels(configuration.getDatasetPath()+"/models");
//
//        IFeatureExtractor featureExtractor = new FeatureExtractorFactory().produce();
//        for (int i = 0; i < treeDecompositions.size(); i++)
//        {
//            DecompositionDetails details = DecompositionDetails.getInstance(featureExtractor.getFeatures(i,treeDecompositions.get(i),TDKEnumFactory.getGraph() ));
//            allDecompositionDetails.add(details);
//        }

//        DecompositionPool decompositionPool = DecompositionPool.createDecompositionPool(allDecompositionDetails, TDKEnumFactory.getInputFile().getFile());
//        DecompositionPool transformedDecompositionPool = decompositionPool;
//
//        switch (configuration.getTransformationMode()) {
//            case NORMALIZE: {
//                transformedDecompositionPool = decompositionPool.normalize();
//
//                break;
//            }
//            case STANDARDIZE: {
//                transformedDecompositionPool = decompositionPool.standardize();
//
//                break;
//            }
//            default: {
//                break;
//            }
//        }
//
//        DecompositionPool normalizedDecompositionPool = decompositionPool.normalize();
//        DecompositionPool standardizedDecompositionPool = decompositionPool.standardize();
//
//        double modelOverhead =
//                (double)(Math.max(System.currentTimeMillis() - start , 0.0)) / 1000;
//        resultHeaderBuilder.append("PreparationOverhead,");
//        resultStringBuilder.append(StringOperations.formatNumber(format, modelOverhead));
//        resultStringBuilder.append(",");


//        List<SelectionTemplate> selectionTemplates = classifier.predictDecompositions(transformedDecompositionPool, modelLoadPath);

        TDKMLConfiguration configuration = (TDKMLConfiguration)TDKEnumFactory.getConfiguration();
        long start = System.currentTimeMillis();
        List<DecompositionDetails> allDecompositionDetails = new ArrayList<>();
        IFeatureExtractor featureExtractor = new FeatureExtractorFactory().produce();

        allDecompositionDetails = IntStream.range(0,treeDecompositions.size()).parallel().mapToObj(i -> DecompositionDetails.getInstance(featureExtractor.getFeatures(i,treeDecompositions.get(i), TDKEnumFactory.getMlGraph() ))).collect(Collectors.toList());

//        for (int i = 0; i < treeDecompositions.size(); i++)
//        {
//            DecompositionDetails details = DecompositionDetails.getInstance(featureExtractor.getFeatures(i,treeDecompositions.get(i),TDKEnumFactory.getGraph() ));
//            allDecompositionDetails.add(details);
//        }

        DecompositionPool decompositionPool = DecompositionPool.createDecompositionPool(allDecompositionDetails, TDKEnumFactory.getInputFile().getFile());
        double featuresExtractionOverhead =
                (double)(Math.max(System.currentTimeMillis() - start , 0.0)) / 1000;
        List<SelectionTemplate> selectionTemplates = classifier.predictDecompositions(decompositionPool, configuration.getDatasetPath()+"/models");
//        List<SelectionTemplate> selectionTemplates = classifier.predictDecompositions(treeDecompositions, configuration.getDatasetPath()+"/models");
        BenchmarkDetails benchmarkDetails = getPredictedTDBenchmarkDetails(selectionTemplates, treeDecompositions, inputFile);
        Set<Integer> tdIDs = new HashSet<>();
        for (SelectionTemplate selectionTemplate : selectionTemplates)
        {
            tdIDs.add(selectionTemplate.getFirstId());
            tdIDs.add(selectionTemplate.getBeneficialId());
            tdIDs.add(selectionTemplate.getOptimalId());
            tdIDs.add(selectionTemplate.getPreferredId());
        }
        List<DecompositionDetails> benchmarkedDecompositionDetails = new ArrayList<>();

        for (Integer tdId: tdIDs)
        {
            DecompositionDetails details = DecompositionDetails.getInstance(featureExtractor.getFeatures(tdId,treeDecompositions.get(tdId),TDKEnumFactory.getMlGraph()));
            benchmarkedDecompositionDetails.add(details);

        }



        DecompositionPool benchmarkedDecompositionPool = DecompositionPool.createDecompositionPool(benchmarkedDecompositionDetails, TDKEnumFactory.getInputFile().getFile());

        List<EvaluatedDecompositionDetails> details =
                getEvaluatedDecompositionDetails(benchmarkDetails, benchmarkedDecompositionPool);
//
//        List<EvaluatedDecompositionDetails> normalizedDetails =
//                getEvaluatedDecompositionDetails(benchmarkDetails, normalizedDecompositionPool);
//
//        List<EvaluatedDecompositionDetails> transformedDetails =
//                getEvaluatedDecompositionDetails(benchmarkDetails, transformedDecompositionPool);
//
//        List<EvaluatedDecompositionDetails> standardizedDetails =
//                getEvaluatedDecompositionDetails(benchmarkDetails, standardizedDecompositionPool);



        List<EvaluationDetails> evaluation = new ArrayList<>();

        for (SelectionTemplate selectionTemplate : selectionTemplates) {
            if (selectionTemplate != null) {
                EvaluatedDecompositionDetails firstDecomposition = null;
                EvaluatedDecompositionDetails optimalDecomposition = null;
                EvaluatedDecompositionDetails preferredDecomposition = null;
                EvaluatedDecompositionDetails beneficialDecomposition = null;

                String headerPrefix = selectionTemplate.getMlModelType().name();


//
//                resultHeaderBuilder.append(headerPrefix);
//                resultHeaderBuilder.append("SelectionOverhead,");
//                resultStringBuilder.append(StringOperations.formatNumber(format, selectionTemplate.getSelectionOverhead()));
//                resultStringBuilder.append(",");

                //List<EvaluationInput> evaluationInput = new ArrayList<>();

                firstDecomposition = details.stream().filter(
                        evaluatedDecompositionDetails -> evaluatedDecompositionDetails.getTdId()==selectionTemplate.getFirstId()).
                        findFirst().get();
                optimalDecomposition = details.stream().filter(
                        evaluatedDecompositionDetails -> evaluatedDecompositionDetails.getTdId()==selectionTemplate.getOptimalId()).
                        findFirst().get();
                preferredDecomposition = details.stream().filter(
                        evaluatedDecompositionDetails -> evaluatedDecompositionDetails.getTdId()==selectionTemplate.getPreferredId()).
                        findFirst().get();
                beneficialDecomposition = details.stream().filter(
                        evaluatedDecompositionDetails -> evaluatedDecompositionDetails.getTdId()==selectionTemplate.getBeneficialId()).
                        findFirst().get();

                
                EvaluationDetails evaluationDetails = new EvaluationDetails();
                evaluationDetails.setMlModelType( selectionTemplate.getMlModelType());
                evaluationDetails.setFirstDecomposition(new EvaluationInput(firstDecomposition.getActualRuntime(), selectionTemplate.getFirstPredictedRuntime()));
                evaluationDetails.setOptimalDecomposition(new EvaluationInput(optimalDecomposition.getActualRuntime(), selectionTemplate.getOptimalPredictedRuntime()));
                evaluationDetails.setPreferredDecomposition(new EvaluationInput(preferredDecomposition.getActualRuntime(), selectionTemplate.getPreferredPredictedRuntime()));
                evaluationDetails.setBeneficialDecomposition(new EvaluationInput(beneficialDecomposition.getActualRuntime(), selectionTemplate.getBeneficialPredictedRuntime()));
                evaluationDetails.setModelOverhead(selectionTemplate.getSelectionOverhead());
                evaluationDetails.setExtractionOverhead(featuresExtractionOverhead);
                evaluation.add(evaluationDetails);

//                for (EvaluatedDecompositionDetails decomposition : transformedDetails) {
//                    if (decomposition != null) {
//                        if (decomposition.getTdId() == selectionTemplate.getFirstId()) {
//                            firstDecomposition = decomposition;
//                        }
//
//                        if (decomposition.getTdId() == selectionTemplate.getOptimalId()) {
//                            optimalDecomposition = decomposition;
//                        }
//
//                        if (decomposition.getTdId() == selectionTemplate.getPreferredId()) {
//                            preferredDecomposition = decomposition;
//                        }
//
//                        if (decomposition.getTdId() == selectionTemplate.getBeneficialId()) {
//                            beneficialDecomposition = decomposition;
//                        }
//
////                        EvaluationInput input =
////                                getEvaluationInput(decomposition, selectionTemplate, runtimes);
//
//                        EvaluationInput input = new EvaluationInput(decomposition.getActualRuntime(), )
//
//
//                        if (input != null) {
//                            evaluationInput.add(input);
//                        }
//                    }
//                }

             //   evaluation.put(selectionTemplate.getMlModelType(), evaluationInput);

//                resultHeaderBuilder.append(getEvaluatedDecompositionDetailsHeader(headerPrefix, "Random"));
//                resultStringBuilder.append(getEvaluatedDecompositionDetailsString(firstDecomposition, selectionTemplate));
//                resultHeaderBuilder.append(",");
//                resultStringBuilder.append(",");
//                resultHeaderBuilder.append(getEvaluatedDecompositionDetailsHeader(headerPrefix, "Beneficial"));
//                resultStringBuilder.append(getEvaluatedDecompositionDetailsString(beneficialDecomposition, selectionTemplate));
//                resultHeaderBuilder.append(",");
//                resultStringBuilder.append(",");
//                resultHeaderBuilder.append(getEvaluatedDecompositionDetailsHeader(headerPrefix, "Preferred"));
//                resultStringBuilder.append(getEvaluatedDecompositionDetailsString(preferredDecomposition, selectionTemplate));
//                resultHeaderBuilder.append(",");
//                resultStringBuilder.append(",");
//                resultHeaderBuilder.append(getEvaluatedDecompositionDetailsHeader(headerPrefix, "Optimal"));
//                resultStringBuilder.append(getEvaluatedDecompositionDetailsString(optimalDecomposition, selectionTemplate));
//                resultHeaderBuilder.append(",");
//                resultStringBuilder.append(",");
            }

        }
//        File modelTargetFile =
//                Paths.get(modelLoadPath).toAbsolutePath().resolve("model_results.csv").toFile();
//
//        MemoryFile modelTargetFileContent =
//                MemoryFile.getInstance(modelTargetFile, modelTargetFile.toString());
//
//        modelTargetFileContent.appendLine("Model,Correlation,MeanAbsoluteError,RelativeAbsoluteError,RootMeanSquareError,RootRelativeSquareError");
//
//        for(MLModelType modelType : evaluation.keySet())
//        {
//            List<EvaluationInput> evaluationInput = evaluation.get(modelType);
//            String modelName = "Model " + modelType.name();
//
//
//
//            double correlation = EvaluationOperations.getPearsonsCorrelationCoefficient(evaluationInput);
//
//            double meanAbsoluteError = EvaluationOperations.getMeanAbsoluteError(evaluationInput);
//            double relativeAbsoluteError = EvaluationOperations.getRelativeAbsoluteError(evaluationInput) * 100.0;
//
//            double rootMeanSquareError = EvaluationOperations.getRootMeanSquareError(evaluationInput);
//            double rootRelativeSquareError = EvaluationOperations.getRootRelativeSquareError(evaluationInput) * 100.0;
//
//            System.out.println(modelName + ":");
//            System.out.println();
//            System.out.println("   Correlation:                " + String.format("%12s", StringOperations.formatNumber(format, correlation)));
//            System.out.println();
//            System.out.println("   Mean Absolute Error:        " + String.format("%12s", StringOperations.formatNumber(format, meanAbsoluteError)));
//            System.out.println("   Relative Absolute Error:    " + String.format("%12s", StringOperations.formatNumber(format, relativeAbsoluteError)) + " %");
//            System.out.println();
//            System.out.println("   Root Mean Square Error:     " + String.format("%12s", StringOperations.formatNumber(format, rootMeanSquareError)));
//            System.out.println("   Root Relative Square Error: " + String.format("%12s", StringOperations.formatNumber(format, rootRelativeSquareError)) + " %");
//            System.out.println();
//
//            StringBuilder sb = new StringBuilder();
//
//            sb.append("\"");
//            sb.append(modelName);
//            sb.append("\"");
//            sb.append(",");
//            sb.append(StringOperations.formatNumber(format, correlation));
//            sb.append(",");
//            sb.append(StringOperations.formatNumber(format, meanAbsoluteError));
//            sb.append(",");
//            sb.append(StringOperations.formatNumber(format, relativeAbsoluteError));
//            sb.append(",");
//            sb.append(StringOperations.formatNumber(format, rootMeanSquareError));
//            sb.append(",");
//            sb.append(StringOperations.formatNumber(format, rootRelativeSquareError));
//            modelTargetFileContent.appendLine(sb.toString());
//        }
//
//        File evaluationTargetFile =
//                Paths.get(modelLoadPath).toAbsolutePath().resolve("evaluation_results.csv").toFile();
//
//        File performanceTargetFile =
//                Paths.get(modelLoadPath).toAbsolutePath().resolve("performance_results.csv").toFile();
//
//        File featureTargetFile =
//                Paths.get(modelLoadPath).toAbsolutePath().resolve("feature_results.csv").toFile();
//
//        File normalizedFeatureTargetFile =
//                Paths.get(modelLoadPath).toAbsolutePath().resolve("feature_results_normalized.csv").toFile();
//
//        File standardizedFeatureTargetFile =
//                Paths.get(modelLoadPath).toAbsolutePath().resolve("feature_results_standardized.csv").toFile();
//
//        MemoryFile featureTargetFileContent =
//                MemoryFile.getInstance(featureTargetFile, featureTargetFile.toString());
//
//        MemoryFile normalizedFeatureTargetFileContent =
//                MemoryFile.getInstance(normalizedFeatureTargetFile, normalizedFeatureTargetFile.toString());
//
//        MemoryFile standardizedFeatureTargetFileContent =
//                MemoryFile.getInstance(standardizedFeatureTargetFile, standardizedFeatureTargetFile.toString());
//
//        if (details.isEmpty()) {
//            //TODO
//        }
//        else
//        {
//            boolean firstResult = true;
//
//            List<Double> normalizedRuntimes = new ArrayList<>();
//            List<Double> standardizedRuntimes = new ArrayList<>();
//
//            for (EvaluatedDecompositionDetails decomposition: details) {
//                if (decomposition != null) {
//                    normalizedRuntimes.add(decomposition.getActualRuntime());
//                    standardizedRuntimes.add(decomposition.getActualRuntime());
//                }
//                else {
//                    normalizedRuntimes.add(Double.NaN);
//                    standardizedRuntimes.add(Double.NaN);
//                }
//            }
//
//            normalizedRuntimes =
//                    TransformationOperations.normalize(normalizedRuntimes);
//
//            standardizedRuntimes =
//                    TransformationOperations.standardize(standardizedRuntimes);
//            for (int i = 0; i < details.size(); i++) {
//                EvaluatedDecompositionDetails decompositionDetails = details.get(i);
//                EvaluatedDecompositionDetails normalizedDecompositionDetails = normalizedDetails.get(i);
//                EvaluatedDecompositionDetails standardizedDecompositionDetails = standardizedDetails.get(i);
//
//                if (decompositionDetails != null) {
//                    double runtime = benchmarkDetails.getBenchmarkRuns().stream().filter(
//                            benchmarkRun -> benchmarkRun.getTdID() != decompositionDetails.getTdId()).
//                            findFirst().get().getUserTime();
//                            //runtimes.get(i);
//
//                    if (firstResult) {
//                        firstResult = false;
//
//                        featureTargetFileContent.appendLine(DecompositionDetails.getCSVHeader());
//                        normalizedFeatureTargetFileContent.appendLine(DecompositionDetails.getCSVHeader());
//                        standardizedFeatureTargetFileContent.appendLine(DecompositionDetails.getCSVHeader());
//                    }
//
//                    featureTargetFileContent.appendLine(decompositionDetails.toCSV(runtime));
//                }
//
//                if (normalizedDecompositionDetails != null) {
//                    double normalizedRuntime =
//                            normalizedRuntimes.get(i);
//
//                    normalizedFeatureTargetFileContent.appendLine(normalizedDecompositionDetails.toCSV(normalizedRuntime));
//                }
//
//                if (standardizedDecompositionDetails != null) {
//                    double standardizedRuntime =
//                            standardizedRuntimes.get(i);
//
//                    standardizedFeatureTargetFileContent.appendLine(standardizedDecompositionDetails.toCSV(standardizedRuntime));
//                }
//            }
//
//            MemoryFile evaluationTargetFileContent =
//                    MemoryFile.getInstance(evaluationTargetFile, evaluationTargetFile.toString());
//
//            MemoryFile performanceTargetFileContent =
//                    benchmarkDetails.toCSVFile(performanceTargetFile);
//
//            evaluationTargetFileContent.appendLine(resultHeaderBuilder.toString());
//            evaluationTargetFileContent.appendLine(resultStringBuilder.toString());
//
//
//            System.out.println();
//            System.out.println();
//            System.out.println("Additional actions:");
//            printFile(modelTargetFileContent, modelTargetFile, "model");
//            printFile(evaluationTargetFileContent, evaluationTargetFile, "evaluation");
//            printFile(performanceTargetFileContent, performanceTargetFile, "performance" );
//            printFile(featureTargetFileContent, featureTargetFile, "feature");
//            printFile(normalizedFeatureTargetFileContent,normalizedFeatureTargetFile, "normalized feature" );
//            printFile(standardizedFeatureTargetFileContent,standardizedFeatureTargetFile, "standardized feature" );


//        }

        return evaluation;
    }

    private void printFile(MemoryFile targetFileContent, File targetFile, String fileDataName) {

        System.out.println();

        System.out.println("   Exporting " + fileDataName +" results to the following file:");
        System.out.println("      " + targetFile.getAbsolutePath());
        if (targetFileContent.export(targetFile, true)) {
            System.out.println("   OPERATION FINISHED SUCCESSFULLY!");
        }
        else {
            System.out.println("   ERROR WHILE WRITING FILE!");
            System.out.println();
            System.out.println("   The intended content of the file is ...");
            System.out.println();

            targetFileContent.print();

            System.out.println();
            System.out.println();
        }
    }

    private static List<EvaluatedDecompositionDetails> getEvaluatedDecompositionDetails(BenchmarkDetails benchmarkDetails, DecompositionPool decompositionPool) {
        List<EvaluatedDecompositionDetails> ret = new ArrayList<>();

        if (benchmarkDetails != null && decompositionPool != null) {
            List<BenchmarkRun> benchmarkRuns =
                    benchmarkDetails.getBenchmarkRuns();

            List<DecompositionDetails> decompositions =
                    decompositionPool.accessDecompositions();

            if (benchmarkRuns != null &&
                    decompositions != null
                 ) {

                for (int i = 0; i < benchmarkRuns.size(); i++) {
                    BenchmarkRun benchmarkRun = benchmarkRuns.get(i);
                    DecompositionDetails decomposition = decompositions.stream().filter(decompositionDetails ->decompositionDetails.getTdId() == benchmarkRun.getTdID()).findFirst().get();

                    PredictedDecompositionDetails predictedDecompositionDetails =
                            PredictedDecompositionDetails.getInstance(decomposition);

                    if (predictedDecompositionDetails != null) {
                        EvaluatedDecompositionDetails evaluatedDecompositionDetails =
                                EvaluatedDecompositionDetails.getInstance(predictedDecompositionDetails,
                                        benchmarkRun.getUserTime(),
                                        benchmarkRun.getMemoryConsumption(),
                                        benchmarkRun.getExitCode(),
                                        benchmarkRun.isMemoryError(),
                                        benchmarkRun.isTimeoutError(),
                                        benchmarkRun.isExitCodeError());

                        if (evaluatedDecompositionDetails != null) {
                            ret.add(evaluatedDecompositionDetails);
                        }
                    }
                }
            }
        }
        return  ret;
    }


    private List<ITreeDecomposition> getFirst40(ITreeDecompositionEnumerator enumerator) {
        List<ITreeDecomposition> decompositions = new ArrayList<>();
        while(enumerator.hasNext() && decompositions.size()<40)
        {
            decompositions.add(enumerator.next());
        }
        return decompositions;
    }

    private ITreeDecompositionEnumerator createSingleThreadRandomEnumerator() {
        TDKEnumConfiguration configuration = TDKEnumFactory.getConfiguration();
        TDKTreeDecompositionEnumConfiguration  decompositionEnumConfiguration = new TDKTreeDecompositionEnumConfiguration();
        decompositionEnumConfiguration.setEnumerationType(EnumerationType.NICE_TD);
        decompositionEnumConfiguration.setMinimalTriangulatorType(MinimalTriangulatorType.RANDOM);
        decompositionEnumConfiguration.setRunningMode(RunningMode.SINGLE_THREAD);
        decompositionEnumConfiguration.setSeparatorsGraphType(SeparatorsGraphType.CACHED);
        decompositionEnumConfiguration.setSingleThreadMISEnumeratorType(SingleThreadMISEnumeratorType.OPTIMIZED_CKK);
        TDKMLConfiguration tdkmlConfiguration = (TDKMLConfiguration) configuration;
        decompositionEnumConfiguration.setTriangulationAlgorithm(tdkmlConfiguration.getTriangulationAlgorithm());
        TDKEnumFactory.setConfiguration(decompositionEnumConfiguration);
        ITreeDecompositionEnumerator enumerator = new NiceTreeDecompositionEnumeratorFactory().produce();
        TDKEnumFactory.setConfiguration(configuration);
        return enumerator;

    }

    private ITreeDecompositionEnumerator creatSingleThreadEnumerator() {
       return createSingleThreadEnumerator(false);

    }

    private ITreeDecompositionEnumerator createSingleThreadEnumerator(boolean rand)
    {
        TDKEnumConfiguration configuration = TDKEnumFactory.getConfiguration();
        TDKTreeDecompositionEnumConfiguration  decompositionEnumConfiguration = new TDKTreeDecompositionEnumConfiguration();
        if(rand)
        {
            decompositionEnumConfiguration.setMinimalTriangulatorType(MinimalTriangulatorType.RANDOM);
        }

        decompositionEnumConfiguration.setEnumerationType(EnumerationType.NICE_TD);
        decompositionEnumConfiguration.setRunningMode(RunningMode.SINGLE_THREAD);
        TDKMLConfiguration tdkmlConfiguration = (TDKMLConfiguration) configuration;
        decompositionEnumConfiguration.setTriangulationAlgorithm(tdkmlConfiguration.getTriangulationAlgorithm());
        TDKEnumFactory.setConfiguration(decompositionEnumConfiguration);
        ITreeDecompositionEnumerator enumerator = new NiceTreeDecompositionEnumeratorFactory().produce();
        TDKEnumFactory.setConfiguration(configuration);
        return enumerator;
    }

    private ITreeDecompositionEnumerator createVanillaEnumerator()
    {
        TDKEnumConfiguration configuration = TDKEnumFactory.getConfiguration();
        TDKTreeDecompositionEnumConfiguration  decompositionEnumConfiguration = new TDKTreeDecompositionEnumConfiguration();
        decompositionEnumConfiguration.setEnumerationType(EnumerationType.NICE_TD);
        decompositionEnumConfiguration.setRunningMode(RunningMode.SINGLE_THREAD);
        decompositionEnumConfiguration.setSeparatorsGraphType(SeparatorsGraphType.VANILLA);
        decompositionEnumConfiguration.setSingleThreadMISEnumeratorType(SingleThreadMISEnumeratorType.CKK);
        TDKMLConfiguration tdkmlConfiguration = (TDKMLConfiguration) configuration;
        decompositionEnumConfiguration.setTriangulationAlgorithm(tdkmlConfiguration.getTriangulationAlgorithm());
        TDKEnumFactory.setConfiguration(decompositionEnumConfiguration);
        ITreeDecompositionEnumerator enumerator = new NiceTreeDecompositionEnumeratorFactory().produce();
        TDKEnumFactory.setConfiguration(configuration);
        return enumerator;
    }



    private static String getEvaluatedDecompositionDetailsHeader(String prefix, String suffix) {
        StringBuilder sb =
                new StringBuilder();

        String actualPrefix = "";
        String actualSuffix = "";

        if (prefix != null) {
            actualPrefix = prefix;
        }

        if (suffix != null) {
            actualSuffix = suffix;
        }

        sb.append(actualPrefix);
        sb.append("TDId");
        sb.append(actualSuffix);
        sb.append(",");

        sb.append(actualPrefix);
        sb.append("PredictedRuntime");
        sb.append(actualSuffix);
        sb.append(",");

//        sb.append(actualPrefix);
//        sb.append("PredictedRank");
//        sb.append(actualSuffix);
//        sb.append(",");

        sb.append(actualPrefix);
        sb.append("ActualRuntime");
        sb.append(actualSuffix);
        sb.append(",");

        sb.append(actualPrefix);
        sb.append("MemoryConsumption");
        sb.append(actualSuffix);
        sb.append(",");

//        sb.append(actualPrefix);
//        sb.append("ActualRelaxedRank");
//        sb.append(actualSuffix);
//        sb.append(",");
//
//        sb.append(actualPrefix);
//        sb.append("ActualConcreteRank");
//        sb.append(actualSuffix);
//        sb.append(",");

        sb.append(actualPrefix);
        sb.append("ExitCode");
        sb.append(actualSuffix);
        sb.append(",");

        sb.append(actualPrefix);
        sb.append("MemoryError");
        sb.append(actualSuffix);
        sb.append(",");

        sb.append(actualPrefix);
        sb.append("TimeoutError");
        sb.append(actualSuffix);
        sb.append(",");

        sb.append(actualPrefix);
        sb.append("ExitCodeError");
        sb.append(actualSuffix);

        return sb.toString();
    }

    private static String getEvaluatedDecompositionDetailsString(EvaluatedDecompositionDetails decompositionDetails, SelectionTemplate selectionTemplate) {
        StringBuilder sb =
                new StringBuilder();

        DecimalFormat format =
                new DecimalFormat("0.000");

        if (decompositionDetails != null) {
            sb.append(decompositionDetails.getTdId());
            sb.append(",");

            int predictedRank = 1;
            double predictedRuntime = Double.NaN;

            if (selectionTemplate != null) {
                predictedRank = selectionTemplate.getPredictedRankForId(decompositionDetails.getTdId());
                predictedRuntime = selectionTemplate.getPredictedRuntimeForId(decompositionDetails.getTdId());
            }

            if (!Double.isNaN(predictedRuntime)) {
                sb.append(StringOperations.formatNumber(format, predictedRuntime));
            }
            else {
                sb.append("-1");
            }

//            sb.append(",");
//            sb.append(predictedRank);
//            sb.append(",");

            double actualRuntime =
                    decompositionDetails.getActualRuntime();

            if (decompositionDetails.isValidMeasurement() && !Double.isNaN(actualRuntime) && actualRuntime >= 0.0) {
                sb.append(StringOperations.formatNumber(format, actualRuntime));
            }
            else {
                sb.append("-1");
            }

            sb.append(",");

            double memoryConsumption =
                    decompositionDetails.getMemoryConsumption();

            if (decompositionDetails.isValidMeasurement() && !Double.isNaN(memoryConsumption) && memoryConsumption >= 0.0) {
                sb.append(StringOperations.formatNumber(format, memoryConsumption));
            }
            else {
                sb.append("-1");
            }

//            sb.append(",");
//            sb.append(PerformanceRanking.getRank(actualRuntime, actualRuntimes, 5.0));
//            sb.append(",");
//            sb.append(PerformanceRanking.getRank(actualRuntime, actualRuntimes));
//            sb.append(",");

            sb.append(decompositionDetails.getExitCode());
            sb.append(",");

            sb.append(decompositionDetails.isMemoryErrorOccurred() ? 1 : 0);
            sb.append(",");
            sb.append(decompositionDetails.isTimeoutErrorOccurred() ? 1 : 0);
            sb.append(",");
            sb.append(decompositionDetails.isExitCodeErrorOccurred() ? 1 : 0);
        }
        else {
//            sb.append("-1,");
//            sb.append("-1,");
//            sb.append("-1,");
            sb.append("-1,");
            sb.append("-1,");
            sb.append("-1,");
            sb.append("-1,");
            sb.append("-1,");
            sb.append("-1,");
            sb.append("-1");
        }

        return sb.toString();
    }

    BenchmarkDetails getPredictedTDBenchmarkDetails(List<SelectionTemplate> selectionTemplates, List<ITreeDecomposition> treeDecompositions, InputFile inputFile)
    {
        ISolver solver = new SolverFactory().produce();
        Set<Integer>tdIDs = new HashSet<>();
        for (SelectionTemplate selectionTemplate : selectionTemplates)
        {
            tdIDs.add(selectionTemplate.getFirstId());
            tdIDs.add(selectionTemplate.getBeneficialId());
            tdIDs.add(selectionTemplate.getOptimalId());
            tdIDs.add(selectionTemplate.getPreferredId());
        }
        List<TreeDecompositionMeta> treeDecompositionMetas = new ArrayList<>();
        for (Integer tdId: tdIDs)
        {
            treeDecompositionMetas.add(new TreeDecompositionMeta(treeDecompositions.get(tdId), tdId, inputFile.getName(), inputFile.getFile()));

        }
        treeDecompositionMetas.parallelStream().forEach(treeDecompositionMeta -> {
            treeDecompositionMeta.setCommandResult(solver.solve(inputFile.getFile(), treeDecompositionMeta.getFile()));
            treeDecompositionMeta.deleteFile();
        });


        BenchmarkDetails benchmarkDetails = new BenchmarkDetails();
        for(TreeDecompositionMeta treeDecompositionMeta : treeDecompositionMetas)
        {
            benchmarkDetails.addBenchmarkRun(treeDecompositionMeta.getBenchmarkRun());
        }
        return  benchmarkDetails;

    }


}
