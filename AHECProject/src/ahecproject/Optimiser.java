package ahecproject;

/**
 * Workflow controller thread that is responsible for communication with the solvers.
 * Runs on its own thread in an infiniete loop.
 * @author Dsiefus
 */
public class Optimiser extends Thread {

    public double r, t, theta; // parameters
    double drag, lift; // results
    public double bestr, bestt, besttheta, bestratio, bestlift, bestdrag; // known best results

    /**
     * Default constructor for the class.
     * It initialises the parameters with a random vaule and
     * sets the actual best values to zero.
     */
    public Optimiser() {
        r = Math.random() * 9;
        t = Math.random() * 9;
        theta = Math.random() * 39 + 1.0;
        bestr = 0;
        bestt = 0;
        besttheta = 0;
        bestratio = 0;
        bestlift = 0;
        bestdrag = 0;
    }

    /**
     * Method that contains the main execution loop.
     * The loop runs infinitely if nothing interrupt it.
     * At every step it gets the results from the solvers,
     * updates its variables and then waits for a bit.
     */
    @Override
    public void run() {
        try {
            while (true) {
                drag = AHECProject.dragSolver.getResults(r, t, theta);
                lift = AHECProject.liftSolver.getResults(r, t, theta);
                if (drag == 0.0) {
                    System.out.println("0!!");
                    drag = AHECProject.dragSolver.getResults(r, t, theta);
                }
                //System.out.println("drag=" + drag + ",lift=" + lift + ",ratio=" + (lift / drag));
                //best record: Best: r=7.890889320762843,t=5.299056644485266, theta=39.238508530409035, ratio=17.139604125757014
                if (lift / drag > bestratio) {
                    bestratio = lift / drag;
                    bestlift = lift;
                    bestdrag = drag;
                    bestr = r;
                    bestt = t;
                    besttheta = theta;
                    System.out.println("Best: r=" + AHECProject.optimiser.bestr + ",t="
                            + AHECProject.optimiser.bestt + ", theta=" + AHECProject.optimiser.besttheta
                            + ", ratio=" + AHECProject.optimiser.bestratio);
                }
                r = (r + Math.random() * 9) % 9.0;
                t = (t + Math.random() * 9) % 9.0;
                theta = ((r + Math.random() * 39) % 39.0) + 1.0;
                Thread.sleep(300);
            }
        } catch (InterruptedException ex) {
            System.out.println("Optimiser interrupted!");
        }

    }

    /**
     * Public getter function for lift.
     * @return the current value of lift
     * @see #lift
     */
    public double getLift() {
        return lift;
    }

    /**
     * Public getter function for drag.
     * @return the current value of drag
     * @see #drag
     */
    public double getDrag() {
        return drag;
    }
}
