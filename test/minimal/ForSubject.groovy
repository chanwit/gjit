/**
 *
 */
package minimal

/**
 * @author chanwit
 *
 */
public class ForSubject{

    int add_001() {
        int n = 10
        for(i in 0..<n) {
            println i
        }
    }

    int add_002() {
        int n = 10
        while(true) {
            for(i in 0..<n) {
                println i
            }
            int i = 0
            break
        }
    }

    int add_003() {
        int n = 10
        int[] data = new int[n]
        data[0] = 0
        for(i in 1..<n) {
            data[i] = data[i-1]+1
        }
    }

    int add_004() {
        int n = 10
        int[] data = new int[n]
        int[] data2 = new int[n]
        data[0] = 0
        for(i in 1..<n) {
            data[i] = data2[i]
        }
    }

}
