import java.util.Arrays;

/*
 * This class encapsulates N words/strings so that the
 * group of N words can be treated as a key in a map or an
 * element in a set, or an item to be searched for in an array.
 * <P>
 * @author YOU,COMPSCI 201 STUDENT
 */

public class WordNgram implements Comparable<WordNgram>{

	private String[] myWords;

	/*
	 * Store the n words that begin at index start of array list as the N words
	 * of this N-gram.
	 * 
	 * @param list contains at least n words beginning at index start
	 * 
	 * @start is the first of the N words to be stored in this N-gram
	 * 
	 * @n is the number of words to be stored (the n in this N-gram)
	 */
	public WordNgram(String[] list, int start, int n) {
		myWords = new String[n];
		System.arraycopy(list, start, myWords, 0, n);
	}
	public WordNgram(String[] list){
		//I don't use this
	}
	public String toString(){
		//Returns the myWords array as a String
		String result = Arrays.toString(myWords);
		return result;
	}
	/**
	 * Return value that meets criteria of compareTo conventions.
	 * 
	 * @param wg
	 *            is the WordNgram to which this is compared
	 * @return appropriate value less than zero, zero, or greater than zero
	 */
	public int compareTo(WordNgram wg) {
		int i = 0;
		while(i<wg.myWords.length){
			if(this.myWords[i].equals(wg.myWords[i])){
				i++;
			}
			else{
				return (this.myWords[i].compareTo(wg.myWords[i]));
			}
		}
		return 0;
	}
		
	

	/**
	 * Return true if this N-gram is the same as the parameter: all words the
	 * same.
	 * 
	 * @param o
	 *            is the WordNgram to which this one is compared
	 * @return true if o is equal to this N-gram
	 */
	public boolean equals(Object o) {
		if (this == o){
			return true;
		}
		if (o==null || getClass() != o.getClass()){
			return false;
		}
		WordNgram wg = (WordNgram) o;
		for (int k =0; k < myWords.length; k++){
			if (!myWords[k].equals(wg.myWords[k])){
				return false;
			}
		}
		return true;

	}

	/**
	 * Returns a good value for this N-gram to be used in hashing.
	 * 
	 * @return value constructed from all N words in this N-gram
	 */
	public int hashCode() {
		//Uses squares of sums to generate hashcodes
		int sum = 0;
		for(int k=0; k<myWords.length; k++){
			sum = (sum*sum) + myWords[k].hashCode();
		}
		return sum;
		//return 15;
		
	}
	
	public String getLastWord(){
		//simply returns the last string in the myWords array (needed for 
		//updating the markov chain build)
		return myWords[myWords.length-1];
	}
}
