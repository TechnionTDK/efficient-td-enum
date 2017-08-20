package tdenum.common.IO;

import org.apache.commons.io.FilenameUtils;
import tdenum.graph.data_structures.Node;
import tdenum.graph.data_structures.NodeSet;
import tdenum.graph.graphs.Graph;
import tdenum.graph.graphs.interfaces.IGraph;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class GraphReader {

    public static IGraph read(final String fileName)
    {
        File input = Paths.get(fileName).toFile();
        String extension = FilenameUtils.getExtension(fileName);

        switch (extension)
        {
            case "hg":
            {
                return readCliques(fileName);

            }
            case "sp":
            {
                return readCliques(fileName);
            }
            case "wcnf":
            {
                return readCnf(fileName);
            }
            case "uai":
            {
                return readAUI(fileName);
            }
            case "csv":
            {
                return readCSV(fileName);
            }
            case "bliss":
            {
                return readBliss(fileName);
            }
            default:
            {
                System.out.println("Unrecognized file extension");
            }
        }
        return new Graph();
    }

    static IGraph readCliques(String fileName)
    {
        IGraph g = new Graph();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

            String line = br.readLine();

            Scanner scanner = new Scanner(line);
            int numberOfNodes = scanner.nextInt();
            int numberOfClicks = scanner.nextInt();

            g = new Graph(numberOfNodes);

            for (int i = 0; i < numberOfClicks; i++)
            {
                line = br.readLine();
                scanner = new Scanner(line);
                NodeSet clique = new NodeSet();
                while(scanner.hasNextInt())
                {
                    clique.add(new Node(scanner.nextInt()));
                }
                g.addClique(clique);

            }

        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return g;
    }

    static IGraph readCnf(String fileName)
    {
        IGraph g = new Graph();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName)))
        {
            String line = br.readLine();
            while (line.startsWith("c"))
            {
                line = br.readLine();
            }
            boolean isWeighted = false;

            if (line.contains("p cnf"))
            {
                isWeighted = false;
            }
            else if (line.contains("p wcnf"))
            {
                isWeighted = true;
            }
            else
            {
                System.out.println("Parsing Error");
                return g;
            }
            Scanner scanner = new Scanner(line);
            int numberOfNodes = scanner.nextInt();
            int numberOfCliques = scanner.nextInt();
            g = new Graph(numberOfNodes);
            for (int i =0; i < numberOfCliques; i++)
            {
                line = br.readLine();
                scanner = new Scanner(line);
                if (isWeighted)
                {
                    scanner.nextInt();
                }
                NodeSet clique = new NodeSet();
                while(scanner.hasNextInt())
                {
                    int v = scanner.nextInt();
                    if (v == 0)
                    {
                        break;
                    }
                    v = v > 0 ? v : -v;
                    v = v == numberOfNodes ? 0 : v;
                    clique.add(new Node(v));
                }
                g.addClique(clique);
            }

        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return g;
    }

    static IGraph readAUI(String fileName)
    {
        IGraph g = new Graph();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName)))
        {
            br.readLine();
            String line = br.readLine();
            Scanner scanner = new Scanner(line);
            int numberOfNodes = scanner.nextInt();
            br.readLine();
            line = br.readLine();
            scanner = new Scanner(line);
            int numberOfCliques = scanner.nextInt();
            g = new Graph(numberOfNodes);
            for (int i = 0 ; i < numberOfCliques; i++)
            {
                line = br.readLine();
                scanner = new Scanner(line);
                scanner.nextInt();
                NodeSet clique = new NodeSet();
                while(scanner.hasNextInt())
                {
                    clique.add(new Node(scanner.nextInt()));
                }
                g.addClique(clique);
            }
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return g;
    }

    static IGraph readCSV(String fileName)
    {
        HashMap<String, Node> nodeNames = new HashMap<>();
        ArrayList<NodeSet> edges = new ArrayList<>();
        IGraph g = new Graph();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName)))
        {
            String line = br.readLine();
            while(line != null)
            {
               String[] nodes = line.split(",");
               if (nodes[nodes.length-1].endsWith("\r"))
               {
                   nodes[nodes.length-1] = nodes[nodes.length-1].replace("\r","");
               }
               NodeSet edge = new NodeSet();
               for (String nodeName : nodes)
               {
                   if(!nodeNames.containsKey(nodeName))
                   {
                       Node node = new Node(nodeNames.size());
                       nodeNames.put(nodeName,node);
                   }
                   edge.add(nodeNames.get(nodeName));

               }
               edges.add(edge);
               line = br.readLine();
            }
            g = new Graph(nodeNames.size());
            for(NodeSet clique: edges)
            {
                g.addClique(clique);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return g;
    }


    static IGraph readBliss(String fileName)
    {
        IGraph g = new Graph();


        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

            String line = br.readLine();
            while(line.startsWith("c"))
            {
                line = br.readLine();
            }

            Scanner scanner = new Scanner(line);
            while(!scanner.hasNextInt())
            {
                scanner.next();
            }
            int numberOfNodes = scanner.nextInt();
            int numberOfEdges = scanner.nextInt();
            g = new Graph(numberOfNodes);
            for (int i = 0; i < numberOfEdges; i++)
            {
                line = br.readLine();
                scanner = new Scanner(line);
                NodeSet clique = new NodeSet();
                while(!scanner.hasNextInt())
                {
                    scanner.next();
                }
                while(scanner.hasNextInt())
                {
                    clique.add(new Node(scanner.nextInt()-1));
                }
                g.addClique(clique);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return g;
    }

}
