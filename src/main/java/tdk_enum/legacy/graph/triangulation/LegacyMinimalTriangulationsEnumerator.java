package tdk_enum.legacy.graph.triangulation;

import tdk_enum.enumerators.independent_set.scoring.single_thread.IndSetScorerByTriangulation;
import tdk_enum.enumerators.triangulation.minimal_triangulators.MinimalTriangulator;
import tdk_enum.common.configuration.config_types.TriangulationAlgorithm;
import tdk_enum.common.configuration.config_types.TriangulationScoringCriterion;
import tdk_enum.graph.data_structures.MinimalSeparator;
import tdk_enum.graph.graphs.Graph;
import tdk_enum.graph.graphs.succinct_graphs.separator_graph.single_thread.SeparatorGraph;
import tdk_enum.graph.graphs.chordal_graph.IChordalGraph;
import tdk_enum.graph.graphs.IGraph;
import tdk_enum.enumerators.independent_set.set_extender.single_thread.IndSetExtBySeparators;
import tdk_enum.enumerators.independent_set.set_extender.single_thread.IndSetExtByTriangulation;
import tdk_enum.legacy.graph.independent_set.LegacyMaximalIndependentSetsEnumerator;
import tdk_enum.common.configuration.config_types.SeparatorsScoringCriterion;
import tdk_enum.graph.graphs.Converter;

import static tdk_enum.common.configuration.config_types.TriangulationAlgorithm.SEPARATORS;

/**
 * Created by dvir.dukhan on 7/11/2017.
 */
public class LegacyMinimalTriangulationsEnumerator {

    IGraph graph;
    SeparatorGraph seperatorGraph;
    MinimalTriangulator triangulator;
    IndSetExtByTriangulation triExtender;
    IndSetExtBySeparators sepExtender;
    IndSetScorerByTriangulation scorer;
    LegacyMaximalIndependentSetsEnumerator<MinimalSeparator> setsEnumerator;

    public LegacyMinimalTriangulationsEnumerator(final IGraph g, TriangulationScoringCriterion triC,
                                                 SeparatorsScoringCriterion sepC, TriangulationAlgorithm heuristic)
    {
        graph = new Graph(g);
        seperatorGraph = new SeparatorGraph(graph, sepC);
        triangulator = new MinimalTriangulator(heuristic);
        scorer = new IndSetScorerByTriangulation(graph, triC);

        triExtender = new IndSetExtByTriangulation(graph, triangulator);

        sepExtender = new IndSetExtBySeparators(graph);
        setsEnumerator = new LegacyMaximalIndependentSetsEnumerator<MinimalSeparator>(seperatorGraph, triExtender, scorer);
        if (heuristic == SEPARATORS){
            setsEnumerator = new LegacyMaximalIndependentSetsEnumerator<MinimalSeparator>(seperatorGraph, sepExtender, scorer);
        }


//
//        Properties prop = new Properties();
//        try (InputStream input = new FileInputStream("config.properties"))
//        {
//              prop.load(input);
//              if (Boolean.valueOf(prop.getProperty("singleThreadImprovements")))
//              {
//                  System.out.println("using single thread improving mechanism");
//                  if (Boolean.valueOf(prop.getProperty("cacheEdgesBetweenSeparators")))
//                  {
//                      seperatorGraph = new CachableSeparatorGraph(graph, sepC);
//                  }
//                  if (Boolean.valueOf(prop.getProperty("logging")))
//                  {
//                      triExtender = new LoggableIndSetExtByTriangulation(graph, triangulator);
//                      sepExtender = new LoggableIndSetExtBySeparators(graph);
//                      setsEnumerator = new ImprovedMaximalIndependentSetsEnumerator<MinimalSeparator>(seperatorGraph, triExtender, scorer);
//                      if (heuristic == SEPARATORS){
//                          setsEnumerator = new ImprovedMaximalIndependentSetsEnumerator<MinimalSeparator>(seperatorGraph, sepExtender, scorer);
//                      }
//
//                  }
//                  else
//                  {
//                      triExtender = new IndSetExtByTriangulation(graph, triangulator);
//
//                      sepExtender = new IndSetExtBySeparators(graph);
//                      setsEnumerator = new ImprovedMaximalIndependentSetsEnumerator<MinimalSeparator>(seperatorGraph, triExtender, scorer);
//                      if (heuristic == SEPARATORS){
//                          setsEnumerator = new ImprovedMaximalIndependentSetsEnumerator<MinimalSeparator>(seperatorGraph, sepExtender, scorer);
//                      }
//                  }
//
//              }
//              else if (Boolean.valueOf(prop.getProperty("logging")))
//              {
//                  triExtender = new LoggableIndSetExtByTriangulation(graph, triangulator);
//                  sepExtender = new LoggableIndSetExtBySeparators(graph);
//                  setsEnumerator = new LoggableMaximalIndependentSetsEnumerator<MinimalSeparator>(seperatorGraph, triExtender, scorer);
//                  if (heuristic == SEPARATORS){
//                      setsEnumerator = new LoggableMaximalIndependentSetsEnumerator<MinimalSeparator>(seperatorGraph, sepExtender, scorer);
//                  }
//
//              }
//              else
//              {
//                  triExtender = new IndSetExtByTriangulation(graph, triangulator);
//
//                  sepExtender = new IndSetExtBySeparators(graph);
//                  setsEnumerator = new LegacyMaximalIndependentSetsEnumerator<MinimalSeparator>(seperatorGraph, triExtender, scorer);
//                  if (heuristic == SEPARATORS){
//                      setsEnumerator = new LegacyMaximalIndependentSetsEnumerator<MinimalSeparator>(seperatorGraph, sepExtender, scorer);
//                  }
//              }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }



    }

    public boolean hasNext()
    {
        return setsEnumerator.hasNext();

    }

    public IChordalGraph next()
    {
        return Converter.minimalSeparatorsToTriangulation(graph, setsEnumerator.next());
    }


    public int getNumberOfMinimalSeperatorsGenerated()
    {
        return seperatorGraph.getNumberOfNodesGenerated();

    }





}
