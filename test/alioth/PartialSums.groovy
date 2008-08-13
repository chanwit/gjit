package alioth

/**
 * @author chanwit
 *
 */
public class PartialSums{
    
    double a1
    double a2
    double a3
    double a4
    double a5
    double a6
    double a7
    double a8
    double a9

    def run(n) {
        a1 = a2 = a3 = a4 = a5 = a6 = a7 = a8 = a9 = 0.0D
        double twothirds = 2.0D/3.0D
        
        double alt = -1.0D
        double k = 1.0D

        while (k <= n) {
           double k2 = k * k
           double k3 = k2 * k
           double sk = Math.sin(k)
           double ck = Math.cos(k)
           alt = -alt

           a1 += twothirds**(k-1.0D)
           a2 += 1.0D/Math.sqrt(k)
           a3 += 1.0D/(k*(k+1.0D))
           a4 += 1.0D/(k3*sk*sk)
           a5 += 1.0D/(k3*ck*ck)
           a6 += 1.0D/k
           a7 += 1.0D/k2
           a8 += alt/k
           a9 += alt/(2.0D*k - 1.0D)

           k += 1.0D
        }
    }

//		printf("%.9f\t(2/3)^k\n", a1)
//		printf("%.9f\tk^-0.5\n", a2)
//		printf("%.9f\t1/k(k+1)\n", a3)
//		printf("%.9f\tFlint Hills\n", a4)
//		printf("%.9f\tCookson Hills\n", a5)
//		printf("%.9f\tHarmonic\n", a6)
//		printf("%.9f\tRiemann Zeta\n", a7)
//		printf("%.9f\tAlternating Harmonic\n", a8)
//		printf("%.9f\tGregory\n", a9)

	static main(args) {
		def n = Integer.parseInt(args[0])
		new PartialSums().run(n)
	}
	
}
