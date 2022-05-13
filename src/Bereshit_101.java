

/**
 * This class represents the basic flight controller of the Bereshit space craft.
 * @author ben-moshe
 *
 */
public class Bereshit_101 {
    public static final double WEIGHT_EMP = 165; // kg
    public static final double WEIGHT_FULE = 420; // kg
    public static final double WEIGHT_FULL = WEIGHT_EMP + WEIGHT_FULE; // kg
    // https://davidson.weizmann.ac.il/online/askexpert/%D7%90%D7%99%D7%9A-%D7%9E%D7%98%D7%99%D7%A1%D7%99%D7%9D-%D7%97%D7%9C%D7%9C%D7%99%D7%AA-%D7%9C%D7%99%D7%A8%D7%97
    public static final double MAIN_ENG_F = 430; // N
    public static final double SECOND_ENG_F = 25; // N
    public static final double MAIN_BURN = 0.15; //liter per sec, 12 liter per m'
    public static final double SECOND_BURN = 0.009; //liter per sec 0.6 liter per m'
    public static final double ALL_BURN = MAIN_BURN + 8*SECOND_BURN;

    public static double accMax(double weight) {
        return acc(weight, true,8);
    }
    public static double acc(double weight, boolean main, int seconds) {
        double t = 0;
        if(main) {t += MAIN_ENG_F;}
        t += seconds*SECOND_ENG_F;
        double ans = t/weight;
        return ans;
    }
    // 14095, 955.5, 24.8, 2.0
    public static void main(String[] args) {
        System.out.println("Simulating Bereshit's Landing:");
        // starting point:
        double vs = 24.8;
        double hs = 932;
        double dist = 181*1000;
        double ang = 58.3; // zero is vertical (as in landing)
        double alt = 13748; // 2:25:40 (as in the simulation) // https://www.youtube.com/watch?v=JJ0VfRL9AMs, height from the moon
        double time = 0;
        double dt = 1; // sec
        double acc=0; // Acceleration rate (m/s^2)
        double fuel = 121; //
        double weight = WEIGHT_EMP + fuel;
        System.out.println("time, vs, hs, fuel");
        double NN = 0.6; // rate[0,1]
        // ***** main simulation loop ******
        while(alt>0) {
            if(time%10==0 || alt<100) {
                System.out.println(vs+", "+hs+", "+ang+", "+fuel);
//                System.out.println(time+", "+vs+", "+hs+", "+dist+", "+alt+", "+ang+", "+weight+", "+acc+", "+fuel);

            }
            // over 2 km above the ground


            if(alt>2000) {	// maintain a vertical speed of [20-25] m/s
                if(vs >25) {
                    NN+=0.003*dt;
                } // more power for braking
                if(vs <15) {
                    NN-=0.003*dt;
                } // less power for braking
				if(alt>3500&&alt<6000){
                    ang=55;
                }
				if (alt>2000&&alt<3500){
                    ang=58.6;
                }
            }

            // lower than 2 km - horizontal speed should be close to zero
            else {
                if(ang>3) {ang-=3;} // rotate to vertical position.
                else {ang =0;}
                NN=0.4; // brake slowly, a proper PID controller here is needed!
                if(hs<2) {hs=0;}
                if(alt<125) { // very close to the ground!
                    vs=5;
                    NN=0.6; //
                     //
                }
            }
            if(alt<10) { // no need to stop
                NN=0.9;
            }
            // main computations
            double ang_rad = Math.toRadians(ang);
            double h_acc = Math.sin(ang_rad)*acc;
            double v_acc = Math.cos(ang_rad)*acc;
            double vacc = Moon.getAcc(hs);
            time+=dt;
            double dw = dt*ALL_BURN*NN;
            if(fuel>0) {
                fuel -= dw;
                weight = WEIGHT_EMP + fuel;
                acc = NN* accMax(weight);
            }
            else { // ran out of fuel
                acc=0;
            }

            v_acc -= vacc;
            if(hs>0) {hs -= h_acc*dt;}
            dist -= hs*dt;
            vs -= v_acc*dt;
            alt -= dt*vs;
        }
    }
}
