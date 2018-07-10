package tdk_enum.graph.independent_set.parallel.nested;

import tdk_enum.graph.independent_set.parallel.ParallelMaximalIndependentSetsEnumerator;

import java.util.HashSet;
import java.util.Set;

public abstract class AuxiliaryMaximalIndependentSetsEnumerator<T>  extends ParallelMaximalIndependentSetsEnumerator<T>
        implements  Runnable{



    long id;

    Set<T> extendingSet = new HashSet<>();

    TaskManager taskManager;

    Thread mainThread;

    public void setMainThread(Thread mainThread)
    {
        this.mainThread = mainThread;
    }

    protected abstract boolean finishCondition();

    protected abstract void algFinish();

    public void setTaskManager(TaskManager taskManager) {
        this.taskManager = taskManager;
    }

    @Override
    protected void runFullEnumeration() {


        //while (!taskManager.allDone)
        try
        {
            while (!finishCondition()) {
                while (!Q.isEmpty() && !timeLimitReached()) {
                    notifyWorking();
                    currentSet = Q.poll();
                    if(currentSet==null)
                    {
                        break;
                    }
                    extendingSet = V;
                    setsNotExtended.add(currentSet);
                    P.add(currentSet);
                    handleIterationNodePhase();
                }
                while (graph.hasNextNode(id) && !timeLimitReached()) {
                    notifyWorking();
                    Set<T> newNodes = graph.nextBatch(id);
                    V.addAll(newNodes);
                    extendingSet = newNodes;
                    handleIterationSetPhase();

                }
                algFinish();
            }
//            System.out.println(id + " has finished");
        }catch (Exception ex)
        {
            ex.printStackTrace();
        }


    }

    protected abstract void notifyWorking();


    @Override
    protected void handleIterationNodePhase() {

        for (T v : extendingSet) {

            if (timeLimitReached()) {
                return;
            }
            extendSetInDirectionOfNode(currentSet, v);
        }
    }


    @Override
    protected void handleIterationSetPhase() {
        for (Set<T> extendedMis : setsNotExtended) {

            if (timeLimitReached()) {
                return;
            }
            for (T v : extendingSet) {
                if (timeLimitReached()) {
                    return;
                }
                extendSetInDirectionOfNode(extendedMis, v);
            }

        }
    }

    @Override
    protected void parallelNewSetFound(final Set<T> generatedSet) {

        if (!P.contains(generatedSet)) {
            if (Q.add(generatedSet)) {
                resultPrinter.print(generatedSet);
            }
        }
    }

    @Override
    protected boolean timeLimitReached() {
        return Thread.currentThread().isInterrupted();
    }



    @Override
    public void run() {
        runFullEnumeration();

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


}