/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ahecproject;

/**
 *
 * @author Dsiefus
 */
public class Optimiser extends Thread {
    
    public double r, t, theta;
    double drag, lift;
    public double bestr, bestt, besttheta, bestratio;

    public Optimiser() {        
        r = Math.random() * 9;
        t = Math.random() * 9;
        theta = Math.random() * 39 + 1.0;
        bestr=0;
        bestt=0;
        besttheta=0;
        bestratio=0;       
    }

    @Override
    public void run() {
        try {
            while (true) {                      
                drag= AHECProject.dragSolver.getResults(r, t, theta);
                lift= AHECProject.liftSolver.getResults(r, t, theta);
                if (drag == 0.0){ System.out.println("0!!");
                    drag= AHECProject.dragSolver.getResults(r, t, theta);}
                //System.out.println("drag=" + drag + ",lift=" + lift + ",ratio=" + (lift / drag));
                //best record: Best: r=7.890889320762843,t=5.299056644485266, theta=39.238508530409035, ratio=17.139604125757014
                if (lift / drag > bestratio){
                    bestratio = lift/drag;
                    bestr=r;
                    bestt=t;
                    besttheta=theta;   
                    System.out.println("Best: r="+AHECProject.optimiser.bestr+",t="+
                    AHECProject.optimiser.bestt+", theta="+AHECProject.optimiser.besttheta+
                    ", ratio="+AHECProject.optimiser.bestratio); 
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

    public double getLift() {
        return lift;
    }

    public double getDrag() {
        return drag;
    }
    
    
}
