package tdk_enum.ml.solvers.execution;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author ABSEHER Michael (abseher@dbai.tuwien.ac.at)
 */
class StreamHandlerThread extends Thread {
    private MemoryFile output = null;
    private InputStream input = null;

    private long currentLineCount = 0;

    public StreamHandlerThread(InputStream input) {
        this.input = input;
        this.output = null;
    }

    public StreamHandlerThread(InputStream input, MemoryFile output) {
        this.input = input;
        this.output = output;
    }

    public long getCurrentLineCount() {
        return currentLineCount;
    }

    @Override
    public void run()
    {
        if (input != null) {
            try {
                InputStreamReader isr = new InputStreamReader(input);
                BufferedReader br = new BufferedReader(isr);

                if (output != null) {
                    String line = null;

                    while ((line = br.readLine()) != null) {
                        output.appendLine(line);

                        currentLineCount++;
                    }
                }
                else {
                    while (br.readLine() != null) {
                        currentLineCount++;
                    }
                }
            }
            catch (IOException ex) {

            }
        }
    }

    public void abort() {
        if (input != null) {
            try {
                input.close();
            }
            catch (IOException ex) {

            }
        }
    }
}