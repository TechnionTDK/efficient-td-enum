package tdk_enum.solvers;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DflatSolverTest {

    @Test
    public void test1()
    {
        List<String> command = new ArrayList<>();
        command.add("./dflat/dflat");
        command.add("-p");
        command.add("./dflat/dynamic.lp");
        command.add("--graphml-in");
        command.add("./dflat/8.gml");
        command.add("-f");
        command.add("./dflat/instance_n20_p0.10_001.lp");
        command.add("--depth");
        command.add("0");
        command.add("--output");
        command.add("quiet");
        //"./dflat/dflat","-p", "./dflat/dynamic.lp",
        //                                                            "--graphml-in", "./dflat/1.gml",
        //                                                            "-f", "./dflat/instance_n20_p0.10_001.lp",
        //                                                            "--depth", "0"
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        try {
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream())) ;
            String line;
            while((line=reader.readLine())!=null){
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}