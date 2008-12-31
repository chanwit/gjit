package valuepath;

public class ValuePathTest {
	
	public static void main(String[] args) {
		// use = <s, {v1(s0), v2(s1), v3(s2)}>; data used at s, produced by s0, s1, s2
		// s0: LDC 20         ;  use = <s0, {v1(null)}>
		// s1: LDC 10         ;  use = <s1, {v1(null)}>
		// s2: invoke box(int);  use = <s2, {v1(s1)}> ; path<s2, {null,s1,s2}>
		// s2_1: LDC 30       ;  use = <s2_1, {v1{null}}>
		// s2_2: ADD          ;  
		// s3: invoke call(int,Object)  ;  use = <s3, {v1(s0),v2(s2)}> ; path<s3,{}
		// PATH = 
		// s4: invoke println ;  use = <s4, {v1(s3)}>
		// if s2 was deleted, then all value produced by s2 (v?(s2)) must be subst with v?(
		
		
		
		
		// hard case: if s3 was deleted, then 		
	}
	
}
