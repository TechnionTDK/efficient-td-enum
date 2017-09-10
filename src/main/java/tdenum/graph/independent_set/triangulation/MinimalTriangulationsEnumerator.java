package tdenum.graph.independent_set.triangulation;

import tdenum.single_thread_improvments.cachable.graphs.CachableSeparatorGraph;
import tdenum.graph.data_structures.MinimalSeparator;
import tdenum.graph.graphs.Graph;
import tdenum.graph.graphs.SeparatorGraph;
import tdenum.graph.graphs.interfaces.IChordalGraph;
import tdenum.graph.graphs.interfaces.IGraph;
import tdenum.graph.graphs.interfaces.ISeparatorGraph;
import tdenum.graph.independent_set.IndSetExtBySeparators;
import tdenum.graph.independent_set.IndSetExtByTriangulation;
import tdenum.graph.independent_set.MaximalIndependentSetsEnumerator;
import tdenum.graph.independent_set.separators.SeparatorsScoringCriterion;
import tdenum.graph.independent_set.Converter;
import tdenum.loggable.independent_set.LoggableIndSetExtBySeparators;
import tdenum.loggable.independent_set.LoggableIndSetExtByTriangulation;
import tdenum.loggable.independent_set.LoggableMaximalIndependentSetsEnumerator;
import tdenum.single_thread_improvments.graphs.independent_set.ImprovedMaximalIndependentSetsEnumerator;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static tdenum.graph.independent_set.triangulation.TriangulationAlgorithm.SEPARATORS;

/**
 * Created by dvir.dukhan on 7/11/2017.
 */
public class MinimalTriangulationsEnumerator {

    IGraph graph;
    ISeparatorGraph seperatorGraph;
    MinimalTriangulator triangulator;
    IndSetExtByTriangulation triExtender;
    IndSetExtBySeparators sepExtender;
    IndSetScorerByTriangulation scorer;
    MaximalIndependentSetsEnumerator<MinimalSeparator> setsEnumerator;

    public MinimalTriangulationsEnumerator(final IGraph g, TriangulationScoringCriterion triC,
                                           SeparatorsScoringCriterion sepC, TriangulationAlgorithm heuristic)
    {
        graph = new Graph(g);
        seperatorGraph = new SeparatorGraph(graph, sepC);
        triangulator = new MinimalTriangulator(heuristic);
        scorer = new IndSetScorerByTriangulation(graph, triC);


        Properties prop = new Properties();
        try (InputStream input = new FileInputStream("config.properties"))
        {
              prop.load(input);
              if (Boolean.valueOf(prop.getProperty("singleThreadImprovements")))
              {
                  System.out.println("using single thread improving mechanism");
                  if (Boolean.valueOf(prop.getProperty("cacheEdgesBetweenSeparators")))
                  {
                      seperatorGraph = new CachableSeparatorGraph(graph, sepC);
                  }
                  if (Boolean.valueOf(prop.getProperty("logging")))
                  {
                      triExtender = new LoggableIndSetExtByTriangulation(graph, triangulator);
                      sepExtender = new LoggableIndSetExtBySeparators(graph);
                      setsEnumerator = new ImprovedMaximalIndependentSetsEnumerator<MinimalSeparator>(seperatorGraph, triExtender, scorer);
                      if (heuristic == SEPARATORS){
                          setsEnumerator = new ImprovedMaximalIndependentSetsEnumerator<MinimalSeparator>(seperatorGraph, sepExtender, scorer);
                      }

                  }
                  else
                  {
                      triExtender = new IndSetExtByTriangulation(graph, triangulator);

                      sepExtender = new IndSetExtBySeparators(graph);
                      setsEnumerator = new ImprovedMaximalIndependentSetsEnumerator<MinimalSeparator>(seperatorGraph, triExtender, scorer);
                      if (heuristic == SEPARATORS){
                          setsEnumerator = new ImprovedMaximalIndependentSetsEnumerator<MinimalSeparator>(seperatorGraph, sepExtender, scorer);
                      }
                  }

              }
              else if (Boolean.valueOf(prop.getProperty("logging")))
              {
                  triExtender = new LoggableIndSetExtByTriangulation(graph, triangulator);
                  sepExtender = new LoggableIndSetExtBySeparators(graph);
                  setsEnumerator = new LoggableMaximalIndependentSetsEnumerator<MinimalSeparator>(seperatorGraph, triExtender, scorer);
                  if (heuristic == SEPARATORS){
                      setsEnumerator = new LoggableMaximalIndependentSetsEnumerator<MinimalSeparator>(seperatorGraph, sepExtender, scorer);
                  }

              }
              else
              {
                  triExtender = new IndSetExtByTriangulation(graph, triangulator);

                  sepExtender = new IndSetExtBySeparators(graph);
                  setsEnumerator = new MaximalIndependentSetsEnumerator<MinimalSeparator>(seperatorGraph, triExtender, scorer);
                  if (heuristic == SEPARATORS){
                      setsEnumerator = new MaximalIndependentSetsEnumerator<MinimalSeparator>(seperatorGraph, sepExtender, scorer);
                  }
              }

        } catch (IOException e) {
            e.printStackTrace();
        }



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
