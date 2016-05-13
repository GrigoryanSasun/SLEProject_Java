/**
 * Created by Grigoryan on 09.04.2016.
 */
public class Startup {
    public static void main(String[] args) {
        ISolutionWriter solutionWriter = null;
        ISLEReader sleReader = null;
        for (int i = 0; i < args.length - 1; i += 2) {
            if (args[i].equals("--inputfile")) {
                sleReader = new FileSLEReader(args[i + 1]);
            } else if (args[i].equals("--outputfile")) {
                solutionWriter = new FileSolutionWriter(args[i + 1]);
            }
        }
        if (sleReader == null) {
            sleReader = new ConsoleSLEReader();
        }
        if (solutionWriter == null) {
            solutionWriter = new ConsoleSolutionWriter();
        }
        try {
            SLEContainer[] augmentedMatrixContainers = sleReader.ReadSLEAugmentedMatrices();
            SLESolver solver = new SLESolver(solutionWriter);
            for (SLEContainer container : augmentedMatrixContainers)
            {
                solver.SolveByGaussJordanElimination(container.GetSLE());
            }
        }
        catch (Throwable exc) {
            System.out.println(exc.getMessage());
        }
    }
}
